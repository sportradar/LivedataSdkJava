package com.sportradar.sdk.dispatch.livescout;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import org.joda.time.Duration;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class LoggingEntityEventHandler extends EntityEventHandler {

    /**
     * The {@link SdkLogger} implementation used to log messages.
     */
    private final SdkLogger sdkLogger;

    /**
     * Initializes a new instance of the {@link EntityEventHandler} class
     *
     * @param id              The id of the current {@link EntityEventHandler} used to determine which messages will be processed by the current instance.
     * @param dispatcherCount The number of {@link EntityEventHandler} instances handling user events
     * @param listener        The {@link LiveScoutFeedListener} instance used to inform the user about incoming messages
     * @param feed            The {@link LiveScoutFeed} which will be passed to method calls on the user provided listener
     * @param sdkLogger       The {@link SdkLogger} implementation used to log messages.
     */
    public LoggingEntityEventHandler(int id, int dispatcherCount, LiveScoutFeedListener listener, LiveScoutFeed feed, SdkLogger sdkLogger) {
        super(id, dispatcherCount, listener, feed);
        checkNotNull(sdkLogger, "sdkLogger cannot be a null reference");

        this.sdkLogger = sdkLogger;
    }

    @Override
    protected void dispatchFullMatchUpdateReceived(MatchUpdateEntity matchUpdate) {
        long durationStart = System.currentTimeMillis();
        super.dispatchFullMatchUpdateReceived(matchUpdate);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("onFullMatchUpdateReceived [%s] : %s", matchUpdate.getMatchHeader().getMatchId(), matchUpdate),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("onFullMatchUpdateReceived %s %s",
                            (matchUpdate.getMatchHeader() != null
                                    ? matchUpdate.getMatchHeader().getMatchId() : null),
                            matchUpdate.getMatchStatus()),
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchLineups(LineupsEntity lineupsEntity) {
        long durationStart = System.currentTimeMillis();
        super.dispatchLineups(lineupsEntity);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (lineupsEntity.getPlayers() != null) {
            if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.DEBUG,
                        String.format("Lineups [%s] : %s", lineupsEntity.getMatchId(), lineupsEntity),
                        durationEnd, null);
            } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.INFO,
                        String.format("Lineups match id : %s", lineupsEntity.getMatchId()),
                        durationEnd, null);
            }
        }
    }

    @Override
    protected void dispatchMatchBooking(MatchBookingEntity matchBookingEntity) {
        long durationStart = System.currentTimeMillis();
        super.dispatchMatchBooking(matchBookingEntity);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("OnMatchBookingReply : %s", matchBookingEntity),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("OnMatchBookingReply %s %s",
                            matchBookingEntity.getResult(),
                            matchBookingEntity.getMessage()),
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchMatchData(MatchDataEntity matchData) {
        long durationStart = System.currentTimeMillis();
        super.dispatchMatchData(matchData);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("onMatchData [%s] : %s", matchData.getMatchId(), matchData),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("onMatchData %s Match time %s Remaining time %s",
                            matchData.getMatchId(),
                            matchData.getMatchTime(),
                            matchData.getRemainingTimeInPeriod()),
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchMatchList(MatchListEntity matchList) {
        long durationStart = System.currentTimeMillis();
        super.dispatchMatchList(matchList);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (matchList.getMatches() != null) {
            if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.DEBUG,
                        String.format("onMatchListReceived : %s", matchList),
                        durationEnd, null);
            } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.INFO,
                        String.format("onMatchListReceived #%s",
                                matchList.getMatches().size()),
                        durationEnd, null);
            }
        } else {
            sdkLogger.logClientInteraction(Level.INFO,
                    "Discarding MatchList client event notify: empty match list",
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchMatchListUpdate(MatchListUpdateEntity matchListUpdate) {
        long durationStart = System.currentTimeMillis();
        super.dispatchMatchListUpdate(matchListUpdate);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (matchListUpdate.getMatches() != null) {
            if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.DEBUG,
                        String.format("onMatchListUpdateReceived : %s", matchListUpdate),
                        durationEnd, null);
            } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.INFO,
                        String.format("onMatchListUpdateReceived #%s",
                                matchListUpdate.getMatches().size()),
                        durationEnd, null);
            }
        } else {
            sdkLogger.logClientInteraction(Level.INFO,
                    "Discarding MatchListUpdate client event notify: empty match list",
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchMatchStop(MatchStopEntity matchStopEntity) {
        long durationStart = System.currentTimeMillis();
        super.dispatchMatchStop(matchStopEntity);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("onFullMatchUpdateReceived [%s] : %s", matchStopEntity.getMatchId(), matchStopEntity),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("onMatchStopped %s %s",
                            matchStopEntity.getMatchId(),
                            matchStopEntity.getReason()),
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchMatchUpdate(MatchUpdateEntity matchUpdate) {
        long durationStart = System.currentTimeMillis();
        super.dispatchMatchUpdate(matchUpdate);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("onMatchUpdate [%s] : %s", matchUpdate.getMatchHeader().getMatchId(), matchUpdate),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("onMatchUpdate : %s %s",
                            matchUpdate.getMatchHeader().getMatchId(),
                            matchUpdate.getMatchStatus()),
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchOddsSuggestion(OddsSuggestionsEntity oddsSuggestionsEntity) {
        long durationStart = System.currentTimeMillis();
        super.dispatchOddsSuggestion(oddsSuggestionsEntity);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (oddsSuggestionsEntity.getOdds() != null) {
            if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.DEBUG,
                        String.format("OnOddsSuggestion [%s] : %s", oddsSuggestionsEntity.getMatchId(), oddsSuggestionsEntity),
                        durationEnd, null);
            } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
                sdkLogger.logClientInteraction(Level.INFO,
                        String.format("OnOddsSuggestion %s #%s",
                                oddsSuggestionsEntity.getMatchId(),
                                oddsSuggestionsEntity.getOdds().size()),
                        durationEnd, null);
            }
        } else {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    "Discarding OddsSuggestions client event notify: empty odds list",
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchOnMatchDeltaUpdateDeltaReceived(MatchUpdateEntity matchUpdate) {
        long durationStart = System.currentTimeMillis();
        super.dispatchOnMatchDeltaUpdateDeltaReceived(matchUpdate);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("onMatchDeltaUpdateUpdateReceived [%s] : %s", matchUpdate.getMatchHeader().getMatchId(), matchUpdate),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("onMatchDeltaUpdateUpdateReceived %s %s",
                            matchUpdate.getMatchHeader().getMatchId(),
                            matchUpdate.getMatchStatus()),
                    durationEnd, null);
        }
    }

    @Override
    protected void dispatchOnMatchDeltaUpdateReceived(MatchUpdateEntity matchUpdate) {
        long durationStart = System.currentTimeMillis();
        super.dispatchOnMatchDeltaUpdateReceived(matchUpdate);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isDebugEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.DEBUG,
                    String.format("onMatchDeltaUpdateReceived [%s] : %s", matchUpdate.getMatchHeader().getMatchId(), matchUpdate),
                    durationEnd, null);
        } else if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("onMatchDeltaUpdateReceived %s %s",
                            matchUpdate.getMatchHeader().getMatchId(),
                            matchUpdate.getMatchStatus()),
                    durationEnd, null);
        }
    }


    @Override
    protected void dispatchServerTime(ServerTimeEntity entity) {
        long durationStart = System.currentTimeMillis();
        super.dispatchServerTime(entity);
        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);

        if (sdkLogger.isInfoEnabled(SdkLogAppenderType.CLIENT_INTERACTION)) {
            sdkLogger.logClientInteraction(Level.INFO,
                    String.format("OnServerTime : %s", entity.getServerTime()),
                    durationEnd, null);
        }
    }
}
