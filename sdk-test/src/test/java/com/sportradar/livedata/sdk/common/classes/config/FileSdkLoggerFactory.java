package com.sportradar.livedata.sdk.common.classes.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.sportradar.livedata.sdk.common.classes.FileSdkLogger;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.settings.LoggerSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.Executors;

public class FileSdkLoggerFactory {

    private String logPath;
    private final String ROOT_NS = FileSdkLogger.ROOT_NS +".logger";
    private LoggerSettings loggerSettings;
    private Logger logger;
    private Duration oldLogCleanupInterval;
    private Duration oldLogMaxAge;
    private Timer timer;
    public FileSdkLogger buildFileSdkLogger(Path tempDirectory) throws SdkException {
        logPath = tempDirectory.toAbsolutePath().toString();
        logger = (Logger) LoggerFactory.getLogger(ROOT_NS);
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

        return new FileSdkLogger(loggerSettings, timer, "markerName");
    }
}
