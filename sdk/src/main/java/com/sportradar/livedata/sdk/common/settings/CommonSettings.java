package com.sportradar.livedata.sdk.common.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class CommonSettings {

    protected int dispatcherThreadCount;
    protected int dispatcherQueueSize;
    protected LoggerSettings loggerSettings;
    protected boolean debugMode;

    protected CommonSettings(int dispatcherThreadCount,
                             int dispatcherQueueSize,
                             LoggerSettings loggerSettings,
                             boolean debugMode) {
        this.dispatcherThreadCount = dispatcherThreadCount;
        this.dispatcherQueueSize = dispatcherQueueSize;
        this.loggerSettings = loggerSettings;
        this.debugMode = debugMode;

    }
}
