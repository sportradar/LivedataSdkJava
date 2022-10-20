package com.sportradar.sdk.common.settings;

public abstract class CommonSettingsBuilder<T extends CommonSettingsBuilder> {

    protected boolean enabled;
    protected LoggerSettingsBuilder loggerSettingsBuilder;
    protected int dispatcherThreadCount;
    protected int dispatcherQueueSize;
    protected boolean debugMode;


    public LoggerSettingsBuilder getLoggerSettingsBuilder() {
        return loggerSettingsBuilder;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @SuppressWarnings("unchecked")
    public T setEnabled(boolean enabled) {
        this.enabled = enabled;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setLoggerSettings(LoggerSettingsBuilder loggerSettingsBuilder) {
        this.loggerSettingsBuilder = loggerSettingsBuilder;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setDispatcherThreadCount(int dispatcherThreadCount) {
        this.dispatcherThreadCount = dispatcherThreadCount;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setDispatcherQueueSize(int dispatcherQueueSize) {
        this.dispatcherQueueSize = dispatcherQueueSize;
        return (T) this;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public T setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return (T) this;
    }

    public int getDispatcherThreadCount() {
        return dispatcherThreadCount;
    }

    public int getDispatcherQueueSize() {
        return dispatcherQueueSize;
    }
}
