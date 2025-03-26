package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Provides basic match information
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class MatchHeaderEntity implements Serializable {

    private static final long serialVersionUID = 907154056838604015L;
    private Boolean active;
    private MatchBetStatus betStatus;
    private Boolean booked;
    private Boolean connectionStatus;
    private Coverage coveredFrom;
    private Boolean deepCoverage;
    private boolean isRts;
    private Integer device;
    private Integer distance;
    private TeamsReversed teamsReversed;
    private Integer extraInfo;
    private ScoutFeedType feedType;
    private Team firstServe;
    private Team firstServeGoldenSet;
    private Team firstServeTieBreak;
    private long matchId;
    private String matchTime;
    private Integer numberOfSets;
    private Integer setLimit;
    private Sex sex;
    private String sourceId;
    private Integer st1Id;
    private Integer st2Id;
    private DateTime start;
    private IdNameTuple team1;
    private String team1Abbreviation;
    private String team1Division;
    private IdNameTuple team2;
    private String team2Abbreviation;
    private String team2Division;
    private Boolean tieBreakLastSet;
    private Boolean timeRunning;
    private Team wonJumpBall;
    private Integer coverageStatusId;
    private IdNameTuple team1Neutral;
    private IdNameTuple team2Neutral;
    private Integer sportId;
    private String extMatchId;
    private Integer var;
    private Boolean teamMatch;
    private Long teamMatchId;
    private Boolean cancelled;
    private Long sTime;
    private String vbpClassification;
    private String homeState;
    private String awayState;
    private String venue;
    private String region;
    private PaginationEntity pagination;
    private LatencyLevel expectedLatencyLevel;

    /**
     * For Serializable
     */
    protected MatchHeaderEntity() {}

    /**
     * Whether market is open or closed.
     *
     * @return bet status
     */
    public MatchBetStatus getBetStatus() {
        return betStatus;
    }

    /**
     * Whether scout is currently connected or not.
     *
     * @return connection status
     */
    public Boolean getConnectionStatus() {
        return connectionStatus;
    }

    /**
     * Whether the match is covered from TV or venue.
     *
     * @return coverage
     */
    public Coverage getCoveredFrom() {
        return coveredFrom;
    }

    /**
     * Device
     *
     * @return device
     */
    public Integer getDevice() {
        return device;
    }

    /**
     * Distance between teams in km.
     *
     * @return distance between teams in km
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Shows whether order not confirmed or reversed
     *
     * @return teams reversed
     */
    public TeamsReversed getTeamsReversed() {
        return teamsReversed;
    }

    /**
     * Integer specifying special information for the match.
     * Can be one of the following:
     * <ul>
     * <li>1 – Soccer match played 2*40 minutes (+2*10 minutes extra time)</li>
     * <li>2 – Tennis no advantage rule, super tie break to 10 points</li>
     * <li>3 – Tennis no advantage rule, super tie break to 7 points</li>
     * <li>4 – Tennis no advantage rule, no super tie break</li>
     * <li>5 – Tennis advantage rule, super tie break to 10 points</li>
     * <li>6 – Tennis advantage rule, super tie break to 7 points</li>
     * <li>7 – Basketball 12 minute periods</li>
     * <li>8 – Volleyball best of 3 sets</li>
     * <li>9 - Soccer match played 2*35 minutes (+2* 5 minutes extra time)</li>
     * <li>10 - Soccer match played 2*30 minutes (+2* 5 minutes extra time)</li>
     * <li>11 - Soccer match played 2*25 minutes (+2* 5 minutes extra time)</li>
     * <li>12 - Ice hockey 10 minutes extra time periods</li>
     * <li>13 - Ice hockey 20 minutes extra time periods</li>
     * <li>14 – Futsal match played 2*25 minutes (+2* 5 minutes extra time)</li>
     * </ul>
     * Tennis advantage rule is when games must be won by at least 2 points
     * (i.e. the use of deuce and advantage player X).
     * In doubles matches advantage rule is usually not used.
     * Super tie break in tennis is when the last set is a special tie break set.
     *
     * @return extra info
     */
    public Integer getExtraInfo() {
        return extraInfo;
    }

    /**
     * Whether this is a full, delta or deltaupdate message.
     *
     * @return feed type
     */
    public ScoutFeedType getFeedType() {
        return feedType;
    }

    /**
     * Which player / team has first serve of match (tennis).
     *
     * @return player / team that has first serve of match
     */
    public Team getFirstServe() {
        return firstServe;
    }

    /**
     * Which player / team has first serve of golden set
     *
     * @return player / team that has first serve of golden set
     */
    public Team getFirstServeGoldenSet() {

        return firstServeGoldenSet;
    }

    /**
     * Which player / team has first serve in tie-break (tennis).
     *
     * @return player / team that has first serve in tie-break
     */
    public Team getFirstServeTieBreak() {
        return firstServeTieBreak;
    }

    /**
     * Match Id.
     *
     * @return match id
     */
    public long getMatchId() {
        return matchId;
    }

    /**
     * Match time, mm:ss (e.g. 40:00 or 00:00).
     *
     * @return match time
     */
    public String getMatchTime() {
        return matchTime;
    }

    /**
     * Number of sets in match (tennis).
     *
     * @return number of sets
     */
    public Integer getNumberOfSets() {
        return numberOfSets;
    }

    /**
     * Gets number of points needed to win a set
     *
     * @return number of points needed to win a set
     */
    public Integer getSetLimit() {
        return setLimit;
    }

    /**
     * Gender (sex) of the player / team.
     *
     * @return player/team gender
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Customer matchid. Included if set up and if available for this match. Contact
     * Sportradar in order to include this in the scout feed
     *
     * @return customer match id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Returns the id of the coverage status for the associated match
     *
     * @return - if available, the id of the coverage status for the associated match;
     *           otherwise null
     */
    public Integer getCoverageStatusId() {
        return coverageStatusId;
    }

    /**
     * Betradar super team id for team 1.
     * <p>
     * Note: Super team is the parent a certain team derives from.
     * New sponsor might completely change the name of the team
     * no matter it is actually the same thing.
     * </p>
     *
     * @return team 1 super id
     */
    public Integer getSt1Id() {
        return st1Id;
    }

    /**
     * Betradar super team id for team 2.
     * <p>
     * Note: Super team is the parent a certain team derives from.
     * New sponsor might completely change the name of the team
     * no matter it is actually the same thing.
     * </p>
     *
     * @return team 2 super id
     */
    public Integer getSt2Id() {
        return st2Id;
    }

    /**
     * Official match start time, as a timestamp in UTC.
     *
     * @return match start time
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * Betradar team 1 (home team).
     *
     * @return home team
     */
    public IdNameTuple getTeam1() {
        return team1;
    }

    /**
     * Gets team 1 abbreviation
     *
     * @return team 1 abbreviation
     */
    public String getTeam1Abbreviation() {
        return team1Abbreviation;
    }

    /**
     * Gets team 1 division
     *
     * @return team 1 division
     */
    public String getTeam1Division() {
        return team1Division;
    }

    /**
     * Returns the team 1 (home team) neutral name
     *
     * @return the team 1 (home team) neutral name
     */
    public IdNameTuple getTeam1Neutral() {
        return team1Neutral;
    }

    /**
     * Betradar team 2 (away team).
     *
     * @return away team
     */
    public IdNameTuple getTeam2() {
        return team2;
    }

    /**
     * Gets team 2 abbreviation
     *
     * @return team 2 abbreviation
     */
    public String getTeam2Abbreviation() {
        return team2Abbreviation;
    }

    /**
     * Gets team 2 division
     *
     * @return team 2 division
     */
    public String getTeam2Division() {
        return team2Division;
    }

    /**
     * Returns the team 2 (away team) neutral name
     *
     * @return the team 2 (away team) neutral name
     */
    public IdNameTuple getTeam2Neutral() {
        return team2Neutral;
    }

    /**
     * Which team won jump ball at the start of the match.
     *
     * @return team which won the jump ball
     */
    public Team getWonJumpBall() { return wonJumpBall;  }

    /**
     * Returns the sport id
     *
     * @return the sport id
     */
    public Integer getSportId() {
        return sportId;
    }

    /**
     * Is match active?
     *
     * @return true if this match is active
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Whether the match is booked by the client.
     *
     * @return true if this match is booked
     */
    public Boolean isBooked() {
        return booked;
    }

    /**
     * Whether this is a deep coverage match.
     *
     * @return true if this match has deep coverage
     */
    public Boolean isDeepCoverage() {
        return deepCoverage;
    }

    /**
     * Whether this is a rts match.
     *
     * @return true if this is a rts match
     */
    public boolean isRts() {
        return isRts;
    }

    /**
     * Last set has tie break.
     *
     * @return true if last set has tie break
     */
    public Boolean isTieBreakLastSet() {
        return tieBreakLastSet;
    }

    /**
     * Whether time/clock is running/active or not.
     *
     * @return true if time is running
     */
    public Boolean isTimeRunning() {
        return timeRunning;
    }

    public String getExtMatchId() { return extMatchId; }

    public Integer getVar() { return var; }

    public Boolean isTeamMatch() { return teamMatch; }

    public Long getTeamMatchId() { return teamMatchId; }

    public Boolean isCancelled() { return cancelled; }

    public Long getSTime() { return sTime; }

    public String getVbpClassification() {
        return vbpClassification;
    }

    public String getHomeState() { return homeState; }

    public String getAwayState() { return awayState; }

    public String getVenue() { return venue; }

    public String getRegion() { return region; }

    /**
     * Too big feed can be split if such configuration is activated in LiveData.
     *
     * @return {@link PaginationEntity}
     */
    public PaginationEntity getPagination() {
        return pagination;
    }

    /**
     * Return latency enum value.
     * If not available, return {@link LatencyLevel#INVALID} with value and name taken from feed.
     * {@link LatencyLevel#INVALID} holding only latest unexpected value and name.
     *
     * @return {@link LatencyLevel}
     */
    public LatencyLevel getExpectedLatencyLevel() {
        return expectedLatencyLevel;
    }
}
