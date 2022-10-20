package com.sportradar.sdk.test;

import java.io.IOException;

/**
 * Represents a network server
 */
public interface NetworkServer {

    /**
     * Sets the {@code NetworkServerListener} instance used to observe the current {@link NetworkServer}.
     *
     * @param listener The {@link NetworkServerListener} used to observe the current {@link NetworkServer}.
     */
    void setListener(NetworkServerListener listener);

    /**
     * Asynchronously starts the current {@link NetworkServer}.
     *
     * @throws IOException The exception was thrown while opening the server.
     */
    void start() throws IOException;

    /**
     * Asynchronously stops the current {@link NetworkServer}
     */
    void stop();

    /**
     * Forcefully disconnects the currently connected client.
     *
     * @throws IllegalStateException the client is not connected.
     */
    void disconnectClient();

    /**
     * Sends data to the connected client
     *
     * @param data The data to be send.
     * @throws IOException           exception has benn thrown while sending data.
     * @throws IllegalStateException the client is not connected.
     */
    void sendData(byte[] data) throws IOException;
}
