package com.sportradar.livedata.sdk.di;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.livedata.sdk.feed.common.ProtocolManager;
import com.sportradar.livedata.sdk.feed.common.interfaces.UserRequestManager;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutUserRequestManagerImpl;
import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Contains unit-tests for the {@link LiveScoutInjectionModule} class
 */
public class LiveScoutInjectionModuleTests {

    private static Injector injector;

    @BeforeAll
    public static void setUpStatic() {
        injector = DiTestHelper.getInjector();
    }

    @Test
    void SdkLoggerIsResolvedAsSingleton() {
        Key<SdkLogger> key = Key.get(SdkLogger.class);
        SdkLogger logger1 = injector.getInstance(key);
        SdkLogger logger2 = injector.getInstance(key);

        assertThat(logger1, is(logger2));
    }

    @Test
    void gatewayIsResolvedAsSingleton() {
        Key<Gateway> key = Key.get(Gateway.class);

        Gateway gateway1 = injector.getInstance(key);
        Gateway gateway2 = injector.getInstance(key);

        assertThat(gateway1.equals(gateway2), is(true));
    }

    @Test
    void userRequestManagerAndItsImplementationAreResolvedAsSingleton() {
        Key<LiveScoutUserRequestManager> interfaceKey = Key.get(LiveScoutUserRequestManager.class);
        Key<LiveScoutUserRequestManagerImpl> key = Key.get(LiveScoutUserRequestManagerImpl.class);

        UserRequestManager managerInterface = injector.getInstance(interfaceKey);
        LiveScoutUserRequestManagerImpl manager = injector.getInstance(key);

        assertThat(manager.equals(managerInterface), is(true));
    }

    @Test
    void protocolManagerIsResolvedAsSingleton() {
        Key<ProtocolManager<OutgoingMessage, LiveScoutEntityBase>> key = Key.get(
                new TypeLiteral<ProtocolManager<OutgoingMessage, LiveScoutEntityBase>>() {
                });

        ProtocolManager<OutgoingMessage, LiveScoutEntityBase> manager1 = injector.getInstance(key);
        ProtocolManager<OutgoingMessage, LiveScoutEntityBase> manager2 = injector.getInstance(key);

        assertThat(manager1.equals(manager2), is(true));
    }

    @Test
    void dispatcherIsResolvedAsSingleton() {
        Key<LiveScoutDispatcher> key = Key.get(LiveScoutDispatcher.class);
        LiveScoutDispatcher dispatcher1 = injector.getInstance(key);
        LiveScoutDispatcher dispatcher2 = injector.getInstance(key);

        assertThat(dispatcher1.equals(dispatcher2), is(true));
    }

    @Test
    void feedIsResolvedAsSingleton() {
        Key<LiveScoutFeed> key = Key.get(LiveScoutFeed.class);
        LiveScoutFeed feed1 = injector.getInstance(key);
        LiveScoutFeed feed2 = injector.getInstance(key);

        assertThat(feed1.equals(feed2), is(true));
    }
}
