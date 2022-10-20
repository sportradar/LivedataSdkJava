package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * Represents a class which is capable of producing messages for the betradar server.
 *
 * @param <T> The type of the requests produced by the current {@link RequestProducer}
 */
public interface RequestProducer<T extends OutgoingMessage> {

    /**
     * Sets the {@link RequestProducerListener} used to observe the current {@link RequestProducer}
     *
     * @param listener The listener used to observer the current {@link RequestProducer}
     */
    void setListener(RequestProducerListener<T> listener);

    /**
     * Instructs the {@link RequestProducer} to start generating requests.
     */
    void start();

    /**
     * Instructs the {@link RequestProducer} to stop generating requests;
     */
    void stop();
}
