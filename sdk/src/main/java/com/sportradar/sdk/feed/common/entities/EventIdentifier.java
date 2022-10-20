/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common.entities;


import java.io.Serializable;

/**
 * Uniquely identifies an event.
 */
public class EventIdentifier implements Serializable {
    private final static long serialVersionUID = 1L;
    protected final long eventId;

    protected EventIdentifier(long eventId) {
        this.eventId = eventId;
    }

    /**
     * Determines whether passed {@link EventIdentifier} is equal to the current one
     *
     * @param other The {@link EventIdentifier} to be compared to the current one
     * @return True if passed {@link EventIdentifier} is equal to the current one, otherwise false.
     */
    public boolean equals(EventIdentifier other) {
        if (other == null) {
            return false;
        }

        return this.eventId == other.eventId;
    }

    /**
     * Gets event id
     * @return event id
     */
    public long getEventId() {
        return eventId;
    }

    @Override
    public int hashCode() {
        int result = (int) eventId;
        result = 31 * result;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventIdentifier)) return false;

        EventIdentifier other = (EventIdentifier) o;
        return equals(other);
    }

    @Override
    public String toString() {
        return "EventIdentifier{" +
                "eventId=" + eventId +
                '}';
    }

    /**
     * Build an EventIdentifier from id
     *
     * @param id - id of an event
     * @return an EventIdentifier
     */
    public static EventIdentifier id(long id) {
        return new EventIdentifier(id);
    }
}
