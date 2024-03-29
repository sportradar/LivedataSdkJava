package com.sportradar.livedata.sdk.feed.common;

import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;

/**
 * Represents a listener used to observer the {@link MessageProcessor} instances
 */
public interface MessageProcessorListener<T extends LiveScoutEntityBase> {

    /**
     * Invoked by the observed {@link MessageProcessor} when it has processed the message
     *
     * @param processor The {@link MessageProcessor} which processed the message
     * @param message   The processed message.
     */
    void onMessageProcessed(T message, final MessageProcessor<T> processor);
}
