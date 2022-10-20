/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.proto.dto.MessageBase;

import java.io.InputStream;

/**
 * Represents a base class for message parsers.
 */
public abstract class MessageParserBase<T extends MessageBase> implements MessageParser<T> {

    private final MessageTokenizer tokenizer;

    /**
     * The {@link MessageParserListener} used to observe the current {@link MessageParserBase}
     */
    private volatile MessageParserListener<T> listener;

    /**
     * Initializes a new instance of the {@link MessageParserBase} class.
     *
     * @param tokenizer A {@link MessageTokenizer} implementation used to tokenize received data, or a null reference.
     */
    protected MessageParserBase(MessageTokenizer tokenizer) {
        this.tokenizer = tokenizer;

        setDependencyListeners();
    }

    /**
     * Sets listeners on injected dependencies.
     */
    private void setDependencyListeners() {
        if (tokenizer == null) {
            return;
        }
        tokenizer.setListener(new MessageTokenizerListener() {
            @Override
            public void onMessageReady(InputStream stream) throws SdkException {
                onDataReady(stream);
            }
        });
    }

    /**
     * When overridden in derived class, it processes the received data.
     *
     * @param stream A {@link InputStream} implementation containing the received data.
     */
    protected abstract void onDataReady(InputStream stream) throws SdkException;

    /**
     * Notifies the attached observer that message was successfully parsed.
     *
     * @param message the parsed message
     */
    protected void notifyMessageReady(T message) {
        MessageParserListener<T> copy = this.listener;
        if (copy != null) {
            copy.onMessageReady(message);
        }
    }

    /**
     * Processes passed data. If passed data and any previously stored data represent a whole message the {@code MessageParserListener.onMessageReady(T)} method is invoked
     *
     * @param stream A {@link InputStream} instance containing message data.
     * @throws IllegalArgumentException the {@code stream} is a null reference.
     * @throws ProtocolException        the data could not be processed either because the buffer is full, an un-expected character was encountered or there was an error while un-marshalling the message
     */
    public final void processData(InputStream stream) throws SdkException {
        if (tokenizer != null) {
            tokenizer.processData(stream);
        } else {
            onDataReady(stream);
        }
    }

    /**
     * Removes any data potentially left in the parser's buffer.
     */
    public void clear() {
        if (tokenizer != null) {
            tokenizer.clear();
        }
    }


    /**
     * Sets the {@link MessageParserListener} instance used to observe the current {@link MessageParserBase}
     *
     * @param listener The {@link MessageParserListener} instance used to observe the current {@link MessageParserBase} or a null reference.
     */
    public void setListener(MessageParserListener<T> listener) {
        this.listener = listener;
    }
}
