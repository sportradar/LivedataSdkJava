package com.sportradar.sdk.di;

import com.google.inject.Module;
import com.google.inject.Provides;
import com.sportradar.sdk.common.classes.FileSdkLogger;
import com.sportradar.sdk.common.classes.NullSdkLogger;
import com.sportradar.sdk.common.classes.jmx.LiveScoutStatisticsCounter;
import com.sportradar.sdk.common.classes.jmx.SimpleJMX;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.ReconnectingGateway;
import com.sportradar.sdk.common.networking.TcpGateway;
import com.sportradar.sdk.common.rategate.*;
import com.sportradar.sdk.common.settings.LimiterData;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.networking.ConnectionMonitoringGateway;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDisruptorDispatcher;
import com.sportradar.sdk.feed.common.*;
import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.common.interfaces.UserRequestManager;
import com.sportradar.sdk.feed.livescout.classes.LiveScoutFeedImpl;
import com.sportradar.sdk.feed.livescout.classes.LiveScoutTestManagerImpl;
import com.sportradar.sdk.feed.livescout.classes.LiveScoutUserRequestManagerImpl;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.proto.LiveFeedProtocol;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.livescout.LiveScoutClientAliveProducer;
import com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory;
import org.apache.commons.net.DefaultSocketFactory;
import org.joda.time.Duration;

import javax.inject.Provider;
import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A base class for all dependency injection modules binding types associated with the live-scout feed.
 */
public abstract class LiveScoutInjectionModuleBase implements Module {

    /**
     * The name of the package containing entities send from the feed to the sdk
     */
    private static final String INCOMING_PACKAGE_NAME = com.sportradar.sdk.proto.dto.incoming.livescout.ObjectFactory.class.getPackage().getName();

    /**
     * The name of the package containing entities send from the sdk to the feed
     */
    private static final String OUTGOING_PACKAGE_NAME = com.sportradar.sdk.proto.dto.outgoing.livescout.ObjectFactory.class.getPackage().getName();

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
     * @param settings       The {@link LiveScoutSettings} containing live-scout configurable values.
     */
    public LiveScoutInjectionModuleBase(LiveScoutSettings settings) {
        checkNotNull(settings, "settings cannot be a null reference");
        this.settings = settings;
    }

    protected Protocol<IncomingMessage, OutgoingMessage> buildProtocol(
            ScheduledExecutorService scheduledExecutorService,
            Gateway gateway,
            OutgoingMessageInspector<OutgoingMessage> outgoingMessageInspector,
            LiveScoutStatusFactory statusFactory,
            SdkLogger sdkLogger) throws JAXBException {

        JAXBContext incomingContext = JAXBContext.newInstance(INCOMING_PACKAGE_NAME);
        JaxbBuilder incomingBuilder = new JaxbFactory(incomingContext);
        MessageTokenizer tokenizer = new IncrementalMessageTokenizer(sdkLogger, settings.getMaxMessageSize());
        MessageParser<IncomingMessage> messageParser = new JaxbMessageParser<>(incomingBuilder, tokenizer, sdkLogger);

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
                outgoingMessageInspector,
                statusFactory,
                settings,
                sdkLogger);
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

    protected LiveScoutClientAliveProducer provideAliveProducer(
            LiveScoutStatusFactory entityFactory,
            ScheduledExecutorService executor) {
        return new LiveScoutClientAliveProducer(entityFactory, executor, settings.getClientAliveMsgTimeout());
    }

    protected LiveScoutDispatcher provideDispatcher(
            ExecutorService executorService,
            SdkLogger sdkLogger,
            SimpleJMX jmxManager,
            LiveScoutStatisticsCounter liveScoutStatisticsCounter) {

        jmxManager.add(liveScoutStatisticsCounter);
        return new LiveScoutDisruptorDispatcher(
                settings.getDispatcherThreadCount(),
                settings.getDispatcherQueueSize(),
                executorService,
                sdkLogger,
                liveScoutStatisticsCounter);
    }

    protected LiveScoutFeed provideFeed(
            Provider<ProtocolManager<OutgoingMessage, EntityBase>> protocolManagerProvider,
            Provider<LiveScoutUserRequestManagerImpl> userRequestManagerProvider,
            Provider<LiveScoutDispatcher> dispatcherProvider,
            Provider<SdkLogger> sdkLoggerProvider,
            LiveScoutSettings settings) {
        return settings.isEnabled()
                ? new LiveScoutFeedImpl(
                protocolManagerProvider.get(),
                userRequestManagerProvider.get(),
                dispatcherProvider.get(),
                sdkLoggerProvider.get(),
                settings)
                : null;
    }

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
                settings.getInitialReconnectDelay(),
                settings.getReconnectDelay());

        Timer monitoringTimer = new PeriodicTimer(scheduledExecutorService);

        return new ConnectionMonitoringGateway(
                reconnectingGateway,
                monitoringTimer,
                Duration.standardSeconds(5),
                settings.getServerMessageTimeout(),
                settings.isDebugMode());
    }

    @SuppressWarnings("unchecked")
    protected ProtocolManager<OutgoingMessage, EntityBase> provideProtocolManager(
            Protocol<IncomingMessage, OutgoingMessage> protocol,
            EntityMapper<IncomingMessage, EntityBase> entityMapper,
            LiveScoutUserRequestManagerImpl userRequestManager,
            LiveScoutClientAliveProducer aliveProducer,
            SdkLogger sdkLogger) throws JAXBException {

        MessageProcessor<EntityBase>[] processors = new MessageProcessor[]{
                userRequestManager,
        };
        MessageProcessor<EntityBase> pipeline = new MessagePipeline<>(1, processors);

        RequestProducer<OutgoingMessage>[] producers = new RequestProducer[]{
                aliveProducer,
                userRequestManager
        };

        RequestProducer<OutgoingMessage> producerComposite = new RequestProducerComposite<>(producers);

        return new LiveFeedProtocolManager(
                protocol,
                entityMapper,
                pipeline,
                producerComposite,
                sdkLogger);
    }

    protected SdkLogger provideSdkLogger(ScheduledExecutorService scheduledExecutorService) {
        Timer timer = new PeriodicTimer(scheduledExecutorService);

        SdkLogger ret;

        try {
            ret = new FileSdkLogger(settings.getLoggerSettings(), timer, "FEED");
        } catch (SdkException e) {
            ret = new NullSdkLogger();
        }

        return ret;
    }

    @Provides
    protected TestManager provideTestManager(
                    Protocol<IncomingMessage, OutgoingMessage> protocol
    ) {
        if (!settings.isTest()) {
            return new TestManager(){
                public boolean isEnabled() {
                    return false;
                }
            };
        } else {
            return new LiveScoutTestManagerImpl(protocol);
        }
    }

    protected LiveScoutUserRequestManagerImpl provideUserRequestManager(
            LiveScoutStatusFactory factory,
            TestManager testManager,
            SdkLogger sdkLogger) {
        return new LiveScoutUserRequestManagerImpl(
                USER_REQUEST_MANAGER_INDEX,
                factory,
                settings.getMatchExpireMaxAge(),
                testManager,
                sdkLogger);
    }
}
