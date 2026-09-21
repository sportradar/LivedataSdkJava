package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import lombok.*;

import java.io.Serializable;

/**
 * Stores player statics fields that are coming with PLAYER_MATCH_STATS(1714) event.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class PlayerStatisticsEntity implements Serializable {

  private static final long serialVersionUID = 2985143044035983853L;

  public static final int PLAYER_MATCH_STATS = 1714;

  static PlayerStatisticsEntity tryCreate(Event event) {
    if (PLAYER_MATCH_STATS == event.getType()) {
      return new PlayerStatisticsEntity(event);
    }
    return null;
  }

  /**
   * -- GETTER -- Returns total home team player statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsTotal;

  /**
   * -- GETTER -- Returns total away team player statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsTotal;

  /**
   * -- GETTER -- Returns home team player statistics for 1st pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsP1;

  /**
   * -- GETTER -- Returns away team player statistics for 1st pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsP1;

  /**
   * -- GETTER -- Returns home team player 1st period statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsH1;

  /**
   * -- GETTER -- Returns away team player 1st period statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsH1;

  /**
   * -- GETTER -- Returns home team player statistics for 2nd pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsP2;

  /**
   * -- GETTER -- Returns away team player statistics for 2nd pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsP2;

  /**
   * -- GETTER -- Returns home team player 2nd period statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsH2;

  /**
   * -- GETTER -- Returns away team player 2nd period statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsH2;

  /**
   * -- GETTER -- Returns home team player statistics for 3rd pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsP3;

  /**
   * -- GETTER -- Returns away team player statistics for 3rd pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsP3;

  /**
   * -- GETTER -- Returns home team player statistics for 4th pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsP4;

  /**
   * -- GETTER -- Returns away team player statistics for 4th pitching that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsP4;

  /**
   * -- GETTER -- Returns home team player statistics for over time that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsOt;

  /**
   * -- GETTER -- Returns away team player statistics for over time that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsOt;

  /**
   * -- GETTER -- Returns home team pitchers statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePitchersStatsTotal;

  /**
   * -- GETTER -- Returns away team pitchers statistics that is coming with
   * PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPitchersStatsTotal;

  /**
   * -- GETTER -- Returns home team batters statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String homeBattersStatsTotal;

  /**
   * -- GETTER -- Returns away team batters statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayBattersStatsTotal;

  /**
   * -- GETTER -- Returns home team set 1 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsSet1;

  /**
   * -- GETTER -- Returns away team set 1 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsSet1;

  /**
   * -- GETTER -- Returns home team set 2 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsSet2;

  /**
   * -- GETTER -- Returns away team set 2 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsSet2;

  /**
   * -- GETTER -- Returns home team set 3 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsSet3;

  /**
   * -- GETTER -- Returns away team set 3 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsSet3;

  /**
   * -- GETTER -- Returns home team set 4 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsSet4;

  /**
   * -- GETTER -- Returns away team set 4 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsSet4;

  /**
   * -- GETTER -- Returns home team set 5 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String homePlayerStatsSet5;

  /**
   * -- GETTER -- Returns away team set 5 statistics that is coming with PLAYER_MATCH_STATS(1714)
   *
   */
  private String awayPlayerStatsSet5;

  private PlayerStatisticsEntity(Event event) {
    this.homePlayerStatsTotal = event.getHomeplayerstatstotal();
    this.awayPlayerStatsTotal = event.getAwayplayerstatstotal();

    this.homePlayerStatsP1 = event.getHomeplayerstatsp1();
    this.awayPlayerStatsP1 = event.getAwayplayerstatsp1();
    this.homePlayerStatsH1 = event.getHomeplayerstatsh1();
    this.awayPlayerStatsH1 = event.getAwayplayerstatsh1();
    this.homePlayerStatsP2 = event.getHomeplayerstatsp2();
    this.awayPlayerStatsP2 = event.getAwayplayerstatsp2();
    this.homePlayerStatsH2 = event.getHomeplayerstatsh2();
    this.awayPlayerStatsH2 = event.getAwayplayerstatsh2();
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

}
