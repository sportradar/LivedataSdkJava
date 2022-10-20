package com.sportradar.sdk.di;

import com.google.inject.*;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.classes.jmx.LiveScoutStatisticsCounter;
import com.sportradar.sdk.common.classes.jmx.SimpleJMX;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.sdk.feed.common.EntityMapper;
import com.sportradar.sdk.feed.common.ProtocolManager;
import com.sportradar.sdk.feed.common.TestManager;
import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.livescout.classes.LiveScoutEntityMapper;
import com.sportradar.sdk.feed.livescout.classes.LiveScoutUserRequestManagerImpl;
import com.sportradar.sdk.feed.livescout.entities.JaxbLiveScoutEntityFactory;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.sdk.proto.common.Protocol;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.livescout.LiveScoutClientAliveProducer;
import com.sportradar.sdk.proto.livescout.LiveScoutOutgoingMessageInspector;
import com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory;
import org.apache.commons.net.DefaultSocketFactory;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.bind.JAXBException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A {@link com.google.inject.Module} implementation used by Guice to set up live-scout dependency tree.
 */
public class LiveScoutInjectionModule extends LiveScoutInjectionModuleBase {

    /**
     * Initializes a new instance of the {@link LiveScoutInjectionModule} class.
     *
     * @param settings The {@link LiveScoutSettings} containing live-scout configurable values.
     */
    public LiveScoutInjectionModule(LiveScoutSettings settings) {
        super(settings);
    }

    /**
     * Contributes bindings and other configurations for this module to {@code binder}.
     * <p/>
     * <p><strong>Do not invoke this method directly</strong> to install submodules. Instead use
     * {@link com.google.inject.Binder#install(com.google.inject.Module)}, which ensures that {@link com.google.inject.Provides provider methods} are
     * discovered.
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(LiveScoutEntityFactory.class)
                .to(JaxbLiveScoutEntityFactory.class)
                .in(Singleton.class);

        binder.bind(new TypeLiteral<EntityMapper<IncomingMessage, EntityBase>>() {})
                .to(LiveScoutEntityMapper.class)
                .in(Singleton.class);

        binder.bind(LiveScoutStatusFactory.class)
                .in(Singleton.class);


        binder.bind(LiveScoutUserRequestManager.class)
                .to(Key.get(LiveScoutUserRequestManagerImpl.class))
                .in(Singleton.class);
    }

    @Override
    @Provides
    @Singleton
    protected SdkLogger provideSdkLogger(ScheduledExecutorService scheduledExecutorService) {
        return super.provideSdkLogger(scheduledExecutorService);
    }

    @Override
    @Provides
    @Singleton
    protected Gateway provideGateway(
            ExecutorService executorService,
            ScheduledExecutorService scheduledExecutorService,
            DefaultSocketFactory socketFactory,
            SSLSocketFactory sslSocketFactory) {

        return super.provideGateway(executorService, scheduledExecutorService, socketFactory, sslSocketFactory);
    }

    @Override
    @Provides
    @Singleton
    protected LiveScoutUserRequestManagerImpl provideUserRequestManager(LiveScoutStatusFactory factory,
                                                                        TestManager testManager,
                                                                        SdkLogger sdkLogger) {
        return super.provideUserRequestManager(factory, testManager, sdkLogger);
    }

    @Override
    @Provides
    @Singleton
    protected LiveScoutClientAliveProducer provideAliveProducer(
            LiveScoutStatusFactory entityFactory,
            ScheduledExecutorService executor) {

        return super.provideAliveProducer(entityFactory, executor);
    }

    @Override
    @Provides
    @Singleton
    protected ProtocolManager<OutgoingMessage, EntityBase> provideProtocolManager(
            Protocol<IncomingMessage, OutgoingMessage> protocol,
            EntityMapper<IncomingMessage, EntityBase> entityMapper,
            LiveScoutUserRequestManagerImpl userRequestManager,
            LiveScoutClientAliveProducer aliveProducer,
            SdkLogger sdkLogger) throws JAXBException {
        return super.provideProtocolManager(protocol, entityMapper, userRequestManager, aliveProducer, sdkLogger);
    }

    @Provides
    @Singleton
    protected LiveScoutDispatcher provideDispatcher(
            ExecutorService executorService,
            SimpleJMX jmxManager,
            SdkLogger sdkLogger) {

        LiveScoutStatisticsCounter counter = new LiveScoutStatisticsCounter();
        return super.provideDispatcher(executorService, sdkLogger, jmxManager, counter);
    }

    @Provides
    @Singleton
    protected LiveScoutFeed provideFeed(
            Provider<ProtocolManager<OutgoingMessage, EntityBase>> protocolManagerProvider,
            Provider<LiveScoutUserRequestManagerImpl> userRequestManagerProvider,
            Provider<LiveScoutDispatcher> dispatcherProvider,
            Provider<SdkLogger> sdkLoggerProvider) {
        return super.provideFeed(
                protocolManagerProvider,
                userRequestManagerProvider,
                dispatcherProvider,
                sdkLoggerProvider,
                settings);
    }

    @Provides
    @Singleton
    protected Protocol<IncomingMessage, OutgoingMessage> provideProtocol(
            LiveScoutStatusFactory statusFactory,
            ScheduledExecutorService scheduledExecutorService,
            Gateway gateway,
            SdkLogger sdkLogger) throws JAXBException {
        return super.buildProtocol(
                scheduledExecutorService,
                gateway,
                new LiveScoutOutgoingMessageInspector(),
                statusFactory,
                sdkLogger);
    }
}
