package com.sportradar.sdk.test.feed.feed.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.sdk.common.settings.*;
import com.sportradar.sdk.di.*;

public class DiTestHelper {

    private static Injector injector;

    public static Injector getInjector() {
        if (injector == null) {

            LiveScoutSettings liveScoutSettings = DefaultSettingsBuilderHelper.getLiveScout()
                    .setUseSSL(true)
                    .setEnabled(true)
                    .build();

            injector = Guice.createInjector(
                    new GeneralInjectionModule(new JmxSettings(false, "localhost", 12345, null, null)),//TODO test
                    new LiveScoutInjectionModule(liveScoutSettings)
            );
        }
        return injector;
    }
}
