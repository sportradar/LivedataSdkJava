package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import lombok.*;

import java.io.Serializable;

/**
 * Stores team statics fields that are coming with TEAM_MATCH_STATS(1743) event.
 */
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class TeamStatisticsEntity implements Serializable {
    private static final long serialVersionUID = 2985143044035983853L;

    public static final int TEAM_MATCH_STATS = 1743;
    static TeamStatisticsEntity create(Event event){
        if(TEAM_MATCH_STATS == event.getType()){//type is int, so null is not an option
            return new TeamStatisticsEntity(event);
        }
        return null;
    }
    //common
    private String homeTeamStats;
    private String awayTeamStats;
    //Pitching stats
    private String homeTeamStatsTotal;
    private String awayTeamStatsTotal;
    private String homeTeamStatsP1;
    private String awayTeamStatsP1;
    private String homeTeamStatsP2;
    private String awayTeamStatsP2;
    private String homeTeamStatsP3;
    private String awayTeamStatsP3;
    private String homeTeamStatsP4;
    private String awayTeamStatsP4;
    private String homeTeamStatsOt;
    private String awayTeamStatsOt;
    //inning stats
    private String homeTeamStatsI1;
    private String awayTeamStatsI1;
    private String homeTeamStatsI2;
    private String awayTeamStatsI2;
    private String homeTeamStatsI3;
    private String awayTeamStatsI3;
    private String homeTeamStatsI4;
    private String awayTeamStatsI4;
    private String homeTeamStatsI5;
    private String awayTeamStatsI5;
    private String homeTeamStatsI6;
    private String awayTeamStatsI6;
    private String homeTeamStatsI7;
    private String awayTeamStatsI7;
    private String homeTeamStatsI8;
    private String awayTeamStatsI8;
    private String homeTeamStatsI9;
    private String awayTeamStatsI9;
    private String homeTeamStatsIe;
    private String awayTeamStatsIe;

    protected TeamStatisticsEntity(Event event){
        this.homeTeamStats = event.getHometeamstats();
        this.awayTeamStats = event.getAwayteamstats();

        this.homeTeamStatsTotal = event.getHometeamstatstotal();
        this.awayTeamStatsTotal = event.getAwayteamstatstotal();
        this.homeTeamStatsP1 = event.getHometeamstatsp1();
        this.awayTeamStatsP1 = event.getAwayteamstatsp1();
        this.homeTeamStatsP2 = event.getHometeamstatsp2();
        this.awayTeamStatsP2 = event.getAwayteamstatsp2();
        this.homeTeamStatsP3 = event.getHometeamstatsp3();
        this.awayTeamStatsP3 = event.getAwayteamstatsp3();
        this.homeTeamStatsP4 = event.getHometeamstatsp4();
        this.awayTeamStatsP4 = event.getAwayteamstatsp4();
        this.homeTeamStatsOt = event.getHometeamstatsot();
        this.awayTeamStatsOt = event.getAwayteamstatsot();

        this.homeTeamStatsI1 = event.getHometeamstatsi1();
        this.awayTeamStatsI1 = event.getAwayteamstatsi1();
        this.homeTeamStatsI2 = event.getHometeamstatsi2();
        this.awayTeamStatsI2 = event.getAwayteamstatsi2();
        this.homeTeamStatsI3 = event.getHometeamstatsi3();
        this.awayTeamStatsI3 = event.getAwayteamstatsi3();
        this.homeTeamStatsI4 = event.getHometeamstatsi4();
        this.awayTeamStatsI4 = event.getAwayteamstatsi4();
        this.homeTeamStatsI5 = event.getHometeamstatsi5();
        this.awayTeamStatsI5 = event.getAwayteamstatsi5();
        this.homeTeamStatsI6 = event.getHometeamstatsi6();
        this.awayTeamStatsI6 = event.getAwayteamstatsi6();
        this.homeTeamStatsI7 = event.getHometeamstatsi7();
        this.awayTeamStatsI7 = event.getAwayteamstatsi7();
        this.homeTeamStatsI8 = event.getHometeamstatsi8();
        this.awayTeamStatsI8 = event.getAwayteamstatsi8();
        this.homeTeamStatsI9 = event.getHometeamstatsi9();
        this.awayTeamStatsI9 = event.getAwayteamstatsi9();
        this.homeTeamStatsIe = event.getHometeamstatsie();
        this.awayTeamStatsIe = event.getAwayteamstatsie();
    }

    /**
     * Returns home team total statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsTotal() {
        return homeTeamStatsTotal;
    }
    /**
     * Returns away team total statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsTotal() {
        return awayTeamStatsTotal;
    }
    /**
     * Returns home team 1st pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsP1() {
        return homeTeamStatsP1;
    }
    /**
     * Returns away team 1st pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsP1() {
        return awayTeamStatsP1;
    }
    /**
     * Returns home team 3nd pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsP2() {
        return homeTeamStatsP2;
    }
    /**
     * Returns away team 2nd pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsP2() {
        return awayTeamStatsP2;
    }
    /**
     * Returns home team 3rd pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsP3() {
        return homeTeamStatsP3;
    }
    /**
     * Returns away team 3rd pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsP3() {
        return awayTeamStatsP3;
    }
    /**
     * Returns home team 4th pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsP4() {
        return homeTeamStatsP4;
    }
    /**
     * Returns away team 4th pitching statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsP4() {
        return awayTeamStatsP4;
    }
    /**
     * Returns home team over time statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsOt() {
        return homeTeamStatsOt;
    }
    /**
     * Returns away team over time statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsOt() {
        return awayTeamStatsOt;
    }
    /**
     * Returns home team 1st inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI1() {
        return homeTeamStatsI1;
    }
    /**
     * Returns away team 1st inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI1() {
        return awayTeamStatsI1;
    }
    /**
     * Returns home team 2nd inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI2() {
        return homeTeamStatsI2;
    }
    /**
     * Returns away team 2nd inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI2() {
        return awayTeamStatsI2;
    }
    /**
     * Returns home team 3rd inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI3() {
        return homeTeamStatsI3;
    }
    /**
     * Returns away team 3rd inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI3() {
        return awayTeamStatsI3;
    }
    /**
     * Returns home team 4th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI4() {
        return homeTeamStatsI4;
    }
    /**
     * Returns away team 4th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI4() {
        return awayTeamStatsI4;
    }
    /**
     * Returns home team 5th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI5() {
        return homeTeamStatsI5;
    }
    /**
     * Returns away team 5th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI5() {
        return awayTeamStatsI5;
    }
    /**
     * Returns home team 6th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI6() {
        return homeTeamStatsI6;
    }
    /**
     * Returns away team 6th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI6() {
        return awayTeamStatsI6;
    }
    /**
     * Returns home team 7th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI7() {
        return homeTeamStatsI7;
    }
    /**
     * Returns away team 7th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI7() {
        return awayTeamStatsI7;
    }
    /**
     * Returns home team 8th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI8() {
        return homeTeamStatsI8;
    }
    /**
     * Returns away team 8th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI8() {
        return awayTeamStatsI8;
    }
    /**
     * Returns home team 9th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsI9() {
        return homeTeamStatsI9;
    }
    /**
     * Returns away team 9th inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsI9() {
        return awayTeamStatsI9;
    }
    /**
     * Returns home team extra inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomeTeamStatsIe() {
        return homeTeamStatsIe;
    }
    /**
     * Returns away team extra inning statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayTeamStatsIe() {
        return awayTeamStatsIe;
    }
}
