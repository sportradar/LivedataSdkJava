/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.timer;

import org.joda.time.Duration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link Timer} implementation which raises it's events on a background thread.
 *
 * @author uros.bregar
 */
public class PeriodicTimer implements Timer {

    /**
     * The {@link ScheduledExecutorService} used to schedule the timer's events.
     */
    private final ScheduledExecutorService executorService;

    /**
     * the {@link Object} used to synchronize access to shared members.
     */
    private final Object lock = new Object();

    /**
     * The {@link Runnable} implementation used to register events with {@link ScheduledExecutorService}
     */
    private final Runnable runnable;

    /**
     * The listener used to observe the timer events.
     */
    private TimerListener listener;

    /**
     * The {@link ScheduledFuture} used to un-register the runnable use to trigger the timer.
     */
    private ScheduledFuture<?> future;

    /**
     * Initializes a new instance of the {@link PeriodicTimer} class.
     *
     * @param executorService The {@link ScheduledExecutorService} used to schedule the timer's events.
     *
     * @throws IllegalArgumentException The {@code executorService} is a null reference or {@code initialDelay} is smaller than zero or {@code period} is smaller than or equal to zero.
     */
    public PeriodicTimer(ScheduledExecutorService executorService) {
        checkNotNull(executorService, "executorService cannot be a null reference");

        this.executorService = executorService;

        this.runnable = new Runnable() {
            @Override
            public void run() {
                notifyObserver();
            }
        };
    }

    /**
     * Notifies the observer that the timer period has elapsed
     */
    private void notifyObserver() {
        TimerListener localListener = null;
        synchronized (lock) {
            if (future == null) {
                return;
            }

            localListener = this.listener;
        }
        if (localListener != null) {
            localListener.onTick();
        }
    }

    /**
     * Sets the {@link TimerListener} observation used to observe this {@link Timer} implementation
     *
     * @param listener The {@link TimerListener} instance used to observe this {@link Timer} or a null reference.
     */
    @Override
    public void setListener(TimerListener listener) {
        synchronized (lock) {
            this.listener = listener;
        }
    }

    /**
     * Schedules an one-time event to be trigger after time interval specified by passed {@link org.joda.time.Duration}.
     * Any events scheduled by previous calls to either {@link Timer#scheduleOneShot(org.joda.time.Duration)} or
     * {@link Timer#schedule(org.joda.time.Duration, org.joda.time.Duration)} are un-scheduled by this call.
     *
     * @param delay Specifies the time interval after which the one shot event will be triggered.
     * @throws IllegalArgumentException The {@code delay} is a null reference.
     */
    @Override
    public void scheduleOneShot(Duration delay) {
        synchronized (lock) {
            if (future != null) {
                future.cancel(false);
                future = null;
            }
            future = executorService.schedule(runnable, delay.getMillis(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Schedules a periodic event with specified {@code initialDelay} and {@code period}.
     * Any events scheduled by previous calls to either {@link Timer#scheduleOneShot(org.joda.time.Duration)} or
     * {@link Timer#schedule(org.joda.time.Duration, org.joda.time.Duration)} are un-scheduled by this call.
     *
     * @param initialDelay Specifies the time interval after which the one shot event will be triggered.
     * @param period       Specifies the timer period.
     * @throws IllegalArgumentException The {@code initialDelay} is a null reference or
     *                                  {@code period} is a null reference.
     */
    @Override
    public void schedule(Duration initialDelay, Duration period) {
        synchronized (lock) {
            if (future != null) {
                future.cancel(false);
                future = null;
            }
            future = executorService.scheduleWithFixedDelay(
                    runnable,
                    initialDelay.getMillis(),
                    period.getMillis(),
                    TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Un-schedules any events previously registered with calls to {@link Timer#scheduleOneShot(org.joda.time.Duration)}
     * or {@link Timer#schedule(org.joda.time.Duration, org.joda.time.Duration)}. If no events were scheduled it does
     * nothing.
     */
    @Override
    public void stop() {
        synchronized (lock) {
            if (future == null) {
                return;
            }

            future.cancel(false);
            future = null;
        }
    }
}
