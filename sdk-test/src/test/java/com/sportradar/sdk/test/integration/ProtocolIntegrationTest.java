/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.integration;

import com.sportradar.sdk.common.classes.SdkVersion;
import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.ReconnectingGateway;
import com.sportradar.sdk.common.networking.TcpGateway;
import com.sportradar.sdk.common.rategate.NullRateGate;
import com.sportradar.sdk.common.rategate.RateLimiter;
import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.networking.ConnectionMonitoringGateway;
import com.sportradar.sdk.proto.LiveFeedProtocol;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.livescout.LiveScoutOutgoingMessageInspector;
import com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory;
import com.sportradar.sdk.test.FakeOddsServer;
import com.sportradar.sdk.test.NullSdkLogger;
import com.sportradar.sdk.test.TcpServer;
import org.apache.commons.net.DefaultSocketFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Protocol integration test
 */
@Ignore
public class ProtocolIntegrationTest {

    private static String LIVE_ODDS_FILEPATH = "/LiveOdds.xsd";

    private final Synchroniser synchronizer = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchronizer);
    }};

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @SuppressWarnings("unchecked")
    private final ProtocolListener<IncomingMessage> protocolListener = context.mock(ProtocolListener.class);

    private final States protocolState = context.states("protocolState");

    private FakeOddsServer serverDriver;

    private Gateway gateway;
    MessageParser<IncomingMessage> clientMessageParser;
    MessageWriter<OutgoingMessage> clientMessageWriter;
    private Protocol<IncomingMessage, OutgoingMessage> protocol;

    @Before
    public void setUp() throws IOException, JAXBException {

        File file = new File(getClass().getResource(LIVE_ODDS_FILEPATH).getFile());

        JAXBContext jaxbContext = JAXBContext.newInstance(IncomingMessage.class, OutgoingMessage.class);
        JaxbBuilder liveOddsJaxbFactory = new JaxbFactory(jaxbContext);
        JaxbBuilder bookmakerStatusJaxbFactory = new JaxbFactory(jaxbContext);

        MessageParser<OutgoingMessage> serverMessageParser = new JaxbMessageParser<>(bookmakerStatusJaxbFactory, null, new NullSdkLogger());
        MessageWriter<IncomingMessage> serverMessageWriter = new JaxbMessageWriter<>(liveOddsJaxbFactory);

        LiveScoutSettings serverSettings = DefaultSettingsBuilderHelper.getLiveScout()
                .setUsername("1")
                .setPassword("key")
                .build();

        serverDriver = new FakeOddsServer(new TcpServer(executor, 5055), serverMessageParser, serverMessageWriter, serverSettings);
        Gateway tcpGateway = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Timer reconnectTimer = new PeriodicTimer(executorService);
        Gateway reconnectingGateway = new ReconnectingGateway(tcpGateway, reconnectTimer, Duration.millis(200), Duration.millis(200));

        Timer monitoringTimer = new PeriodicTimer(executorService);
        gateway = new ConnectionMonitoringGateway(reconnectingGateway, monitoringTimer, Duration.standardSeconds(5), Duration.standardSeconds(7), false);

        LiveScoutSettings settings = DefaultSettingsBuilderHelper.getLiveScout()
                .setUsername("1")
                .setPassword("key")
                .build();

        clientMessageParser = new JaxbMessageParser<>(
                liveOddsJaxbFactory,
                new IncrementalMessageTokenizer(new NullSdkLogger(), settings.getMaxMessageSize()),
                new NullSdkLogger());
        clientMessageWriter = new JaxbMessageWriter<>(bookmakerStatusJaxbFactory);

        SdkLogger logger = new NullSdkLogger();

        protocol = new LiveFeedProtocol(
                gateway,
                clientMessageParser,
                clientMessageWriter,
                new RateLimiter(new NullRateGate(), new NullRateGate(), new NullRateGate()),
                new LiveScoutOutgoingMessageInspector(),
                new LiveScoutStatusFactory(new SdkVersion()),
                settings,
                logger);


        protocol.setListener(protocolListener);
    }

    @Test(timeout = 5000)
    public void protocolLogsInAndStops() throws InterruptedException, IOException {
        context.checking(new Expectations() {{
            allowing(protocolListener).onMessageReceived(with(any(IncomingMessage.class)));

            oneOf(protocolListener).onLinkEvent(with(any(FeedEventType.class)), with(any(Object.class)));
            then(protocolState.is("connected"));

            oneOf(protocolListener).onLoggedIn();
            when(protocolState.is("connected"));
            then(protocolState.is("logged_in"));

            oneOf(protocolListener).onDisconnected();
            when(protocolState.is("logged_in"));
            then(protocolState.is("stopped"));

        }});

        serverDriver.start();
        protocol.start();

        serverDriver.hasClientConnected();
        serverDriver.hasReceivedLoginRequest();

        synchronizer.waitUntil(protocolState.is("logged_in"), 1000);
        protocol.stop();

        synchronizer.waitUntil(protocolState.is("stopped"), 1000);
        serverDriver.stop();
    }
}
