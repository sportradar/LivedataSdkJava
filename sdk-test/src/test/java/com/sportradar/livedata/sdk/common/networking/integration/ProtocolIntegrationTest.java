/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common.networking.integration;

import com.sportradar.livedata.sdk.common.classes.SdkVersion;
import com.sportradar.livedata.sdk.common.enums.FeedEventType;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.networking.ReconnectingGateway;
import com.sportradar.livedata.sdk.common.networking.TcpGateway;
import com.sportradar.livedata.sdk.common.rategate.NullRateGate;
import com.sportradar.livedata.sdk.common.rategate.RateLimiter;
import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import com.sportradar.livedata.sdk.common.networking.ConnectionMonitoringGateway;
import com.sportradar.livedata.sdk.proto.LiveFeedProtocol;
import com.sportradar.livedata.sdk.proto.common.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageInspector;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutStatusFactory;
import com.sportradar.livedata.sdk.util.FakeServer;
import com.sportradar.livedata.sdk.util.TcpServer;
import org.apache.commons.net.DefaultSocketFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Protocol integration test
 */
@Disabled
class ProtocolIntegrationTest {
    private final Synchroniser synchronizer = new Synchroniser();

    private final Mockery context = new JUnit5Mockery() {{
        setThreadingPolicy(synchronizer);
    }};

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @SuppressWarnings("unchecked")
    private final ProtocolListener<IncomingMessage> protocolListener = context.mock(ProtocolListener.class);

    private final States protocolState = context.states("protocolState");

    private FakeServer serverDriver;

    private Gateway gateway;
    MessageParser<IncomingMessage> clientMessageParser;
    MessageWriter<OutgoingMessage> clientMessageWriter;
    private Protocol<IncomingMessage, OutgoingMessage> protocol;

    @BeforeEach
    public void setUp() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(IncomingMessage.class, OutgoingMessage.class);
        JaxbBuilder liveOddsJaxbFactory = new JaxbFactory(jaxbContext);
        JaxbBuilder bookmakerStatusJaxbFactory = new JaxbFactory(jaxbContext);

        MessageParser<OutgoingMessage> serverMessageParser = new JaxbMessageParser<>(bookmakerStatusJaxbFactory, null);
        MessageWriter<IncomingMessage> serverMessageWriter = new JaxbMessageWriter<>(liveOddsJaxbFactory);

        AuthSettings authSettings = new AuthSettings("1", "key",
                null, null, null, null, null);
        LiveScoutSettings serverSettings = DefaultSettingsBuilderHelper.getLiveScout()
                .authSettings(authSettings)
                .build();

        serverDriver = new FakeServer(new TcpServer(executor, 5055), serverMessageParser, serverMessageWriter, serverSettings);
        Gateway tcpGateway = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Timer reconnectTimer = new PeriodicTimer(executorService);
        Gateway reconnectingGateway = new ReconnectingGateway(tcpGateway, reconnectTimer, Duration.millis(200), Duration.millis(200));

        Timer monitoringTimer = new PeriodicTimer(executorService);
        gateway = new ConnectionMonitoringGateway(reconnectingGateway, monitoringTimer, Duration.standardSeconds(5), Duration.standardSeconds(7), false);

        LiveScoutSettings settings = DefaultSettingsBuilderHelper.getLiveScout()
                .authSettings(authSettings)
                .build();

        clientMessageParser = new JaxbMessageParser<>(
                liveOddsJaxbFactory,
                new IncrementalMessageTokenizer(settings.getTotalBufferSize()));
        clientMessageWriter = new JaxbMessageWriter<>(bookmakerStatusJaxbFactory);

        protocol = new LiveFeedProtocol(
                gateway,
                clientMessageParser,
                clientMessageWriter,
                new RateLimiter(new NullRateGate(), new NullRateGate(), new NullRateGate()),
                new LiveScoutOutgoingMessageInspector(),
                new LiveScoutStatusFactory(new SdkVersion()),
                settings);


        protocol.setListener(protocolListener);
    }

    @Test
    @Timeout(5)
    void protocolLogsInAndStops() throws InterruptedException, IOException {
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
