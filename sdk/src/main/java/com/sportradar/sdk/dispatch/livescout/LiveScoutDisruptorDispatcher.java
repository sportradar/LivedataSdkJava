package com.sportradar.sdk.dispatch.livescout;

import ch.qos.logback.classic.Level;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.InsufficientCapacityException;
import com.sportradar.sdk.common.classes.jmx.LiveScoutStatisticsCounter;
import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.dispatch.DisruptorDispatcher;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import org.joda.time.Duration;
import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link LiveScoutDispatcher} implementation which uses disruptor to dispatch messages on multiple threads
 */
public class LiveScoutDisruptorDispatcher extends DisruptorDispatcher<LiveScoutDispatcherContainer> implements LiveScoutDispatcher {

    /**
     * Lock used in feed event dispatching
     */
    private final static Object lock = new Object();
    /**
     * The {@link LiveScoutStatisticsCounter} event counter used as JMX MBeans
     */
    private LiveScoutStatisticsCounter counter;
    /**
     * Executor service
     */
    private ExecutorService executor;
    /**
     * Reference to the feed.
     */
    private LiveScoutFeed feed;
    /**
     * The {@link LiveScoutFeedListener} provided by the user used to notify the user.
     */
    private LiveScoutFeedListener listener;

    private boolean started;

    /**
     * Initializes a new instance of the {@link LiveScoutDisruptorDispatcher} class.
     *
     * @param dispatcherCount The number of the consumers which dispatch events to the user.
     * @param executor        The {@link ExecutorService} used to spawn background threads
     */
    public LiveScoutDisruptorDispatcher(
            int dispatcherCount,
            int dispatcherQueueSize,
            ExecutorService executor,
            SdkLogger sdkLogger,
            LiveScoutStatisticsCounter counter) {

        super(executor, dispatcherCount, dispatcherQueueSize, sdkLogger);

        checkNotNull(counter);
        this.executor = executor;
        this.counter = counter;
    }

    @Override
    public void dispatchOnClosed(final LiveScoutFeed sender) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        long durationStart = System.currentTimeMillis();
                        listener.onClosed(sender);
                        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);
                        sdkLogger.logClientInteraction(Level.INFO, "" +
                                "onClosed", durationEnd, null);
                    } catch (Exception e) {
                        sdkLogger.logClientInteraction(Level.ERROR, "User handler for onClosed threw exception", e);
                    }
                }
            }
        });
    }

    @Override
    public void dispatchOnFeedEvent(final LiveScoutFeed sender, final FeedEventType eventType) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        long durationStart = System.currentTimeMillis();
                        listener.onFeedEvent(sender, eventType);
                        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);
                        sdkLogger.logClientInteraction(Level.INFO, "onFeedEvent : " + eventType, durationEnd, null);
                    } catch (Exception e) {
                        sdkLogger.logClientInteraction(Level.ERROR, "User handler for onFeedEvent threw exception", e);
                    }
                }
            }
        });
    }

    @Override
    public void dispatchOnInitialized(final LiveScoutFeed sender) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        long durationStart = System.currentTimeMillis();
                        listener.onInitialized(sender);
                        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);
                        sdkLogger.logClientInteraction(Level.INFO, "onInitialized", durationEnd, null);
                    } catch (Exception e) {
                        sdkLogger.logClientInteraction(Level.ERROR, "User handler for onInitialized threw exception", e);
                    }
                }
            }
        });
    }

    @Override
    public void dispatchOnOpened(final LiveScoutFeed sender) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        long durationStart = System.currentTimeMillis();
                        listener.onOpened(sender);
                        Duration durationEnd = new Duration(System.currentTimeMillis()).minus(durationStart);
                        sdkLogger.logClientInteraction(Level.INFO, "onOpened", durationEnd, null);
                    } catch (Exception e) {
                        sdkLogger.logClientInteraction(Level.ERROR, "User handler for onOpened threw exception", e);
                    }
                }
            }
        });
    }

    /**
     * Starts the current {@link LiveScoutDispatcher}
     *
     * @param listener The {@link LiveScoutFeedListener} used by the current {@link LiveScoutFeedListener} to dispatch events
     */
    @Override
    public void start(LiveScoutFeedListener listener) {
        checkNotNull(listener, "listener cannot be a null reference");
        this.listener = listener;

        super.startDisruptor(LiveScoutDispatcherContainer.CONTAINER_FACTORY);
        started = true;
    }

    /**
     * Stops the current {@link LiveScoutDispatcher} and disposed any resources associated with it.
     */
    @Override
    public void stop() {
        started = false;
        super.stopDisruptor(10, TimeUnit.SECONDS);
    }

    /**
     * Dispatches the passed {@link LiveScoutEntityBase} to the user
     *
     * @param entity the {@link LiveScoutEntityBase} to dispatch to the user.
     */
    @Override
    public void dispatchEntity(LiveScoutEntityBase entity) throws InsufficientCapacityException {
        checkNotNull(entity, "entity cannot be a null reference");

        if(started) {
            super.publish(new LiveScoutDispatcherContainer(entity));
        }
    }

    /**
     * Set the feed.
     *
     * @param feed
     */
    public void setFeed(LiveScoutFeed feed) {
        checkNotNull(feed, "feed cannot be a null reference");
        this.feed = feed;
    }

    /**
     * Constructs and returns an array of {@link com.lmax.disruptor.EventHandler} instances
     * representing disruptor dispatchers.
     *
     * @return an array of {@link com.lmax.disruptor.EventHandler} instances representing disruptor dispatchers.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected EventHandler<LiveScoutDispatcherContainer>[] buildConsumers() {
        EventHandler<LiveScoutDispatcherContainer>[] entityDispatchers = new EventHandler[dispatcherCount];

        for (int i = 1; i <= dispatcherCount; i++) {
            entityDispatchers[i - 1] = new StatisticsLiveScoutEntityEventHandler(i, dispatcherCount, listener, feed, sdkLogger, counter);
        }
        return entityDispatchers;
    }

}
