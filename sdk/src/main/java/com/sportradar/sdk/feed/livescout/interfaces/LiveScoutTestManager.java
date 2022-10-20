/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.livescout.interfaces;

import com.sportradar.sdk.feed.common.TestDelayWithSpeed;
import com.sportradar.sdk.feed.common.TestManager;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;

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
