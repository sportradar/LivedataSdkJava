/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * Represents a protocol which knows how to properly communicate with the server
 *
 * @param <I> Specifies the type of the messages received by the protocol
 * @param <O> Specifies the type of the message send by the protocol
 * @author uros.bregar
 */
public interface Protocol<I extends IncomingMessage, O extends OutgoingMessage> {

    /**
     * Starts the protocol - i.e. enables(starts) the communication with the server.
     *
     * @throws IllegalStateException The protocol is already started.
     */
    void start();

    /**
     * Reconnects the underlying feed
     */
    void reconnect();

    /**
     * Gets the value indicating whether the protocol is started - i.e if the communication with server is enabled.
     *
     * @return True if protocol is started. False otherwise.
     */
    boolean isStarted();

    /**
     * Sets the {@link ProtocolListener} used to observe the current {@link Protocol}.
     *
     * @param listener the {@link ProtocolListener} used to observe the current {@link Protocol} or a null reference.
     */
    void setListener(ProtocolListener<I> listener);

    /**
     * Sends the passed {@code message} instance to the server.
     *
     * @param message The message derived from {@link OutgoingMessage} to be send to the server.
     */
    boolean sendMessage(O message, boolean blocking) throws MessageException, ProtocolException;

    /**
     * Sends the passed data to the server as is.
     */
    void sendMessage(String data) throws MessageException, ProtocolException;

    /**
     * Stops the protocol - i.e. disables the communication with the server.
     *
     * @throws IllegalStateException The protocol is already stopped.
     */
    void stop();
}
