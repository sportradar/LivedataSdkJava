package com.sportradar.sdk.test.integration;

import com.sportradar.sdk.test.NetworkServerListener;
import com.sportradar.sdk.test.TcpServer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Ignore
public class FakeOddsServerTest {

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final NetworkServerListener listener = context.mock(NetworkServerListener.class);

    private final States serverState = context.states("serverState");

    private TcpServer server;

    @Before
    public void setUp() throws IOException {
        server = new TcpServer(executor, 5051);
        server.setListener(listener);
    }

    @After
    public void tearDown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test(timeout = 500)
    public void serverNotifiesWhenStartedOrStopped() throws InterruptedException, IOException {
        serverState.startsAs("none");
        context.checking(new Expectations() {{
            oneOf(listener).onStarted();
            when(serverState.is("none"));
            then(serverState.is("started"));

            oneOf(listener).onStopped();
            when(serverState.is("started"));
            then(serverState.is("stopped"));
        }});

        server.start();
        synchroniser.waitUntil(serverState.is("started"), 100);
        server.stop();
        synchroniser.waitUntil(serverState.is("stopped"), 100);
    }
}
