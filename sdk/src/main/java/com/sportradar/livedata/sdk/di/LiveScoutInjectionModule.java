package com.sportradar.livedata.sdk.di;

import com.google.inject.*;
import com.google.inject.Module;
import com.sportradar.livedata.sdk.common.classes.FileSdkLogger;
import com.sportradar.livedata.sdk.common.classes.NullSdkLogger;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.networking.ConnectionMonitoringGateway;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.classes.jmx.LiveScoutStatisticsCounter;
import com.sportradar.livedata.sdk.common.classes.jmx.SimpleJMX;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.common.networking.ReconnectingGateway;
import com.sportradar.livedata.sdk.common.networking.TcpGateway;
import com.sportradar.livedata.sdk.common.rategate.*;
import com.sportradar.livedata.sdk.common.settings.LimiterData;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDisruptorDispatcher;
import com.sportradar.livedata.sdk.feed.common.*;
import com.sportradar.livedata.sdk.feed.common.interfaces.UserRequestManager;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutEntityMapper;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutFeedImpl;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutTestManagerImpl;
import com.sportradar.livedata.sdk.feed.livescout.classes.LiveScoutUserRequestManagerImpl;
import com.sportradar.livedata.sdk.feed.livescout.entities.JaxbLiveScoutEntityFactory;
import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.livedata.sdk.proto.LiveFeedProtocol;
import com.sportradar.livedata.sdk.proto.common.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutClientAliveProducer;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageInspector;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutStatusFactory;
import jakarta.xml.bind.JAXBContext;
import org.apache.commons.net.DefaultSocketFactory;

import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.bind.JAXBException;
import org.joda.time.Duration;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link com.google.inject.Module} implementation used by Guice to set up live-scout dependency tree.
 */
public class LiveScoutInjectionModule implements Module {
    /**
     * The name of the package containing entities send from the feed to the sdk
     */
    private static final String INCOMING_PACKAGE_NAME = com.sportradar.livedata.sdk.proto.dto.incoming.livescout.ObjectFactory.class.getPackage().getName();

    /**
     * The name of the package containing entities send from the sdk to the feed
     */
    private static final String OUTGOING_PACKAGE_NAME = com.sportradar.livedata.sdk.proto.dto.outgoing.livescout.ObjectFactory.class.getPackage().getName();

    /**
     * Index of the bound {@link UserRequestManager} instance.
     */
    private final static int USER_REQUEST_MANAGER_INDEX = 1;

    /**
     * The {@link LiveScoutSettings} instance containing live-scout configurable values.
     */
    protected final LiveScoutSettings settings;

    /**
     * Initializes a new instance of the {@link LiveScoutInjectionModule} class.
     *
     * @param settings The {@link LiveScoutSettings} containing live-scout configurable values.
     */
    public LiveScoutInjectionModule(LiveScoutSettings settings) {
        checkNotNull(settings, "settings cannot be a null reference");
        this.settings = settings;
    }

    /**
     * <p>
     * Contributes bindings and other configurations for this module to {@code binder}.
     * </p>
     * <p><strong>Do not invoke this method directly</strong> to install submodules. Instead use
     * {@link com.google.inject.Binder#install(com.google.inject.Module)}, which ensures that {@link com.google.inject.Provides provider methods} are
     * discovered.
     * </p>
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(LiveScoutEntityFactory.class)
                .to(JaxbLiveScoutEntityFactory.class)
                .in(Singleton.class);

        binder.bind(new TypeLiteral<EntityMapper<IncomingMessage, LiveScoutEntityBase>>() {})
                .to(LiveScoutEntityMapper.class)
                .in(Singleton.class);

        binder.bind(LiveScoutStatusFactory.class)
                .in(Singleton.class);


        binder.bind(LiveScoutUserRequestManager.class)
                .to(Key.get(LiveScoutUserRequestManagerImpl.class))
                .in(Singleton.class);
    }

    @Provides
    @Singleton
    protected SdkLogger provideSdkLogger(ScheduledExecutorService scheduledExecutorService) {
        Timer timer = new PeriodicTimer(scheduledExecutorService);
        try {
            return new FileSdkLogger(settings.getLoggerSettings(), timer, "FEED");
        } catch (SdkException e) {
            return NullSdkLogger.INSTANCE;
        }
    }

    @Provides
    @Singleton
    protected Gateway provideGateway(
            ExecutorService executorService,
            ScheduledExecutorService scheduledExecutorService,
            DefaultSocketFactory socketFactory,
            SSLSocketFactory sslSocketFactory) {
        InetSocketAddress socketAddress = new InetSocketAddress(settings.getHostName(), settings.getPort());


        Gateway actualGateway = new TcpGateway(
                executorService,
                settings.isUseSSL() ? sslSocketFactory : socketFactory,
                socketAddress,
                settings.getReceiveBufferSize());

        Timer reconnectTimer = new PeriodicTimer(scheduledExecutorService);
        Gateway reconnectingGateway = new ReconnectingGateway(
                actualGateway,
                reconnectTimer,
                settings.getInitialReconnectWait(),
                settings.getReconnectWait());

        Timer monitoringTimer = new PeriodicTimer(scheduledExecutorService);

        return new ConnectionMonitoringGateway(
                reconnectingGateway,
                monitoringTimer,
                Duration.standardSeconds(5),
                settings.getServerAliveMsgTimeout(),
                settings.isDebugMode());
    }

    @Provides
    @Singleton
    protected LiveScoutUserRequestManagerImpl provideUserRequestManager(LiveScoutStatusFactory factory,
                                                                        TestManager testManager) {
        return new LiveScoutUserRequestManagerImpl(
                USER_REQUEST_MANAGER_INDEX,
                factory,
                settings.getMatchExpireMaxAge(),
                testManager);
    }

    @Provides
    @Singleton
    protected LiveScoutClientAliveProducer provideAliveProducer(
            LiveScoutStatusFactory entityFactory,
            ScheduledExecutorService executor) {
        return new LiveScoutClientAliveProducer(entityFactory, executor, settings.getClientAliveMsgTimeout());
    }

    @Provides
    @Singleton
    @SuppressWarnings("unchecked")
    protected ProtocolManager<OutgoingMessage, LiveScoutEntityBase> provideProtocolManager(
            Protocol<IncomingMessage, OutgoingMessage> protocol,
            EntityMapper<IncomingMessage, LiveScoutEntityBase> entityMapper,
            LiveScoutUserRequestManagerImpl userRequestManager,
            LiveScoutClientAliveProducer aliveProducer) {

        MessageProcessor<LiveScoutEntityBase>[] processors = new MessageProcessor[]{
                userRequestManager,
        };
        MessageProcessor<LiveScoutEntityBase> pipeline = new MessagePipeline<>(1, processors);

        RequestProducer<OutgoingMessage>[] producers = new RequestProducer[]{
                aliveProducer,
                userRequestManager
        };

        RequestProducer<OutgoingMessage> producerComposite = new RequestProducerComposite<>(producers);

        return new LiveFeedProtocolManager(
                protocol,
                entityMapper,
                pipeline,
                producerComposite);
    }

    @Provides
    @Singleton
    protected LiveScoutDispatcher provideDispatcher(
            ExecutorService executorService,
            SimpleJMX jmxManager) {

        LiveScoutStatisticsCounter counter = new LiveScoutStatisticsCounter();
        jmxManager.add(counter);
        return new LiveScoutDisruptorDispatcher(
                settings.getDispatcherThreadCount(),
                settings.getDispatcherQueueSize(),
                executorService,
                counter);
    }

    @Provides
    @Singleton
    protected LiveScoutFeed provideFeed(
            Provider<ProtocolManager<OutgoingMessage, LiveScoutEntityBase>> protocolManagerProvider,
            Provider<LiveScoutUserRequestManagerImpl> userRequestManagerProvider,
            Provider<LiveScoutDispatcher> dispatcherProvider) {

        return new LiveScoutFeedImpl(
                protocolManagerProvider.get(),
                userRequestManagerProvider.get(),
                dispatcherProvider.get(),
                settings);
    }

    @Provides
    @Singleton
    protected Protocol<IncomingMessage, OutgoingMessage> provideProtocol(
            LiveScoutStatusFactory statusFactory,
            ScheduledExecutorService scheduledExecutorService,
            Gateway gateway) throws JAXBException {

        JAXBContext incomingContext = JAXBContext.newInstance(INCOMING_PACKAGE_NAME);
        JaxbBuilder incomingBuilder = new JaxbFactory(incomingContext);
        MessageTokenizer tokenizer = new IncrementalMessageTokenizer(settings.getTotalBufferSize());
        MessageParser<IncomingMessage> messageParser = new JaxbMessageParser<>(incomingBuilder, tokenizer);

        JAXBContext outgoingContext = JAXBContext.newInstance(OUTGOING_PACKAGE_NAME);
        JaxbBuilder outgoingBuilder = new JaxbFactory(outgoingContext);
        MessageWriter<OutgoingMessage> messageWriter = new JaxbMessageWriter<>(outgoingBuilder);

        RateLimiter rateLimiter = new RateLimiter(
                new NullRateGate(),
                getRateGateFromData(settings.getRequestLimiters(), scheduledExecutorService),
                new NullRateGate());

        return new LiveFeedProtocol(
                gateway,
                messageParser,
                messageWriter,
                rateLimiter,
                new LiveScoutOutgoingMessageInspector(),
                statusFactory,
                settings);

    }

    @Provides
    protected TestManager provideTestManager(
            Protocol<IncomingMessage, OutgoingMessage> protocol
    ) {
        if (!settings.isTest()) {
            return () -> false;
        } else {
            return new LiveScoutTestManagerImpl(protocol);
        }
    }

    private static RateGate getRateGateFromData(List<LimiterData> data, ScheduledExecutorService scheduledExecutorService) {

        SimpleRateGate[] loginRateGates = new SimpleRateGate[data.size()];
        for (int i = 0; i < data.size(); i++) {
            LimiterData limiterData = data.get(i);
            loginRateGates[i] = new SimpleRateGate(
                    limiterData.getLimit(),
                    limiterData.getDuration(),
                    scheduledExecutorService,
                    limiterData.getName());
        }

        return loginRateGates.length == 1
                ? loginRateGates[0]
                : new CombinedRateGate(loginRateGates);
    }
}
