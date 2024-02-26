package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import jakarta.xml.bind.annotation.XmlAttribute;
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
    static PlayerStatisticsEntity create(Event event){
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
}
