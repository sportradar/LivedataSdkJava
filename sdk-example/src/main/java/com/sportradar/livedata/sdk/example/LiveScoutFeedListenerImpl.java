package com.sportradar.livedata.sdk.example;

import com.sportradar.livedata.sdk.common.enums.FeedEventType;
import com.sportradar.livedata.sdk.feed.livescout.entities.LineupsEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchBookingEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchDataEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchListEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchListUpdateEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchStopEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.ServerTimeEntity;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Minimal {@link LiveScoutFeedListener} that logs callbacks without side effects.
 *
 * <p>Use {@link Main} commands to book or subscribe to matches explicitly.
 */
public class LiveScoutFeedListenerImpl implements LiveScoutFeedListener {

  private static final Logger logger = LoggerFactory.getLogger(LiveScoutFeedListenerImpl.class);

  @Override
  public void onOpened(LiveScoutFeed sender) {
    logger.info("Feed opened");
  }

  @Override
  public void onClosed(LiveScoutFeed sender) {
    logger.info("Feed closed");
  }

  @Override
  public void onInitialized(LiveScoutFeed sender) {
    logger.info("Feed initialized");
  }

  @Override
  public void onFeedEvent(LiveScoutFeed sender, FeedEventType feedEventType) {
    logger.info("Feed event: {}", feedEventType);
  }

  @Override
  public void onMatchListReceived(LiveScoutFeed sender, MatchListEntity matchList) {
    logger.info("Match list received (requestId={}, matches={})",
        matchList.getRequestId(), matchList.getMatches().size());
    matchList.getMatches().forEach(match ->
        logger.info("  match id={}, status={}", match.getEventId(), match.getMatchStatus()));
  }

  @Override
  public void onMatchListUpdateReceived(LiveScoutFeed sender, MatchListUpdateEntity matchList) {
    logger.info("Match list update (matches={})", matchList.getMatches().size());
  }

  @Override
  public void onMatchBooked(LiveScoutFeed sender, MatchBookingEntity matchBooked) {
    logger.info("Match booked: eventId={}, result={}",
        matchBooked.getEventId(), matchBooked.getResult());
  }

  @Override
  public void onMatchDataReceived(LiveScoutFeed sender, MatchDataEntity matchData) {
    logger.info("Match data: matchId={}", matchData.getMatchId());
  }

  @Override
  public void onMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
    logger.info("Match update: eventId={}", matchUpdate.getEventId());
  }

  @Override
  public void onFullMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
    logger.info("Full match update: eventId={}", matchUpdate.getEventId());
  }

  @Override
  public void onFullPaginatedMatchUpdateReceived(
      LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
    logger.info("Full paginated match update: eventId={}", matchUpdate.getEventId());
  }

  @Override
  public void onMatchDeltaUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
    logger.info("Match delta update: eventId={}", matchUpdate.getEventId());
  }

  @Override
  public void onMatchDeltaUpdateUpdateReceived(
      LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
    logger.info("Match delta update update: eventId={}", matchUpdate.getEventId());
  }

  @Override
  public void onMatchStopped(LiveScoutFeed sender, MatchStopEntity matchStop) {
    logger.info("Match stopped: eventId={}", matchStop.getEventId());
  }

  @Override
  public void onLineupsReceived(LiveScoutFeed sender, LineupsEntity lineups) {
    logger.info("Lineups received");
  }

  @Override
  public void onServerTimeReceived(LiveScoutFeed sender, ServerTimeEntity serverTime) {
    logger.info("Server time received");
  }
}
