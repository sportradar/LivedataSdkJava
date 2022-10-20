/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common.interfaces;

import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.livescout.entities.MatchBookingEntity;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.common.TestManager;
import com.sportradar.sdk.feed.livescout.entities.MatchStopEntity;
import com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

/**
 * Represents a class used to process explicit user requests by generating and sending server messages.
 */
public interface UserRequestManager {

    /**
     * Book the specified matches. Booked matches cannot be cancelled through this interface.
     * This method can block to prevent going over the server limits. Use this call in non SDK dispatcher thread!
     * <p>
     * You should prefer using the Live Calendar on the Betradar website (http://www.betradar.com) which
     * offers automatic booking for certain types of matches.
     * </p>
     * <p>
     * Note that booking matches will have a cost depending on the type of agreement you have with Sportradar.
     * </p>
     * <p>
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchBooked(LiveScoutFeed, MatchBookingEntity)} message in reply.
     * </p>
     *
     * @param eventIds Array of event ids to book
     * @throws IllegalArgumentException if {@code eventIds} is a null reference.
     */
    void bookMatches(EventIdentifier[] eventIds);

    /**
     * Gets the ids of all subscribed matches.
     *
     * @return the ids of all subscribed matches.
     */
    EventIdentifier[] getSubscribedEvents();

    /**
     * When connected to the test server you can obtain a manager to manipulate it.
     *
     * @return a {@link TestManager} implementation
     */
    TestManager getTestManager();

    /**
     * Subscribe to specified matches.
     * This method can block to prevent going over the server limits. Use this call in non SDK dispatcher thread!
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchUpdateReceived(LiveScoutFeed, MatchUpdateEntity)}
     * message is response. If match subscription is successful, a full match update is sent out.
     * Otherwise explanation will be given in the response message (e.g. you do not have access
     * to the match or there are already two open subscriptions for the match).
     * With LiveScout you <b>must</b> explicitly call {@link LiveScoutFeed#subscribe(EventIdentifier[])} at start-up or no data will be received.
     *
     * @param eventIds Array of eventIds
     */
    void subscribe(EventIdentifier[] eventIds);

    /**
     * Un-subscribe from the specified matches. After this no more events associated with these matches will be sent.
     * This method can block to prevent going over the server limits. Use this call in non SDK dispatcher thread!
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchStopped(LiveScoutFeed, MatchStopEntity)}
     * message in response.
     *
     * @param eventIds Array of event ids from which to un-subscribe
     * @throws IllegalArgumentException if {@code eventIds} is a null reference.
     */
    void unsubscribe(EventIdentifier[] eventIds);
}
