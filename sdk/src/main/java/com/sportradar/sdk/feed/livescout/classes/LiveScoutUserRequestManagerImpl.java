package com.sportradar.sdk.feed.livescout.classes;

import ch.qos.logback.classic.Level;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.timer.TimeProvider;
import com.sportradar.sdk.feed.common.MessageProcessorListener;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.common.interfaces.UserRequestManager;
import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.sdk.feed.livescout.entities.ScoutFakeBetStopFactory;
import com.sportradar.sdk.feed.livescout.enums.ScoutMatchStatus;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;
import com.sportradar.sdk.proto.common.RequestProducerBase;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sportradar.sdk.feed.common.MessageProcessor;
import com.sportradar.sdk.feed.common.TestManager;
import com.sportradar.sdk.feed.livescout.entities.MatchBookingEntity;
import com.sportradar.sdk.feed.livescout.entities.MatchStopEntity;
import com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link UserRequestManager} implementation associated with the live-scout feed.
 * Also handles error recovery for the feed
 */
public class LiveScoutUserRequestManagerImpl
        extends RequestProducerBase<OutgoingMessage>
        implements LiveScoutUserRequestManager, MessageProcessor<LiveScoutEntityBase> {

    /**
     * How long to leave match after it comes into terminal state?
     */
    public static final Duration TERMINAL_STATE_ADDENDUM = Duration.standardMinutes(5);
    private final static Object NullObject = new Object();
    private final static Logger logger = LoggerFactory.getLogger(LiveScoutUserRequestManagerImpl.class);
    /**
     * SDK logger
     */
    protected final SdkLogger sdkLogger;
    /**
     * A {@link com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory} instance used to generate server requests.
     */
    private final LiveScoutStatusFactory requestFactory;
    /**
     * Test manager.
     */
    private final TestManager testManager;
    /**
     * the index of the current {@link MessageProcessor} implementation
     */
    private final int index;
    /**
     * Matches we are currently subscribed to (in normal state)
     */
    private final Cache<EventIdentifier, Object> normalSubscribedMatches;
    /**
     * Matches we are currently subscribed to (in terminal state)
     */
    private final Cache<EventIdentifier, Object> terminalSubscribedMatches;
    /**
     * The {@link MessageProcessorListener} used to notify the observer when the message is processed.
     */
    private volatile MessageProcessorListener<LiveScoutEntityBase> messageListener;
    /**
     * Were we disconnected?
     */
    private volatile boolean wasDisconnected;

    /**
     * Initializes a new instance of the {@link LiveScoutUserRequestManagerImpl} class.
     *
     * @param index          index in pipeline
     * @param requestFactory A {@link LiveScoutStatusFactory} instance used to generate server requests.
     * @param maxAge         max age of events
     * @param testManager    test manager
     * @throws IllegalArgumentException The {@code requestFactory} is a null reference.
     */
    public LiveScoutUserRequestManagerImpl(
            final int index,
            final LiveScoutStatusFactory requestFactory,
            final Duration maxAge,
            final TestManager testManager,
            final SdkLogger sdkLogger
    ) {
        checkArgument(index > 0, "index must be greater than zero");
        checkNotNull(requestFactory, "requestFactory cannot be a null reference");
        checkNotNull(testManager, "testManager cannot be a null reference");
        checkNotNull(sdkLogger, "sdkLogger cannot be a null reference");

        this.requestFactory = requestFactory;
        this.testManager = testManager;

        this.normalSubscribedMatches = CacheBuilder.newBuilder()
                .expireAfterWrite(maxAge.getMillis(), TimeUnit.MILLISECONDS)
                .build();

        this.terminalSubscribedMatches = CacheBuilder.newBuilder()
                .expireAfterWrite(TERMINAL_STATE_ADDENDUM.getMillis(), TimeUnit.MILLISECONDS)
                .build();

        this.index = index;
        this.wasDisconnected = false;
        this.sdkLogger = sdkLogger;
    }

    /**
     * Book the specified matches. Booked matches cannot be cancelled through this interface.
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
     * @param eventIds The id of events to book
     */
    @Override
    public void bookMatches(EventIdentifier[] eventIds) {
        checkNotNull(eventIds);
        sdkLogger.logClientInteraction(Level.INFO, String.format("bookMatches: %s", ArrayUtils.toString(eventIds)));
        for (final Long id : getIds(eventIds)) {
            notifyOnRequestReady(requestFactory.buildMatchBooking(id));
        }
    }

    /**
     * Gets the ids of all subscribed events.
     *
     * @return the ids of all subscribed events.
     */
    @Override
    public EventIdentifier[] getSubscribedEvents() {
        Set<EventIdentifier> eventIdentifiers = getAllSubscribedMatches();
        return eventIdentifiers.toArray(new EventIdentifier[eventIdentifiers.size()]);
    }

    /**
     * When connected to the test server you can obtain a manager to manipulate it.
     *
     * @return a {@link TestManager} implementation
     */
    @Override
    public TestManager getTestManager() {
        return testManager;
    }

    /**
     * Subscribe to specified matches.
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchUpdateReceived(LiveScoutFeed, MatchUpdateEntity)}
     * message is response. If match subscription is successful, a full match update is sent out.
     * Otherwise explanation will be given in the response message (e.g. you do not have access
     * to the match or there are already two open subscriptions for the match).
     * With LiveScout you <b>must</b> explicitly call {@link LiveScoutFeed#subscribe(EventIdentifier[])} at start-up or no data will be received.
     *
     * @param eventIds Array of event ids
     */
    @Override
    public void subscribe(final EventIdentifier[] eventIds) {
        checkNotNull(eventIds);
        sdkLogger.logClientInteraction(Level.INFO, String.format("Subscribe: %s", ArrayUtils.toString(eventIds)));
        List<Long> ids = new ArrayList<>();
        for (EventIdentifier eventId : eventIds) {
            ids.add(eventId.getEventId());
        }
        notifyOnRequestReady(requestFactory.buildMatchSubscribe(ids));
    }

    /**
     * Un-subscribe from the specified matches. After this no more events associated with these matches will be sent.
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchStopped(LiveScoutFeed, MatchStopEntity)}
     * message in response.
     *
     * @param eventIds Array of match ids from which to un-subscribe
     */
    @Override
    public void unsubscribe(EventIdentifier[] eventIds) {
        checkNotNull(eventIds);
        sdkLogger.logClientInteraction(Level.INFO, String.format("Unsubscribe: %s", ArrayUtils.toString(eventIds)));
        for (final Long id : getIds(eventIds)) {
            notifyOnRequestReady(requestFactory.buildMatchUnsubscribe(id));
        }
    }

    /**
     * Gets the index of the current {@link MessageProcessor} implementation used to determine the correct
     * order of message processors when there are multiple message processors processing the same message.
     *
     * @return the index of the current {@link MessageProcessor} implementation
     */
    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void processMessage(LiveScoutEntityBase message) {

        if (message instanceof MatchStopEntity) {

            MatchStopEntity stop = (MatchStopEntity) message;

            this.normalSubscribedMatches.invalidate(stop.getEventId());
            this.terminalSubscribedMatches.invalidate(stop.getEventId());

        } else if (message instanceof MatchUpdateEntity) {

            MatchUpdateEntity update = (MatchUpdateEntity) message;

            boolean isTerminal = ScoutMatchStatus.isTerminalState(update.getMatchStatus());

            if (isTerminal) {
                this.terminalSubscribedMatches.put(update.getEventId(), NullObject);
                this.normalSubscribedMatches.invalidate(update.getEventId());
            } else {
                this.normalSubscribedMatches.put(update.getEventId(), NullObject);
                this.terminalSubscribedMatches.invalidate(update.getEventId());
            }
        }

        notifyOnMessageProcessed(message);
    }

    @Override
    public void setListener(MessageProcessorListener<LiveScoutEntityBase> listener) {
        this.messageListener = listener;
    }

    /**
     * Get a list of matches
     *
     * @param hoursBack        - number of hours back (from current time)
     * @param hoursForward     - number of hours forward (from current time)
     * @param includeAvailable - include also available matches (which you have not booked yet)
     */
    public void getMatchList(int hoursBack, int hoursForward, boolean includeAvailable, Collection<Long> sportIds, Collection<Long> matchIds) {
        sdkLogger.logClientInteraction(Level.INFO, String.format(
                "getEventList hoursBack: %s, hoursForward: %s, includeAvailable: %s, sportIds: %s, matchIds: %s",
                hoursBack, hoursForward, includeAvailable, StringUtils.join(sportIds, ','), StringUtils.join(matchIds, ',')));
        notifyOnRequestReady(requestFactory.buildMatchList(hoursBack, hoursForward, includeAvailable, sportIds, matchIds));
    }

    /**
     * Makes a server time request.
     */
    @Override
    public void requestServerTime() {
        sdkLogger.logClientInteraction(Level.INFO, "requestServerTime");
        notifyOnRequestReady(requestFactory.buildServerTimeRequest());
    }

    /**
     * Instructs the {@link com.sportradar.sdk.proto.common.RequestProducer} to start generating requests.
     */
    @Override
    public void onStarting() {
        // After a reconnect we need to resubscribe to all matches again
        if (wasDisconnected) {
            Set<EventIdentifier> eventIdentifiers = getAllSubscribedMatches();
            subscribe(eventIdentifiers.toArray(new EventIdentifier[eventIdentifiers.size()]));
        }
    }

    /**
     * Instructs the {@link com.sportradar.sdk.proto.common.RequestProducer} to stop generating requests;
     */
    @Override
    public void onStopping() {
        wasDisconnected = true;

        // Invoke betstops
        DateTime now = TimeProvider.getCurrent().getCurrentTime();
        for (EventIdentifier id : getAllSubscribedMatches()) {
            notifyOnMessageProcessed(ScoutFakeBetStopFactory.generateBetStop(id, now));
        }
    }

    private Set<EventIdentifier> getAllSubscribedMatches() {
        Set<EventIdentifier> result = new HashSet<>(this.normalSubscribedMatches.asMap().keySet());
        result.addAll(new HashSet<>(this.terminalSubscribedMatches.asMap().keySet()));
        return result;
    }

    private Set<Long> getIds(EventIdentifier[] ids) {
        HashSet<Long> ret = new HashSet<>();

        for (final EventIdentifier id : ids) {
            if (id == null) {
                continue;
            }
            if (!ret.add(id.getEventId())) {
                logger.warn(String.format("Duplicate id %s", id));
            }
        }

        return ret;
    }

    private void notifyOnMessageProcessed(final LiveScoutEntityBase message) {
        final MessageProcessorListener<LiveScoutEntityBase> copy = this.messageListener;
        if (copy != null) {
            copy.onMessageProcessed(message, this);
        }
    }


}
