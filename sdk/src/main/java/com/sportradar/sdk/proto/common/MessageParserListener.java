/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.MessageBase;

/**
 * A listener used to observe {@code MessageParser} instance.
 *
 * @param <T> The type of the message created by the associated parser.
 */
public interface MessageParserListener<T extends MessageBase> {

    /**
     * Invoked when the observed {@link MessageParser} parsed data which represented a whole message.
     *
     * @param message Message constructed from the received data.
     */
    void onMessageReady(T message);
}
