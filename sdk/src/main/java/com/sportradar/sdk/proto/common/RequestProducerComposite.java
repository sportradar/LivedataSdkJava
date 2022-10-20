package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Represents a composition of multiple {@code RequestProducer} instances.
 */
public class RequestProducerComposite<T extends OutgoingMessage> extends RequestProducerBase<T> {

    /**
     * The list of the {@link RequestProducer} wrapped in the current composite.
     */
    private final List<RequestProducer<T>> producers;

    /**
     * Invoked by the current {@link RequestProducerBase} when it is stopping. Note that the method
     * is called withing critical region, so make sure the execution does not leave the derived class.
     */
    @Override
    protected void onStopping() {
        for (RequestProducer producer : producers) {
            producer.stop();
        }
    }

    /**
     * Invoked by the current {@link RequestProducerBase} when it is starting. Note that the method
     * is called within critical region, so make sure the execution does not leave the derived class.
     */
    @Override
    protected void onStarting() {
        for (RequestProducer producer : producers) {
            producer.start();
        }
    }

    /**
     * Initializes a new instance of the {@link RequestProducerComposite} class.
     *
     * @param producers the list of the {@link RequestProducer} instances to be wrapped in the composite.
     */
    @SafeVarargs
    public RequestProducerComposite(RequestProducer<T>... producers) {
        checkArgument(producers != null && producers.length > 0, "producers cannot be a null reference and it's length must be greater than 0");

        RequestProducerListener<T> listener = new RequestProducerListener<T>() {
            @Override
            public boolean onRequestReady(T request, boolean blocking) {
                return notifyOnRequestReady(request, blocking);
            }
        };

        this.producers = new ArrayList<>(producers.length);
        for (RequestProducer<T> producer : producers) {
            producer.setListener(listener);
            this.producers.add(producer);
        }
    }
}
