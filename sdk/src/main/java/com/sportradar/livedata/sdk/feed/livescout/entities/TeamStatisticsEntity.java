package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import lombok.*;

import java.io.Serializable;

/**
 * Stores team statics fields that are coming with TEAM_MATCH_STATS(1743) event.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class TeamStatisticsEntity implements Serializable {

  private static final long serialVersionUID = 2985143044035983853L;

  public static final int TEAM_MATCH_STATS = 1743;

  static TeamStatisticsEntity tryCreate(Event event) {
    if (TEAM_MATCH_STATS == event.getType()) {//type is int, so null is not an option
      return new TeamStatisticsEntity(event);
    }
    return null;
  }

  //common
  private String homeTeamStats;
  private String awayTeamStats;

  /**
   * -- GETTER -- Returns home team total statistics that is coming with TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsTotal;

  /**
   * -- GETTER -- Returns away team total statistics that is coming with TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsTotal;

  /**
   * -- GETTER -- Returns home team 1st pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsP1;

  /**
   * -- GETTER -- Returns away team 1st pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsP1;

  /**
   * -- GETTER -- Returns home team 1st period statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsH1;

  /**
   * -- GETTER -- Returns away team 1st period statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsH1;

  /**
   * -- GETTER -- Returns home team 2nd pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsP2;

  /**
   * -- GETTER -- Returns away team 2nd pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsP2;

  /**
   * -- GETTER -- Returns home team 2nd period statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsH2;

  /**
   * -- GETTER -- Returns away team 2nd period statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsH2;

  /**
   * -- GETTER -- Returns home team 3rd pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsP3;

  /**
   * -- GETTER -- Returns away team 3rd pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsP3;

  /**
   * -- GETTER -- Returns home team 4th pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsP4;

  /**
   * -- GETTER -- Returns away team 4th pitching statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsP4;

  /**
   * -- GETTER -- Returns home team over time statistics that is coming with TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsOt;

  /**
   * -- GETTER -- Returns away team over time statistics that is coming with TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsOt;

  /**
   * -- GETTER -- Returns home team 1st inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI1;

  /**
   * -- GETTER -- Returns away team 1st inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI1;

  /**
   * -- GETTER -- Returns home team 2nd inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI2;

  /**
   * -- GETTER -- Returns away team 2nd inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI2;

  /**
   * -- GETTER -- Returns home team 3rd inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI3;
  /**
   * -- GETTER -- Returns away team 3rd inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI3;

  /**
   * -- GETTER -- Returns home team 4th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI4;
  /**
   * -- GETTER -- Returns away team 4th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI4;
  /**
   * -- GETTER -- Returns home team 5th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI5;

  /**
   * -- GETTER -- Returns away team 5th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI5;

  /**
   * -- GETTER -- Returns home team 6th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI6;
  /**
   * -- GETTER -- Returns away team 6th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI6;

  /**
   * -- GETTER -- Returns home team 7th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI7;
  /**
   * -- GETTER -- Returns away team 7th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI7;

  /**
   * -- GETTER -- Returns home team 8th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI8;
  /**
   * -- GETTER -- Returns away team 8th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI8;

  /**
   * -- GETTER -- Returns home team 9th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsI9;

  /**
   * -- GETTER -- Returns away team 9th inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsI9;

  /**
   * -- GETTER -- Returns home team extra inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String homeTeamStatsIe;

  /**
   * -- GETTER -- Returns away team extra inning statistics that is coming with
   * TEAM_MATCH_STATS(1743)
   *
   */
  private String awayTeamStatsIe;

  protected TeamStatisticsEntity(Event event) {
    this.homeTeamStats = event.getHometeamstats();
    this.awayTeamStats = event.getAwayteamstats();

    this.homeTeamStatsTotal = event.getHometeamstatstotal();
    this.awayTeamStatsTotal = event.getAwayteamstatstotal();
    this.homeTeamStatsP1 = event.getHometeamstatsp1();
    this.awayTeamStatsP1 = event.getAwayteamstatsp1();
    this.homeTeamStatsH1 = event.getHomeplayerstatsh1();
    this.awayTeamStatsH1 = event.getAwayplayerstatsh1();
    this.homeTeamStatsP2 = event.getHometeamstatsp2();
    this.awayTeamStatsP2 = event.getAwayteamstatsp2();
    this.homeTeamStatsH2 = event.getHomeplayerstatsh2();
    this.awayTeamStatsH2 = event.getAwayplayerstatsh2();
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

}
