package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * Reply sent when a client unsubscribes from a match or when a match subscription failed.
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class MatchStopEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = 671142432732812242L;
    private long matchId;
    private String reason;


    /**
     * Initializes a new instance of the {@link LiveScoutEntityBase} class.
     */
    protected MatchStopEntity() {
        super();
    }

    /**
     * Get the unique event id.
     *
     * @return - an event id or null
     */
    @Override
    public EventIdentifier getEventId() {
        return EventIdentifier.id(getMatchId());
    }

    /**
     * Match id
     *
     * @return match id
     */
    public long getMatchId() {
        return matchId;
    }

    /**
     * Reason for match subscription failure or notification that un-subscription was successful.
     *
     * @return bet stop reason
     */
    public String getReason() {
        return reason;
    }
}
