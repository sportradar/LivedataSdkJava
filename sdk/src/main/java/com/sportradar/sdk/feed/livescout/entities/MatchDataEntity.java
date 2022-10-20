package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;
import java.util.Map;

/**
 * Match time information during a game.
 */
public class MatchDataEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -7594130179371182351L;
    private long matchId;
    private String matchTime;
    private String remainingTimeInPeriod;


    protected MatchDataEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
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
     * Gets match time
     *
     * @return match time
     */
    public String getMatchTime() {
        return matchTime;
    }

    /**
     * Gets remaining time in period
     *
     * @return remaining time in period
     */
    public String getRemainingTimeInPeriod() {
        return remainingTimeInPeriod;
    }

    @Override
    public String toString() {
        return "MatchDataEntity{" +
                "matchId=" + matchId +
                ", matchTime='" + matchTime + '\'' +
                ", remainingTimeInPeriod='" + remainingTimeInPeriod + '\'' +
                '}';
    }

    protected void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    protected void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    protected void setRemainingTimeInPeriod(String remainingTimeInPeriod) {
        this.remainingTimeInPeriod = remainingTimeInPeriod;
    }
}
