/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.livescout.interfaces;

import com.sportradar.sdk.feed.common.interfaces.UserRequestManager;

import java.util.Collection;

/**
 * Represents a manager used to explicitly retrieve data from live-odds server.
 */
public interface LiveScoutUserRequestManager extends UserRequestManager {

    /**
     * Get a list of matches
     *
     * @param hoursBack        - number of hours back (from current time)
     * @param hoursForward     - number of hours forward (from current time)
     * @param includeAvailable - include also available matches (which you have not booked yet)
     * @param sportIds         - filter by sports ids, nullable
     * @param matchIds         - filter by matches ids, nullable
     */
    void getMatchList(int hoursBack, int hoursForward, boolean includeAvailable, Collection<Long> sportIds, Collection<Long> matchIds);


    /**
     * Makes a server time request.
     */
    void requestServerTime();
}
