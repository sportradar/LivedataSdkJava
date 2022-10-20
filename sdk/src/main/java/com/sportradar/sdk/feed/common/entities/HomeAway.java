package com.sportradar.sdk.feed.common.entities;

import java.io.Serializable;

/**
 * Home and away tuple (e.g. counters, stats).
 *
 * @param <T> Specifies the type of the home and away values
 */
public class HomeAway<T> implements Serializable {

    private static final long serialVersionUID = 8894922074608700268L;
    /**
     * The result of the away team
     */
    private T away;
    /**
     * The result of the home team
     */
    private T home;

    /**
     * Initializes a new instance of the {@link HomeAway} class.
     *
     * @param home The result of the home team
     * @param away The result of the away team
     */
    public HomeAway(T home, T away) {
        this.home = home;
        this.away = away;
    }

    /**
     * For Serializable
     */
    protected HomeAway() {

    }

    /**
     * Gets the result of the away team
     *
     * @return The result of the away team
     */
    public T getAway() {
        return away;
    }

    /**
     * Gets the result of the home team
     *
     * @return the result of the home team
     */
    public T getHome() {
        return home;
    }

    @Override
    public int hashCode() {
        int result = away != null ? away.hashCode() : 0;
        result = 31 * result + (home != null ? home.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HomeAway<?> homeAway = (HomeAway<?>) o;

        if (away != null ? !away.equals(homeAway.away) : homeAway.away != null) return false;
        return !(home != null ? !home.equals(homeAway.home) : homeAway.home != null);

    }

    /**
     * Constructs and returns a string representation of the current {@link HomeAway} instnace.
     *
     * @return A string representation of the current {@link HomeAway} instance.
     */
    @Override
    public String toString() {
        return String.format("(%s:%s)", home, away);
    }
}
