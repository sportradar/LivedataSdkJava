package com.sportradar.sdk.test.system.framework.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.sdk.common.settings.*;
import com.sportradar.sdk.di.*;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class TestInjectionHelper {

    public static Injector getInjector(LiveScoutInjectionModule liveScoutInjectionModule) {
        checkNotNull(liveScoutInjectionModule);

        return Guice.createInjector(
                new GeneralInjectionModule(
                        new JmxSettings(false, "localhost", 12345, null, null)
                ),//TODO test
                liveScoutInjectionModule);
    }
}
