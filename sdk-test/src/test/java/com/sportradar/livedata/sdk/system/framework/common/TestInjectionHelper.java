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
                        JmxSettings.builder().jmxPort(12345).build()
                ),//TODO test
                liveScoutInjectionModule);
    }
}
