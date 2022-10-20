package com.sportradar.sdk.common.settings;


import ch.qos.logback.classic.Level;
import org.joda.time.Duration;

public class LoggerSettings {

    private Level alertLogLevel;
    private Level clientInteractionLogLevel;
    private Level executionLogLevel;
    private Level invalidMsgLogLevel;
    private String logPath;
    private int maxFileSize;
    private Duration oldLogCleanupInterval;
    private Duration oldLogMaxAge;
    private Level trafficLogLevel;
    public LoggerSettings(
            String logPath,
            Duration oldLogCleanupInterval,
            Duration oldLogMaxAge,
            Level alertLogLevel,
            Level clientInteractionLogLevel,
            Level executionLogLevel,
            Level invalidMsgLogLevel,
            Level trafficLogLevel,
            int maxFileSize) {
        this.logPath = logPath;
        this.oldLogCleanupInterval = oldLogCleanupInterval;
        this.oldLogMaxAge = oldLogMaxAge;
        this.alertLogLevel = alertLogLevel;
        this.clientInteractionLogLevel = clientInteractionLogLevel;
        this.executionLogLevel = executionLogLevel;
        this.invalidMsgLogLevel = invalidMsgLogLevel;
        this.trafficLogLevel = trafficLogLevel;
        this.maxFileSize = maxFileSize;
    }

    public Level getAlertLogLevel() {
        return alertLogLevel;
    }

    public Level getClientInteractionLogLevel() {
        return clientInteractionLogLevel;
    }

    public Level getExecutionLogLevel() {
        return executionLogLevel;
    }

    public Level getInvalidMsgLogLevel() {
        return invalidMsgLogLevel;
    }

    public String getLogPath() {
        return logPath;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public Duration getOldLogCleanupInterval() {
        return oldLogCleanupInterval;
    }

    public Duration getOldLogMaxAge() {
        return oldLogMaxAge;
    }

    public Level getTrafficLogLevel() {
        return trafficLogLevel;
    }
}
