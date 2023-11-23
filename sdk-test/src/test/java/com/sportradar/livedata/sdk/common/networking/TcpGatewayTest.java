/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common.networking;

import org.apache.commons.net.DefaultSocketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link TcpGateway} class.
 */
class TcpGatewayTest {

    private TcpGateway gateway;

    @BeforeEach
    public void setUp() {
        gateway = new TcpGateway(Executors.newCachedThreadPool(), new DefaultSocketFactory(), new InetSocketAddress("localhost", 5055), 1024);
    }

    @Test
    void throwsExceptionWhenConnectionFails() {
        Exception exception = assertThrows(IOException.class, () -> {
            gateway.connect();
        });
    }

    @Test
    void doesNotThrowIfDisconnectCallWhenNotConnected() {
        gateway.disconnect(false);
    }

    @Test
    void throwsIfSendingWhenDisconnected() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            gateway.sendData(new byte[]{0x00, 0x01});
        });
    }
}
