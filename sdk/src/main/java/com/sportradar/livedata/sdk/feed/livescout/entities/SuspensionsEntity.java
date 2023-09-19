package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.entities.HomeAway;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Contains information about suspensions
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class SuspensionsEntity implements Serializable {

    private static final long serialVersionUID = 4465911394156656399L;

    /**
     * The suspensions of the away team
     */
    private final int away;
    /**
     * The suspensions of the home team
     */
    private final int home;

    private final Team powerplay;

    protected SuspensionsEntity(int home, int away, Team powerplay){
        this.home = home;
        this.away = away;
        this.powerplay = powerplay;
    }

    public int getAway() {
        return away;
    }

    public int getHome() {
        return home;
    }

    public Team getPowerplay() {
        return powerplay;
    }
}