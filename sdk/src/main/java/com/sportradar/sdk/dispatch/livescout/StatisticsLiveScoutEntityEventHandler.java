package com.sportradar.sdk.dispatch.livescout;

import com.sportradar.sdk.common.classes.jmx.LiveScoutStatisticsCounter;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public final class StatisticsLiveScoutEntityEventHandler extends LoggingEntityEventHandler {

    private LiveScoutStatisticsCounter counter;

    /**
     * Initializes a new instance of the {@link EntityEventHandler} class
     *
     * @param id              The id of the current {@link EntityEventHandler} used to determine which messages will be processed by the current instance.
     * @param dispatcherCount The number of {@link EntityEventHandler} instances handling user events
     * @param listener        The {@link LiveScoutFeedListener} instance used to inform the user about incoming messages
     * @param feed            The {@link LiveScoutFeed} which will be passed to method calls on the user provided listener
     * @param sdkLogger       The {@link SdkLogger} implementation used to log messages.
     * @param counter         The {@link LiveScoutStatisticsCounter} used for counting entity calls
     */
    protected StatisticsLiveScoutEntityEventHandler(
            int id,
            int dispatcherCount,
            LiveScoutFeedListener listener,
            LiveScoutFeed feed,
            SdkLogger sdkLogger,
            LiveScoutStatisticsCounter counter) {

        super(id, dispatcherCount, listener, feed, sdkLogger);

        checkNotNull(counter, "The counter cannot be a null reference");
        this.counter = counter;
    }

    @Override
    protected void dispatchMatchUpdate(MatchUpdateEntity matchUpdate) {
        super.dispatchMatchUpdate(matchUpdate);
        counter.countUpOnMatchUpdate();
    }

    @Override
    protected void dispatchLineups(LineupsEntity entity) {
        super.dispatchLineups(entity);
        counter.countUpOnLineups();
    }

    @Override
    protected void dispatchMatchBooking(MatchBookingEntity entity) {
        super.dispatchMatchBooking(entity);
        counter.countUpOnMatchBooking();
    }

    @Override
    protected void dispatchOddsSuggestion(OddsSuggestionsEntity entity) {
        super.dispatchOddsSuggestion(entity);
        counter.countUpOnOddsSuggestion();
    }

    @Override
    protected void dispatchOnMatchDeltaUpdateDeltaReceived(MatchUpdateEntity matchUpdate) {
        super.dispatchOnMatchDeltaUpdateDeltaReceived(matchUpdate);
        counter.countUpOnOnMatchDeltaUpdateDeltaReceived();
    }

    @Override
    protected void dispatchOnMatchDeltaUpdateReceived(MatchUpdateEntity matchUpdate) {
        super.dispatchOnMatchDeltaUpdateReceived(matchUpdate);
        counter.countUpOnOnMatchDeltaUpdateReceived();
    }

    @Override
    protected void dispatchFullMatchUpdateReceived(MatchUpdateEntity matchUpdate) {
        super.dispatchFullMatchUpdateReceived(matchUpdate);
        counter.countUpOnFullMatchUpdateReceived();
    }

    @Override
    protected void dispatchMatchStop(MatchStopEntity entity) {
        super.dispatchMatchStop(entity);
        counter.countUpOnMatchStop();
    }

    @Override
    protected void dispatchMatchData(MatchDataEntity matchData) {
        super.dispatchMatchData(matchData);
        counter.countUpOnMatchData();
    }

    @Override
    protected void dispatchMatchListUpdate(MatchListUpdateEntity entity) {
        super.dispatchMatchListUpdate(entity);
        counter.countUpOnMatchListUpdate();
    }

    @Override
    protected void dispatchMatchList(MatchListEntity entity) {
        super.dispatchMatchList(entity);
        counter.countUpOnMatchList();
    }
}
