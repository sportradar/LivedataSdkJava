/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/
package com.sportradar.sdk.common.networking;

import java.io.IOException;

/**
 * Represents a gateway capable of sending and receiving messages to or/and from other systems.
 *
 * @author uros.bregar
 */
public interface Gateway {

    /**
     * Sets the {@link GatewayListener} used to observe the current {@link Gateway} implementation.
     *
     * @param listener the {@link GatewayListener} used to observe the current {@link Gateway} implementation or a null reference if observation is not required.
     */
    void setListener(GatewayListener listener);

    /**
     * Sends data through the current {@link Gateway}.
     *
     * @param data data to send.
     * @throws IllegalArgumentException The {@code data} is a null reference or an empty array
     * @throws IOException connection problem
     */
    void sendData(byte[] data) throws IOException;

    /**
     * Attempts to establish a connection to the remote site.
     *
     * @throws IOException There was an error establishing the connection.
     */
    void connect() throws IOException;

    /**
     * Drops the established connection.
     *
     * @param dueToError - when false user wished to close connection; when true there was a problem and we had
     *                   to close it
     */
    void disconnect(boolean dueToError);

    /**
     * Gets a value specifying whether the connection to the remote site is established.
     *
     * @return Value specifying whether the connection to the remote site is established.
     */
    boolean isConnected();

    /**
     * Gets last timestamp in unix millis when the last msg was received
     *
     * @return timestamp when the last msg was received
     */
    long getLastReceivedMsgTimestamp();


    /**
     * Gets gateway id (hostname, port, ...)
     * @return gateway identification
     */
    String getId();
}
