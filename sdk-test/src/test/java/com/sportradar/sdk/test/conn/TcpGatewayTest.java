/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.conn;

import com.sportradar.sdk.common.networking.TcpGateway;
import org.apache.commons.net.DefaultSocketFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Unit tests for the {@link TcpGateway} class.
 */
public class TcpGatewayTest {

    private TcpGateway gateway;

    @Before
    public void setUp() {
        gateway = new TcpGateway(Executors.newCachedThreadPool(), new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
    }

    @Test(expected = IOException.class)
    public void throwsExceptionWhenConnectionFails() throws IOException {
        gateway.connect();
    }

    @Test
    public void doesNotThrowIfDisconnectCallWhenNotConnected() {
        gateway.disconnect(false);
    }

    @Test(expected = IllegalStateException.class)
    public void throwsIfSendingWhenDisconnected() throws IOException {
        gateway.sendData(new byte[]{0x00, 0x01});
    }
}
