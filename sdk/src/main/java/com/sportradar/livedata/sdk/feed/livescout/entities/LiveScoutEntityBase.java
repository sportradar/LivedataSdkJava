package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Serves as a base class that all LiveScout should be derived from.
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public abstract class LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = 435631727372382886L;

    /**
     * Get the unique event id.
     *
     * @return - an event id or null
     */
    public EventIdentifier getEventId() {
        return null;
    }
}