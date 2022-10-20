package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.entities.EventIdentifier;

import java.io.Serializable;
import java.util.Map;

/**
 * Reply sent when a client unsubscribes from a match or when a match subscription failed.
 */
public class MatchStopEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = 671142432732812242L;
    private long matchId;
    private String reason;


    /**
     * Initializes a new instance of the {@link LiveScoutEntityBase} class.
     */
    protected MatchStopEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
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

    /**
     * Returns a string that represents the current object.
     * <p>
     * Note : Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "MatchStopEntity{" +
                super.toString() +
                "matchId=" + matchId +
                ", reason='" + reason + '\'' +
                '}';
    }

    protected void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    protected void setReason(String reason) {
        this.reason = reason;
    }
}
