package com.sportradar.sdk.common.timer;

import org.joda.time.DateTime;

import java.util.Date;


/**
 * A {@link TimeProviderBase} implementation which uses system time
 *
 * @author uros.bregar
 */
public class RealTimeTimeProvider extends TimeProviderBase {

    /**
     * Gets the {@link Date} specifying the current date and time.
     *
     * @return The {@link Date} specifying the current date and time.
     */
    public Date getDate() {
        return new Date();
    }

    /**
     * When overridden in derived class, gets the {@link org.joda.time.DateTime} specifying the current date and time.
     *
     * @return the {@link org.joda.time.DateTime} specifying the current date and time.
     */
    public DateTime getDateTime() {
        return DateTime.now();
    }
}
