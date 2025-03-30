package com.sportradar.livedata.sdk.system.framework.common;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.settings.*;
import com.sportradar.livedata.sdk.di.LiveScoutInjectionModule;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.livedata.sdk.feed.common.ProtocolManager;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutFeedImpl;
import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;

public class SystemTestHelper {

    public static LiveScoutFeed getLiveScoutFeed(Gateway gateway,
                                                 LiveScoutDispatcher dispatcher,
                                                 LiveScoutSettingsBuilder settingsBuilder) {
        LiveScoutSettings liveScoutSettings = settingsBuilder.build();
        LiveScoutInjectionModule liveScoutInjectionModule = new LiveScoutInjectionModule(liveScoutSettings);
        Injector injector = TestInjectionHelper.getInjector(liveScoutInjectionModule);
        Key<ProtocolManager<OutgoingMessage, LiveScoutEntityBase>> protocolManagerKey = Key.get(
                new TypeLiteral<>() {
                });
        Key<LiveScoutUserRequestManager> userRequestManagerKey = Key.get(
                LiveScoutUserRequestManager.class);
        return new LiveScoutFeedImpl(
                injector.getInstance(protocolManagerKey),
                injector.getInstance(userRequestManagerKey),
                dispatcher,
                liveScoutSettings);
    }


}
