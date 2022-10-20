package com.sportradar.sdk.dispatch.livescout;

import com.lmax.disruptor.EventHandler;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.feed.livescout.enums.ScoutFeedType;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link EventHandler} implementation used to dispatch live-scout related entities to the user.
 */
public abstract class EntityEventHandler implements EventHandler<LiveScoutDispatcherContainer> {

    /**
     * The {@link Logger} implementation used for logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(EntityEventHandler.class);
    /**
     * The number of {@link EntityEventHandler} instances handling user events
     */
    private final int dispatcherCount;
    /**
     * Reference to the feed.
     */
    private final LiveScoutFeed feed;
    /**
     * The id of the current {@link EntityEventHandler} used to determine which messages will be processed by
     * the current instance.
     */
    private final int id;
    /**
     * The {@link LiveScoutFeedListener} instance used to inform the user about incoming messages
     */
    private final LiveScoutFeedListener listener;

    /**
     * Initializes a new instance of the {@link EntityEventHandler} class
     *
     * @param id              The id of the current {@link EntityEventHandler} used to determine which messages will be processed by the current instance.
     * @param dispatcherCount The number of {@link EntityEventHandler} instances handling user events
     * @param listener        The {@link LiveScoutFeedListener} instance used to inform the user about incoming messages
     * @param feed            The {@link LiveScoutFeed} which will be passed to method calls on the user provided listener
     */
    protected EntityEventHandler(int id, int dispatcherCount, LiveScoutFeedListener listener, LiveScoutFeed feed) {
        checkArgument(id > 0, "id must be greater than zero");
        checkArgument(dispatcherCount >= id, "dispatcherCount must be greater then the dispatcher id");
        checkNotNull(listener, "listener cannot be a null reference");
        checkNotNull(feed, "feed cannot be a null reference");

        this.dispatcherCount = dispatcherCount;
        this.listener = listener;
        this.id = id;
        this.feed = feed;
    }

    /**
     * Invoked by the disruptor when a message is ready to be processed by the current handler.
     *
     * @param entityContainer The {@link JaxbLiveScoutEntityFactory} containing the information about the message to be raised.
     * @param sequenceNumber  The ring buffer's sequence number of the current item.
     * @param isEndOfBatch    Specifies whether the current item is the last of the available item batch.
     */
    @Override
    public void onEvent(LiveScoutDispatcherContainer entityContainer, long sequenceNumber, boolean isEndOfBatch) {
        if (!entityContainer.isValid()) {
            return;
        }

        LiveScoutEntityBase entity = entityContainer.getEntity();

        if (entity == null) {
            return;
        }

        if (!EntityEventHandler.canDispatchEntity(id, dispatcherCount, entity)) {
            return;
        }

        dispatchEntity(entity);
        entityContainer.clear();
    }

    protected void dispatchFullMatchUpdateReceived(MatchUpdateEntity matchUpdate) {
        try {
            listener.onFullMatchUpdateReceived(feed, matchUpdate);
        } catch (Exception e) {
            logger.warn("User handler for onFullMatchUpdateReceived threw exception", e);
        }
    }

    protected void dispatchLineups(LineupsEntity entity) {
        try {
            listener.onLineupsReceived(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onLineupsReceived threw exception", e);
        }
    }

    protected void dispatchMatchBooking(MatchBookingEntity entity) {
        try {
            listener.onMatchBooked(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onMatchBooked threw exception", e);
        }
    }

    protected void dispatchMatchData(MatchDataEntity entity) {
        try {
            listener.onMatchDataReceived(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onLineupsReceived threw exception", e);
        }
    }

    protected void dispatchMatchList(MatchListEntity entity) {
        try {
            listener.onMatchListReceived(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onMatchListReceived threw exception", e);
        }
    }

    protected void dispatchMatchListUpdate(MatchListUpdateEntity entity) {
        try {
            listener.onMatchListUpdateReceived(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onMatchListUpdateReceived threw exception", e);
        }
    }

    protected void dispatchMatchStop(MatchStopEntity entity) {
        try {
            listener.onMatchStopped(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onMatchStopped threw exception", e);
        }
    }

    protected void dispatchMatchUpdate(MatchUpdateEntity matchUpdate) {
        try {
            listener.onMatchUpdateReceived(feed, matchUpdate);
        } catch (Exception e) {
            logger.warn("User handler for onMatchUpdateReceived threw exception", e);
        }
    }

    protected void dispatchOddsSuggestion(OddsSuggestionsEntity entity) {
        try {
            listener.onOddsSuggestionReceived(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onOddsSuggestionReceived threw exception", e);
        }
    }

    protected void dispatchOnMatchDeltaUpdateDeltaReceived(MatchUpdateEntity matchUpdate) {
        try {
            listener.onMatchDeltaUpdateUpdateReceived(feed, matchUpdate);
        } catch (Exception e) {
            logger.warn("User handler for onMatchDeltaUpdateUpdateReceived threw exception", e);
        }
    }

    protected void dispatchOnMatchDeltaUpdateReceived(MatchUpdateEntity matchUpdate) {
        try {
            listener.onMatchDeltaUpdateReceived(feed, matchUpdate);
        } catch (Exception e) {
            logger.warn("User handler for onMatchDeltaUpdateReceived threw exception", e);
        }
    }


    protected void dispatchServerTime(ServerTimeEntity entity) {
        try {
            listener.onServerTimeReceived(feed, entity);
        } catch (Exception e) {
            logger.warn("User handler for onScoutInfoReceived threw exception", e);
        }
    }

    /**
     * Determines the actual type of the passed {@code entity} and calls the appropriate method on the {@code listener.}
     *
     * @param entity The {@link LiveScoutEntityBase} derived instance representing the message to be dispatched
     */
    private void dispatchEntity(LiveScoutEntityBase entity) {

        if (entity instanceof MatchListUpdateEntity) {
            dispatchMatchListUpdate((MatchListUpdateEntity) entity);
        } else if (entity instanceof MatchListEntity) {
            dispatchMatchList((MatchListEntity) entity);
        } else if (entity instanceof MatchStopEntity) {
            dispatchMatchStop((MatchStopEntity) entity);
        } else if (entity instanceof MatchDataEntity) {
            dispatchMatchData((MatchDataEntity) entity);
        } else if (entity instanceof MatchUpdateEntity) {
            MatchUpdateEntity matchUpdate = (MatchUpdateEntity) entity;

            if (matchUpdate.getMatchHeader() == null) {
                dispatchMatchUpdate(matchUpdate);
            } else {
                ScoutFeedType feedType = matchUpdate.getMatchHeader().getFeedType();

                switch (feedType) {
                    case FULL:
                        dispatchFullMatchUpdateReceived(matchUpdate);
                        break;
                    case DELTA:
                        dispatchOnMatchDeltaUpdateReceived(matchUpdate);
                        break;
                    case DELTAUPDATE:
                        dispatchOnMatchDeltaUpdateDeltaReceived(matchUpdate);
                        break;
                    default:
                        logger.warn("Unknown type of the ScoutFeedType encountered. type: {}", feedType);

                }
            }
        } else if (entity instanceof OddsSuggestionsEntity) {
            dispatchOddsSuggestion((OddsSuggestionsEntity) entity);
        } else if (entity instanceof MatchBookingEntity) {
            dispatchMatchBooking((MatchBookingEntity) entity);
        } else if (entity instanceof LineupsEntity) {
            dispatchLineups((LineupsEntity) entity);
        } else if (entity instanceof ServerTimeEntity) {
            dispatchServerTime((ServerTimeEntity) entity);
        } else {
            logger.warn("Cannot dispatch entity because type: {} is not supported", entity.getClass().getName());
        }
    }

    /**
     * Determines whether the dispatcher with specified {@code dispatcherId} can process the entity with specified id
     *
     * @param dispatcherId    The id of the current {@link EntityEventHandler} used to determine which messages will be processed by
     *                        the current instance.
     * @param dispatcherCount The number of {@link EntityEventHandler} instances handling user events
     * @param entity          The {@link LiveScoutEntityBase} derived instance to be dispatched
     * @return True if the passed {@link LiveScoutEntityBase} derived instance can be dispatched by the current handler. Otherwise false.
     */
    private static boolean canDispatchEntity(int dispatcherId, int dispatcherCount, LiveScoutEntityBase entity) {
        EventIdentifier identifier = entity.getEventId();

        if (identifier == null) {
            return (dispatcherId == 1);
        }

        long eventId = identifier.getEventId();
        //Training matches can have negative match id
        eventId = Math.abs(eventId);
        return (eventId % dispatcherCount) == dispatcherId - 1;
    }
}
