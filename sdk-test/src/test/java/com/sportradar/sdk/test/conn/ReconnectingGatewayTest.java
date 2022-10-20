package com.sportradar.sdk.test.conn;

import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.networking.ReconnectingGateway;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.timer.TimerListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ReconnectingGatewayTest {

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};
    GatewayListener gatewayListenerMock = context.mock(GatewayListener.class);
    private final States state = context.states("state");
    private final Sequence sequence = context.sequence("sequence");

    private ReconnectingGateway gateway;
    private FakeGateway fakeGateway;
    private ReconnectingGateway reconnectingGateway;
    private final int initialReconnectInterval = 10;
    private final int reconnectInterval = 10;
    private Timer reconnectTimer;
    private TimerListenerImpl timerListener;
    private final Logger logger = LoggerFactory.getLogger(ReconnectingGatewayTest.class);

    @Before
    public void setUp() {
        fakeGateway = new FakeGateway();
        reconnectTimer = new PeriodicTimer(Executors.newScheduledThreadPool(2));
        timerListener = new TimerListenerImpl();
        reconnectTimer.setListener(timerListener);
        this.gateway = new ReconnectingGateway(
                fakeGateway,
                reconnectTimer,
                Duration.millis(initialReconnectInterval),
                Duration.millis(reconnectInterval));
        //this.gateway.setListener(gatewayListenerMock);

        this.reconnectingGateway = new ReconnectingGateway(
                fakeGateway,
                reconnectTimer,
                Duration.millis(initialReconnectInterval),
                Duration.millis(reconnectInterval));
        this.reconnectingGateway.setListener(gatewayListenerMock);
    }

    @After
    public void tearDown() {
    }

    //@Test TODO: fix //(timeout = 500)
    public void connectionIsAttemptedUntilSucceeded() throws Exception {
        final int CONNECTION_REQUESTS = 10;
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, CONNECTION_REQUESTS);
        this.fakeGateway.setFakeGatewayListener(impl);

        context.checking(new Expectations() {{
            oneOf(gatewayListenerMock).onConnected();
            then(state.is("connection_approved"));
            inSequence(sequence);

            oneOf(gatewayListenerMock).onDisconnected(null);
            when(state.is("connection_approved"));
            then(state.is("disconnected"));
            inSequence(sequence);

            for (int i = 1; i < CONNECTION_REQUESTS; ++i) {
                oneOf(gatewayListenerMock).onConnected();
                when(state.is("disconnected"));
                then(state.is("reconnecting"));
                inSequence(sequence);
                oneOf(gatewayListenerMock).onDisconnected(null);
                then(state.is("disconnected"));
            }

            then(state.is("disconnected"));
            inSequence(sequence);
        }});

        gateway.connect();
        synchroniser.waitUntil(state.is("connection_approved"));
        assertThat(impl.getConnectionRequests(), is(1));
        assertThat(gateway.isConnected(), is(true));

        for (int i = 1; i < CONNECTION_REQUESTS; ++i) {
            assertThat(fakeGateway.isConnected(), is(true));
            assertThat(gateway.isConnected(), is(true));

            fakeGateway.disconnect(true);
            assertThat(fakeGateway.isConnected(), is(false));
            assertThat(gateway.isConnected(), is(false));
            assertThat(impl.getConnectionRequests(), is(i));
            synchroniser.waitUntil(state.is("disconnected"));
            //Thread.sleep(reconnectInterval + 1);
        }
        assertThat(fakeGateway.isConnected(), is(true));
        assertThat(gateway.isConnected(), is(true));

        gateway.disconnect(true);
        assertThat(gateway.isConnected(), is(false));
        assertThat(fakeGateway.isConnected(), is(false));
        synchroniser.waitUntil(state.is("disconnected"));
        assertThat(impl.getConnectionRequests(), is(CONNECTION_REQUESTS));
        context.assertIsSatisfied();
    }

    @Test//(timeout = 500)
    public void connectionDataIsSend() throws Exception {
        final int CONNECTION_REQUESTS = 1;
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, CONNECTION_REQUESTS);
        this.fakeGateway.setFakeGatewayListener(impl);

        final byte[] data = {0x01, 0x02, 0x03, 0x04};

        context.checking(new Expectations() {{
            oneOf(gatewayListenerMock).onConnected();
            then(state.is("connection_approved"));
            inSequence(sequence);

            oneOf(gatewayListenerMock).onDataReceived(with(any(InputStream.class)));
            when(state.is("connection_approved"));
            then(state.is("data_received"));
            inSequence(sequence);

            oneOf(gatewayListenerMock).onDisconnected(null);
            then(state.is("connection_approved"));
            then(state.is("disconnected"));
            inSequence(sequence);
        }});

        gateway.connect();
        synchroniser.waitUntil(state.is("connection_approved"));
        assertThat(impl.getConnectionRequests(), is(CONNECTION_REQUESTS));

        gateway.sendData(data);
        assertThat(impl.getConnectionRequests(), is(CONNECTION_REQUESTS));
        synchroniser.waitUntil(state.is("data_received"));
        assertThat(impl.getData(), is(data));

        gateway.disconnect(true);
        assertThat(impl.getConnectionRequests(), is(CONNECTION_REQUESTS));
        synchroniser.waitUntil(state.is("disconnected"));
        context.assertIsSatisfied();
    }

    @Test//(timeout = 500)
    public void onDisconnectedWithExceptionParameter() throws Exception {
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, 1);
        this.fakeGateway.setFakeGatewayListener(impl);
        Exception exception = new Exception("The server closed the connection");

        context.checking(new Expectations() {{
            atLeast(1).of(gatewayListenerMock).onConnected();
            then(state.is("connected"));
            inSequence(sequence);

            oneOf(gatewayListenerMock).onDisconnected(null);
            then(state.is("disconnected"));
            inSequence(sequence);
        }});

        gateway.connect();
        synchroniser.waitUntil(state.is("connected"));

        gateway.disconnect(true);
        synchroniser.waitUntil(state.is("disconnected"));
        context.assertIsSatisfied();
    }

    @Test
    public void onDataReceived() throws Exception {
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, 1);
        this.fakeGateway.setFakeGatewayListener(impl);

        context.checking(new Expectations() {{
            atLeast(1).of(gatewayListenerMock).onConnected();
            then(state.is("connection_approved"));
            inSequence(sequence);

            oneOf(gatewayListenerMock).onDataReceived(with(any(InputStream.class)));
            when(state.is("connection_approved"));
            then(state.is("data_received"));
            inSequence(sequence);

            oneOf(gatewayListenerMock).onDisconnected(null);
            then(state.is("disconnected"));
            inSequence(sequence);
        }});

        gateway.connect();
        synchroniser.waitUntil(state.is("connection_approved"));

        gateway.sendData(new byte[0]);
        synchroniser.waitUntil(state.is("data_received"));

        gateway.disconnect(true);

        context.assertIsSatisfied();
    }

    @Test
    public void testReconnectionAttempt() throws Exception {

        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, 5);
        this.fakeGateway.setFakeGatewayListener(impl);
        this.gateway.setListener(new GatewayListener() {

            /**
             * Invoked by the observed {@link Gateway} instance when it receives new data from the server.
             *
             * @param stream The {@link java.io.InputStream}  containing the received data.
             */
            @Override
            public void onDataReceived(InputStream stream) {

            }

            /**
             * Invoked by the observed {@link Gateway} instance when it establishes a connection to the server.
             */
            @Override
            public void onConnected() {

            }

            /**
             * Invoked by the observed {@link Gateway} instance when it's connection to the server is dropped.
             *
             * @param ex The {@link Exception} which caused the connection to be dropped, or a null reference if disconnect operation was requested.
             */
            @Override
            public void onDisconnected(Exception ex) {

            }
        });

        context.checking(new Expectations() {{
            atLeast(1).of(gatewayListenerMock).onConnected();
            then(state.is("connection_approved"));

            oneOf(gatewayListenerMock).onDisconnected(null);
            atLeast(1).of(gatewayListenerMock).onConnected();
        }});

        gateway.connect();
        synchroniser.waitUntil(state.is("connection_approved"));

        assertThat(reconnectingGateway.isConnected(), equalTo(true));
        assertThat(fakeGateway.isConnected(), equalTo(true));
        //TODO: add more test
    }

    private class TimerListenerImpl implements TimerListener {

        /**
         * Invoked by the observed {@link Timer} implementation when the timer's interval elapses.
         */
        @Override
        public void onTick() {
            logger.debug("TimerListenerImpl.onTick");
        }
    }
}
