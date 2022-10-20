package com.sportradar.sdk.common.timer;

import org.joda.time.DateTime;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class TimeProviderBase {

    /**
     * Currently used {@link TimeProviderBase} instance.
     */
    protected static TimeProviderBase current;

    /**
     * Initializes static members of the {@link TimeProvider} class.
     */
    static {
        current = new RealTimeTimeProvider();
    }

    /**
     * Gets the currently used {@link TimeProviderBase} instance.
     *
     * @return The currently used {@link TimeProviderBase} instance.
     */
    public static TimeProviderBase getCurrent() {
        return current;
    }

    /**
     * When overridden in derived class, gets the {@link org.joda.time.DateTime} specifying the current date and time.
     *
     * @return the {@link org.joda.time.DateTime} specifying the current date and time.
     */
    public abstract DateTime getDateTime();

    public DateTime getCurrentTime() {
        return getDateTime();
    }

    /**
     * When overridden in derived class, gets the {@link Date} specifying the current date and time.
     *
     * @return The {@link Date} specifying the current date and time.
     */
    public abstract Date getDate();

    /**
     * Sets the currently used {@link TimeProviderBase} instance.
     *
     * @param provider The {@link TimeProviderBase} to be used.
     * @throws IllegalArgumentException The {@code provider} is a null reference.
     */
    public static void setCurrent(TimeProviderBase provider) {
        checkNotNull(provider, "The provider cannot be a null reference");

        current = provider;
    }

    /**
     * Sets the currently used {@link TimeProviderBase} to default one which uses system time.
     */
    public static void resetToRealTime() {
        current = new RealTimeTimeProvider();
    }

    /**
     * Adds millis to current date
     *
     * @param millis milliseconds to add to current date
     * @return The {@link Date} specifying future date and time
     */
    public Date getFutureDate(long millis) {
        return new Date(getDate().getTime() + millis);
    }
}
