package com.sportradar.sdk.test.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.util.StatusPrinter;
import com.sportradar.sdk.common.classes.FileSdkLogger;
import com.sportradar.sdk.common.classes.SdkLoggerCfg;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.LoggerSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SdkLoggerCfgTest {

    File logDirectory = new File(System.getProperty("java.io.tmpdir") + File.separator + "logs");
    @Rule
    public TemporaryFolder directory = new TemporaryFolder(logDirectory);
    final private String LIVE_SCOUT_SUBDIRECTORY = "LiveScout";

    private Logger rootLogger = (Logger) LoggerFactory.getLogger(FileSdkLogger.ROOT_NS);
    private Logger testLogger;
    private LoggerSettings loggerSettings;
    private LoggerSettings liveScoutLoggerSettings;
    private ScheduledExecutorService scheduledExecutorService;
    private DummyLBAppender dummyLBAppender;
    final private Level level = Level.DEBUG;

    public SdkLoggerCfgTest() {
    }

    @Before
    public void setUp() throws Exception {
        File tmpDirectory = directory.newFolder();
        loggerSettings = createLoggerSettings(tmpDirectory, "");
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        testLogger = (Logger) LoggerFactory.getLogger(SdkLoggerCfg.class);

        liveScoutLoggerSettings = createLoggerSettings(logDirectory, LIVE_SCOUT_SUBDIRECTORY);
        dummyLBAppender = new DummyLBAppender();
    }

    private LoggerSettings createLoggerSettings(File logDirectory, String subDirectory) {
        return new LoggerSettings(
                logDirectory.getPath() + File.separator + subDirectory,
                new Duration(1000 * 60 * 60),
                new Duration(1000 * 60 * 60 * 60),
                Level.INFO,
                Level.WARN,
                Level.TRACE,
                Level.DEBUG,
                Level.DEBUG,
                10*1024*1024
        );
    }

    private void addDummyLBAppender(LoggerContext loggerContext) {
        dummyLBAppender.setContext(loggerContext);
        for (Logger l : rootLogger.getLoggerContext().getLoggerList()) {
            l.addAppender(dummyLBAppender);
        }
        dummyLBAppender.start();
    }

    private LoggerContext getLoggerContext() {
        int maxFileSize = 1024*1024*10;
        final Appender appender = SdkLoggerCfg.createAppender(SdkLogAppenderType.EXECUTION,
                loggerSettings.getLogPath(),
                loggerSettings.getExecutionLogLevel(), null, true, "Common", maxFileSize);
        LoggerContext loggerContext = (LoggerContext) appender.getContext();
        StatusPrinter.print(loggerContext);
        testLogger.addAppender(appender);
        rootLogger.addAppender(dummyLBAppender);
        testLogger.getLoggerContext().setName("TestLoggerContext");
        return loggerContext;
    }

    private ConcurrentLinkedQueue<Object[]> log(SdkLogger sdkLogger, String markerName) {
        ConcurrentLinkedQueue<Object[]> result = new ConcurrentLinkedQueue<>();

        String str = "alert message";
        sdkLogger.logAlert(level, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.ALERT.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.ALERT.getAppenderName(),
                markerName,
                str
        });

        str = "alert message";
        String exStr = "alert exception1";
        sdkLogger.logAlert(level, str, new Exception(exStr));
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.ALERT.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.ALERT.getAppenderName(),
                markerName,
                str
        });

        str = "client interaction message1";
        sdkLogger.logClientInteraction(level, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                markerName,
                str
        });

        str = "client interaction message2";
        exStr = "client exception1";
        sdkLogger.logClientInteraction(level, str, new Exception(exStr));
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                markerName,
                str
        });

        int durationTime = 123;
        str = "client interaction message3";
        exStr = "client exception2";
        sdkLogger.logClientInteraction(level, str, Duration.millis(durationTime), new Exception(exStr));
        str = String.format("[Elapsed %s] %s", durationTime, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                markerName,
                str
        });

        String s = "<- ";
        str = "traffic";
        sdkLogger.logTraffic(false, str);
        result.add(new Object[]{
                level, FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                markerName,
                s + str
        });

        s = "-> ";
        str = "traffic";
        sdkLogger.logTraffic(true, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                markerName,
                s + str
        });


        str = "invalid message";
        sdkLogger.logInvalidMessage(level, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.INVALID_MSG.getAppenderName(),
                markerName + "-" + SdkLogAppenderType.INVALID_MSG.getAppenderName(),
                markerName,
                str
        });

        return result;
    }

    public void simulateLogging(SdkLogger sdkLogger) {
        for (int i = 0; i < 10000; ++i) {
            sdkLogger.logClientInteraction(Level.DEBUG, "client intereaction " + i);
        }
    }

    @Test
    public void perfomanceTest() throws Exception {
        LoggerContext loggerContext = getLoggerContext();

        LiveScoutFileSdkLogger liveScoutFileSdkLogger = new LiveScoutFileSdkLogger(liveScoutLoggerSettings);
        final SdkLogger liveScoutSdkLogger = liveScoutFileSdkLogger.createLogger(scheduledExecutorService);
        Thread liveScoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                simulateLogging(liveScoutSdkLogger);
            }
        });

        long startTime = System.nanoTime();

        liveScoutThread.start();
        liveScoutThread.join();

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println(new Duration(elapsedTime));
    }

    @Test
    public void testFileSdkLogger() throws Exception {
        LoggerContext loggerContext = getLoggerContext();

        final ConcurrentLinkedQueue<Object> concurrentQueue = new ConcurrentLinkedQueue<>();

        LiveScoutFileSdkLogger liveScoutFileSdkLogger = new LiveScoutFileSdkLogger(liveScoutLoggerSettings);
        final SdkLogger liveScoutSdkLogger = liveScoutFileSdkLogger.createLogger(scheduledExecutorService);
        Thread liveScoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConcurrentLinkedQueue<Object[]> result = log(liveScoutSdkLogger, "LiveScout");
                concurrentQueue.addAll(result);
            }
        });

        addDummyLBAppender(loggerContext);

        liveScoutThread.start();
        liveScoutThread.join();

        rootLogger.debug("test msg");
        List<ILoggingEvent> list = new ArrayList<>(dummyLBAppender.list);

        for (ILoggingEvent event : list) {
            System.out.println(String.format("%s %s %s %s",
                    event.getLoggerName(),
                    event.getLevel().levelStr,
                    event.getMarker(),
                    event.getMessage()));
            if (concurrentQueue.isEmpty()) {
                break;
            }
            Object[] object = (Object[]) concurrentQueue.poll();
            assertThat(level, equalTo(object[0]));
            Marker marker = event.getMarker();
            if (marker instanceof Marker) {
                assertThat(marker.getName(), equalTo(object[2]));
                assertThat(event.getMessage(), equalTo(object[4]));
            }
        }
    }

    private static class LoggerClassBar {

        public LoggerClassBar() {
        }

        public static void bar(Logger logger) {
            logger.debug("test bar");
        }
    }

    private static class LiveScoutFileSdkLogger {

        org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerClassBar.class);
        private LoggerSettings loggerSettings;

        public LiveScoutFileSdkLogger(LoggerSettings loggerSettings) {
            logger.info("constructor");
            this.loggerSettings = loggerSettings;
        }

        public SdkLogger createLogger(ScheduledExecutorService scheduledExecutorService) throws SdkException {
            logger.info("create logger");
            Timer timer = new PeriodicTimer(scheduledExecutorService);
            return new FileSdkLogger(loggerSettings, timer, "LiveScout");
        }
    }

    private static class LoggerClassFooBar {

        public LoggerClassFooBar() {
        }

        public static void foobar(Logger logger) {
            logger.debug("test foobar");
        }
    }

    private static class LiveOddsFileSdkLogger {

        org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerClassFooBar.class);
        private LoggerSettings loggerSettings;

        public LiveOddsFileSdkLogger(LoggerSettings loggerSettings) {
            logger.info("constructor");
            this.loggerSettings = loggerSettings;
        }

        public SdkLogger createLogger(ScheduledExecutorService scheduledExecutorService) throws SdkException {
            logger.info("create logger");
            Timer timer = new PeriodicTimer(scheduledExecutorService);
            return new FileSdkLogger(loggerSettings, timer, "LiveOdds");
        }
    }
}
