package com.sportradar.livedata.sdk.dispatch.common;

import com.sportradar.livedata.sdk.common.enums.FeedEventType;

public interface LiveFeedDispatcher<LiveScoutFeed> {


    void dispatchOnClosed(LiveScoutFeed sender);

    void dispatchOnFeedEvent(LiveScoutFeed sender, FeedEventType eventType);

    void dispatchOnInitialized(LiveScoutFeed sender);

    void dispatchOnOpened(LiveScoutFeed sender);


}
