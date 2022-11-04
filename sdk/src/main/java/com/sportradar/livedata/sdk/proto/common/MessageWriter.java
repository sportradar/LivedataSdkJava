/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.proto.common;

import com.sportradar.livedata.sdk.proto.dto.MessageBase;

/**
 * Represents a writer capable of writing message data to byte array
 *
 * @param <T> Specifies the type of the messages recognized by the writer.
 */
public interface MessageWriter<T extends MessageBase> {

    /**
     * Writes the passed message to the returned {@code byte[]}
     *
     * @param message The message to be written to the array.
     * @return The {@code byte[]} containing message data.
     * @throws ProtocolException thrown when an un-expected situation is encountered.
     * @throws MessageException The {@code message} could not be written to specified media
     */
    byte[] write(T message) throws ProtocolException, MessageException;

    /**
     * Gets the configured character encoding for the writer.
     *
     * @return a string, i.e. UTF-8
     */
    String getEncoding();
}
