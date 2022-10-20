package com.sportradar.sdk.test.integration;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.networking.TcpGateway;
import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.test.FakeOddsServer;
import com.sportradar.sdk.test.NullSdkLogger;
import com.sportradar.sdk.test.TcpServer;
import org.apache.commons.net.DefaultSocketFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
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
import java.util.concurrent.TimeUnit;

/**
 * Contains end-to-end tests for the SDK
 */
@Ignore
public class TcpGatewayIntegrationTest {

    private final static Exception nullException = null;

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final GatewayListener clientListener = context.mock(GatewayListener.class);

    private final States clientState = context.states("clientState");

    private JAXBContext jaxbContext;
    private MessageParser<OutgoingMessage> messageParser;
    private MessageWriter<IncomingMessage> messageWriter;
    private FakeOddsServer serverDriver;
    private Gateway client;

    @Before
    public void setUp() throws IOException, JAXBException {
        jaxbContext = JAXBContext.newInstance(OutgoingMessage.class, IncomingMessage.class);
        JaxbBuilder builder = new JaxbFactory(jaxbContext);

        messageParser = new JaxbMessageParser<>(builder, null, new NullSdkLogger());
        messageWriter = new JaxbMessageWriter<>(builder);

        LiveScoutSettings serverSettings = DefaultSettingsBuilderHelper.getLiveScout().build();
        serverDriver = new FakeOddsServer(new TcpServer(executor, 5055), messageParser, messageWriter, serverSettings);
        client = new TcpGateway(executor, new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
        client.setListener(clientListener);
    }

    @After
    public void tearDown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test(timeout = 1000)
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

    @Test(timeout = 1000)
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

    @Test(timeout = 5000)
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
