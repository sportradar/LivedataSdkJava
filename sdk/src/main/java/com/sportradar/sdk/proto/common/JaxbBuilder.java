/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

/**
 * <p> Represents a builder used to build JAXB based {@link Marshaller} and {@link Unmarshaller} instances. Different implementations
 * can be used to determine whether each time new instance is created or an existing instance is returned.
 * </p>
 * <p> For performance reasons there should only be one instance of the {@link jakarta.xml.bind.JAXBContext} used throughout the application.
 * The context can than be used to create marshaller and un-marshaller instances. Since the marshallers and un-marshallers
 * are not thread safe one instance should only be used within one thread or synchronization should be applied to avoid threading issues.
 * </p>
 * <p> When application is marshalling and un-marshalling larger numbers of small objects, the creation of marshallers and un-marshallers
 * can become an expansive operation. In those cases performance can be approved by sharing marshallers and un-marshallers between
 * multiple threads.
 * </p>
 */
public interface JaxbBuilder {

    /**
     * Returns a {@link Marshaller} implementation.
     *
     * @return The created {@link Marshaller} instance.
     * @throws ProtocolException the marshaller could not be build
     */
    Marshaller buildMarshaller() throws ProtocolException;

    /**
     * Returns a {@link Unmarshaller} implementation.
     *
     * @return The created {@link Unmarshaller} instance.
     * @throws ProtocolException The unmarshaller could not be build
     */
    Unmarshaller buildUnmarshaller() throws ProtocolException;

    /**
     * Gets the configured character encoding for the builder.
     *
     * @return - a string, i.e. UTF-8
     */
    String getEncoding();
}
