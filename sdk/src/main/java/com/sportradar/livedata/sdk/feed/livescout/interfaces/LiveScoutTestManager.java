/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.feed.livescout.interfaces;

import com.sportradar.livedata.sdk.feed.common.TestDelayWithSpeed;
import com.sportradar.livedata.sdk.feed.common.TestManager;
import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;

/**
 * Represents an interface used to drive the LiveScout test server
 */
public interface LiveScoutTestManager extends TestManager {

    /**
     * Subscribe to an event
     *
     * @param id    - event id
     * @param delay - start message number can be specified and a speed how fast new events arrive
     */
    void subscribe(EventIdentifier id, TestDelayWithSpeed delay);
}
