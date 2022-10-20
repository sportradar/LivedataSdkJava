package com.sportradar.sdk.test.integration;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.networking.ReconnectingGateway;
import com.sportradar.sdk.common.networking.TcpGateway;
import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
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

/**
 * Integration tests for the {@link ReconnectingGateway} class.
 */
@Ignore
public class ReconnectingGatewayIntegrationTest {

    private final static Exception nullException = null;

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
        LiveScoutSettings serverSettings = DefaultSettingsBuilderHelper.getLiveScout()
                .setUsername("1")
                .setPassword("key")
                .build();
        this.serverDriver = new FakeOddsServer(new TcpServer(executor, 5055), messageParser, messageWriter, serverSettings);

        Gateway actualGateway = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
        Timer reconnectTimer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        this.client = new ReconnectingGateway(actualGateway, reconnectTimer, Duration.millis(300), Duration.millis(300));
        this.client.setListener(clientListener);
    }

    @Test
    public void gatewayConnectsToServerWhenServerIsStarted() throws IOException, InterruptedException {
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
    }

    @Test(timeout = 5000)
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

    @Test
    public void gatewayReceivesData() throws IOException, InterruptedException, SdkException {
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
