package com.sportradar.livedata.sdk.system.framework.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.livedata.sdk.common.settings.*;
import com.sportradar.livedata.sdk.di.*;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

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
