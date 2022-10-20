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
import com.sportradar.sdk.test.NullSdkLogger;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Server test
 */
@Ignore
public class BetradarServerTest {

    private static final Logger logger = LoggerFactory.getLogger(BetradarServerTest.class);

    private final Synchroniser synchronizer = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchronizer);
    }};

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @SuppressWarnings("unchecked")
    private final ProtocolListener<IncomingMessage> protocolListener = context.mock(ProtocolListener.class);

    private final States protocolState = context.states("protocolState");

    private final Sequence sequence = context.sequence("sequence");

    private Gateway gateway;
    MessageParser<IncomingMessage> clientMessageParser;
    MessageWriter<OutgoingMessage> clientMessageWriter;
    private Protocol<IncomingMessage, OutgoingMessage> protocol;

    @Before
    public void setUp() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(IncomingMessage.class, OutgoingMessage.class);
        JaxbBuilder jaxbBuilder = new JaxbFactory(jaxbContext);

        Gateway tcpGateway = new TcpGateway(executor, SSLSocketFactory.getDefault(), new InetSocketAddress("liveodds.betradar.com", 1981), 1024);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Timer reconnectTimer = new PeriodicTimer(executorService);
        Gateway reconnectingGateway = new ReconnectingGateway(tcpGateway, reconnectTimer, Duration.standardSeconds(5), Duration.standardSeconds(5));

        Timer monitoringTimer = new PeriodicTimer(executorService);
        gateway = new ConnectionMonitoringGateway(reconnectingGateway, monitoringTimer, Duration.standardSeconds(2), Duration.standardSeconds(10), false);

        clientMessageParser = new JaxbMessageParser<>(
                jaxbBuilder,
                new IncrementalMessageTokenizer(new NullSdkLogger(), 4 * 1024 * 1024),
                new NullSdkLogger());
        clientMessageWriter = new JaxbMessageWriter<>(jaxbBuilder);
    }

    @Test(timeout = 25000)
    public void protocolLogsInAndStops() throws InterruptedException, IOException, MessageException, ProtocolException {

        LiveScoutSettings settings = DefaultSettingsBuilderHelper.getLiveScout()
                .setUsername("1762")
                .setPassword("DirKES7ew")
                .build();

        SdkLogger skdLogger = new NullSdkLogger();

        protocol = new LiveFeedProtocol(
                gateway,
                clientMessageParser,
                clientMessageWriter,
                new RateLimiter(new NullRateGate(), new NullRateGate(), new NullRateGate()),
                new LiveScoutOutgoingMessageInspector(),
                new LiveScoutStatusFactory(new SdkVersion()),
                settings,
                skdLogger);

        protocol.setListener(protocolListener);

        context.checking(new Expectations() {{
            ignoring(protocolListener).onLinkEvent(with(any(FeedEventType.class)), with(any(Object.class)));


            oneOf(protocolListener).onLoggedIn();
            then(protocolState.is("logged_in"));
            inSequence(sequence);

            oneOf(protocolListener).onMessageReceived(with(any(IncomingMessage.class)));
            inSequence(sequence);

            oneOf(protocolListener).onMessageReceived(with(any(IncomingMessage.class)));
            when(protocolState.is("logged_in"));
            then(protocolState.is("data_received"));
            inSequence(sequence);

            oneOf(protocolListener).onDisconnected();
            when(protocolState.is("data_received"));
            then(protocolState.is("stopped"));

        }});

        protocol.start();
        logger.debug("Waiting for protocol to enter 'logged_in' state");
        synchronizer.waitUntil(protocolState.is("logged_in"));

        logger.debug("Sending meta request to server");
        logger.debug("Waiting for protocol to enter 'data_received' state");
        synchronizer.waitUntil(protocolState.is("data_received"));

        logger.debug("stopping the protocol");
        protocol.stop();

        logger.debug("Waiting for protocol to enter 'stopped' state");
        synchronizer.waitUntil(protocolState.is("stopped"), 2500);
    }
}
