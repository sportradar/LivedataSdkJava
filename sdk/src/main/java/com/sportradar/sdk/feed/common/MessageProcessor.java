package com.sportradar.sdk.feed.common;

import com.sportradar.sdk.feed.common.entities.EntityBase;

/**
 * Represents a class capable of receiving an input message, processing it and passing in forward for further processing
 *
 * @param <T> specifies the type of the messages processed by the {@link MessageProcessor}
 */
public interface MessageProcessor<T extends EntityBase> {

    /**
     * Gets the index of the current {@link MessageProcessor} implementation used to determine the correct
     * order of message processors when there are multiple message processors processing the same message.
     *
     * @return the index of the current {@link MessageProcessor} implementation
     */
    int getIndex();

    /**
     * Processes the passed message derived from {@link EntityBase} class.
     *
     * @param message The {@link EntityBase} derived class representing the message.
     * @throws IllegalArgumentException the {@code message} is a null reference.
     */
    void processMessage(T message);

    /**
     * Sets the {@link MessageProcessorListener} instance used to observe the current {@link MessageProcessor}
     *
     * @param listener The {@link MessageProcessorListener} used to observe the processor.
     */
    void setListener(MessageProcessorListener<T> listener);
}
