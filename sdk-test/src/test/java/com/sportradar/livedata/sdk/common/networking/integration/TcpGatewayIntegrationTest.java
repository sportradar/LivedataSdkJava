package com.sportradar.livedata.sdk.common.networking.integration;

import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.networking.GatewayListener;
import com.sportradar.livedata.sdk.common.networking.TcpGateway;
import com.sportradar.livedata.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.proto.common.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.util.FakeServer;
import com.sportradar.livedata.sdk.util.NullSdkLogger;
import com.sportradar.livedata.sdk.util.TcpServer;
import org.apache.commons.net.DefaultSocketFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.jupiter.api.*;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Contains end-to-end tests for the SDK
 */
@Disabled
public class TcpGatewayIntegrationTest {

    private final static Exception nullException = null;

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit5Mockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final GatewayListener clientListener = context.mock(GatewayListener.class);

    private final States clientState = context.states("clientState");

    private JAXBContext jaxbContext;
    private MessageParser<OutgoingMessage> messageParser;
    private MessageWriter<IncomingMessage> messageWriter;
    private FakeServer serverDriver;
    private Gateway client;

    @BeforeEach
    public void setUp() throws IOException, JAXBException {
        jaxbContext = JAXBContext.newInstance(OutgoingMessage.class, IncomingMessage.class);
        JaxbBuilder builder = new JaxbFactory(jaxbContext);

        messageParser = new JaxbMessageParser<>(builder, null, new NullSdkLogger());
        messageWriter = new JaxbMessageWriter<>(builder);

        LiveScoutSettings serverSettings = DefaultSettingsBuilderHelper.getLiveScout().build();
        serverDriver = new FakeServer(new TcpServer(executor, 5055), messageParser, messageWriter, serverSettings);
        client = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
        client.setListener(clientListener);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Timeout(1)
    public void sdkStartsAndStopsConnection() throws InterruptedException, IOException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDisconnected(nullException);
            when(clientState.is("connected"));
            then(clientState.is("disconnected"));
        }});

        serverDriver.start();
        client.connect();

        synchroniser.waitUntil(clientState.is("connected"), 400);
        serverDriver.hasClientConnected();

        client.disconnect(false);
        synchroniser.waitUntil(clientState.is("disconnected"), 400);

        serverDriver.stop();
    }

    @Timeout(1)
    public void gatewayDetectsWhenServerDropsConnection() throws InterruptedException, IOException {
        context.checking(new Expectations() {{
            oneOf(clientListener).onConnected();
            then(clientState.is("connected"));

            oneOf(clientListener).onDisconnected(with(any(Exception.class)));
            when(clientState.is("connected"));
            then(clientState.is("disconnected"));
        }});

        serverDriver.start();
        client.connect();
        synchroniser.waitUntil(clientState.is("connected"), 200);

        serverDriver.stop();
        synchroniser.waitUntil(clientState.is("disconnected"), 200);
    }

    @Timeout(5)
    public void gatewayReceivesData() throws InterruptedException, IOException, SdkException {
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
