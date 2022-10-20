/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a tokenizer where data arrives in chunks.
 * Contains logic for low-level byte splitting.
 *
 * @author uros.bregar
 */
public class IncrementalMessageTokenizer extends MessageTokenizerBase {

    /**
     * The name of the used encoding
     */
    public static final Charset ENCODING = Charset.forName("UTF-8");
    private static final Logger logger = LoggerFactory.getLogger(IncrementalMessageTokenizer.class);
    /**
     * Byte sequence which indicate the of the element
     */
    private static final byte[] NORMAL_ELEMENT_END_SUFFIX = new byte[]{'>', 0x0D, 0x0A, 0x0D, 0x0A};
    /**
     * Byte sequence which indicate the end of the element - shorten format.
     */
    private static final byte[] SHORTEN_ELEMENT_END = new byte[]{'/', '>', 0x0D, 0x0A, 0x0D, 0x0A};
    /**
     * A {@link SdkLogger} implementation used to log traffic.
     */
    private final SdkLogger sdkLogger;
    /**
     * The buffer used to temporarily store the un-processed data.
     */
    private final byte[] buffer;
    /**
     * The buffer index
     */
    private int bufferIndex = 0;

    /**
     * Initializes a new instance of the {@link IncrementalMessageTokenizer} class.
     *
     * @param sdkLogger  A {@link SdkLogger} implementation used to log traffic.
     * @param bufferSize the size of the internal buffer used to store incomplete messages
     * @throws IllegalArgumentException the {@code bufferSize} is smaller than or equal to 0.
     */
    public IncrementalMessageTokenizer(SdkLogger sdkLogger, int bufferSize) {
        checkNotNull(sdkLogger, "sdkLogger cannot be a null reference");
        checkArgument(bufferSize > 0, "The maxBufferSize must be greater than zero");

        this.sdkLogger = sdkLogger;
        this.buffer = new byte[bufferSize];
    }

    /**
     * Clears and temporary data stored in the tokenizer.
     */
    @Override
    public void clear() {
        Arrays.fill(buffer, (byte) 0);
        bufferIndex = 0;
    }

    /**
     * Processes passed data. If passed data and any previously stored data represent a whole message the {@code MessageTokenizerListener.onMessageReady(byte[])} method is invoked
     *
     * @param stream A {@link InputStream} containing data to be tokenized.
     * @throws IllegalArgumentException                          the {@code stream} is a null reference.
     * @throws ProtocolException the data could not be processed either because the buffer is full
     */
    @Override
    public void processData(InputStream stream) throws SdkException {
        checkNotNull(stream != null, "stream cannot be a null reference.");

        byte[] data = null;

        try {
            data = IOUtils.toByteArray(stream);
        } catch (IOException e) {
            throw new MessageException("An exception was thrown while reading input stream", e);
        }

        int offset = 0;

        // Copy the data to the internal buffer. If there was not enough free space in the buffer then return.
        if (!copyToBuffer(data)) {
            logger.error("The received message is too big for the reserved buffer. Increase the buffer size in the sdk settings");
            return;
        }
        try {
            // Perform the following steps in the loop to ensure all the messages in the buffer are processed.
            while (true) {
                ElementInfo elementInfo = findElementInfo(buffer, offset, bufferIndex);
                if (elementInfo == null) {
                    break;
                }

                int endIndex = findElementEnd(
                        buffer,
                        elementInfo.getElementName(),
                        elementInfo.getStartIndex() + elementInfo.getElementName().length,
                        bufferIndex);
                if (endIndex == -1) {
                    break;
                }
                byte[] messageData = new byte[endIndex - elementInfo.getStartIndex() + 1];
                System.arraycopy(buffer, elementInfo.getStartIndex(), messageData, 0, messageData.length);

                String messageString = new String(messageData, ENCODING);
                sdkLogger.logTraffic(false, messageString);
                offset = endIndex;
                notifyListener(new ByteArrayInputStream(messageData));
                offset = endIndex + 5;
            }

        } finally {
            // If any message was processed move the remaining data in the buffer to the start of the buffer.
            if (offset != 0) {
                compactBuffer(offset);
            }
        }
    }

    /**
     * Finds the index of the first occurrence of bytes representing the end of the element in the {@code source}.
     * The end of the element can be specified in a normal format '</elementName>' or the shorten one (/>) but must be
     * followed by double LF/CR combination. The search starts at the location specified by the {@code offset}.
     * The returned index points to the '>' character.
     *
     * @param source      the array to be searched
     * @param elementName the byte array representation of the element's name
     * @param offset      offset the index specifying where in the source array to begin the search.
     * @param maxIndex    specifies the maximum {@code source} index to be included in the search.
     * @return The index of the first occurrence or -1 if the {@code MESSAGE_END} does not exist in the {@code source}.
     */
    protected int findElementEnd(final byte[] source, final byte[] elementName, final int offset, final int maxIndex) {
        byte[] normalElementEnd = new byte[elementName.length + NORMAL_ELEMENT_END_SUFFIX.length];
        System.arraycopy(elementName, 0, normalElementEnd, 0, elementName.length);
        System.arraycopy(NORMAL_ELEMENT_END_SUFFIX, 0, normalElementEnd, elementName.length, NORMAL_ELEMENT_END_SUFFIX.length);
        int shortenEndIndex = indexOf(source, SHORTEN_ELEMENT_END, offset, maxIndex);
        int normalEndIndex = indexOf(source, normalElementEnd, offset, maxIndex);

        shortenEndIndex = shortenEndIndex == -1
                ? -1
                : shortenEndIndex + 1;

        normalEndIndex = normalEndIndex == -1
                ? -1
                : normalEndIndex + elementName.length;

        return selectIndex(shortenEndIndex, normalEndIndex);
    }

    /**
     * Moves the un-processed data in the buffer to the beginning of the buffer.
     *
     * @param offset the number of the processed bytes.
     */
    private void compactBuffer(final int offset) {
        if (offset < this.bufferIndex) {
            System.arraycopy(buffer, offset, buffer, 0, bufferIndex - offset);
        }
        bufferIndex = bufferIndex - offset;
    }

    /**
     * If the internally used buffer has enough free space, it copies the content of the {@code data} to it.
     * Otherwise it purges the buffer and discards the data.
     *
     * @param data The data to be copied to the buffer.
     * @return True if the internally used buffer had enough free space and the data was copied to it. Otherwise false.
     */
    private boolean copyToBuffer(final byte[] data) {
        if (bufferIndex < 0) {
            bufferIndex = 0;
        }
        if (bufferIndex + data.length > buffer.length) {
            sdkLogger.logInvalidMessage(Level.ERROR, String.format(
                    "Discarding message as is too long. Increase buffer size. Received msg: %s",
                    (new String(buffer).trim() + new String(data).trim())));

            clear();
            return false;
        }
        System.arraycopy(data, 0, buffer, bufferIndex, data.length);
        bufferIndex = bufferIndex + data.length;
        return true;
    }

    /**
     * returns the {@link ElementInfo} representing the first xml element in the {@code source}. The searching for
     * the first element starts at the position specified by the {@code offset}
     * </p>
     * <p>
     * The method finds the index of the first '<' character, the index of the first '>' character and the index
     * of the ' ' character. End of the message header is considered to be the lower of the last two indexes.
     * Every byte between the start index and the end index is considered to be part of the element's name.
     * </p>
     *
     * @param source   the source of the search
     * @param offset   specifies the start position of the search.
     * @param maxIndex specifies the maximum {@code source} index to be included in the search.
     * @return the {@link ElementInfo} representing the first xml element in the {@code source}, or a null reference if no elements were found
     */
    private ElementInfo findElementInfo(final byte[] source, final int offset, final int maxIndex) {
        int startIndex = indexOf(source, new byte[]{'<'}, offset, maxIndex);
        if (startIndex == -1) {
            return null;
        }

        if (startIndex > offset + 1) {
            String elementStr = source == null || source.length == 0 ? "" : new String(source, StandardCharsets.UTF_8);
            elementStr = elementStr.replace("\0", "");
            sdkLogger.logInvalidMessage(Level.WARN, String.format("Discarded data before '<' character: '%s'. Source: %s",
                    new String(Arrays.copyOfRange(source, offset, startIndex)),
                                                                  elementStr));
        }

        int greaterThenIndex = indexOf(source, new byte[]{'>'}, startIndex + 1, maxIndex);
        int spaceIndex = indexOf(source, new byte[]{' '}, startIndex + 1, maxIndex);

        int endIndex = selectIndex(greaterThenIndex, spaceIndex);

        if (endIndex == -1) {
            return null;
        }

        byte[] nameArray = new byte[endIndex - startIndex - 1];
        System.arraycopy(source, startIndex + 1, nameArray, 0, nameArray.length);
        return new ElementInfo(startIndex, nameArray);
    }

    /**
     * Finds the index of the first occurrence of bytes specified by the {@code pattern} in {@code source}.
     * The search starts at the location specified by the {@code offset}. The returned index points to the first byte of the searched sequence.
     *
     * @param source   the source of the search
     * @param pattern  the bytes to be found in the source.
     * @param offset   the index specifying where in the source array to begin the search.
     * @param maxIndex specifies the maximum {@code source} index to be included in the search.
     * @return The index of the first occurrence or -1 if the {@code pattern} does not exist in the {@code source}.
     */
    protected static int indexOf(final byte[] source, final byte[] pattern, final int offset, final int maxIndex) {
        int lastPIndex = pattern.length - 1;

        int mid = pattern.length / 2 + pattern.length % 2;

        boolean found;
        for (int i = offset; i < maxIndex - lastPIndex; i++) {
            if (source[i] == pattern[0] && source[i + lastPIndex] == pattern[lastPIndex]) {
                found = true;
                for (int j = 1; j < mid - 1; j++) {
                    if (source[i + j] != pattern[j] || source[i + lastPIndex - j] != pattern[lastPIndex - j]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * returns the index which is smaller but is not equal to -1. If both indexes are equal to -1, than -1 is returned.
     *
     * @param index1 the first index.
     * @param index2 the second index
     * @return the index which is smaller but is not equal to -1. If both indexes are equal to -1, than -1 is returned.
     */
    protected static int selectIndex(final int index1, final int index2) {
        int endIndex;
        if (index1 == -1) {
            endIndex = index2;
        } else if (index2 == -1) {
            endIndex = index1;
        } else {
            endIndex = index1 < index2
                    ? index1
                    : index2;
        }
        return endIndex;
    }

    /**
     * A class holding information about the serialized xml root element
     */
    private static class ElementInfo {

        /**
         * the position in the buffer where the element starts.
         */
        private final int startIndex;

        /**
         * the byte array representation of the element's name
         */
        private final byte[] elementName;

        /**
         * Initializes a new instance of the {@link ElementInfo} class.
         *
         * @param startIndex  the position in the buffer where the element starts.
         * @param elementName the byte array representation of the element's name
         */
        public ElementInfo(int startIndex, byte[] elementName) {
            this.startIndex = startIndex;
            this.elementName = elementName;
        }

        /**
         * Get the byte array representation of the element's name.
         *
         * @return the byte array representation of the element's name.
         */
        public byte[] getElementName() {
            return this.elementName;
        }

        /**
         * Gets the element's name.
         *
         * @return the element's name.
         */
        public String getElementNameAsString() {
            return new String(this.elementName);
        }

        /**
         * Gets the position in the buffer where the element starts
         *
         * @return the position in the buffer where the element starts
         */
        public int getStartIndex() {
            return this.startIndex;
        }
    }
}
