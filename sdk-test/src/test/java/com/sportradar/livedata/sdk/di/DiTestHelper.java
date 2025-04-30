package com.sportradar.livedata.sdk.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.livedata.sdk.common.settings.*;

public class DiTestHelper {

    private static Injector injector;

    public static Injector getInjector() {
        if (injector == null) {

            LoggerSettings loggerSettings = DefaultSettingsBuilderHelper.getLoggerSettings().build();
            LiveScoutSettings liveScoutSettings = DefaultSettingsBuilderHelper.getLiveScout()
                    .useSSL(true)
                    .loggerSettings(loggerSettings)
                    .authSettings(new AuthSettings("", ""))
                    .build();

            injector = Guice.createInjector(
                    new GeneralInjectionModule(new JmxSettings(false, "localhost", 12345, null, null)),//TODO test
                    new LiveScoutInjectionModule(liveScoutSettings)
            );
        }
        return injector;
    }
}
