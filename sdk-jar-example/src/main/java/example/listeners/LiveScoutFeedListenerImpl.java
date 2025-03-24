package example.listeners;

import com.sportradar.livedata.sdk.common.enums.FeedEventType;
import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.livedata.sdk.feed.livescout.entities.*;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiveScoutFeedListenerImpl implements LiveScoutFeedListener{

    private static final Logger logger = LoggerFactory.getLogger(LiveScoutFeedListenerImpl.class);

    private LiveScoutFeed feed;

    /**
     * Invoked by the observed live feed when the feed is opened.
     */
    @Override
    public void onOpened(LiveScoutFeed sender){
        feed = (LiveScoutFeed) sender;
        logger.info("On opened");
    }

    /**
     * Invoked by the observed live feed when the feed is closed.
     */
    @Override
    public void onClosed(LiveScoutFeed sender) {
        logger.info("On closed");
    }

    /**
     * Invoked when the feed is initialized and you can accept bets.
     * It is invoked exactly once after the feed has been opened.
     *
     * @param sender - originating feed
     */
    @Override
    public void onInitialized(LiveScoutFeed sender) {
        logger.info("On initialized");
    }

    @Override
    public void onFeedEvent(LiveScoutFeed sender, FeedEventType feedEventType) {
        logger.info("Feed event occurred. Event: {}", feedEventType);
    }

    @Override
    public void onMatchListReceived(LiveScoutFeed sender, MatchListEntity matchList) {
        for (MatchUpdateEntity element : matchList.getMatches()) {
            feed.subscribe(new EventIdentifier[] { element.getEventId() });
        }
    }

    @Override
    public void onMatchDataReceived(LiveScoutFeed liveScoutFeed, MatchDataEntity matchDataEntity) {
        logger.info("On match data");
    }

    @Override
    public void onMatchListUpdateReceived(LiveScoutFeed sender, MatchListUpdateEntity matchList) {
        logger.info("Match list update");
    }

    @Override
    public void onMatchStopped(LiveScoutFeed sender, MatchStopEntity matchStop) {
        logger.info("Match stopped");
    }

    @Override
    public void onMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Match update");
    }

    @Override
    public void onFullMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Full match update");
    }

    @Override
    public void onFullPaginatedMatchUpdateReceived(LiveScoutFeed liveScoutFeed, MatchUpdateEntity matchUpdateEntity) {
        logger.info("Paginated match update");
    }

    @Override
    public void onMatchDeltaUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Match delta update");
    }

    @Override
    public void onMatchDeltaUpdateUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Match delta update update");
    }

    @Override
    public void onServerTimeReceived(LiveScoutFeed liveScoutFeed, ServerTimeEntity serverTimeEntity) {
        logger.info("Server time received");
    }

    @Override
    public void onMatchBooked(LiveScoutFeed sender, MatchBookingEntity matchBooked) {
        logger.info("Match booked");
    }

    @Override
    public void onLineupsReceived(LiveScoutFeed sender, LineupsEntity lineups) {
        logger.info("Lineups");
    }
}
