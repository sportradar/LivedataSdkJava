package com.sportradar.sdk.common.settings;

import ch.qos.logback.classic.Level;
import org.joda.time.Duration;


public class LoggerSettingsBuilder {


    private Level alertLogLevel;
    private Level clientInteractionLogLevel;
    private Level executionLogLevel;
    private Level invalidMsgLogLevel;
    private String logPath;
    private int maxFileSize;
    private Duration oldLogCleanupInterval;
    private Duration oldLogMaxAge;
    private Level trafficLogLevel;

    public LoggerSettings build() {
        return new LoggerSettings(
                logPath,
                oldLogCleanupInterval,
                oldLogMaxAge,
                alertLogLevel,
                clientInteractionLogLevel,
                executionLogLevel,
                invalidMsgLogLevel,
                trafficLogLevel,
                maxFileSize);
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

    public LoggerSettingsBuilder setAlertLogLevel(Level alertLogLevel) {
        this.alertLogLevel = alertLogLevel;
        return this;
    }

    public LoggerSettingsBuilder setClientInteractionLogLevel(Level clientInteractionLogLevel) {
        this.clientInteractionLogLevel = clientInteractionLogLevel;
        return this;
    }

    public LoggerSettingsBuilder setExecutionLogLevel(Level executionLogLevel) {
        this.executionLogLevel = executionLogLevel;
        return this;
    }

    public LoggerSettingsBuilder setInvalidMsgLogLevel(Level invalidMsgLogLevel) {
        this.invalidMsgLogLevel = invalidMsgLogLevel;
        return this;
    }

    public LoggerSettingsBuilder setLogPath(String logPath) {
        this.logPath = logPath;
        return this;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public LoggerSettingsBuilder setOldLogCleanupInterval(Duration oldLogCleanupInterval) {
        this.oldLogCleanupInterval = oldLogCleanupInterval;
        return this;
    }

    public LoggerSettingsBuilder setOldLogMaxAge(Duration oldLogMaxAge) {
        this.oldLogMaxAge = oldLogMaxAge;
        return this;
    }

    public LoggerSettingsBuilder setTrafficLogLevel(Level trafficLogLevel) {
        this.trafficLogLevel = trafficLogLevel;
        return this;
    }
}
