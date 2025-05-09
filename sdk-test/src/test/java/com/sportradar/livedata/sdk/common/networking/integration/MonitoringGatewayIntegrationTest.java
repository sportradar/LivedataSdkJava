package com.sportradar.livedata.sdk.common.networking.integration;

import com.sportradar.livedata.sdk.common.classes.DevHelper;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.networking.GatewayListener;
import com.sportradar.livedata.sdk.common.networking.ReconnectingGateway;
import com.sportradar.livedata.sdk.common.networking.TcpGateway;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import com.sportradar.livedata.sdk.common.networking.ConnectionMonitoringGateway;
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
import java.util.concurrent.ScheduledExecutorService;

/**
 * Integration tests for the {@link ConnectionMonitoringGateway} class
 */

@Disabled
class MonitoringGatewayIntegrationTest {

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
        LiveScoutSettings serverSettings = LiveScoutSettings.builder(false).build();
        this.serverDriver = new FakeServer(new TcpServer(executor, 5055), messageParser, messageWriter, serverSettings);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Gateway actualGateway = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
        Timer reconnectTimer = new PeriodicTimer(executorService);

        Gateway reconnectingGateway = new ReconnectingGateway(actualGateway, reconnectTimer, Duration.millis(300), Duration.millis(300));

        Timer monitorTimer = new PeriodicTimer(executorService);
        this.client = new ConnectionMonitoringGateway(reconnectingGateway, monitorTimer, Duration.millis(300), Duration.millis(500), false);
        this.client.setListener(clientListener);
    }

    @SuppressWarnings("unused") // not used but may be needed in future
    @Timeout(3)
    public void gatewayConnectsToTheServerWhenServerStarted() throws IOException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDisconnected(with(any(Exception.class)));
            when(clientState.is("connected"));
            then(clientState.is("disconnected"));


        }});

        serverDriver.scheduleStart(1500);
        client.connect();
        serverDriver.waitForServerToStart(2000);

        synchroniser.waitUntil(clientState.is("connected"));

        client.disconnect(false);
        serverDriver.stop();
    }

    @Test
    @Timeout(3)
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
        client.disconnect(false);
    }

    @Test
    @Timeout(3)
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
    @Timeout(3)
    void gatewayReceivesData() throws IOException, InterruptedException, SdkException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDataReceived(with(any(InputStream.class)));
            then(clientState.is("data_received"));

            allowing(clientListener).onDisconnected(with(any(Exception.class)));
        }});

        serverDriver.start();
        client.connect();
        synchroniser.waitUntil(clientState.is("connected"));

        serverDriver.sendMessage();
        synchroniser.waitUntil(clientState.is("data_received"));

        client.disconnect(false);
        serverDriver.stop();
    }

    @Test
    @Timeout(3)
    void gatewayDisconnectsWhenAliveTimeoutElapses() throws IOException, InterruptedException {
        //This test fails when the debugger is attached because the gateway does not check alive messages
        if (DevHelper.isDebuggerAttached()) {
            return;
        }
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));
            inSequence(sequence);

            oneOf(clientListener).onDisconnected(with(any(Exception.class)));
            then(clientState.is("disconnected"));
            inSequence(sequence);

            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));
            inSequence(sequence);

            oneOf(clientListener).onDisconnected(with(any(Exception.class)));
            then(clientState.is("disconnected"));
            inSequence(sequence);


        }});

        serverDriver.start();
        client.connect();
        synchroniser.waitUntil(clientState.is("connected"));

        //The client should automatically disconnect because it doesn't receive alive message
        synchroniser.waitUntil(clientState.is("disconnected"));

        //The client should automatically connect because of the auto-reconnect functionality
        synchroniser.waitUntil(clientState.is("connected"));

        client.disconnect(false);
        serverDriver.stop();
    }

    @Test
    @Timeout(3)
    void gatewayDoesNotDisconnectIfReceivingAliveMessages() throws IOException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDataReceived(with(any(InputStream.class)));
            then(clientState.is("data_received"));

            allowing(clientListener).onDisconnected(with(any(Exception.class)));
        }});

        serverDriver.start();
        client.connect();

        synchroniser.waitUntil(clientState.is("connected"));
        serverDriver.startSendingAliveMessage();

        synchroniser.waitUntil(clientState.is("data_received"), 500);
        serverDriver.stopSendingAliveMessage();

        client.disconnect(false);
        serverDriver.stop();
    }
}
