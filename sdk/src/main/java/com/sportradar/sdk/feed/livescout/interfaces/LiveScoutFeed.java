package com.sportradar.sdk.feed.livescout.interfaces;

import com.sportradar.sdk.feed.common.entities.ConnectionParams;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.common.interfaces.UserRequestManager;

import java.util.Collection;

/**
 * Represents an access-point for live-scout feed.
 */
public interface LiveScoutFeed extends UserRequestManager {

    /**
     * Gets the value specifying whether the current feed is opened
     *
     * @return the value specifying whether the current feed is opened
     */
    boolean isOpened();

    /**
     * Opens the communication channel with the live-scout server
     *
     * @param listener The {@link LiveScoutFeedListener} instance used to observe the current {@link LiveScoutFeed}
     * @throws IllegalArgumentException if {@code listener} is a null reference.
     * @throws IllegalStateException    if the feed is already opened or disposed.
     */
    void open(LiveScoutFeedListener listener);

    /**
     * Holds data about underlying connection parameters
     * @return feed connection parameters
     */
    ConnectionParams getConnectionParams();

    /**
     * Requests a list of available scout matches for a given time frame.
     * This method can block to prevent going over the server limits. Use this call in non SDK dispatcher thread!
     * <p>
     * By default only matches you have access to will be sent.
     * You can also request all available matches by setting the {@code includeAvailable}
     * If this is done the reply will also include matches that are open for booking.
     * </p>
     *
     * @param hoursBack        Specifies the open of the time frame
     * @param hoursForward     Specifies the end of the time frame
     * @param includeAvailable Specifies whether matches available for booking should be included
     * @param sportIds         - filter by sports ids, nullable
     * @param matchIds         - filter by matches ids, nullable
     * @throws IllegalStateException if the feed is not opened
     */
    void getMatchList(int hoursBack, int hoursForward, boolean includeAvailable, Collection<Long> sportIds, Collection<Long> matchIds);

    /**
     * Get a list of matches
     *
     * @param hoursBack        - number of hours back (from current time)
     * @param hoursForward     - number of hours forward (from current time)
     * @param includeAvailable - include also available matches (which you have not booked yet)
     */
    void getMatchList(int hoursBack, int hoursForward, boolean includeAvailable);

    /**
     * Makes a server time request.
     * This method can block to prevent going over the server limits. Use this call in non SDK dispatcher thread!
     *
     * @throws IllegalStateException if the feed is already opened or disposed.
     */
    void requestServerTime();

    /**
     * Request a full feed for the match, and then update messages will follow in the same pace as they were sent out live.
     * <p>
     * This should only be used on the test server.
     * If {@code startMessage} is greater than or equal to the number of messages for the match,
     * a full feed for the finished match is sent out, and no more messages will follow after this.
     * </p>
     *
     * @param eventId      The id of the event.
     * @param messageDelay Specifies how many milliseconds to wait between sending out each message
     * @param startMessage Specifies which message to open replay on (how far into the match).
     * @throws IllegalArgumentException if {@code eventId} is a null reference.
     * @throws IllegalStateException    if the feed is not opened
     */
    void subscribeToTest(EventIdentifier eventId, Integer messageDelay, Integer startMessage);

    /**
     * Closes the communication channel with the live-scout server.
     *
     * @throws IllegalStateException if the feed is already closed.
     */
    void close();

    /**
     * Closes the current feed, and disposes all resources associated with it. Once the feed is disposed,
     * it can n longer be used.
     */
    void dispose();
}
