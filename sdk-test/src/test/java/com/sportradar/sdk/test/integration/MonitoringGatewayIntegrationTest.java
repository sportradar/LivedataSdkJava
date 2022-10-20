package com.sportradar.sdk.test.integration;

import com.sportradar.sdk.common.classes.DevHelper;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.networking.ReconnectingGateway;
import com.sportradar.sdk.common.networking.TcpGateway;
import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.networking.ConnectionMonitoringGateway;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.test.FakeOddsServer;
import com.sportradar.sdk.test.NullSdkLogger;
import com.sportradar.sdk.test.TcpServer;
import org.apache.commons.net.DefaultSocketFactory;
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

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Integration tests for the {@link ConnectionMonitoringGateway} class
 */

@Ignore
public class MonitoringGatewayIntegrationTest {

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};

    private final GatewayListener clientListener = context.mock(GatewayListener.class);
    private final States clientState = context.states("clientState");
    private final Sequence sequence = context.sequence("sequence");
    private final ExecutorService executor = Executors.newCachedThreadPool();


    private FakeOddsServer serverDriver;
    private Gateway client;

    @Before
    public void setUp() throws IOException, JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(OutgoingMessage.class, IncomingMessage.class);
        JaxbBuilder JaxbBuilder = new JaxbFactory(jaxbContext);
        MessageParser<OutgoingMessage> messageParser = new JaxbMessageParser<>(JaxbBuilder, null, new NullSdkLogger());
        MessageWriter<IncomingMessage> messageWriter = new JaxbMessageWriter<>(JaxbBuilder);
        LiveScoutSettings serverSettings = DefaultSettingsBuilderHelper.getLiveScout().build();
        this.serverDriver = new FakeOddsServer(new TcpServer(executor, 5055), messageParser, messageWriter, serverSettings);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Gateway actualGateway = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
        Timer reconnectTimer = new PeriodicTimer(executorService);

        Gateway reconnectingGateway = new ReconnectingGateway(actualGateway, reconnectTimer, Duration.millis(300), Duration.millis(300));

        Timer monitorTimer = new PeriodicTimer(executorService);
        this.client = new ConnectionMonitoringGateway(reconnectingGateway, monitorTimer, Duration.millis(300), Duration.millis(500), false);
        this.client.setListener(clientListener);
    }

    @Test(timeout = 3000)
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

    @Test(timeout = 3000)
    public void gatewayDetectWhenServerDropsTheConnection() throws IOException, InterruptedException {
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

    @Test(timeout = 3000)
    public void gatewayReconnectsIfConnectionClosed() throws IOException, InterruptedException {
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

    @Test(timeout = 3000)
    public void gatewayReceivesData() throws IOException, InterruptedException, SdkException {
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

    @Test(timeout = 3000)
    public void gatewayDisconnectsWhenAliveTimeoutElapses() throws IOException, InterruptedException {
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

    @Test(timeout = 3000)
    public void gatewayDoesNotDisconnectIfReceivingAliveMessages() throws IOException, InterruptedException, SdkException {
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
