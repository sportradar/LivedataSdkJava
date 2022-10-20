package com.sportradar.sdk.common.classes;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.util.CachingDateFormatter;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link InputStream} implementation which, logs data passed through the stream.
 */
public class LoggingInputStream extends InputStream {

    /**
     * The {@link Logger} implementation used for logging the data passed through the stream.
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggingInputStream.class);

    /**
     * The {@link ch.qos.logback.core.util.CachingDateFormatter} used to format date.
     */
    private static CachingDateFormatter cachingDateFormatter = new CachingDateFormatter(CoreConstants.ISO8601_PATTERN);

    /**
     * The actual {@link InputStream}
     */
    private final InputStream actualStream;

    /**
     * The size (in characters) of the {@link #streamBuffer} buffer for storing chunk of characters
     */
    private final int bufferSize;

    /**
     * The {@link StringBuffer} instance used for storing stream data
     */
    private final StringBuffer streamBuffer;

    /**
     * The {@link SdkLogger} instance used for logging
     */
    private final SdkLogger sdkLogger;

    /**
     * The index of the character to be read from the {@link #actualStream}
     */
    private long streamPosition;

    /**
     * The index of the log position.
     */
    private long logStreamPosition;

    /**
     * The flag that indicate if {@link #actualStream} stream is closed.
     */
    private boolean closed = false;

    /**
     * Flag that indicate if read {@link #actualStream} to the end of the stream
     * when call {@link #close()} method.
     */
    private final boolean readOnClose;

    /**
     * Flag that indicate if eof message will be written to log.
     */
    private boolean writeEofMessage = true;

    /**
     * Flag that indicate if header is written to log.
     */
    private boolean isHeaderWritten = false;

    /**
     * Initializes a new instance of the {@link LoggingInputStream} class
     * with specified input stream, logger and buffer size.
     *
     * @param actualStream A {@link InputStream} instance to be wrapped by the constructed instance.
     * @param sdkLogger    The {@link SdkLogger} implementation used for actual logging.
     * @param bufferSize   The size (in characters) of the {@link #streamBuffer} buffer.
     * @param readOnClose  Read the {@link #actualStream} to the end of the stream when call {@link #close()} method.
     * @throws IllegalArgumentException The {@code actualStream} is a null reference,
     *                                  or {@code logger} is a null reference.
     */
    public LoggingInputStream(InputStream actualStream, SdkLogger sdkLogger, int bufferSize, boolean readOnClose) {
        checkNotNull(logger, "logger cannot be a null reference");

        this.actualStream = actualStream;
        this.sdkLogger = sdkLogger;
        this.bufferSize = bufferSize;
        this.streamPosition = 0;
        this.logStreamPosition = 0;
        this.streamBuffer = new StringBuffer(bufferSize);
        this.readOnClose = readOnClose;
    }

    /**
     * <p> Reads the next byte of data from the input stream.
     * If the byte value is -1 or size of the buffer is equal to {@link #bufferSize}
     * then buffer {@link #streamBuffer} will be written to {@link #sdkLogger}.
     * The value byte is returned as an {@code int} in the range {@code 0} to
     * {@code 255}. If no byte is available because the end of the stream
     * has been reached, the value {@code -1} is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     * </p>
     *
     * @return the next byte of data, or {@code -1} if the end of the
     * stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public int read() throws IOException {
        int data = actualStream.read();

        if (!isHeaderWritten && data != -1) {
            String now = getCurrentDateTime();
            sdkLogger.logTraffic(now + System.lineSeparator());
            isHeaderWritten = true;
        }

        if (data == -1) {
            onClose();
            writeEofMessage = false;
            return -1;
        }
        if (logStreamPosition < ++streamPosition) {
            ++logStreamPosition;
            if (streamBuffer.length() >= bufferSize) {
                flushBuffer();
            }
            streamBuffer.append((char) data);
        }
        return data;
    }

    /**
     * <p> Skips over and discards {@code n} bytes of data from this input
     * stream. The {@code skip} method may, for a variety of reasons, end
     * up skipping over some smaller number of bytes, possibly {@code 0}.
     * This may result from any of a number of conditions; reaching end of file
     * before {@code n} bytes have been skipped is only one possibility.
     * The actual number of bytes skipped is returned.  If {@code n} is
     * negative, no bytes are skipped.
     * </p>
     * <p> The {@code skip} method of this class creates a
     * byte array and then repeatedly reads into it until {@code n} bytes
     * have been read or the end of the stream has been reached. Subclasses are
     * encouraged to provide a more efficient implementation of this method.
     * For instance, the implementation may depend on the ability to seek.
     * </p>
     *
     * @param n the number of bytes to be skipped.
     * @return the actual number of bytes skipped.
     * @throws IOException if the stream does not support seek,
     *                             or if some other I/O error occurs.
     */
    @Override
    public long skip(long n) throws IOException {
        return actualStream.skip(n);
    }

    /**
     * <p> Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation
     * might be the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     * </p>
     * <p> Note that while some implementations of {@code InputStream} will return
     * the total number of bytes in the stream, many will not.  It is
     * never correct to use the return value of this method to allocate
     * a buffer intended to hold all data in this stream.
     * </p>
     * <p> A subclass' implementation of this method may choose to throw an
     * {@link IOException} if this input stream has been closed by
     * invoking the {@link #close()} method.
     * </p>
     * <p> The {@code available} method for class {@code InputStream} always
     * returns {@code 0}.
     * </p>
     *
     * @return an estimate of the number of bytes that can be read (or skipped
     * over) from this input stream without blocking or {@code 0} when
     * it reaches the end of the input stream.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public int available() throws IOException {
        return actualStream.available();
    }

    /**
     * <p> Flush {@link #streamBuffer} buffer, closes this input stream and releases
     * any system resources associated with the stream.
     * </p>
     * <p> The {@code close} method of {@code InputStream} does
     * nothing.
     * </p>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        isHeaderWritten = false;
        if (closed) {
            return;
        }
        closed = true;
        if (readOnClose) {
            readToEOF();
        }
        onClose();
        actualStream.close();

    }

    /**
     * <p> Marks the current position in this input stream. A subsequent call to
     * the {@code reset} method repositions this stream at the last marked
     * position so that subsequent reads re-read the same bytes.
     * </p>
     * <p> The {@code readlimit} arguments tells this input stream to
     * allow that many bytes to be read before the mark position gets
     * invalidated.
     * </p>
     * <p> The general contract of {@code mark} is that, if the method
     * {@code markSupported} returns {@code true}, the stream somehow
     * remembers all the bytes read after the call to {@code mark} and
     * stands ready to supply those same bytes again if and whenever the method
     * {@code reset} is called.  However, the stream is not required to
     * remember any data at all if more than {@code readlimit} bytes are
     * read from the stream before {@code reset} is called.
     * </p>
     * <p> Marking a closed stream should not have any effect on the stream.
     * </p>
     * <p> The {@code mark} method of {@code InputStream} does
     * nothing.
     * </p>
     *
     * @param readlimit the maximum limit of bytes that can be read before
     *                  the mark position becomes invalid.
     * @see InputStream#reset()
     */
    @Override
    public synchronized void mark(int readlimit) {
        actualStream.mark(readlimit);
    }

    /**
     * <p> Repositions this stream to the position at the time the
     * {@code mark} method was last called on this input stream.
     * </p>
     * <p> The general contract of {@code reset} is:
     * </p>
     * <ul>
     * <li> If the method {@code markSupported} returns
     * {@code true}, then:
     * <ul><li> If the method {@code mark} has not been called since
     * the stream was created, or the number of bytes read from the stream
     * since {@code mark} was last called is larger than the argument
     * to {@code mark} at that last call, then an
     * {@code IOException} might be thrown.
     * <li> If such an {@code IOException} is not thrown, then the
     * stream is reset to a state such that all the bytes read since the
     * most recent call to {@code mark} (or since the start of the
     * file, if {@code mark} has not been called) will be resupplied
     * to subsequent callers of the {@code read} method, followed by
     * any bytes that otherwise would have been the next input data as of
     * the time of the call to {@code reset}. </ul>
     * <li> If the method {@code markSupported} returns
     * {@code false}, then:
     * <ul><li> The call to {@code reset} may throw an
     * {@code IOException}.
     * <li> If an {@code IOException} is not thrown, then the stream
     * is reset to a fixed state that depends on the particular type of the
     * input stream and how it was created. The bytes that will be supplied
     * to subsequent callers of the {@code read} method depend on the
     * particular type of the input stream. </ul></ul>
     * <p> The method {@code reset} for class {@code InputStream}
     * does nothing except throw an {@code IOException}.
     * </p>
     *
     * @throws IOException if this stream has not been marked or if the
     *                             mark has been invalidated.
     * @see InputStream#mark(int)
     * @see IOException
     */
    @Override
    public synchronized void reset() throws IOException {
        streamPosition = 0;
        actualStream.reset();
    }

    /**
     * Tests if this input stream supports the {@code mark} and
     * {@code reset} methods. Whether or not {@code mark} and
     * {@code reset} are supported is an invariant property of a
     * particular input stream instance. The {@code markSupported} method
     * of {@code InputStream} returns {@code false}.
     *
     * @return {@code true} if this stream instance supports the mark
     * and reset methods; {@code false} otherwise.
     * @see InputStream#mark(int)
     * @see InputStream#reset()
     */
    @Override
    public boolean markSupported() {
        return actualStream.markSupported();
    }

    /**
     * Read to the end of {@link #actualStream} stream and write {@link #actualStream} stream to log.
     */
    public void readToEOF() {
        int data;
        try {
            do {
                data = read();
            } while (data != -1);
        } catch (IOException e) {
            logger.error("Error while reading input stream. exception ", e);
        }
    }

    private void flushBuffer() {
        if (streamBuffer.length() == 0) {
            return;
        }
        sdkLogger.logTraffic(streamBuffer.toString());
        streamBuffer.setLength(0);
    }

    private void onClose() {
        flushBuffer();
        if (writeEofMessage) {
            sdkLogger.logTraffic(System.lineSeparator());
        }
    }

    private String getCurrentDateTime() {
        return cachingDateFormatter.format(System.currentTimeMillis());
    }
}
