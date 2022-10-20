package com.sportradar.sdk.test.feed.feed.di;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.di.LiveScoutInjectionModule;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.sdk.feed.common.ProtocolManager;
import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.common.interfaces.UserRequestManager;
import com.sportradar.sdk.feed.livescout.classes.LiveScoutUserRequestManagerImpl;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Contains unit-tests for the {@link LiveScoutInjectionModule} class
 */
public class LiveScoutInjectionModuleTests {

    private static Injector injector;

    @BeforeClass
    public static void setUpStatic() {
        injector = DiTestHelper.getInjector();
    }

    @Test
    public void SdkLoggerIsResolvedAsSingleton() {
        Key<SdkLogger> key = Key.get(SdkLogger.class);
        SdkLogger logger1 = injector.getInstance(key);
        SdkLogger logger2 = injector.getInstance(key);

        assertThat(logger1, is(logger2));
    }

    @Test
    public void gatewayIsResolvedAsSingleton() {
        Key<Gateway> key = Key.get(Gateway.class);

        Gateway gateway1 = injector.getInstance(key);
        Gateway gateway2 = injector.getInstance(key);

        assertThat(gateway1.equals(gateway2), is(true));
    }

    @Test
    public void userRequestManagerAndItsImplementationAreResolvedAsSingleton() {
        Key<LiveScoutUserRequestManager> interfaceKey = Key.get(LiveScoutUserRequestManager.class);
        Key<LiveScoutUserRequestManagerImpl> key = Key.get(LiveScoutUserRequestManagerImpl.class);

        UserRequestManager managerInterface = injector.getInstance(interfaceKey);
        LiveScoutUserRequestManagerImpl manager = injector.getInstance(key);

        assertThat(manager.equals(managerInterface), is(true));
    }

    @Test
    public void protocolManagerIsResolvedAsSingleton() {
        Key<ProtocolManager<OutgoingMessage, EntityBase>> key = Key.get(
                new TypeLiteral<ProtocolManager<OutgoingMessage, EntityBase>>() {
                });

        ProtocolManager<OutgoingMessage, EntityBase> manager1 = injector.getInstance(key);
        ProtocolManager<OutgoingMessage, EntityBase> manager2 = injector.getInstance(key);

        assertThat(manager1.equals(manager2), is(true));
    }

    @Test
    public void dispatcherIsResolvedAsSingleton() {
        Key<LiveScoutDispatcher> key = Key.get(LiveScoutDispatcher.class);
        LiveScoutDispatcher dispatcher1 = injector.getInstance(key);
        LiveScoutDispatcher dispatcher2 = injector.getInstance(key);

        assertThat(dispatcher1.equals(dispatcher2), is(true));
    }

    @Test
    public void feedIsResolvedAsSingleton() {
        Key<LiveScoutFeed> key = Key.get(LiveScoutFeed.class);
        LiveScoutFeed feed1 = injector.getInstance(key);
        LiveScoutFeed feed2 = injector.getInstance(key);

        assertThat(feed1.equals(feed2), is(true));
    }
}
