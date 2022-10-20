package com.sportradar.sdk.test.conn;

import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.networking.ConnectionMonitoringGateway;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConnectionMonitoringGatewayTest {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionMonitoringGatewayTest.class);
    GatewayListener gatewayListenerMock = null;
    private final Synchroniser synchroniser = new Synchroniser();
    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private States state;
    private Sequence sequence;
    private ConnectionMonitoringGateway gateway;
    private FakeGateway fakeGateway;
    private Timer timer;
    private final Duration checkInterval = new Duration(10);
    private final Duration noActivityTimeout = new Duration(15);

    @Before
    public void setUp() {
        gatewayListenerMock = context.mock(GatewayListener.class);
        state = context.states("state");
        sequence = context.sequence("sequence");
        this.fakeGateway = new FakeGateway();
        timer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        this.gateway = new ConnectionMonitoringGateway(
                fakeGateway,
                timer,
                checkInterval,
                noActivityTimeout,
                false
        );
        this.gateway.setListener(gatewayListenerMock);
    }

    //TODO: @Test(timeout = 10000)
    public void connectionClosedDuringUnstableConnection() throws Exception {
        final int CONNECTION_REQUESTS = 3;
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, CONNECTION_REQUESTS);
        this.fakeGateway.setFakeGatewayListener(impl);
        assertThat(this.fakeGateway.getSetGatewayListenerCounter(), is(1));

        context.checking(new Expectations() {{
            atLeast(1).of(gatewayListenerMock).onConnected();
            then(state.is("connection_approved"));
            inSequence(sequence);

            atLeast(1).of(gatewayListenerMock).onDisconnected(null);
            then(state.is("disconnected"));
            inSequence(sequence);
        }});

        gateway.connect();
        logger.info("waiting for connection approved");
        synchroniser.waitUntil(state.is("connection_approved"));
        logger.info("wait for connection approved done");
        assertThat(impl.getConnectionRequests(), is(2));
        assertThat(impl.getDisconnectedRequests(), is(1));
        assertThat(gateway.isConnected(), is(true));

        logger.info("waiting for disconnected");
        synchroniser.waitUntil(state.is("disconnected"));
        logger.info("wait for disconnected done");
        assertThat(impl.getConnectionRequests(), greaterThanOrEqualTo(1));
        assertThat(impl.getDisconnectedRequests(), greaterThanOrEqualTo(0));

        assertThat(this.fakeGateway.getSetGatewayListenerCounter(), is(1));
        assertThat(this.fakeGateway.getDisconnectCounter(), greaterThanOrEqualTo(0));
        gateway.disconnect(false);
        context.assertIsSatisfied();
    }

    @Test(timeout = 5000)
    public void onDisconnectedWithExceptionParameter() throws Exception {
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, 1);
        this.fakeGateway.setFakeGatewayListener(impl);

        context.checking(new Expectations() {{
            atLeast(1).of(gatewayListenerMock).onConnected();
            then(state.is("connected"));

            atLeast(1).of(gatewayListenerMock).onDisconnected((Exception) null);
            then(state.is("disconnected"));
        }});

        assertThat(gateway.isConnected(), is(false));
        assertThat(fakeGateway.isConnected(), is(false));

        gateway.connect();
        assertThat(gateway.isConnected(), is(true));
        assertThat(fakeGateway.isConnected(), is(true));
        assertThat(impl.getConnectionRequests(), is(1));
        assertThat(impl.getDisconnectedRequests(), is(0));
        synchroniser.waitUntil(state.is("connected"));

        gateway.disconnect(false);
        assertThat(impl.getConnectionRequests(), greaterThanOrEqualTo(1));
        assertThat(impl.getDisconnectedRequests(), greaterThanOrEqualTo(1));
        synchroniser.waitUntil(state.is("disconnected"));

        assertThat(fakeGateway.isConnected(), is(false));
        assertThat(gateway.isConnected(), is(false));
        context.assertIsSatisfied();
    }

    //TODO: fix @Test(timeout = 5000)
    public void onDataReceived() throws Exception {
        FakeGatewayListenerImpl impl = new FakeGatewayListenerImpl(fakeGateway, 1);
        this.fakeGateway.setFakeGatewayListener(impl);

        context.checking(new Expectations() {{
            atLeast(1).of(gatewayListenerMock).onConnected();
            //then(state.is("connected"));
            //inSequence(sequence);

            atLeast(1).of(gatewayListenerMock).onDataReceived(with(any(InputStream.class)));
            //then(state.is("data_received"));
            //inSequence(sequence);

            //never(gatewayListenerMock).onDisconnected((Exception)null);
        }});

        assertThat(gateway.isConnected(), is(false));
        assertThat(fakeGateway.isConnected(), is(false));

        gateway.connect();
        assertThat(gateway.isConnected(), is(true));
        assertThat(fakeGateway.isConnected(), is(true));
        //synchroniser.waitUntil(state.is("connected"));

        gateway.sendData(new byte[0]);
        assertThat(gateway.isConnected(), is(true));
        assertThat(fakeGateway.isConnected(), is(true));
        //synchroniser.waitUntil(state.is("data_received"));

        //gateway.disconnect(true);
        context.assertIsSatisfied();
    }
}
