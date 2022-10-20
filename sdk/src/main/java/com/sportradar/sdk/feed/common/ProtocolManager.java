package com.sportradar.sdk.feed.common;

import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * Represents a class used to manage access to the underlying {@link com.sportradar.sdk.proto.common.Protocol}.
 */
public interface ProtocolManager<O extends OutgoingMessage, E extends EntityBase> {


    /**
     * Closes the underlying {@link com.sportradar.sdk.proto.common.Protocol} and components associated with it.
     */
    void close();

    /**
     * Opens the underlying {@link com.sportradar.sdk.proto.common.Protocol} and components associated with it
     */
    void open();

    /**
     * Reconnects the underlying feed
     */
    void reconnect();


    /**
     * Forwards the passed data as-is to the underlying protocol which sends it to the server.
     * There is no rate-limiting.
     *
     * @param data - outgoing data
     */
    void sendMessage(String data);


    /**
     * Sets the {@link ProtocolManagerListener} instance used to observe the current {@link ProtocolManager}
     *
     * @param listener the {@link ProtocolManagerListener} instance used to observe the current {@link ProtocolManager}
     */
    void setListener(ProtocolManagerListener<E> listener);
}
