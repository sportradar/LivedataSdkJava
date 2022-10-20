package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.livescout.enums.Coverage;
import com.sportradar.sdk.feed.livescout.enums.MatchBetStatus;
import com.sportradar.sdk.feed.livescout.enums.ScoutFeedType;
import com.sportradar.sdk.feed.livescout.enums.Sex;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Provides basic match information
 */
public class MatchHeaderEntity implements Serializable {

    private static final long serialVersionUID = 907154056838604015L;
    private Boolean active;
    private MatchBetStatus betStatus;
    private Boolean booked;
    private Boolean connectionStatus;
    private Coverage coveredFrom;
    private Boolean deepCoverage;
    private Integer device;
    private Integer distance;
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
    private Long stime;
    private String homeState;
    private String awayState;
    private String venue;

    /**
     * For Serializable
     */
    protected MatchHeaderEntity() {
    }

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

    public Boolean setIsTeamMatch() { return teamMatch; }

    public Long getTeamMatchId() { return teamMatchId; }

    public Boolean setIsCancelled() { return cancelled; }

    public Long getSTime() { return stime; }

    public String getHomeState() { return homeState; }

    public String getAwayState() { return awayState; }

    public String getVenue() { return venue; }

    /**
     * Returns a string that represents the current object.
     * <p>
     * Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "MatchHeaderEntity{" +
                "active=" + active +
                ", betStatus=" + betStatus +
                ", booked=" + booked +
                ", connectionStatus=" + connectionStatus +
                ", coveredFrom=" + coveredFrom +
                ", deepCoverage=" + deepCoverage +
                ", device=" + device +
                ", distance=" + distance +
                ", extraInfo=" + extraInfo +
                ", feedType=" + feedType +
                ", firstServe=" + firstServe +
                ", firstServeGoldenSet=" + firstServeGoldenSet +
                ", firstServeTieBreak=" + firstServeTieBreak +
                ", matchId=" + matchId +
                ", matchTime='" + matchTime + '\'' +
                ", numberOfSets=" + numberOfSets +
                ", setLimit=" + setLimit +
                ", sex=" + sex +
                ", sourceId='" + sourceId + '\'' +
                ", st1Id=" + st1Id +
                ", st2Id=" + st2Id +
                ", start=" + start +
                ", team1=" + team1 +
                ", team1Abbreviation='" + team1Abbreviation +
                ", team1Division='" + team1Division + '\'' +
                ", team2=" + team2 +
                ", team2Abbreviation='" + team2Abbreviation +
                ", team2Division='" + team2Division +'\'' +
                ", tieBreakLastSet=" + tieBreakLastSet +
                ", timeRunning=" + timeRunning +
                ", wonJumpBall=" + wonJumpBall +
                ", coverageStatusId=" + coverageStatusId +
                ", sportId=" + sportId +
                ", extMatchId='" + extMatchId + '\'' +
                ", var=" + var +
                ", teamMatch=" + teamMatch +
                ", teamMatchId=" + teamMatchId +
                ", cancelled=" + cancelled +
                ", stime=" + stime +
                ", homeState=" + homeState +
                ", awayState=" + awayState +
                ", venue=" + venue +
                '}';
    }

    protected void setActive(Boolean active) {
        this.active = active;
    }

    protected void setBetStatus(MatchBetStatus betStatus) {
        this.betStatus = betStatus;
    }

    protected void setBooked(Boolean booked) {
        this.booked = booked;
    }

    protected void setConnectionStatus(Boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    protected void setCoveredFrom(Coverage coveredFrom) {
        this.coveredFrom = coveredFrom;
    }

    protected void setDeepCoverage(Boolean deepCoverage) {
        this.deepCoverage = deepCoverage;
    }

    protected void setDevice(Integer device) { this.device = device; }

    protected void setDistance(Integer distance) {
        this.distance = distance;
    }

    protected void setExtraInfo(Integer extraInfo) {
        this.extraInfo = extraInfo;
    }

    protected void setFeedType(ScoutFeedType feedType) {
        this.feedType = feedType;
    }

    protected void setFirstServe(Team firstServe) {
        this.firstServe = firstServe;
    }

    protected void setFirstServeGoldenSet(Team firstServeGoldenSet) {
        this.firstServeGoldenSet = firstServeGoldenSet;
    }

    protected void setFirstServeTieBreak(Team firstServeTieBreak) {
        this.firstServeTieBreak = firstServeTieBreak;
    }

    protected void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    protected void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    protected void setNumberOfSets(Integer numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    protected void setSetLimit(Integer setLimit) {
        this.setLimit = setLimit;
    }

    protected void setSex(Sex sex) {
        this.sex = sex;
    }

    protected void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    protected void setSt1Id(Integer st1Id) {
        this.st1Id = st1Id;
    }

    protected void setSt2Id(Integer st2Id) {
        this.st2Id = st2Id;
    }

    protected void setStart(DateTime start) {
        this.start = start;
    }

    protected void setTeam1(IdNameTuple team1) {
        this.team1 = team1;
    }

    protected void setTeam1Abbreviation(String team1Abbreviation) {
        this.team1Abbreviation = team1Abbreviation;
    }

    protected void setTeam1Division(String team1Division) {
        this.team1Division = team1Division;
    }

    protected void setTeam2(IdNameTuple team2) {
        this.team2 = team2;
    }

    protected void setTeam2Abbreviation(String team2Abbreviation) {
        this.team2Abbreviation = team2Abbreviation;
    }

    protected void setTeam2Division(String team2Division) {
        this.team2Division = team2Division;
    }

    protected void setTieBreakLastSet(Boolean tieBreakLastSet) {
        this.tieBreakLastSet = tieBreakLastSet;
    }

    protected void setTimeRunning(Boolean timeRunning) {
        this.timeRunning = timeRunning;
    }

    protected void setWonJumpBall(Team wonJumpBall) {
        this.wonJumpBall = wonJumpBall;
    }

    public void setCoverageStatusId(Integer coverageStatusId) {
        this.coverageStatusId = coverageStatusId;
    }

    public void setTeam1Neutral(IdNameTuple team1Neutral) {
        this.team1Neutral = team1Neutral;
    }

    public void setTeam2Neutral(IdNameTuple team2Neutral) {
        this.team2Neutral = team2Neutral;
    }

    public void setSportId(Integer sportId) { this.sportId = sportId; }

    public void setExtMatchId(String extMatchId) { this.extMatchId = extMatchId; }

    public void setVar(Integer var) { this.var = var; }

    public void setIsTeamMatch(Boolean teamMatch) { this.teamMatch = teamMatch; }

    public void setTeamMatchId(Long teamMatchId) { this.teamMatchId = teamMatchId; }

    public void setIsCancelled(Boolean cancelled) { this.cancelled = cancelled; }

    public void setSTime(Long stime) { this.stime = stime; }

    public void setHomeState(String homeState) { this.homeState = homeState; }

    public void setAwayState(String awayState) { this.awayState = awayState; }

    public void setVenue(String venue) { this.venue = venue; }
}
