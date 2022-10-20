package com.sportradar.sdk.common.settings;

import org.joda.time.Duration;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class LimiterData {

    private int limit;
    private Duration duration;
    private String name;

    public LimiterData(int limit, Duration duration, String name) {
        checkNotNull(duration);
        this.limit = limit;
        this.duration = duration;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + duration.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LimiterData) {
            LimiterData ld = (LimiterData) obj;
            return (ld.getLimit() == limit && ld.getDuration().equals(duration));
        }
        return false;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getLimit() {
        return limit;
    }
}
