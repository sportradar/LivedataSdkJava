/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common;

/**
 * Represents an interface used to drive the test/replay servers.
 */
public interface TestManager {

    /**
     * Is the test manager available?
     *
     * @return - false when not in test mode; true else
     */
    boolean isEnabled();
}
