package com.sportradar.sdk.feed.livescout.classes;

import ch.qos.logback.classic.Level;
import com.lmax.disruptor.InsufficientCapacityException;
import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.sdk.feed.common.ProtocolManager;
import com.sportradar.sdk.feed.common.ProtocolManagerListener;
import com.sportradar.sdk.feed.common.entities.ConnectionParams;
import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.common.interfaces.UserRequestManager;
import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.sdk.feed.livescout.enums.ScoutFeedType;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sportradar.sdk.feed.common.TestManager;
import com.sportradar.sdk.feed.livescout.entities.MatchBookingEntity;
import com.sportradar.sdk.feed.livescout.entities.MatchStopEntity;
import com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutUserRequestManager;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link LiveScoutFeed} implementation used to manage live-scout feed.
 */
public class LiveScoutFeedImpl implements LiveScoutFeed {


    private final static Logger logger = LoggerFactory.getLogger(LiveScoutFeedImpl.class);
    /**
     * Object lock to ensure closing of the feed is called only once
     */
    protected final Object lock = new Object();
    /**
     * Sdk logging facility.
     */
    protected final SdkLogger sdkLogger;
    /**
     * A {@link LiveScoutDispatcher} implementation used to dispatch messages to the user.
     */
    private final LiveScoutDispatcher dispatcher;
    /**
     * A {@link ProtocolManager} implementation used to manage the underlying protocol and related components
     */
    private final ProtocolManager<OutgoingMessage, EntityBase> protocolManager;
    /**
     * The {@link LiveScoutUserRequestManager} used to process user requests.
     */
    private final LiveScoutUserRequestManager userRequestManager;
    private final ConnectionParams connectionParams;
    private final LiveScoutSettings settings;
    /**
     * Is the feed already initialized?
     */
    private boolean isInitialized;
    /**
     * Value specifying whether the feed is opened.
     */
    private volatile boolean opened;
    /**
     * Was the open event sent already?
     */
    private boolean wasOpenNotified;

    /**
     * Value specifying whether the feed was disposed and can no longer be used.
     */
    private volatile boolean disposed;
    private boolean closing;

    /**
     * Initializes a new instance of the {@link LiveScoutFeedImpl} class.
     *
     * @param protocolManager    A {@link ProtocolManager} implementation used to manage the underlying protocol and related components
     * @param userRequestManager The {@link UserRequestManager} used to process user requests.
     * @param dispatcher         A {@link LiveScoutDispatcher} implementation used to dispatch messages to the user.
     * @throws IllegalArgumentException The {@code protocolManager} is a null reference or
     *                                  a {@code userRequestManager} is a null reference or
     *                                  a {@code dispatcher} is a null reference.
     */
    public LiveScoutFeedImpl(
            ProtocolManager<OutgoingMessage, EntityBase> protocolManager,
            LiveScoutUserRequestManager userRequestManager,
            LiveScoutDispatcher dispatcher,
            SdkLogger sdkLogger,
            LiveScoutSettings settings) {

        checkNotNull(protocolManager, "protocolManager cannot be a null reference");
        checkNotNull(userRequestManager, "userRequestManager cannot be a null reference");
        checkNotNull(dispatcher, "dispatcher cannot be a null reference");
        checkNotNull(settings, "settings cannot be a null reference");

        this.protocolManager = protocolManager;
        this.userRequestManager = userRequestManager;
        this.dispatcher = dispatcher;
        this.sdkLogger = sdkLogger;
        this.settings = settings;
        dispatcher.setFeed(this);

        setDependencyListeners();
        connectionParams = new ConnectionParams(settings.getHostName(), settings.getPort(), null);
    }

    /**
     * Gets the value specifying whether the current feed is opened
     *
     * @return the value specifying whether the current feed is opened
     */
    @Override
    public boolean isOpened() {
        return opened;
    }

    /**
     * Opens the communication channel with the live-scout server
     *
     * @param listener The {@link LiveScoutFeedListener} instance used to observe the current {@link LiveScoutFeed}
     */
    @Override
    public synchronized void open(LiveScoutFeedListener listener) {
        checkNotNull(listener, "The listener cannot be a null reference");
        checkState(!disposed, "The feed is disposed and cannot be re-opened");
        checkState(!opened, "The feed is already opened");

        wasOpenNotified = false;
        isInitialized = false;
        dispatcher.start(listener);
        protocolManager.open();
        opened = true;
    }

    @Override
    public ConnectionParams getConnectionParams() {
        return connectionParams;
    }

    /**
     * Requests a list of available scout matches for a given time frame.
     * <p/>
     * By default only matches you have access to will be sent.
     * You can also request all available matches by setting the {@code includeAvailable}
     * If this is done the reply will also include matches that are open for booking.
     *
     * @param hoursBack        Specifies the open of the time frame
     * @param hoursForward     Specifies the end of the time frame
     * @param includeAvailable Specifies whether matches available for booking should be included
     * @param sportIds         - filter by sports ids, nullable
     * @param matchIds         - filter by matches ids, nullable
     */
    @Override
    public void getMatchList(int hoursBack, int hoursForward, boolean includeAvailable, Collection<Long> sportIds, Collection<Long> matchIds) {
        checkState(opened, "The feed is not opened");
        userRequestManager.getMatchList(hoursBack, hoursForward, includeAvailable, sportIds, matchIds);
    }

    @Override
    public void getMatchList(int hoursBack, int hoursForward, boolean includeAvailable) {
        getMatchList(hoursBack, hoursForward, includeAvailable, null, null);
    }


    @Override
    public void requestServerTime() {
        checkState(opened, "The feed is not opened");
        userRequestManager.requestServerTime();
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
        checkState(opened, "The feed is not opened");

        userRequestManager.bookMatches(eventIds);
    }

    /**
     * Gets the ids of all subscribed matches.
     *
     * @return the ids of all subscribed matches.
     */
    @Override
    public EventIdentifier[] getSubscribedEvents() {
        checkState(opened, "The feed is not opened");

        return userRequestManager.getSubscribedEvents();
    }

    /**
     * Subscribe to specified matches.
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchUpdateReceived(LiveScoutFeed, MatchUpdateEntity)}
     * message is response. If match subscription is successful, a full match update is sent out.
     * Otherwise explanation will be given in the response message (e.g. you do not have access
     * to the match or there are already two open subscriptions for the match).
     * With LiveScout you <b>must</b> explicitly call {@link LiveScoutFeed#subscribe(EventIdentifier[])} at open-up or no data will be received.
     *
     * @param eventIds Array of event ids
     */
    @Override
    public void subscribe(EventIdentifier[] eventIds) {
        checkState(opened, "The feed is not opened");

        userRequestManager.subscribe(eventIds);
    }

    /**
     * Un-subscribe from the specified matches. After this no more events associated with these matches will be sent.
     * LiveScout: Expect {@link LiveScoutFeedListener#onMatchStopped(LiveScoutFeed, MatchStopEntity)}
     * message in response.
     *
     * @param eventIds Array of event ids from which to un-subscribe
     */
    @Override
    public void unsubscribe(EventIdentifier[] eventIds) {
        checkState(opened, "The feed is not opened");

        userRequestManager.unsubscribe(eventIds);
    }

    /**
     * Request a full feed for the match, and then update messages will follow in the same pace as they were sent out live.
     * </p>
     * <p>
     * This should only be used on the test server.
     * </p>
     *
     * @param eventId      The id of the event.
     * @param messageDelay Specifies how many milliseconds to wait between sending out each message
     * @param startMessage Specifies which message to open replay on (how far into the match).
     *                     If {@code startMessage} is greater than or equal to the number of messages for the match,
     *                     a full feed for the finished match is sent out, and no more messages will follow after this.
     */
    @Override
    public void subscribeToTest(EventIdentifier eventId, Integer messageDelay, Integer startMessage) {
        checkNotNull(eventId);
        checkState(opened, "The feed is not opened");

        String delayStr = messageDelay != null ? String.format("messagedelay=\"%s\" ", messageDelay) : "";
        String startStr = startMessage != null ? String.format("startmessage=\"%s\" ", startMessage) : "";

        String fmt = String.format("<match matchid=\"%s\" %s%s feedtype=\"delta\"/>", eventId.getEventId(), delayStr, startStr);

        protocolManager.sendMessage(fmt);
    }

    /**
     * When connected to the test server you can obtain a manager to manipulate it.
     *
     * @return a {@link TestManager} implementation
     */
    @Override
    public TestManager getTestManager() {
        return userRequestManager.getTestManager();
    }

    /**
     * Closes the communication channel with the live-scout server.
     */
    @Override
    public synchronized void close() {
        checkState(opened, "The feed is not opened");

        protocolManager.close();
        dispatcher.stop();
        opened = false;
    }

    /**
     * Closes the current feed, and disposes all resources associated with it. Once the feed is disposed,
     * it can n longer be used.
     */
    @Override
    public final synchronized void dispose() {
        if (disposed) {
            return;
        }
        if (opened) {
            close();
        }
        disposed = true;
    }

    private void handleDispatcherException() {
        synchronized (lock) {
            if (isOpened()) {
                if (!closing) {
                    closing = true;
                    String errorMessage = "Dispatcher is full, closing the feed";
                    sdkLogger.logAlert(Level.ERROR, errorMessage);
                    logger.error(errorMessage);
                    dispatcher.dispatchOnFeedEvent(LiveScoutFeedImpl.this, FeedEventType.DISPATCHER_FULL);
                    close();
                    closing = false;
                }
            }
        }
    }

    /**
     * Marks the current feed as initialized and notifies the listener.
     */
    private void initialize() {
        if (!isInitialized) {
            dispatcher.dispatchOnInitialized(this);
            isInitialized = true;
        }
    }

    /**
     * Sets listeners on injected dependencies
     */
    private void setDependencyListeners() {
        this.protocolManager.setListener(new ProtocolManagerListener<EntityBase>() {
            @Override
            public void onDisconnected() {
                dispatcher.dispatchOnClosed(LiveScoutFeedImpl.this);
            }

            @Override
            public void onFeedEvent(FeedEventType eventType) {
                dispatcher.dispatchOnFeedEvent(LiveScoutFeedImpl.this, eventType);
                if (eventType == FeedEventType.PARSE_ERROR && settings.disconnectOnParseError()) {
                    logger.error("Disconnect on parse error enabled, disconnecting the feed");
                    protocolManager.reconnect();
                }
            }

            @Override
            public void onLoggedIn() {
                if (!wasOpenNotified) {
                    dispatcher.dispatchOnOpened(LiveScoutFeedImpl.this);
                    wasOpenNotified = true;
                }
                dispatcher.dispatchOnFeedEvent(LiveScoutFeedImpl.this, FeedEventType.AUTHENTICATED);
            }

            @Override
            public void onMessageReceived(EntityBase message) {
                // After first FULL update arrives we are initialized
                if (message instanceof MatchUpdateEntity) {
                    MatchUpdateEntity entity = (MatchUpdateEntity) message;

                    if (entity.getMatchHeader() != null && entity.getMatchHeader().getFeedType() == ScoutFeedType.FULL) {
                        initialize();
                    }
                }
                if (isOpened()) {
                    try {
                        dispatcher.dispatchEntity((LiveScoutEntityBase) message);
                    } catch (InsufficientCapacityException e) {
                        handleDispatcherException();
                    }
                }
            }
        });
    }
}
