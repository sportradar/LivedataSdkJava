import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

public class LiveScoutListenerImpl implements LiveScoutFeedListener {

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when match list requested by the user is received.
     *
     * @param sender    The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchList The {@link com.sportradar.sdk.feed.livescout.entities.MatchListEntity} representing the received match list.
     */
    @Override
    public void onMatchListReceived(LiveScoutFeed sender, MatchListEntity matchList) {

    }

    @Override
    public void onMatchDataReceived(LiveScoutFeed sender, MatchDataEntity matchData) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when an unsolicited match list is received.
     *
     * @param sender    The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchList {@link com.sportradar.sdk.feed.livescout.entities.MatchListUpdateEntity} representing the received match list.
     */
    @Override
    public void onMatchListUpdateReceived(LiveScoutFeed sender, MatchListUpdateEntity matchList) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} after the was un-subscribed from a match or if subscription to a
     * match failed.
     *
     * @param sender    The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchStop The {@link com.sportradar.sdk.feed.livescout.entities.MatchStopEntity} representing the un-subscription message
     */
    @Override
    public void onMatchStopped(LiveScoutFeed sender, MatchStopEntity matchStop) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when match update message is received. There are three types of match information messages:
     * full match feed, delta match feed and delta update feed (see {@link com.sportradar.sdk.feed.livescout.enums.ScoutFeedType}.
     * All three can contain statistical information as well as individual events (an event being a goal, card, etc.).
     * </p>
     * <p>
     * When a client application subscribes to a match, it will receive a full match update immediately after
     * (given the subscription was successful). This message will contain all data for the match. Different event types
     * are offered for different sports. Note that new event types will be added in the future.
     * </p>
     * <p>
     * After a full match update is received, most updates will be sent as delta updates. In these messages only the new data is sent,
     * if a yellow card is given only this event will be sent. In addition summary data will be sent for the event type in question,
     * i.e. the total number of yellow cards for the home/away team for the match.
     * </p>
     * <p>
     * If an existing event is changed, a delta update is sent for this event. E.g. a yellow card event is entered,
     * and 1 minute later the event is updated by including which player got the card.
     * Client must consider this as an update to an existing event, and not a new event (based on Event Id property).
     * </p>
     *
     * @param sender      The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchUpdate The {@link com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity} representing the received match-update message
     */
    @Override
    public void onMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when full match update is received. For more information see the
     * {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener#onMatchUpdateReceived(com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed, com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity)}
     *
     * @param sender      The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchUpdate The {@link com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity} representing the received match-update message
     */
    @Override
    public void onFullMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when match delta update is received. For more information see the
     * {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener#onMatchUpdateReceived(com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed, com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity)}
     *
     * @param sender      The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchUpdate The {@link com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity} representing the received match-update message
     */
    @Override
    public void onMatchDeltaUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when match delta update delta message is received. For more
     * information see the {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener#onMatchUpdateReceived(com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed, com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity)}
     *
     * @param sender      The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchUpdate The {@link com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity} representing the received match-update message
     */
    @Override
    public void onMatchDeltaUpdateUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when odds suggestions are received. Suggested live odds are
     * constantly updated and sent out according to match events. These odds are generated based on statistical models.
     *
     * @param sender          The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param oddsSuggestions The {@link com.sportradar.sdk.feed.livescout.entities.OddsSuggestionsEntity} instance representing the received odds suggestions.
     */
    @Override
    public void onOddsSuggestionReceived(LiveScoutFeed sender, OddsSuggestionsEntity oddsSuggestions) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when a match-booked reply is received. Reply is sent by the
     * live-scout server when a client books a match. If match booking failed for some reason
     * (match already booked, match finished etc.), the reply will contain additional explanation.
     *
     * @param sender      The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param matchBooked The {@link com.sportradar.sdk.feed.livescout.entities.MatchBookingEntity} representing the received message.
     */
    @Override
    public void onMatchBooked(LiveScoutFeed sender, MatchBookingEntity matchBooked) {

    }

    /**
     * Invoked by the observed {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} when player lineups for both teams are received.
     * </p>
     * <p>
     * The event is dispatched immediately after subscribing to a match and every time the lineup changes.
     * XML configuration option "Send lineups for matches where this is available" needs to be activated (currently works just for deep coverage soccer matches).
     * </p>
     *
     * @param sender  The {@link com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed} sender of the event.
     * @param lineups The {@link com.sportradar.sdk.feed.livescout.entities.LineupsEntity} representing the received lineups.
     */
    @Override
    public void onLineupsReceived(LiveScoutFeed sender, LineupsEntity lineups) {

    }

    @Override
    public void onServerTimeReceived(LiveScoutFeed sender, ServerTimeEntity serverTime) {

    }

    /**
     * Invoked by the observed live feed when the feed is opened.
     *
     * @param sender - originating feed
     */
    @Override
    public void onOpened(LiveScoutFeed sender) {

    }

    /**
     * Invoked by the observed live feed when the feed is closed.
     *
     * @param sender - originating feed
     */
    @Override
    public void onClosed(LiveScoutFeed sender) {

    }

    /**
     * Invoked when the feed is initialized and you can accept bets.
     * It is invoked exactly once after the feed has been opened.
     *
     * @param sender - originating feed
     */
    @Override
    public void onInitialized(LiveScoutFeed sender) {

    }

    /**
     * Invoked by the observed live feed when it encounters an special event related to the behavior of the server.
     *
     * @param sender
     * @param eventType The {@link com.sportradar.sdk.common.enums.FeedEventType} member specifying the type of the occurred event.
     */
    @Override
    public void onFeedEvent(LiveScoutFeed sender, FeedEventType eventType) {

    }
}
