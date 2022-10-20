package com.sportradar.sdk.common.settings;

public abstract class CommonSettings {

    protected int dispatcherThreadCount;
    protected int dispatcherQueueSize;
    protected boolean enabled;
    protected LoggerSettings loggerSettings;
    protected boolean debugMode;

    protected CommonSettings(boolean enabled,
                             int dispatcherThreadCount,
                             int dispatcherQueueSize,
                             LoggerSettings loggerSettings,
                             boolean debugMode) {
        this.enabled = enabled;
        this.dispatcherThreadCount = dispatcherThreadCount;
        this.dispatcherQueueSize = dispatcherQueueSize;
        this.loggerSettings = loggerSettings;
        this.debugMode = debugMode;

    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public int getDispatcherThreadCount() {
        return dispatcherThreadCount;
    }

    public int getDispatcherQueueSize() {
        return dispatcherQueueSize;
    }

    public LoggerSettings getLoggerSettings() {
        return loggerSettings;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
