package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * A base class for all classes implementing {@link RequestProducer} interface.
 *
 * @param <T> The type of the requests produced by the current {@link RequestProducerBase}
 */
public abstract class RequestProducerBase<T extends OutgoingMessage> implements RequestProducer<T> {

    /**
     * the {@link RequestProducerListener} used to observe the current {@link RequestProducer}
     */
    private volatile RequestProducerListener<T> listener;

    /**
     * The {@link Object} used to synchronize access to shared variables.
     */
    private final Object lock = new Object();

    /**
     * The value specifying whether the current {@link RequestProducerBase} is started.
     */
    private boolean isStarted;

    /**
     * Sets the {@link RequestProducerListener} used to observe the current {@link RequestProducer}
     *
     * @param listener The listener used to observer the current {@link RequestProducer}
     */
    @Override
    public final void setListener(RequestProducerListener<T> listener) {
        this.listener = listener;
    }

    /**
     * Instructs the {@link RequestProducer} to start generating requests.
     */
    @Override
    public final void start() {
        synchronized (lock) {
            onStarting();
            this.isStarted = true;
        }
    }

    /**
     * Invoked by the current {@link RequestProducerBase} when it is starting. Note that the method
     * is called within critical region, so make sure the execution does not leave the derived class.
     */
    protected void onStarting() {

    }

    /**
     * Instructs the {@link RequestProducer} to stop generating requests;
     */
    @Override
    public final void stop() {
        synchronized (lock) {
            onStopping();
            this.isStarted = false;
        }
    }

    /**
     * Invoked by the current {@link RequestProducerBase} when it is stopping. Note that the method
     * is called withing critical region, so make sure the execution does not leave the derived class.
     */
    protected void onStopping() {

    }

    /**
     * Gets the value specifying whether the current {@link RequestProducerBase} is started.
     *
     * @return the value specifying whether the current {@link RequestProducerBase} is started.
     */
    protected boolean getIsStarted() {
        synchronized (lock) {
            return this.isStarted;
        }
    }

    protected boolean notifyOnRequestReady(T message) {
        return notifyOnRequestReady(message, true);
    }

    /**
     * Notifies the observer that new request is ready to be send to the server.
     *
     * @param message the {@code T} representing the request to be send to the betradar server.
     */
    protected boolean notifyOnRequestReady(T message, boolean blocking) {
        RequestProducerListener<T> listenerCopy = this.listener;

        boolean result = false;
        if (listenerCopy != null) {
            result = listenerCopy.onRequestReady(message, blocking);
        }
        return result;
    }
}
