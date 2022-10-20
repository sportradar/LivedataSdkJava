package com.sportradar.sdk.feed.common;


import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.feed.common.entities.EntityBase;


/**
 * Represents a listener used to observe the {@link ProtocolManager}
 */
public interface ProtocolManagerListener<E extends EntityBase> {

    /**
     * Invoked by the observed {@link ProtocolManager} when the connection to the target server is lost
     */
    void onDisconnected();

    /**
     * Invoked by the observed {@link ProtocolManager} instance when there is an eventType about which the user has to be notified.
     *
     * @param eventType Specifies the type of the occurred eventType.
     */
    void onFeedEvent(FeedEventType eventType);

    /**
     * Invoked by the observed {@link ProtocolManager} when it logs in the server
     */
    void onLoggedIn();

    /**
     * Invoked by the observed {@link ProtocolManager} when the message from the server is received.
     *
     * @param message The {@link EntityBase} derived type representing the received message
     */
    void onMessageReceived(E message);
}
