/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Represents a message tokenizer base.
 */
public abstract class MessageTokenizerBase implements MessageTokenizer {

    /**
     * A {@link MessageTokenizerListener} implementation used to observe current {@link MessageTokenizerBase} class.
     */
    protected volatile MessageTokenizerListener listener;

    /**
     * Sets the listener used to observer this {@link MessageTokenizerListener} implementation.
     *
     * @param listener the listener used to observer this {@link MessageTokenizerListener} implementation.
     */
    public void setListener(MessageTokenizerListener listener) {
        this.listener = listener;
    }

    /**
     * Processes passed data. If passed data and any previously stored data represent a whole message the {@code MessageTokenizerListener.onMessageReady(byte[])} method is invoked
     *
     * @param stream A {@link InputStream} containing data to be tokenized.
     * @throws IllegalArgumentException the {@code stream} is a null reference.
     * @throws ProtocolException        the data could not be processed either because the buffer is full
     */
    @Override
    public abstract void processData(InputStream stream) throws SdkException;

    /**
     * Notifies the observer that data was tokenized.
     *
     * @param stream A {@link InputStream} instance containing the tokenized data.
     * @throws SdkException
     */
    protected void notifyListener(InputStream stream) throws SdkException {
        MessageTokenizerListener copy = this.listener;
        if (copy != null) {
            copy.onMessageReady(stream);
        }
    }
}
