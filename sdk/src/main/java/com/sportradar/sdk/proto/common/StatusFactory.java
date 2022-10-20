package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * Represents a class used to build requests to be send to the betradar feed.
 */
public interface StatusFactory {

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-in request.
     *
     * @param username the bookmaker's username
     * @param password the bookmaker's password
     * @return a {@link OutgoingMessage} representing a log-in request.
     */
    OutgoingMessage buildLoginRequest(String username, String password);

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-out request.
     *
     * @return a {@link OutgoingMessage} representing a log-out request or a null reference if protocol associate with
     * the current factory does not support log-out
     */
    OutgoingMessage buildLogOutRequest();
}
