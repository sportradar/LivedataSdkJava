package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.entities.HomeAway;
import com.sportradar.sdk.feed.common.enums.Team;

import java.io.Serializable;

/**
 * Contains information about suspensions
 */
public class SuspensionsEntity implements Serializable {

    private static final long serialVersionUID = 4465911394156656399L;

    /**
     * The suspensions of the away team
     */
    private int away;
    /**
     * The suspensions of the home team
     */
    private int home;

    private Team powerplay;

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

    @Override
    public String toString() {
        return "SuspensionsEntity{" +
                "home=" + home +
                ", away=" + away +
                ", powerplay=" + powerplay +
                '}';
    }
}