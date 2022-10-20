/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.proto.dto.MessageBase;

import java.io.InputStream;

/**
 * Represents a message parser used to parse received byte arrays into messages of specified type
 *
 * @param <T> The type of the message created by the parser.
 */
public interface MessageParser<T extends MessageBase> {

    /**
     * Sets the listener used to observer this {@link MessageParser} implementation.
     *
     * @param listener the listener used to observer this {@link MessageParser} implementation.
     */
    void setListener(MessageParserListener<T> listener);

    /**
     * Processes passed data. If passed data and any previously stored data represent a whole message the {@code MessageParserListener.onMessageReady(T)} method is invoked
     *
     * @param stream A {@link InputStream} instance containing message data.
     * @throws IllegalArgumentException the {@code stream} is a null reference.
     * @throws ProtocolException        the data could not be processed either because the buffer is full, an un-expected character was encountered or there was an error while un-marshalling the message
     */
    void processData(InputStream stream) throws SdkException;

    /**
     * Gets the configured character encoding for the parser.
     *
     * @return - a string, i.e. UTF-8
     */
    String getEncoding();

    /**
     * Removes any data potentially left in the parser's buffer.
     */
    void clear();
}
