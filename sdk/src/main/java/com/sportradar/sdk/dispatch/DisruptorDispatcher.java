package com.sportradar.sdk.dispatch;

import ch.qos.logback.classic.Level;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.*;

/**
 * A base class for dispatcher classes which internally uses a disruptor pattern for dispatching.
 *
 * @param <T> Specifies the type used by disruptor to store unprocessed messages.
 */
public abstract class DisruptorDispatcher<T extends DispatcherContainer<T>> {

    private final static Logger logger = LoggerFactory.getLogger(DisruptorDispatcher.class);
    private final static int Status_NotStarted = 0;
    private final static int Status_InProcess = 1;
    private final static int Status_Started = 2;

    /**
     * The {@link SdkLogger} instance used for structured logging.
     */
    protected final SdkLogger sdkLogger;
    /**
     * The number of the dispatchers
     */
    protected final int dispatcherCount;

    /**
     * The size of the disruptor
     */
    protected final int dispatcherQueueSize;

    /**
     * The half size of the disruptor
     */
    protected final long halfDispatcherQueueSize;

    /**
     * The {@link ExecutorService} used to spawn background threads
     */
    private final ExecutorService executor;

    /**
     * The disruptor used to dispatch events to the user.
     */
    private volatile Disruptor<T> disruptor;
    /**
     * Value indicating whether the dispatcher is started.
     */
    private final AtomicInteger status = new AtomicInteger(Status_NotStarted);

    /**
     * The ring-buffer used to store events which haven't yet been dispatched to the user.
     */
    private volatile RingBuffer<T> ringBuffer;


    /**
     * Initializes a new instance of the {@link DisruptorDispatcher} class.
     *
     * @param executor The {@link ExecutorService} used to spawn background threads
     */
    protected DisruptorDispatcher(
            ExecutorService executor,
            int dispatcherCount,
            int dispatcherQueueSize,
            SdkLogger sdkLogger) {
        checkNotNull(executor, "executor cannot be a null reference");
        checkArgument(dispatcherCount > 0, "dispatcherCount must be greater than 0");
        checkArgument(dispatcherQueueSize > 0, "dispatcherQueueSize must be greater than 0");
        checkNotNull(sdkLogger, "sdkLogger cannot be a null reference");

        this.executor = executor;
        this.dispatcherQueueSize = dispatcherQueueSize;
        this.halfDispatcherQueueSize = dispatcherQueueSize / 2;
        this.sdkLogger = sdkLogger;
        this.dispatcherCount = dispatcherCount;
    }


    /**
     * Publishes the passed {@code container} to the ring buffer.
     *
     * @param container The container to be published
     * @throws IllegalStateException    The dispatcher is stopped.
     * @throws IllegalArgumentException The {@code container} is a null reference.
     */
    protected void publish(T container) throws InsufficientCapacityException {
        checkNotNull(container, "container cannot be a null reference");

        checkState(status.get() == Status_Started, "DisruptorDispatcher is not started");

        RingBuffer<T> ringBufferTmp = this.ringBuffer;
        if (ringBufferTmp == null) {
            checkState(false, "RingBuffer is null");
        }
        long sequence = ringBufferTmp.tryNext();
        long capacity = ringBufferTmp.remainingCapacity();
        if (capacity < this.halfDispatcherQueueSize) {
            sdkLogger.logAlert(
                    Level.WARN,
                    String.format("DisruptorDispatcher has only %s remaining capacity (of %s)", capacity, this.dispatcherQueueSize));
            logger.warn(String.format("DisruptorDispatcher has only %s remaining capacity (of %s)", capacity, this.dispatcherQueueSize));
        }
        try {
            T ringBufferContainer = ringBufferTmp.get(sequence);
            ringBufferContainer.copy(container);
        } finally {
            ringBufferTmp.publish(sequence);
        }
    }

    /**
     * Constructs and starts the disruptor and associated elements
     * </p>
     * <p>
     * Notice that method is not thread safe and should only be called within critical region.
     * </p>
     *
     * @param containerFactory A {@link EventFactory} implementation used to construct containers
     * @throws IllegalStateException the {@link DisruptorDispatcher} is already started.
     */
    @SuppressWarnings("unchecked")
    protected void startDisruptor(EventFactory<T> containerFactory) {

        while (status.get() == Status_InProcess) {
            Thread.yield();
        }
        if (status.compareAndSet(Status_NotStarted, Status_InProcess)) {
            try {
                disruptor = new Disruptor<>(containerFactory, dispatcherQueueSize, executor);
                EventHandler[] handlers = buildConsumers();
                disruptor.handleEventsWith((EventHandler<T>[]) handlers);
                ringBuffer = disruptor.start();
                status.set(Status_Started);
            } catch (Exception exc) {
                try {
                    if (disruptor != null) {
                        disruptor.shutdown();
                    }
                } finally {
                    disruptor = null;
                    ringBuffer = null;
                    status.set(Status_NotStarted);
                }
                throw exc;
            }
        }
    }

    protected abstract EventHandler[] buildConsumers();


    /**
     * Gracefully stops the disruptor in given interval and disposes resources
     * associated with the current {@link DisruptorDispatcher} instance.
     *
     * @param timeout  timeout
     * @param timeUnit timeUnit
     */
    protected void stopDisruptor(final long timeout, final TimeUnit timeUnit) {
        try {
            disruptor.shutdown(timeout, timeUnit);
        } catch (Exception exc) {
            logger.error("failed to shutdown disruptor cleanly, forcing shutdown", exc);
            disruptor.halt();
        } finally {
            disruptor = null;
            ringBuffer = null;
            status.set(Status_NotStarted);
        }
    }

}