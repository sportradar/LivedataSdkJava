/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.enums;

/**
 * Enumerates types of events that can occur on the feed and the user is notified about them.
 */
public enum FeedEventType {

    /**
     * Specifies that the connection to the server was opened without the user's explicit request.
     */
    CONNECTED,

    /**
     * Specifies that the credentials passed to the server were accepted and logged in response was received by the SDK
     */
    AUTHENTICATED,

    /**
     * Specifies that the connection to the server was closed without the user's explicit request.
     */
    DISCONNECTED,

    /**
     * Specifies that the message received from the server could not be parsed
     */
    PARSE_ERROR,


    /**
     * Specifies that the event dispatcher got filled up. The feed will close, no automatic restart will follow
     */
    DISPATCHER_FULL
}
