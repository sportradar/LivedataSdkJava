package com.sportradar.livedata.sdk.dispatch.livescout;

import com.lmax.disruptor.InsufficientCapacityException;
import com.sportradar.livedata.sdk.dispatch.common.LiveFeedDispatcher;
import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

/**
 * Represents a dispatcher used to dispatch live-scout related events to the user.
 */
public interface LiveScoutDispatcher extends LiveFeedDispatcher<LiveScoutFeed> {

    /**
     * Starts the current {@link LiveScoutDispatcher}
     *
     * @param listener The {@link LiveScoutFeedListener} used by the current {@link LiveScoutFeedListener} to dispatch events
     */
    void start(LiveScoutFeedListener listener);

    /**
     * Stops the current {@link LiveScoutDispatcher} and disposed any resources associated with it.
     */
    void stop();

    /**
     * Dispatches the passed {@link LiveScoutEntityBase} to the user
     *
     * @param entity the {@link LiveScoutEntityBase} to dispatch to the user.
     * @throws InsufficientCapacityException ring buffer is full.
     */
    void dispatchEntity(LiveScoutEntityBase entity) throws InsufficientCapacityException;

    /**
     * Set the feed.
     *
     * @param feed feed that will be served.
     */
    void setFeed(LiveScoutFeed feed);
}
