package com.sportradar.sdk.test;


import com.sportradar.sdk.common.timer.TimeProvider;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link TimeProvider} implementation used for testing.
 */
public class FakeTimeProvider extends TimeProvider {

    private DateTime fakeTime;

    public FakeTimeProvider(DateTime fakeTime) {
        checkNotNull(fakeTime, "fakeTime cannot be a null reference");

        this.fakeTime = fakeTime;
    }

    /**
     * When overridden in derived class, gets the {@link org.joda.time.DateTime} specifying the current date and time.
     *
     * @return The {@link org.joda.time.DateTime} specifying the current date and time.
     */
    @Override
    public DateTime getCurrentTime() {
        return fakeTime;
    }

    @Override
    public Date getDate() {
        return getCurrentTime().toDate();
    }

    @Override
    public DateTime getDateTime() {
        return getCurrentTime();
    }

    /**
     * Moves the time reported by the current {@link TimeProvider} forward for value specified by passed {@link Duration}
     *
     * @param duration {@link Duration} specifying the interval for which the time should be moved forward.
     */
    public void moveForward(Duration duration) {
        checkNotNull(duration, "duration cannot be a null reference");
        fakeTime = fakeTime.plus(duration);
    }

    /**
     * Moves the time reported by the current {@link TimeProvider} backward for value specified by passed {@link Duration}
     *
     * @param duration {@link Duration} specifying the interval for which the time should be moved backward.
     */
    public void moveBackward(Duration duration) {
        checkNotNull(duration, "duration cannot be a null reference");
        fakeTime = fakeTime.minus(duration);
    }

}
