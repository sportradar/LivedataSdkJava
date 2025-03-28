package com.sportradar.livedata.sdk.common.classes;

import com.google.inject.Inject;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;

public class LoggerProvider {
    private static SdkLogger sdkLogger;

    @Inject
    private LoggerProvider(SdkLogger injectedLogger) {
        sdkLogger = injectedLogger;
    }

    public static SdkLogger getLogger() {
        return sdkLogger;
    }
}
