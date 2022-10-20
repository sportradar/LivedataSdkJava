package com.sportradar.sdk.test.common;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sportradar.sdk.common.classes.*;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LoggerSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executors;

import static com.sportradar.sdk.test.common.FileSdkLoggerTestBase.getLogger;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LoggingInputStreamTest {

    private LoggingInputStream loggingInputStream;
    private InputStream inputStream;
    private Logger trafficLogger;
    private DummyLBAppender listAppender;
    private LoggerContext loggerContext;
    private static final String HEADER = "date";
    private final static String NEWLINE = System.lineSeparator();
    private final int bufferSize = 1024;
    private SdkLogger sdkLogger;
    LoggerSettings loggerSettings;
    private Timer timer;
    private final String MARKER_NAME = "LoggingTest";
    private boolean ignoreHeader;

    public LoggingInputStreamTest() {
    }

    @Before
    public void setUp() throws Exception {
        loggerSettings = DefaultSettingsBuilderHelper.getLiveScout().getLoggerSettingsBuilder().build();
        timer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        sdkLogger = new FileSdkLogger(loggerSettings, timer, MARKER_NAME);

        listAppender = new DummyLBAppender();
        trafficLogger = getLogger(sdkLogger, FileSdkLogger.ROOT_NS + ".logger." + MARKER_NAME + ".Traffic");
        int maxFileSize = 1024*1024*10;
        trafficLogger.addAppender(SdkLoggerCfg.createAppender(SdkLogAppenderType.TRAFFIC,
                loggerSettings.getLogPath(),
                loggerSettings.getExecutionLogLevel(), "%msg", true, MARKER_NAME, maxFileSize));
        loggerContext = trafficLogger.getLoggerContext();
        listAppender.setContext(loggerContext);
        trafficLogger.addAppender(listAppender);
        listAppender.start();
        ignoreHeader = true;
    }

    @After
    public void tearDown() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmptyMessage() throws Exception {
        ignoreHeader = false;
        final String SHORT_MESSAGE = "";
        initInputStream(SHORT_MESSAGE, bufferSize, true);
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{NEWLINE});
    }

    @Test
    public void testShortMessage() throws Exception {
        final String SHORT_MESSAGE = "test string";
        initInputStream(SHORT_MESSAGE, bufferSize, true);
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE, NEWLINE});
    }

    @Test
    public void testResetMethod() throws Exception {
        final String SHORT_MESSAGE = "test string 123456";
        initInputStream(SHORT_MESSAGE, bufferSize, true);
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE, NEWLINE});
        loggingInputStream.reset();
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE, NEWLINE});
    }

    @Test
    public void testResetMethod2() throws Exception {
        final String SHORT_MESSAGE = "test string 1234567";
        final int newBufferSize = SHORT_MESSAGE.length() / 3;
        initInputStream(SHORT_MESSAGE, newBufferSize, true);
        final int j = newBufferSize + 1;
        for (int i = 0; i < j; ++i) {
            loggingInputStream.read();
        }
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE.substring(0, j - 1)});
        loggingInputStream.reset();
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER,
                        SHORT_MESSAGE.substring(0, j - 1),
                        SHORT_MESSAGE.substring(j - 1, 2 * (j - 1)),
                        SHORT_MESSAGE.substring(2 * (j - 1), 3 * (j - 1)),
                        SHORT_MESSAGE.substring(3 * (j - 1)),
                        NEWLINE});
    }

    @Test
    public void testResetMethod3() throws Exception {
        final String SHORT_MESSAGE = "test string 123456";
        final int newBufferSize = SHORT_MESSAGE.length() / 3;
        initInputStream(SHORT_MESSAGE, newBufferSize, true);
        final int j = newBufferSize + 1;
        for (int i = 0; i < j; ++i) {
            loggingInputStream.read();
        }
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE.substring(0, j - 1)});
        loggingInputStream.reset();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE.substring(0, j - 1)});

        for (int i = 0; i < j; ++i) {
            loggingInputStream.read();
        }
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE.substring(0, j - 1)});
        loggingInputStream.reset();

        for (int i = 0; i < 2 * j; ++i) {
            loggingInputStream.read();
        }
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER,
                        SHORT_MESSAGE.substring(0, j - 1),
                        SHORT_MESSAGE.substring(j - 1, 2 * (j - 1))
                });
        loggingInputStream.reset();

        readInputStream();

        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER,
                        SHORT_MESSAGE.substring(0, j - 1),
                        SHORT_MESSAGE.substring(j - 1, 2 * (j - 1)),
                        SHORT_MESSAGE.substring(2 * (j - 1), 3 * (j - 1)),
                        NEWLINE});
    }

    @Test
    public void testResetMethodOnLargeText() throws Exception {
        final int j = 32;
        final int actualBufferSize = bufferSize * j;
        final String LONG_MESSAGE = getLongMessage(actualBufferSize);
        initInputStream(LONG_MESSAGE, bufferSize, true);
        readInputStream();

        String[] loggerMessages = new String[1 + j + 1 + 1];
        loggerMessages[0] = HEADER;
        for (int i = 0; i <= j; ++i) {
            loggerMessages[i + 1] = LONG_MESSAGE.substring(bufferSize * i, bufferSize * (i + 1));
        }
        loggerMessages[j + 1 + 1] = NEWLINE;
        loggingInputStream.reset();
        readInputStream();

        assertThatLoggerMessagesEqualTo(loggerMessages);
    }

    @Test
    public void testLongMessageEqualToBufferSize() throws Exception {
        final String LONG_MESSAGE = getLongMessage(0);
        initInputStream(LONG_MESSAGE, bufferSize, true);
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, LONG_MESSAGE, NEWLINE});
    }

    @Test
    public void testLongMessageLargerThanBufferSize() throws Exception {
        final String LONG_MESSAGE = getLongMessage(1);
        initInputStream(LONG_MESSAGE, bufferSize, true);
        readInputStream();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER,
                        LONG_MESSAGE.substring(0, bufferSize),
                        LONG_MESSAGE.substring(bufferSize),
                        NEWLINE});
    }

    @Test
    public void testLarge() throws Exception {
        final int j = 128;
        final int actualBufferSize = bufferSize * j;
        final String LONG_MESSAGE = getLongMessage(actualBufferSize);
        initInputStream(LONG_MESSAGE, bufferSize, true);
        readInputStream();

        String[] loggerMessages = new String[1 + j + 1 + 1];
        loggerMessages[0] = HEADER;
        for (int i = 0; i <= j; ++i) {
            loggerMessages[i + 1] = LONG_MESSAGE.substring(bufferSize * i, bufferSize * (i + 1));
        }
        loggerMessages[j + 1 + 1] = NEWLINE;

        assertThatLoggerMessagesEqualTo(loggerMessages);
    }

    @Test
    public void testReadToEndOfStream() throws Exception {
        final String SHORT_MESSAGE = "test string";
        initInputStream(SHORT_MESSAGE, bufferSize, true);
        for (int i = 0; i < SHORT_MESSAGE.length() / 3; ++i) {
            loggingInputStream.read();
        }
        loggingInputStream.readToEOF();
        assertThatLoggerMessagesEqualTo(
                new String[]{HEADER, SHORT_MESSAGE, NEWLINE});
    }

    // helper methods
    private void initInputStream(final String message, int bufferSize, boolean readToEOF) {
        if (message == null) {
            return;
        }
        inputStream = new ByteArrayInputStream(message.getBytes());
        LoggingInputStreamFactory factory = new LoggingInputStreamFactory(sdkLogger);
        loggingInputStream = factory.buildInputStream(inputStream, bufferSize, readToEOF);
    }

    private void readInputStream() throws Exception {
        int data = -1;
        do {
            data = loggingInputStream.read();
        } while (data != -1);
    }

    private void assertThatLoggerMessagesEqualTo(final String[] messages) {
        List<ILoggingEvent> loggingEvents = listAppender.list;
        boolean skip = false;
        final int index = 0;

        assertThat(loggingEvents.size(), equalTo(messages.length));
        if (messages.length > 0 && messages[index].equals(HEADER)) {
            skip = true;
        }

        int i = 0;
        for (String line : messages) {
            if (skip && ignoreHeader && i == index) {
                skip = false;
                ++i;
                continue;
            }
            assertThat(line, not(equalTo(null)));
            ILoggingEvent event = loggingEvents.get(i++);
            assertThat(event, not(equalTo(null)));
        }
    }

    private String getLongMessage(final int additionalLength) {
        return StringUtils.repeat("x", bufferSize + additionalLength);
    }
}
