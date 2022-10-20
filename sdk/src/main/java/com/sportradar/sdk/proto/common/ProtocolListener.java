/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.proto.dto.IncomingMessage;

/**
 * Represents a listener used to observe the {@code Protocol} implementation
 */
public interface ProtocolListener<I extends IncomingMessage> {

    /**
     * Invoked by the observed {@link Protocol} instance when it logs(registers) with the server.
     */
    void onLoggedIn();

    /**
     * Invoked by the observed {@link Protocol} instance when there is an error about which the user has to be notified.
     *
     * @param event Specifies the type of the occurred error.
     * @param data  Opaque additional data.
     */
    void onLinkEvent(FeedEventType event, Object data);

    /**
     * Invoked by the observed {@link Protocol} instance when a message is received from the server.
     *
     * @param message the received message.
     */
    void onMessageReceived(I message);

    /**
     * Invoked by the observed {@link Protocol} instance when the connection to the server is closed due to user request.
     */
    void onDisconnected();
}
