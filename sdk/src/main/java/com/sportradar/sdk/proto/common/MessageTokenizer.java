/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;

import java.io.InputStream;

/**
 * Represents a message parser used to parse received byte arrays into messages of specified type
 */
public interface MessageTokenizer {

    /**
     * Sets the listener used to observer this {@link MessageTokenizerListener} implementation.
     *
     * @param listener the listener used to observer this {@link MessageTokenizerListener} implementation.
     */
    void setListener(MessageTokenizerListener listener);

    /**
     * Processes passed data. If passed data and any previously stored data represent a whole message the {@code MessageParserListener.onMessageReady(T)} method is invoked
     *
     * @param stream A {@link InputStream} instance containing the message data.
     * @throws IllegalArgumentException the {@code stream} is a null reference.
     * @throws ProtocolException        the data could not be processed either because the buffer is full, an un-expected character was encountered or there was an error while un-marshalling the message
     */
    void processData(InputStream stream) throws SdkException;

    /**
     * Clears and temporary data stored in the tokenizer.
     */
    void clear();
}
