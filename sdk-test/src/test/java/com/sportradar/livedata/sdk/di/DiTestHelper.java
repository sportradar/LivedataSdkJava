package com.sportradar.livedata.sdk.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.livedata.sdk.common.settings.*;

public class DiTestHelper {

    private static Injector injector;

    public static Injector getInjector() {
        if (injector == null) {

            LoggerSettings loggerSettings = LoggerSettings.builder().build();
            LiveScoutSettings liveScoutSettings = LiveScoutSettings.builder(false)
                    .useSSL(true)
                    .loggerSettings(loggerSettings)
                    .authSettings(new AuthSettings("", ""))
                    .build();

            injector = Guice.createInjector(
                    new GeneralInjectionModule(JmxSettings.builder().jmxPort(12345).build()),
                    new LiveScoutInjectionModule(liveScoutSettings)
            );
        }
        return injector;
    }
}
