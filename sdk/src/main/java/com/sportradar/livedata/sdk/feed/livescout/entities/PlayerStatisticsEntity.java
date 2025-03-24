package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import lombok.*;

import java.io.Serializable;

/**
 * Stores player statics fields that are coming with PLAYER_MATCH_STATS(1714) event.
 */
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class PlayerStatisticsEntity implements Serializable {
    private static final long serialVersionUID = 2985143044035983853L;

    public static final int PLAYER_MATCH_STATS = 1714;
    static PlayerStatisticsEntity tryCreate(Event event){
        if(PLAYER_MATCH_STATS == event.getType()){
            return new PlayerStatisticsEntity(event);
        }
        return null;
    }

    private String homePlayerStatsTotal;
    private String awayPlayerStatsTotal;

    private String homePlayerStatsP1;
    private String awayPlayerStatsP1;
    private String homePlayerStatsP2;
    private String awayPlayerStatsP2;
    private String homePlayerStatsP3;
    private String awayPlayerStatsP3;
    private String homePlayerStatsP4;
    private String awayPlayerStatsP4;
    private String homePlayerStatsOt;
    private String awayPlayerStatsOt;

    private String homePitchersStatsTotal;
    private String awayPitchersStatsTotal;
    private String homeBattersStatsTotal;
    private String awayBattersStatsTotal;

    private String homePlayerStatsSet1;
    private String awayPlayerStatsSet1;
    private String homePlayerStatsSet2;
    private String awayPlayerStatsSet2;
    private String homePlayerStatsSet3;
    private String awayPlayerStatsSet3;
    private String homePlayerStatsSet4;
    private String awayPlayerStatsSet4;
    private String homePlayerStatsSet5;
    private String awayPlayerStatsSet5;


    private PlayerStatisticsEntity(Event event){
        this.homePlayerStatsTotal = event.getHomeplayerstatstotal();
        this.awayPlayerStatsTotal = event.getAwayplayerstatstotal();

        this.homePlayerStatsP1 = event.getHomeplayerstatsp1();
        this.awayPlayerStatsP1 = event.getAwayplayerstatsp1();
        this.homePlayerStatsP2 = event.getHomeplayerstatsp2();
        this.awayPlayerStatsP2 = event.getAwayplayerstatsp2();
        this.homePlayerStatsP3 = event.getHomeplayerstatsp3();
        this.awayPlayerStatsP3 = event.getAwayplayerstatsp3();
        this.homePlayerStatsP4 = event.getHomeplayerstatsp4();
        this.awayPlayerStatsP4 = event.getAwayplayerstatsp4();
        this.homePlayerStatsOt = event.getHomeplayerstatsot();
        this.awayPlayerStatsOt = event.getAwayplayerstatsot();

        this.homePlayerStatsSet1 = event.getHomeplayerstatsset1();
        this.awayPlayerStatsSet1 = event.getAwayplayerstatsset1();
        this.homePlayerStatsSet2 = event.getHomeplayerstatsset2();
        this.awayPlayerStatsSet2 = event.getAwayplayerstatsset2();
        this.homePlayerStatsSet3 = event.getHomeplayerstatsset3();
        this.awayPlayerStatsSet3 = event.getAwayplayerstatsset3();
        this.homePlayerStatsSet4 = event.getHomeplayerstatsset4();
        this.awayPlayerStatsSet4 = event.getAwayplayerstatsset4();
        this.homePlayerStatsSet5 = event.getHomeplayerstatsset5();
        this.awayPlayerStatsSet5 = event.getAwayplayerstatsset5();


        this.homePitchersStatsTotal = event.getHomepitchersstatstotal();
        this.awayPitchersStatsTotal = event.getAwaypitchersstatstotal();
        this.homeBattersStatsTotal = event.getHomebattersstatstotal();
        this.awayBattersStatsTotal = event.getAwaybattersstatstotal();
    }

    /**
     * Returns total home team player statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getHomePlayerStatsTotal() {
        return homePlayerStatsTotal;
    }
    /**
     * Returns total away team player statistics that is coming with TEAM_MATCH_STATS(1743)
     * @return statistics as String
     */
    public String getAwayPlayerStatsTotal() {
        return awayPlayerStatsTotal;
    }
    /**
     * Returns home team player statistics for 1st pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsP1() {
        return homePlayerStatsP1;
    }
    /**
     * Returns away team player statistics for 1st pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsP1() {
        return awayPlayerStatsP1;
    }
    /**
     * Returns home team player statistics for 2nd pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsP2() {
        return homePlayerStatsP2;
    }
    /**
     * Returns away team player statistics for 2nd pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsP2() {
        return awayPlayerStatsP2;
    }
    /**
     * Returns home team player statistics for 3rd pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsP3() {
        return homePlayerStatsP3;
    }
    /**
     * Returns away team player statistics for 3rd pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsP3() {
        return awayPlayerStatsP3;
    }
    /**
     * Returns home team player statistics for 4th pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsP4() {
        return homePlayerStatsP4;
    }
    /**
     * Returns away team player statistics for 4th pitching that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsP4() {
        return awayPlayerStatsP4;
    }
    /**
     * Returns home team player statistics for over time that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsOt() {
        return homePlayerStatsOt;
    }
    /**
     * Returns away team player statistics for over time that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsOt() {
        return awayPlayerStatsOt;
    }
    /**
     * Returns home team pitchers statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePitchersStatsTotal() {
        return homePitchersStatsTotal;
    }
    /**
     * Returns away team pitchers statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPitchersStatsTotal() {
        return awayPitchersStatsTotal;
    }
    /**
     * Returns home team batters statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomeBattersStatsTotal() {
        return homeBattersStatsTotal;
    }
    /**
     * Returns away team batters statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayBattersStatsTotal() {
        return awayBattersStatsTotal;
    }
    /**
     * Returns home team set 1 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsSet1() {
        return homePlayerStatsSet1;
    }
    /**
     * Returns away team set 1 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsSet1() {
        return awayPlayerStatsSet1;
    }
    /**
     * Returns home team set 2 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsSet2() {
        return homePlayerStatsSet2;
    }
    /**
     * Returns away team set 2 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsSet2() {
        return awayPlayerStatsSet2;
    }
    /**
     * Returns home team set 3 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsSet3() {
        return homePlayerStatsSet3;
    }
    /**
     * Returns away team set 3 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsSet3() {
        return awayPlayerStatsSet3;
    }
    /**
     * Returns home team set 4 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsSet4() {
        return homePlayerStatsSet4;
    }
    /**
     * Returns away team set 4 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsSet4() {
        return awayPlayerStatsSet4;
    }
    /**
     * Returns home team set 5 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getHomePlayerStatsSet5() {
        return homePlayerStatsSet5;
    }
    /**
     * Returns away team set 5 statistics that is coming with PLAYER_MATCH_STATS(1714)
     * @return statistics as String
     */
    public String getAwayPlayerStatsSet5() {
        return awayPlayerStatsSet5;
    }
}
