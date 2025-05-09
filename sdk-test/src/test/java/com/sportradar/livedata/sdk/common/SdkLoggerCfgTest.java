package com.sportradar.livedata.sdk.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.util.StatusPrinter;
import com.sportradar.livedata.sdk.common.classes.FileSdkLogger;
import com.sportradar.livedata.sdk.common.classes.SdkLoggerCfg;
import com.sportradar.livedata.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.common.settings.LoggerSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import org.joda.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SdkLoggerCfgTest {

    private final String tmpLogPath = System.getProperty("java.io.tmpdir") + File.separator + "logs";

    @TempDir
    public File directory;

    private final Logger rootLogger = (Logger) LoggerFactory.getLogger(FileSdkLogger.ROOT_NS);
    private Logger testLogger;
    private LoggerSettings loggerSettings;
    private LoggerSettings liveScoutLoggerSettings;
    private ScheduledExecutorService scheduledExecutorService;
    private DummyLBAppender dummyLBAppender;
    final private Level level = Level.DEBUG;

    public SdkLoggerCfgTest() {
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @BeforeEach
    public void setUp() throws Exception {
        File logDirectory = new File(directory, tmpLogPath);
        if (!logDirectory.exists() && !logDirectory.isDirectory()) {
            logDirectory.mkdir();
        }
        File tmpDirectory = Files.createTempDirectory(directory.toPath(), "file_logger_test").toFile();
        loggerSettings = createLoggerSettings(tmpDirectory, "");
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        testLogger = (Logger) LoggerFactory.getLogger(SdkLoggerCfg.class);

        String LIVE_SCOUT_SUBDIRECTORY = "LiveScout";
        liveScoutLoggerSettings = createLoggerSettings(logDirectory, LIVE_SCOUT_SUBDIRECTORY);
        dummyLBAppender = new DummyLBAppender();
    }

    private LoggerSettings createLoggerSettings(File logDirectory, String subDirectory) {
        return LoggerSettings.builder()
                .logPath(logDirectory.getPath() + File.separator + subDirectory)
                .oldLogCleanupInterval(new Duration(1000 * 60 * 60))
                .oldLogMaxAge(new Duration(1000 * 60 * 60 * 60))
                .alertLogLevel(Level.INFO)
                .clientInteractionLogLevel(Level.WARN)
                .executionLogLevel(Level.TRACE)
                .invalidMsgLogLevel(Level.DEBUG)
                .trafficLogLevel(Level.DEBUG)
                .maxFileSize(10*1024*1024)
                .build();
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
        final Appender<ILoggingEvent> appender = SdkLoggerCfg.createAppender(SdkLogAppenderType.EXECUTION,
                loggerSettings.getLogPath(),
                loggerSettings.getExecutionLogLevel(), null, true, "Common", maxFileSize);
        LoggerContext loggerContext = (LoggerContext) appender.getContext();
        StatusPrinter.print(loggerContext);
        testLogger.addAppender(appender);
        rootLogger.addAppender(dummyLBAppender);
        testLogger.getLoggerContext().setName("TestLoggerContext");
        return loggerContext;
    }

    private ConcurrentLinkedQueue<Object[]> log(SdkLogger sdkLogger) {
        ConcurrentLinkedQueue<Object[]> result = new ConcurrentLinkedQueue<>();

        String str = "alert message";
        sdkLogger.logAlert(level, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.ALERT.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.ALERT.getAppenderName(),
                "LiveScout",
                str
        });

        str = "alert message";
        String exStr = "alert exception1";
        sdkLogger.logAlert(level, str, new Exception(exStr));
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.ALERT.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.ALERT.getAppenderName(),
                "LiveScout",
                str
        });

        str = "client interaction message1";
        sdkLogger.logClientInteraction(level, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                "LiveScout",
                str
        });

        str = "client interaction message2";
        exStr = "client exception1";
        sdkLogger.logClientInteraction(level, str, new Exception(exStr));
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                "LiveScout",
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
                "LiveScout" + "-" + SdkLogAppenderType.CLIENT_INTERACTION.getAppenderName(),
                "LiveScout",
                str
        });

        String s = "<- ";
        str = "traffic";
        sdkLogger.logTraffic(false, str);
        result.add(new Object[]{
                level, FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                "LiveScout",
                s + str
        });

        s = "-> ";
        str = "traffic";
        sdkLogger.logTraffic(true, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.TRAFFIC.getAppenderName(),
                "LiveScout",
                s + str
        });


        str = "invalid message";
        sdkLogger.logInvalidMessage(level, str);
        result.add(new Object[]{
                level,
                FileSdkLogger.ROOT_NS + "." + SdkLogAppenderType.INVALID_MSG.getAppenderName(),
                "LiveScout" + "-" + SdkLogAppenderType.INVALID_MSG.getAppenderName(),
                "LiveScout",
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
    void perfomanceTest() throws Exception {
        LiveScoutFileSdkLogger liveScoutFileSdkLogger = new LiveScoutFileSdkLogger(liveScoutLoggerSettings);
        final SdkLogger liveScoutSdkLogger = liveScoutFileSdkLogger.createLogger(scheduledExecutorService);
        Thread liveScoutThread = new Thread(() -> simulateLogging(liveScoutSdkLogger));

        long startTime = System.nanoTime();

        liveScoutThread.start();
        liveScoutThread.join();

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println(new Duration(elapsedTime));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testFileSdkLogger() throws Exception {
        LoggerContext loggerContext = getLoggerContext();

        final ConcurrentLinkedQueue<Object> concurrentQueue = new ConcurrentLinkedQueue<>();

        LiveScoutFileSdkLogger liveScoutFileSdkLogger = new LiveScoutFileSdkLogger(liveScoutLoggerSettings);
        final SdkLogger liveScoutSdkLogger = liveScoutFileSdkLogger.createLogger(scheduledExecutorService);
        Thread liveScoutThread = new Thread(() -> {
            ConcurrentLinkedQueue<Object[]> result = log(liveScoutSdkLogger);
            concurrentQueue.addAll(result);
        });

        addDummyLBAppender(loggerContext);

        liveScoutThread.start();
        liveScoutThread.join();

        rootLogger.debug("test msg");
        List<ILoggingEvent> list = new ArrayList<>(dummyLBAppender.list);

        for (ILoggingEvent event : list) {
            System.out.printf("%s %s %s %s%n",
                    event.getLoggerName(),
                    event.getLevel().levelStr,
                    event.getMarker(),
                    event.getMessage());
            if (concurrentQueue.isEmpty()) {
                break;
            }
            Object[] object = (Object[]) concurrentQueue.poll();
            assertThat(level, equalTo(object[0]));
            Marker marker = event.getMarker();
            if (marker != null) {
                assertThat(marker.getName(), equalTo(object[2]));
                assertThat(event.getMessage(), equalTo(object[4]));
            }
        }
    }

    private static class LoggerClassBar {}

    private static class LiveScoutFileSdkLogger {

        org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerClassBar.class);
        private final LoggerSettings loggerSettings;

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
}
