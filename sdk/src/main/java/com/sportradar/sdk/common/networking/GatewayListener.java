/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/
package com.sportradar.sdk.common.networking;

import java.io.InputStream;

/**
 * Represents a listener used to observe {@link Gateway} implementation.
 *
 * @author uros.bregar
 */
public interface GatewayListener {

    /**
     * Invoked by the observed {@link Gateway} instance when it receives new data from the server.
     *
     * @param stream The {@link InputStream}  containing the received data.
     */
    void onDataReceived(InputStream stream);

    /**
     * Invoked by the observed {@link Gateway} instance when it establishes a connection to the server.
     */
    void onConnected();

    /**
     * Invoked by the observed {@link Gateway} instance when it's connection to the server is dropped.
     *
     * @param ex The {@link Exception} which caused the connection to be dropped, or a null reference if disconnect operation was requested.
     */
    void onDisconnected(Exception ex);
}