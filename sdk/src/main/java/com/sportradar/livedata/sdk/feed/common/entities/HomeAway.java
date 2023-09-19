package com.sportradar.livedata.sdk.feed.common.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Home and away tuple (e.g. counters, stats).
 *
 * @param <T> Specifies the type of the home and away values
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
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
    protected HomeAway() {}

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
}
