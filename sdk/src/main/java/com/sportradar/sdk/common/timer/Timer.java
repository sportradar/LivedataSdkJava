/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/
package com.sportradar.sdk.common.timer;

import org.joda.time.Duration;

/**
 * Represents a timer used to trigger periodic events.
 *
 * @author uros.bregar
 */
public interface Timer {

    /**
     * Sets the {@link TimerListener} observation used to observe this {@link Timer} implementation
     *
     * @param listener The {@link TimerListener} instance used to observe this {@link Timer} or a null reference.
     */
    void setListener(TimerListener listener);

    /**
     * Schedules an one-time event to be trigger after time interval specified by passed {@link Duration}.
     * Any events scheduled by previous calls to either {@link Timer#scheduleOneShot(org.joda.time.Duration)} or
     * {@link Timer#schedule(org.joda.time.Duration, org.joda.time.Duration)} are un-scheduled by this call.
     *
     * @param delay Specifies the time interval after which the one shot event will be triggered.
     * @throws IllegalArgumentException The {@code delay} is a null reference.
     */
    void scheduleOneShot(Duration delay);

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
    void schedule(Duration initialDelay, Duration period);

    /**
     * Un-schedules any events previously registered with calls to {@link Timer#scheduleOneShot(org.joda.time.Duration)}
     * or {@link Timer#schedule(org.joda.time.Duration, org.joda.time.Duration)}. If no events were scheduled it does
     * nothing.
     */
    void stop();
}
