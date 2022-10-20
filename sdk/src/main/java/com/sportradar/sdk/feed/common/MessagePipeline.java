package com.sportradar.sdk.feed.common;

import com.sportradar.sdk.feed.common.entities.EntityBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A message - processing pipeline which consists of multiple {@link MessageProcessor} instances.
 * Each message processor can, after processing the message hand it to the next processor, or can stop
 * the processing of the current message.
 *
 * @author uros.bregar
 */
public class MessagePipeline<T extends EntityBase> implements MessageProcessor<T> {

    /**
     * the index of the current {@link MessageProcessor} implementation
     */
    private final int index;

    /**
     * The list of message processors composing the pipeline.
     */
    private final List<MessageProcessor<T>> messageProcessors;

    /**
     * The {@link MessageProcessorListener} used to observe the current processor.
     */
    private volatile MessageProcessorListener<T> listener;

    /**
     * Initializes a new instance of the {@link MessagePipeline} class from the passed {@link MessageProcessor} instances.
     *
     * @param index the index of the current {@link MessageProcessor} implementation
     * @param processors the {@link MessageProcessor} instances from which the pipeline will be composed.
     */
    @SafeVarargs
    public MessagePipeline(int index, MessageProcessor<T>... processors) {
        checkArgument(index > 0, "index must be greater than zero ");
        checkArgument(processors != null && processors.length != 0, "processors cannot be a null reference and it must contain at least one item");

        this.index = index;
        this.messageProcessors = Arrays.asList(processors);
        Collections.sort(this.messageProcessors, new MessageProcessorComparator<T>());
        setListeners();
    }

    /**
     * Sets the listener used to observe the contained message processors.
     */
    private void setListeners() {
        MessageProcessorListener<T> listener = new MessageProcessorListener<T>() {
            @Override
            public void onMessageProcessed(T message, MessageProcessor<T> processor) {
                int nextIndex = messageProcessors.indexOf(processor) + 1;
                if (nextIndex < messageProcessors.size()) {//TODO AK there is always only 1 messageProcessor (LiveScoutUserRequestManagerImpl)
                    messageProcessors.get(nextIndex).processMessage(message);
                } else {
                    notifyMessageProcessed(message);
                }
            }
        };

        for (MessageProcessor<T> processor : messageProcessors) {
            processor.setListener(listener);
        }
    }

    /**
     * Notifies the observer that the message was processed.
     *
     * @param message The processed message.
     */
    private void notifyMessageProcessed(T message) {
        MessageProcessorListener<T> copy = this.listener;
        if (copy != null) {
            copy.onMessageProcessed(message, this);
        }
    }

    /**
     * Gets the index of the current {@link MessageProcessor} implementation used to determine the correct
     * order of message processors when there are multiple message processors processing the same message.
     *
     * @return the index of the current {@link MessageProcessor} implementation
     */
    @Override
    public int getIndex() {
        return this.index;
    }

    /**
     * Processes the passed message derived from {@link EntityBase} class.
     *
     * @param message The {@link EntityBase} derived class representing the message.
     * @throws IllegalArgumentException the {@code message} is a null reference.
     */
    @Override
    public void processMessage(T message) {
        messageProcessors.get(0).processMessage(message);
    }

    /**
     * Sets the {@link MessageProcessorListener} instance used to observe the current {@link MessageProcessor}
     *
     * @param listener The {@link MessageProcessorListener} used to observe the processor.
     */
    @Override
    public void setListener(MessageProcessorListener<T> listener) {
        this.listener = listener;
    }
}
