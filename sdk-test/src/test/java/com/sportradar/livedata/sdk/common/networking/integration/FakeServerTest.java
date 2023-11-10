package com.sportradar.livedata.sdk.common.networking.integration;

import com.sportradar.livedata.sdk.util.NetworkServerListener;
import com.sportradar.livedata.sdk.util.TcpServer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Disabled
public class FakeServerTest {

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit5Mockery() {{
        setThreadingPolicy(synchroniser);
    }};

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final NetworkServerListener listener = context.mock(NetworkServerListener.class);

    private final States serverState = context.states("serverState");

    private TcpServer server;

    @BeforeEach
    public void setUp() throws IOException {
        server = new TcpServer(executor, 5051);
        server.setListener(listener);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
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
