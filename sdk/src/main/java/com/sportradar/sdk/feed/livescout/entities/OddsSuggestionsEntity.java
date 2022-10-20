package com.sportradar.sdk.feed.livescout.entities;


import com.sportradar.sdk.feed.common.entities.EventIdentifier;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Suggested live odds for the match which are generated based on statistical models.
 */
public class OddsSuggestionsEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -6650212885745686595L;
    private long matchId;
    private List<ScoutOddsEntity> odds;



    protected OddsSuggestionsEntity(final Map<String, String> otherAttributes) {
        super(otherAttributes);
    }

    /**
     * Get the unique event id.
     *
     * @return - an event id or null
     */
    @Override
    public EventIdentifier getEventId() {
        return EventIdentifier.id(matchId);
    }

    /**
     * Gets match id
     *
     * @return match id
     */
    public long getMatchId() {
        return matchId;
    }

    /**
     * Gets odds
     *
     * @return odds
     */
    public List<ScoutOddsEntity> getOdds() {
        return odds;
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
        return "OddsSuggestionsEntity{" +
                super.toString() +
                "matchId=" + matchId +
                ", odds=" + odds +
                '}';
    }

    protected void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    protected void setOdds(List<ScoutOddsEntity> odds) {
        this.odds = Collections.unmodifiableList(odds);
    }
}
