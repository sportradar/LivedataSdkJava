package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * Represents a listener used to observe the {@code RequestProducer} instances.
 *
 * @param <T> the type of the requests produced by the current {@link RequestProducer}
 * @author uros.bregar
 */
public interface RequestProducerListener<T extends OutgoingMessage> {

    /**
     * Invoked by the observed {@link RequestProducer} when a request for the betradar server is ready.
     *
     * @param request the {@link OutgoingMessage} derived type representing the request for the betradar server.
     */
    boolean onRequestReady(T request, boolean blocking);
}
