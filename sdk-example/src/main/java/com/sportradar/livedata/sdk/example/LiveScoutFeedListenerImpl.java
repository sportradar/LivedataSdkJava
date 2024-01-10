package com.sportradar.livedata.sdk.example;

import com.sportradar.livedata.sdk.common.enums.FeedEventType;
import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.livedata.sdk.feed.livescout.entities.*;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;

public class LiveScoutFeedListenerImpl implements LiveScoutFeedListener {

    private static final Logger logger = LoggerFactory.getLogger(LiveScoutFeedListenerImpl.class);

//    private Timer timer;

    /**
     * Invoked by the observed live feed when the feed is closed.
     */
    @Override
    public void onClosed(LiveScoutFeed sender) {
        logger.info("On closed");
//        timer.cancel();
    }

    @Override
    public void onFeedEvent(LiveScoutFeed sender, FeedEventType feedEventType) {
        logger.info(String.format("Feed event occurred. Event: %s", feedEventType));
    }

    /**
     * Invoked by the observed live feed when the feed is opened.
     */
    @Override
    public void onOpened(final LiveScoutFeed sender) {
//        sender.getMatchList(1, 4, true, null, null);
        logger.info("On opened");

//        timer = new Timer();
//        long hour = Duration.standardHours(1).getMillis();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                sender.getMatchList(0, 4, true);
//            }
//        }, hour, hour);

//        sender.getMatchList(120, 200, false, null, null);
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
    public void onFullMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Full match update");

        if(matchUpdate.getEventId().getEventId() == -941369){
            String status = matchUpdate.getEventId().toString();
        }

        String homeState = matchUpdate.getMatchHeader().getHomeState();
        String awayState = matchUpdate.getMatchHeader().getAwayState();
        String venue = matchUpdate.getMatchHeader().getVenue();
        for(ScoutEventEntity scoutEventEntity : matchUpdate.getEvents()){
            String unavailablePlayersHome = scoutEventEntity.getUnavailablePlayersHome();
            String unavailablePlayersAway = scoutEventEntity.getUnavailablePlayersAway();
        }
    }

    @Override
    public void onFullPaginatedMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Full paginated match update");
    }

    @Override
    public void onLineupsReceived(LiveScoutFeed sender, LineupsEntity lineups) {
        logger.info("Lineups");
    }

    @Override
    public void onMatchBooked(LiveScoutFeed sender, MatchBookingEntity matchBooked) {
        logger.info("Match booked");

        String result = matchBooked.getResult().name();
        System.out.println("onMatchBooked result: " + result + "\n");

        EventIdentifier eventIdentifier = matchBooked.getEventId();
        if(eventIdentifier != null){
            System.out.println(eventIdentifier.toString());
            System.out.println(eventIdentifier.getEventId());
        } else {
            System.out.println("No eventIdentifier." + "\n");
        }
        System.out.println(matchBooked.getMessage());
        System.out.println(matchBooked);
    }

    @Override
    public void onMatchDataReceived(LiveScoutFeed sender, MatchDataEntity matchData) {
        logger.info("Match data");
        if(matchData.getMatchId() == -941369){
            String status = matchData.getEventId().toString();
        }
    }

    @Override
    public void onMatchDeltaUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Match delta update");

        if(matchUpdate.getScores() != null && matchUpdate.getScores().size() > 0){
            for (ScoreEntity se : matchUpdate.getScores()) {
                logger.info("Match score: " + se);
            }
        }
    }

    @Override
    public void onMatchDeltaUpdateUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Match delta update update");
    }

    @Override
    public void onMatchListReceived(LiveScoutFeed sender, MatchListEntity matchList) {
        logger.info("matchlist received");
//        for(MatchUpdateEntity match : matchList.getMatches()){
//            logger.info(match.toString());
//        }
        final List<MatchUpdateEntity> matches = matchList.getMatches();
        EventIdentifier[] toSubscribe = new EventIdentifier[matches.size()];
        for (int i = 0; i < matches.size(); i++) {
            final MatchUpdateEntity element = matches.get(i);
            if (Boolean.FALSE.equals(element.getMatchHeader().isBooked())) {
                sender.bookMatches(new EventIdentifier[]{element.getEventId()});
            }
            toSubscribe[i] = element.getEventId();
        }
        sender.subscribe(toSubscribe);
//        sender.subscribe(new EventIdentifier[]{EventIdentifier.id(23053636)});
//        sender.subscribe(new EventIdentifier[]{EventIdentifier.id(22878272)});

//        sender.subscribe(new EventIdentifier[]{EventIdentifier.id(29702834)});
//        sender.subscribe(new EventIdentifier[]{EventIdentifier.id(30723617)});
    }

    @Override
    public void onMatchListUpdateReceived(LiveScoutFeed sender, MatchListUpdateEntity matchList) {
        logger.info("Match list update");

        for(int i = 0; i < matchList.getMatches().size(); i++){
            MatchUpdateEntity matchUpdateEntity = matchList.getMatches().get(i);
            long id = matchUpdateEntity.getEventId().getEventId();
            logger.info(id + ">" + matchUpdateEntity.getMatchTeams() + ">" + matchUpdateEntity.getMatchStatus());
        }
    }

    @Override
    public void onMatchStopped(LiveScoutFeed sender, MatchStopEntity matchStop) {
        logger.info("Match stopped");
    }

    @Override
    public void onMatchUpdateReceived(LiveScoutFeed sender, MatchUpdateEntity matchUpdate) {
        logger.info("Match update");

        if(matchUpdate.getScores() != null && matchUpdate.getScores().size() > 0){
            for (ScoreEntity se : matchUpdate.getScores()) {
                logger.info("Match score %s", se);
            }
        }
    }

    @Override
    public void onServerTimeReceived(LiveScoutFeed sender, ServerTimeEntity serverTime) {
        logger.info("Server time");
    }
}
