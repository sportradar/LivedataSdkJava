package com.sportradar.livedata.sdk.common.networking.integration;

import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.networking.GatewayListener;
import com.sportradar.livedata.sdk.common.networking.ReconnectingGateway;
import com.sportradar.livedata.sdk.common.networking.TcpGateway;
import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import com.sportradar.livedata.sdk.proto.common.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.util.FakeServer;
import com.sportradar.livedata.sdk.util.TcpServer;
import org.apache.commons.net.DefaultSocketFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
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
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Integration tests for the {@link ReconnectingGateway} class.
 */
@Disabled
class ReconnectingGatewayIntegrationTest {

    private final static Exception nullException = null;

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit5Mockery() {{
        setThreadingPolicy(synchroniser);
    }};

    private final GatewayListener clientListener = context.mock(GatewayListener.class);
    private final States clientState = context.states("clientState");
    private final Sequence sequence = context.sequence("sequence");
    private final ExecutorService executor = Executors.newCachedThreadPool();


    private FakeServer serverDriver;
    private Gateway client;


    @BeforeEach
    public void setUp() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(OutgoingMessage.class, IncomingMessage.class);
        JaxbBuilder JaxbBuilder = new JaxbFactory(jaxbContext);
        MessageParser<OutgoingMessage> messageParser = new JaxbMessageParser<>(JaxbBuilder, null);
        MessageWriter<IncomingMessage> messageWriter = new JaxbMessageWriter<>(JaxbBuilder);
        AuthSettings authSettings = new AuthSettings("1", "key");
        LiveScoutSettings serverSettings = LiveScoutSettings.builder(false)
                .authSettings(authSettings)
                .build();
        this.serverDriver = new FakeServer(new TcpServer(executor, 5055), messageParser, messageWriter, serverSettings);

        Gateway actualGateway = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
        Timer reconnectTimer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        this.client = new ReconnectingGateway(actualGateway, reconnectTimer, Duration.millis(300), Duration.millis(300));
        this.client.setListener(clientListener);
    }

    @Test
    void gatewayConnectsToServerWhenServerIsStarted() throws IOException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            allowing(clientListener).onDisconnected(nullException);
        }});

        serverDriver.scheduleStart(1500);
        client.connect();
        serverDriver.waitForServerToStart(2000);

        synchroniser.waitUntil(clientState.is("connected"));

        client.disconnect(false);
        serverDriver.stop();
    }

    @Test
    void gatewayDetectWhenServerDropsTheConnection() throws IOException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDisconnected(with(any(Exception.class)));
            then(clientState.is("disconnected"));
        }});

        serverDriver.start();
        client.connect();

        synchroniser.waitUntil(clientState.is("connected"));
        serverDriver.stop();
        synchroniser.waitUntil(clientState.is("disconnected"));
    }

    @Test
    @Timeout(5)//(value = 5000, unit = TimeUnit.MICROSECONDS)
    void gatewayReconnectsIfConnectionClosed() throws IOException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));
            inSequence(sequence);

            atLeast(1).of(clientListener).onDisconnected(with(any(Exception.class)));
            then(clientState.is("disconnected"));
            inSequence(sequence);

            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));
        }});

        serverDriver.start();
        client.connect();
        synchroniser.waitUntil(clientState.is("connected"));

        serverDriver.disconnectClient();
        synchroniser.waitUntil(clientState.is("disconnected"));

        synchroniser.waitUntil(clientState.is("connected"));
        client.disconnect(false);
        serverDriver.stop();
    }

    @Test
    void gatewayReceivesData() throws IOException, InterruptedException, SdkException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDataReceived(with(any(InputStream.class)));
            then(clientState.is("data_received"));

            allowing(clientListener).onDisconnected(nullException);
        }});

        serverDriver.start();
        client.connect();
        synchroniser.waitUntil(clientState.is("connected"));

        serverDriver.sendMessage();
        synchroniser.waitUntil(clientState.is("data_received"));

        client.disconnect(false);
        serverDriver.stop();
    }
}
