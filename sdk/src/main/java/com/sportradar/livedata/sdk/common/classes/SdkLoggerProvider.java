package com.sportradar.livedata.sdk.common.classes;

import com.google.inject.Inject;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

public class SdkLoggerProvider {
    private static SdkLogger sdkLogger = NullSdkLogger.INSTANCE;

    @Inject
    private SdkLoggerProvider(SdkLogger injectedLogger) {
        checkNotNull(injectedLogger, "sdkLogger cannot be a null reference");
        sdkLogger = injectedLogger;
    }

    public static SdkLogger get() {
        return sdkLogger;
    }
}
