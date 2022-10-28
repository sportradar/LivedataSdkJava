package com.sportradar.livedata.sdk.test.system.framework.common;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.settings.*;
import com.sportradar.livedata.sdk.di.LiveScoutInjectionModule;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.livedata.sdk.feed.common.ProtocolManager;
import com.sportradar.livedata.sdk.feed.common.entities.EntityBase;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutFeedImpl;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.test.system.framework.livescout.LiveScoutInjectionModuleMock;

public class SystemTestHelper {

    public static LiveScoutFeed getLiveScoutFeed(Gateway gateway,
                                                 LiveScoutDispatcher dispatcher,
                                                 LiveScoutSettingsBuilder settingsBuilder) {
        LiveScoutSettings liveScoutSettings = settingsBuilder.build();
        LoggerSettings loggerSettings = DefaultSettingsBuilderHelper.getLiveScout().getLoggerSettingsBuilder().build();
        LiveScoutInjectionModule liveScoutInjectionModule = new LiveScoutInjectionModule(liveScoutSettings);
        Modules.override(liveScoutInjectionModule).with(new LiveScoutInjectionModuleMock(liveScoutSettings, gateway));
        Injector injector = TestInjectionHelper.getInjector(liveScoutInjectionModule);
        Key<ProtocolManager<OutgoingMessage, EntityBase>> protocolManagerKey = Key.get(
                new TypeLiteral<ProtocolManager<OutgoingMessage, EntityBase>>() {
                });
        Key<LiveScoutUserRequestManager> userRequestManagerKey = Key.get(
                LiveScoutUserRequestManager.class);
        Key<SdkLogger> sdkLogger = Key.get(
                SdkLogger.class);
        return new LiveScoutFeedImpl(
                injector.getInstance(protocolManagerKey),
                injector.getInstance(userRequestManagerKey),
                dispatcher,
                injector.getInstance(sdkLogger),
                liveScoutSettings);
    }


}
