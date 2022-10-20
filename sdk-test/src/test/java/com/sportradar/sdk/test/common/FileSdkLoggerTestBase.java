/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sportradar.sdk.common.classes.FileSdkLogger;
import com.sportradar.sdk.common.classes.RealTimeProvider;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.LoggerSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.TimeProvider;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import org.apache.commons.lang3.ArrayUtils;
import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileSdkLoggerTestBase {

    private final String tmpLogPath = System.getProperty("java.io.tmpdir") + File.separator + "logs";
    @Rule
    public TemporaryFolder directory;
    private File tmpDirectory;
    private LoggerSettings loggerSettings;
    private FileSdkLogger fileSdkLogger;
    private final String ROOT_NS = FileSdkLogger.ROOT_NS +".logger";
    private Logger logger;
    private DummyLBAppender listAppender;
    private DummyLBAppender rootListAppender;
    private LoggerContext loggerContext;
    private String logPath;
    private Duration oldLogCleanupInterval;
    private Duration oldLogMaxAge;
    private Logger sdkRootLogger;
    private Logger sdkRootMarkerLogger;
    private Logger invalidMessageLogger;
    protected Logger trafficLogger;
    private Logger clientInteractionLogger;
    private Logger alertLogger;
    private Timer timer;
    private final String markerName = "markerName";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public FileSdkLoggerTestBase() {
    }

    @Before
    public void setUp() throws Exception {
        File logDirectory = new File(tmpLogPath);
        if (!logDirectory.exists() && !logDirectory.isDirectory()) {
            logDirectory.mkdir();
        }
        directory = new TemporaryFolder(logDirectory);
        directory.create();
        logger = (Logger) LoggerFactory.getLogger(ROOT_NS);
        tmpDirectory = directory.newFolder();
        logPath = tmpDirectory.getAbsolutePath();

        oldLogCleanupInterval = Duration.standardHours(1);
        oldLogMaxAge = Duration.standardMinutes(5);
        Level alertLogLevel = Level.ALL;
        Level clientInteractionLogLevel = Level.INFO;
        Level configLogLevel = Level.INFO;
        Level executionLogLevel = Level.INFO;
        Level invalidMsgLogLevel = Level.INFO;
        Level statsLogLevel = Level.INFO;
        Level trafficLogLevel = Level.INFO;
        int maxFileSize = 1024*1024*10;
        timer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        loggerSettings = new LoggerSettings(
                logPath, oldLogCleanupInterval, oldLogMaxAge,
                alertLogLevel, clientInteractionLogLevel,
                executionLogLevel,
                invalidMsgLogLevel, trafficLogLevel, maxFileSize);
        fileSdkLogger = new FileSdkLogger(loggerSettings, timer, markerName);

        listAppender = new DummyLBAppender();
        loggerContext = logger.getLoggerContext();
        listAppender.setContext(loggerContext);

        rootListAppender = new DummyLBAppender();
        rootListAppender.setContext(loggerContext);

        final String markerNameWithDot = "." + markerName;
        final String markerNameWithDotPlusDot = "." + markerName + ".";

        sdkRootLogger = loggerContext.getLogger(ROOT_NS + ".common");
        assertThat(sdkRootLogger, CoreMatchers.instanceOf(Logger.class));
        sdkRootMarkerLogger = loggerContext.getLogger(ROOT_NS + markerNameWithDot);
        assertThat(sdkRootMarkerLogger, CoreMatchers.instanceOf(Logger.class));
        invalidMessageLogger = getLogger(fileSdkLogger, ROOT_NS + markerNameWithDotPlusDot + SdkLogAppenderType.INVALID_MSG.getFileName());
        trafficLogger = getLogger(fileSdkLogger, ROOT_NS + markerNameWithDotPlusDot + SdkLogAppenderType.TRAFFIC.getFileName());
        clientInteractionLogger = getLogger(fileSdkLogger, ROOT_NS + markerNameWithDotPlusDot + SdkLogAppenderType.CLIENT_INTERACTION.getFileName());
        alertLogger = getLogger(fileSdkLogger, ROOT_NS + markerNameWithDotPlusDot + SdkLogAppenderType.ALERT.getFileName());

        sdkRootLogger.addAppender(rootListAppender);
        sdkRootMarkerLogger.addAppender(rootListAppender);
        invalidMessageLogger.addAppender(listAppender);
        trafficLogger.addAppender(listAppender);
        clientInteractionLogger.addAppender(listAppender);
        alertLogger.addAppender(listAppender);
        listAppender.start();
        rootListAppender.start();
    }

    @After
    public void tearDown() {
        TimeProvider.setCurrent(new RealTimeProvider());
    }

    public static Logger getLogger(SdkLogger sdkLogger, String loggerName) {
        Map<Logger, Marker> markers = null;
        Logger logger = null;
        try {
            Field fieldMarker = sdkLogger.getClass().getDeclaredField("markers");
            fieldMarker.setAccessible(true);
            markers = (Map<Logger, Marker>) fieldMarker.get(sdkLogger);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (markers != null) {
            for (Map.Entry<Logger, Marker> entry : markers.entrySet()) {
                if (loggerName.equals(entry.getKey().getName())) {
                    logger = entry.getKey();
                    break;
                }
            }
        }
        return logger;
    }

    @Test
    public void testLogTraffic() {
        assertThat(trafficLogger, CoreMatchers.instanceOf(Logger.class));
        boolean result = fileSdkLogger.isLevelEnabled(Level.INFO, SdkLogAppenderType.TRAFFIC);
        assertThat(result, is(true));

        final String outMessage = "test out traffic";
        final String outMessagePrefix = "-> ";

        fileSdkLogger.logTraffic(true, outMessage);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(1));
        ILoggingEvent event = loggingEvents.get(0);
        assertThat(outMessagePrefix + outMessage, equalTo(event.getFormattedMessage()));
        assertThat(event.getLevel(), equalTo(Level.INFO));

        final String inMessage = "test in traffic";
        final String inMessagePrefix = "<- ";
        fileSdkLogger.logTraffic(false, inMessage);
        assertThat(loggingEvents.size(), is(2));
        event = loggingEvents.get(1);
        assertThat(inMessagePrefix + inMessage, equalTo(event.getFormattedMessage()));
        assertThat(event.getLevel(), equalTo(Level.INFO));
    }

    @Test
    public void testLogClientInteraction() {
        assertThat(clientInteractionLogger, CoreMatchers.instanceOf(Logger.class));
        boolean result = fileSdkLogger.isLevelEnabled(Level.INFO, SdkLogAppenderType.CLIENT_INTERACTION);
        assertThat(result, is(true));

        final String messagePrefix = "";
        final String message = "test client interaction message 123";
        Level level = Level.INFO;
        fileSdkLogger.logClientInteraction(level, message);

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(1));
        ILoggingEvent event = loggingEvents.get(0);
        assertThat(messagePrefix + message, equalTo(event.getFormattedMessage()));
        assertThat(event.getLevel(), equalTo(level));
    }

    @Test
    public void testLogAlert() {
        assertThat(alertLogger, CoreMatchers.instanceOf(Logger.class));
        boolean result = fileSdkLogger.isLevelEnabled(Level.INFO, SdkLogAppenderType.ALERT);
        assertThat(result, is(true));

        final String message = "test 123 alert logger";
        Level level = Level.INFO;
        fileSdkLogger.logAlert(level, message);

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(1));
        ILoggingEvent event = loggingEvents.get(0);
        assertThat(message, equalTo(event.getFormattedMessage()));
        assertThat(event.getLevel(), equalTo(level));
    }



    @Test
    public void testLogInvalidMessage() {
        assertThat(invalidMessageLogger, CoreMatchers.instanceOf(Logger.class));

        final String message = "test 123 invalid message logger";
        Level level = Level.INFO;
        fileSdkLogger.logInvalidMessage(level, message);

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(1));
        ILoggingEvent event = loggingEvents.get(0);
        assertThat(message, equalTo(event.getFormattedMessage()));
        assertThat(event.getLevel(), equalTo(level));
    }

    @Test
    public void testLogClientInteractionExceptionMessage() {
        assertThat(clientInteractionLogger, CoreMatchers.instanceOf(Logger.class));

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(0));

        Level level = Level.INFO;
        String message = "client interaction with exception message";
        try {
            new IllegalArgumentException("argument exception test 111");
        } catch (IllegalArgumentException exception) {
            fileSdkLogger.logClientInteraction(level, message, exception);
            loggingEvents = listAppender.list;
            assertThat(loggingEvents.size(), is(1));

            ILoggingEvent event = loggingEvents.get(0);
            assertThat(message, equalTo(event.getFormattedMessage()));
            assertThat(event.getLevel(), equalTo(level));
        }
    }

    @Test
    public void testLogClientInteractionDurationIsZero() {
        assertThat(clientInteractionLogger, CoreMatchers.instanceOf(Logger.class));

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(0));

        Level level = Level.INFO;
        String message = "client interaction with duration message";
        ILoggingEvent event;

        try {
            new IllegalArgumentException("argument exception test with duration zero");
        } catch (IllegalArgumentException exception) {
            fileSdkLogger.logClientInteraction(level, message, Duration.ZERO, exception);
            loggingEvents = listAppender.list;
            assertThat(loggingEvents.size(), is(1));

            event = loggingEvents.get(0);
            assertThat(message, equalTo(event.getFormattedMessage()));
            assertThat(event.getLevel(), equalTo(level));
        }
    }

    @Test
    public void testLogClientInteractionDurationIs1324() {
        assertThat(clientInteractionLogger, CoreMatchers.instanceOf(Logger.class));

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(0));

        Level level = Level.INFO;
        String message;
        ILoggingEvent event;

        try {
            new IllegalArgumentException("test exception with duration non zero");
        } catch (IllegalArgumentException exception) {
            message = "duration message";
            fileSdkLogger.logClientInteraction(level, message, Duration.millis(1324), exception);
            assertThat(loggingEvents.size(), is(2));

            event = loggingEvents.get(1);
            assertThat("[Elapsed PT1.324S] " + message, equalTo(event.getFormattedMessage()));
            assertThat(event.getLevel(), equalTo(level));
        }
    }

    @Test
    public void testLogClientInteractionDurationIsNull() {
        assertThat(clientInteractionLogger, CoreMatchers.instanceOf(Logger.class));

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(0));

        Level level = Level.INFO;
        String message = "client interaction with duration is null";
        Duration interval = null;
        fileSdkLogger.logClientInteraction(level, message, interval, null);

        ILoggingEvent event = loggingEvents.get(0);
        assertThat(message, equalTo(event.getFormattedMessage()));
        assertThat(event.getLevel(), equalTo(level));
    }

    @Test
    public void testLogClientInteractionWithEmptyArray() {
        assertThat(clientInteractionLogger, CoreMatchers.instanceOf(Logger.class));

        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertThat(loggingEvents.size(), is(0));

        Level level = Level.INFO;
        EventIdentifier[] eventsIds = {};
        String message = String.format("Empty: %s", ArrayUtils.toString(eventsIds));
        fileSdkLogger.logClientInteraction(level, message);

        ILoggingEvent event = loggingEvents.get(0);
        assertThat(message, equalTo(event.getFormattedMessage()));
        assertThat(event.getFormattedMessage(), equalTo("Empty: {}"));
        assertThat(event.getLevel(), equalTo(level));
    }

    @Test
    public void testLoggerLevelIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("level");
        FileSdkLogger.getLevel(null);
    }

    @Test
    public void testLoggerLevelIsEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("level");
        FileSdkLogger.getLevel("");
    }

    @Test
    public void testLoggerLevelIsEmp() {
        Level level = FileSdkLogger.getLevel("foo bar");
        assertThat(level, equalTo(Level.OFF));
    }

    @Test
    public void testAppenderTypeNonNull() {
        SdkLogAppenderType appenderTypeAlert = SdkLogAppenderType.ALERT;
        final String markerName = "marker";
        String appenderName = SdkLogAppenderType.getAppenderName(appenderTypeAlert, markerName);
        assertThat(appenderName, equalTo(SdkLogAppenderType.resolveAppenderName(appenderTypeAlert, markerName)));
    }

    @Test
    public void testAppenderTypeNull() {
        SdkLogAppenderType appenderTypeAlert = null;
        final String markerName = "marker";
        thrown.expectMessage("appenderType");
        thrown.expect(IllegalArgumentException.class);
        SdkLogAppenderType.getAppenderName(appenderTypeAlert, markerName);
    }

    @Test
    public void testMarker() {
        SdkLogAppenderType appenderType = SdkLogAppenderType.TRAFFIC;
        final String markerName = "LiveOdds";
        Marker marker = appenderType.getMarker(markerName);
        assertThat(marker, not(equalTo(null)));
    }

    @Test
    public void testGetAppenderType_InvalidCharacters() {
        final SdkLogAppenderType inputAppenderType = SdkLogAppenderType.ALERT;
        SdkLogAppenderType outputAppenderType = FileSdkLogger.getAppenderType(FileSdkLogger.ROOT_NS + inputAppenderType.name() + "\"[!@#$%^");
        assertThat(outputAppenderType, equalTo(inputAppenderType));
    }

    @Test
    public void testGetAppenderType_InvalidName() {
        final SdkLogAppenderType inputAppenderType = SdkLogAppenderType.ALERT;
        final String suffix = "new";
        final String appenderName = FileSdkLogger.ROOT_NS + inputAppenderType.name() + "." + suffix;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("No enum constant " + inputAppenderType.getClass().getName() + "." + inputAppenderType.name() + suffix);
        SdkLogAppenderType outputAppenderType = FileSdkLogger.getAppenderType(appenderName);
    }

    @Test
    public void testDeletingOldFiles() throws Exception {
        Method cleanUpMethod = fileSdkLogger.getClass().getDeclaredMethod("oldLogsCleanup");
        cleanUpMethod.setAccessible(true);

        class FakeTimeProvider extends TimeProvider {

            @Override
            public DateTime getCurrentTime() {
                return DateTime.now().plus(Duration.millis(loggerSettings.getOldLogCleanupInterval().getMillis()));
            }

            @Override
            public Date getDate() {
                return getCurrentTime().toDate();
            }

            @Override
            public DateTime getDateTime() {
                return getCurrentTime();
            }
        }

        final String trafficLogPath = loggerSettings.getLogPath() + File.separator + SdkLogAppenderType.TRAFFIC.getFileName();
        tmpDirectory.mkdirs();
        File trafficDirectory = new File(trafficLogPath);

        String fileNamePrefix = "oldFooInBar";
        File trafficLogFile = createFile(trafficLogPath, SdkLogAppenderType.TRAFFIC.getFileName() + FileSdkLogger.LOG_SUFFIX);
        File logFile = createFile(trafficLogPath, fileNamePrefix + FileSdkLogger.LOG_SUFFIX);
        File zipFile = createFile(trafficLogPath, fileNamePrefix + FileSdkLogger.ZIP_SUFFIX);

        Method getFilesOrDirectories = FileSdkLogger.class.getDeclaredMethod("getFilesOrDirectories", String.class, boolean.class);
        getFilesOrDirectories.setAccessible(true);
        String[] files = (String[]) getFilesOrDirectories.invoke(fileSdkLogger, trafficLogPath, false);
        Arrays.sort(files);
        assertThat(files.length, is(3));
        assertThat(files[0], CoreMatchers.endsWith(File.separator + trafficLogFile.getName()));
        assertThat(files[1], CoreMatchers.endsWith(File.separator + logFile.getName()));
        assertThat(files[2], CoreMatchers.endsWith(File.separator + zipFile.getName()));

        TimeProvider.setCurrent(new FakeTimeProvider());
        setOldDate(logFile);
        setOldDate(zipFile);
        cleanUpMethod.invoke(fileSdkLogger);

        String[] trafficFiles = trafficDirectory.list();
        assertThat(trafficFiles.length, is(0));
    }

    private File createFile(final String directoryPath, final String fileName) throws Exception {
        File directory = new File(directoryPath);
        directory.mkdirs();
        File file = new File(directoryPath + File.separator + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
        fileOutputStream.write(("foo in bar text" + fileName).getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return file;
    }

    private void setOldDate(File file) {
        file.setLastModified(DateTime.now().minus(Duration.millis(loggerSettings.getOldLogMaxAge().getMillis())).getMillis());
    }
}
