/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.MessageBase;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link MessageWriter} implementation which uses JAXB to marshal the messages
 *
 * @author uros.bregar
 */
public class JaxbMessageWriter<T extends MessageBase> implements MessageWriter<T> {

    /**
     * A byte sequence containing bytes used to separate messages
     */
    private static final byte[] MESSAGE_SEPARATOR = new byte[]{0x0D, 0x0A, 0x0D, 0x0A};

    /**
     * The builder used to retrieve the {@link Marshaller}
     */
    private final JaxbBuilder builder;

    /**
     * Initializes a new instance of the {@link JaxbMessageWriter} class.
     *
     * @param builder the builder used to build requeired {@link Marshaller} instances.
     * @throws IllegalArgumentException The {@code builder} is a null reference
     */
    public JaxbMessageWriter(JaxbBuilder builder) {
        checkNotNull(builder, "The builder cannot be a null reference");

        this.builder = builder;
    }

    /**
     * Writes the passed {@code message} to the returned {@code byte[]}
     *
     * @param message The message to be written to the array.
     * @return The {@code byte[]} containing message data.
     * @throws MessageException The {@code message} could not be written to specified media
     */
    @Override
    public byte[] write(T message) throws ProtocolException, MessageException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Marshaller marshaller = builder.buildMarshaller();
        try {
            marshaller.marshal(message, outputStream);
        } catch (JAXBException e) {
            throw new MessageException("An error occurred while writing the message to byte array. See inner exception for more details", e);
        }

        byte[] messageData = outputStream.toByteArray();
        byte[] data = new byte[messageData.length + MESSAGE_SEPARATOR.length];
        System.arraycopy(messageData, 0, data, 0, messageData.length);
        System.arraycopy(MESSAGE_SEPARATOR, 0, data, messageData.length, MESSAGE_SEPARATOR.length);
        return data;
    }

    /**
     * Gets the configured character encoding for the writer.
     *
     * @return - a string, i.e. UTF-8
     */
    @Override
    public String getEncoding() {
        return builder.getEncoding();
    }
}
