package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.common.entities.HomeAway;
import com.sportradar.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.livescout.enums.*;
import org.joda.time.DateTime;
import com.sportradar.sdk.feed.livescout.enums.IceConditions;
import com.sportradar.sdk.feed.livescout.enums.PitchConditions;
import com.sportradar.sdk.feed.livescout.enums.ScoutMatchStatus;
import com.sportradar.sdk.feed.livescout.enums.WeatherConditions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Match information message. Contains statistical information about a match
 * as well as individual events (e.g. goals, cards, etc.).
 */
public class MatchUpdateEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -3625209060165281600L;
    private HomeAway<Integer> attacks;
    private HomeAway<Integer> blackCards;
    private IdNameTuple category;
    private HomeAway<Integer> corners;
    private CourtEntity court;
    private HomeAway<Integer> dangerousAttacks;
    private HomeAway<Integer> directFoulsPeriod;
    private HomeAway<Integer> directFreeKicks;
    private List<ScoutEventEntity> events;
    private HomeAway<Integer> freeKicks;
    private HomeAway<Integer> freeThrows;
    private HomeAway<Integer> goalKicks;
    private HomeAway<Integer> goalkeeperSaves;
    private IceConditions iceConditions;
    private HomeAway<Integer> injuries;
    private List<InningsEntity> innings;
    private Team kickoffTeam;
    private Team kickoffTeamFirstHalf;
    private Team kickoffTeamOt;
    private Team kickoffTeamSecondHalf;
    private List<FormatEntity> matchFormat;
    private MatchHeaderEntity matchHeader;
    private int matchStatusId;
    private ScoutMatchStatus matchStatus;
    private DateTime matchStatusStart;
    private HomeAway<Integer> offsides;
    private Team openingFaceoff1StPeriod;
    private Team openingFaceoff2NdPeriod;
    private Team openingFaceoff3RdPeriod;
    private Team openingFaceoffOvertime;
    private HomeAway<Integer> penalties;
    private PitchConditions pitchConditions;
    private HomeAway<Integer> possession;
    private Team possessionTeam;
    private HomeAway<Integer> redCards;
    private ScoutEntity scout;
    private Map<String, HomeAway<Double>> score;
    private List<ScoreEntity> scores;
    private Team serve;
    private HomeAway<Integer> shotsBlocked;
    private HomeAway<Integer> shotsOffTarget;
    private HomeAway<Integer> shotsOnTarget;
    private IdNameTuple sport;
    private SurfaceType surfaceType;
    private SuspensionsEntity suspensions;
    private HomeAway<Integer> throwins;
    private Boolean tieBreak;
    private IdNameTuple tournament;
    private WeatherConditions weatherConditions;
    private HomeAway<Integer> yellowCards;
    private GoldEntity gold;
    private NetWorthEntity netWorth;
    private List<JerseyEntity> jerseys;
    private GoalsEntity goals;
    private BehindsEntity behinds;
    private List<MatchPropertyEntity> matchProperties;
    private List<MatchTeamEntity> matchTeams;
    private HomeAway<Integer> greenCards;

    /**
     * Initializes a new instance of the {@link MatchUpdateEntity} class.
     */
    protected MatchUpdateEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
        this.innings = new ArrayList<>();
        this.matchTeams = new ArrayList<>();
    }

    /**
     * Gets attacks statistics.
     *
     * @return number of attacks
     */
    public HomeAway<Integer> getAttacks() {
        return attacks;
    }

    /**
     * Gets black cards (badminton).
     *
     * @return number of black cards
     */
    public HomeAway<Integer> getBlackCards() {
        return blackCards;
    }

    /**
     * Gets sport category.
     *
     * @return category
     */
    public IdNameTuple getCategory() {
        return category;
    }

    /**
     * Gets corner statistics.
     *
     * @return number of corners
     */
    public HomeAway<Integer> getCorners() {
        return corners;
    }

    /**
     * Gets scout entity
     *
     * @return scout entity
     */
    public ScoutEntity getScout() {
        return scout;
    }

    /**
     * Gets type of court.
     *
     * @return court type
     */
    public CourtEntity getCourt() {
        return court;
    }

    /**
     * Gets dangerous attack statistics.
     *
     * @return number of dangerous attacks
     */
    public HomeAway<Integer> getDangerousAttacks() {
        return dangerousAttacks;
    }

    /**
     * Gets direct fouls for current period statistics (futsal).
     *
     * @return number of direct fouls for current period
     */
    public HomeAway<Integer> getDirectFoulsPeriod() {
        return directFoulsPeriod;
    }

    /**
     * Gets direct free kicks statistics (futsal).
     *
     * @return number of  direct free kicks
     */
    public HomeAway<Integer> getDirectFreeKicks() {
        return directFreeKicks;
    }

    /**
     * Gets the unique event id.
     *
     * @return - an event id or null
     */
    @Override
    public EventIdentifier getEventId() {
        return matchHeader == null ? null : EventIdentifier.id(matchHeader.getMatchId());
    }

    /**
     * Gets list of scout events.
     *
     * @return list of scout events
     */
    public List<ScoutEventEntity> getEvents() {
        return events;
    }

    /**
     * Gets free kick statistics.
     *
     * @return number of free kicks
     */
    public HomeAway<Integer> getFreeKicks() {
        return freeKicks;
    }

    /**
     * Gets free throws statistics.
     *
     * @return number of free throws
     */
    public HomeAway<Integer> getFreeThrows() {
        return freeThrows;
    }

    /**
     * Gets goal kick statistics.
     *
     * @return number of kick on goal
     */
    public HomeAway<Integer> getGoalKicks() {
        return goalKicks;
    }

    /**
     * Gets goalkeeper saves statistics.
     *
     * @return number of goalkeeper saves
     */
    public HomeAway<Integer> getGoalkeeperSaves() {
        return goalkeeperSaves;
    }

    /**
     * Ice conditons
     *
     * @return ice conditons
     */
    public IceConditions getIceConditions() {
        return iceConditions;
    }

    /**
     * Gets injuries statistics.
     *
     * @return number of injuries
     */
    public HomeAway<Integer> getInjuries() {
        return injuries;
    }

    /**
     * Returns a {@link List} of innings for the current match
     *
     * @return - a {@link List} of innings for the current match
     */
    public List<InningsEntity> getInnings() {
        return innings;
    }

    /**
     * Gets kickoff team
     *
     * @return kickoff team
     */
    public Team getKickoffTeam() {
        return kickoffTeam;
    }

    /**
     * Gets first half kickoff team
     *
     * @return first half kickoff team
     */
    public Team getKickoffTeamFirstHalf() {
        return kickoffTeamFirstHalf;
    }

    /**
     * Gets overtime kickoff team
     *
     * @return overtime kickoff team
     */
    public Team getKickoffTeamOt() {
        return kickoffTeamOt;
    }

    /**
     * Gets second half kickoff team
     *
     * @return second half kickoff team
     */
    public Team getKickoffTeamSecondHalf() {
        return kickoffTeamSecondHalf;
    }

    /**
     * Gets match format
     *
     * @return match format
     */
    public List<FormatEntity> getMatchFormat() {
        return matchFormat;
    }

    /**
     * Basic match information.
     *
     * @return match header
     */
    public MatchHeaderEntity getMatchHeader() {
        return matchHeader;
    }

    /**
     * Current match status identifier
     *
     * @return the match status identifier
     */
    public int getMatchStatusId() {
        return matchStatusId;
    }

    /**
     * Current match status. Same status codes as in LiveScore.
     *
     * @return match status
     */
    public ScoutMatchStatus getMatchStatus() {
        return matchStatus;
    }

    /**
     * Timestamp for when match status was set in UTC.
     *
     * @return match status start timestamp
     */
    public DateTime getMatchStatusStart() {
        return matchStatusStart;
    }

    /**
     * Offside statistics.
     *
     * @return number of offsides
     */
    public HomeAway<Integer> getOffsides() {
        return offsides;
    }

    /**
     * Opening faceoff 1st period
     *
     * @return opening faceoff 1st period
     */
    public Team getOpeningFaceoff1StPeriod() {
        return openingFaceoff1StPeriod;
    }

    /**
     * Opening faceoff 2nd period
     *
     * @return opening faceoff 2nd period
     */
    public Team getOpeningFaceoff2NdPeriod() {
        return openingFaceoff2NdPeriod;
    }

    /**
     * Opening faceoff 3rd period
     *
     * @return opening faceoff 3rd period
     */
    public Team getOpeningFaceoff3RdPeriod() {
        return openingFaceoff3RdPeriod;
    }

    /**
     * Opening faceoff overtime
     *
     * @return opening faceoff overtime
     */
    public Team getOpeningFaceoffOvertime() {
        return openingFaceoffOvertime;
    }

    /**
     * Penalties statistics.
     *
     * @return number of penalties
     */
    public HomeAway<Integer> getPenalties() {
        return penalties;
    }

    /**
     * Pitch / grass conditions.
     *
     * @return pitch conditions
     */
    public PitchConditions getPitchConditions() {
        return pitchConditions;
    }

    /**
     * Ball possession statistics.
     *
     * @return ball possession
     */
    public HomeAway<Integer> getPossession() {
        return possession;
    }

    /**
     * Which team currently has possession of the ball.
     *
     * @return possession team
     */
    public Team getPossessionTeam() {
        return possessionTeam;
    }

    /**
     * Red card statistics.
     *
     * @return number of red cards
     */
    public HomeAway<Integer> getRedCards() {
        return redCards;
    }

    /**
     * Match score information.
     *
     * @return scores
     * @deprecated since 1.15.0, use List &lt;ScoreEntity &gt; getScores() instead. It may contain SubScores - i.e. for tennis tie breaks. {@link #getScores()}
     */
    public Map<String, HomeAway<Double>> getScore() {
        return score;
    }

    /**
     * Gets the match scores
     *
     * @return the match scores
     */
    public List<ScoreEntity> getScores() {
        return scores;
    }

    /**
     * Which player/team currently has the serve (tennis, volleyball).
     *
     * @return serving team
     */
    public Team getServe() {
        return serve;
    }

    /**
     * Shots blocked statistics.
     *
     * @return number of blocked shots
     */
    public HomeAway<Integer> getShotsBlocked() {
        return shotsBlocked;
    }

    /**
     * Shots off target statistics.
     *
     * @return number of shots off target
     */
    public HomeAway<Integer> getShotsOffTarget() {
        return shotsOffTarget;
    }

    /**
     * Shots on target statistics.
     *
     * @return number of shots on target
     */
    public HomeAway<Integer> getShotsOnTarget() {
        return shotsOnTarget;
    }

    /**
     * Type of sport.
     *
     * @return sport type
     */
    public IdNameTuple getSport() {
        return sport;
    }

    /**
     * Surface type (tennis).
     *
     * @return surface type
     */
    public SurfaceType getSurfaceType() {
        return surfaceType;
    }

    /**
     * Suspensions statistics.
     *
     * @return suspensions statistics
     */
    public SuspensionsEntity getSuspensions() {
        return suspensions;
    }

    /**
     * Throw-in statistics.
     *
     * @return throw-in statistics
     */
    public HomeAway<Integer> getThrowins() {
        return throwins;
    }

    /**
     * Sport tournament.
     *
     * @return sport tournament
     */
    public IdNameTuple getTournament() {
        return tournament;
    }

    /**
     * Weather conditions.
     *
     * @return weather conditions
     */
    public WeatherConditions getWeatherConditions() {
        return weatherConditions;
    }

    /**
     * Yellow cards statistics.
     *
     * @return number of yellow cards
     */
    public HomeAway<Integer> getYellowCards() {
        return yellowCards;
    }

    /**
     * Whether the match is currently in tie break status (tennis).
     *
     * @return flag is match is currently in tie break status
     */
    public Boolean isTieBreak() {
        return tieBreak;
    }

    public GoldEntity getGold() {
        return gold;
    }

    /**
     * Returns an {@link NetWorthEntity} describing the associated event net-worth
     *
     * @return - an {@link NetWorthEntity} describing the associated event net-worth
     */
    public NetWorthEntity getNetWorth() {
        return netWorth;
    }

    /**
     * Returns a list of known jerseys
     *
     * @return a {@link List} of known jerseys
     */
    public List<JerseyEntity> getJerseys() {
        return jerseys;
    }

    /**
     * Returns the goals entity
     * @return the goals entity
     */
    public GoalsEntity getGoals() {
        return goals;
    }

    /**
     * Returns the behinds entity
     *
     * @return the behinds entity
     */
    public BehindsEntity getBehinds() {
        return behinds;
    }

    /**
     * Returns the list of match properties
     * @return the list of match properties
     */
    public List<MatchPropertyEntity> getMatchProperties() { return matchProperties; }

    /**
     * Returns the list of match teams
     * @return the list of match teams
     */
    public List<MatchTeamEntity> getMatchTeams() { return matchTeams; }

    /**
     * Returns a string that represents the current object.
     * <p>
     * Note : Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "MatchUpdateEntity{" +
                "attacks=" + attacks +
                ", blackCards=" + blackCards +
                ", category=" + category +
                ", corners=" + corners +
                ", court=" + court +
                ", dangerousAttacks=" + dangerousAttacks +
                ", directFoulsPeriod=" + directFoulsPeriod +
                ", directFreeKicks=" + directFreeKicks +
                ", events=" + events +
                ", freeKicks=" + freeKicks +
                ", freeThrows=" + freeThrows +
                ", goalKicks=" + goalKicks +
                ", goalkeeperSaves=" + goalkeeperSaves +
                ", iceConditions=" + iceConditions +
                ", injuries=" + injuries +
                ", innings=" + innings +
                ", kickoffTeam=" + kickoffTeam +
                ", kickoffTeamFirstHalf=" + kickoffTeamFirstHalf +
                ", kickoffTeamOt=" + kickoffTeamOt +
                ", kickoffTeamSecondHalf=" + kickoffTeamSecondHalf +
                ", matchFormat=" + matchFormat +
                ", matchHeader=" + matchHeader +
                ", matchStatusId=" + matchStatusId +
                ", matchStatus=" + matchStatus +
                ", matchStatusStart=" + matchStatusStart +
                ", offsides=" + offsides +
                ", openingFaceoff1StPeriod=" + openingFaceoff1StPeriod +
                ", openingFaceoff2NdPeriod=" + openingFaceoff2NdPeriod +
                ", openingFaceoff3RdPeriod=" + openingFaceoff3RdPeriod +
                ", openingFaceoffOvertime=" + openingFaceoffOvertime +
                ", penalties=" + penalties +
                ", pitchConditions=" + pitchConditions +
                ", possession=" + possession +
                ", possessionTeam=" + possessionTeam +
                ", redCards=" + redCards +
                ", scout=" + scout +
                ", score=" + score +
                ", serve=" + serve +
                ", shotsBlocked=" + shotsBlocked +
                ", shotsOffTarget=" + shotsOffTarget +
                ", shotsOnTarget=" + shotsOnTarget +
                ", sport=" + sport +
                ", surfaceType=" + surfaceType +
                ", suspensions=" + suspensions +
                ", throwins=" + throwins +
                ", tieBreak=" + tieBreak +
                ", tournament=" + tournament +
                ", weatherConditions=" + weatherConditions +
                ", yellowCards=" + yellowCards +
                ", gold=" + gold +
                ", netWorth=" + netWorth +
                ", goals=" + goals +
                ", behinds=" + behinds +
                ", teams=" + matchTeams +
                ", properties=" + matchProperties +
                "} " + super.toString();
    }

    protected void setScout(ScoutEntity scout) {
        this.scout = scout;
    }

    protected void setAttacks(HomeAway<Integer> attacks) {
        this.attacks = attacks;
    }

    protected void setBlackCards(HomeAway<Integer> blackCards) {
        this.blackCards = blackCards;
    }

    protected void setCategory(IdNameTuple category) {
        this.category = category;
    }

    protected void setCorners(HomeAway<Integer> corners) {
        this.corners = corners;
    }

    protected void setCourt(CourtEntity court) {
        this.court = court;
    }

    protected void setDangerousAttacks(HomeAway<Integer> dangerousAttacks) {
        this.dangerousAttacks = dangerousAttacks;
    }

    protected void setDirectFoulsPeriod(HomeAway<Integer> directFoulsPeriod) { this.directFoulsPeriod = directFoulsPeriod; }

    protected void setDirectFreeKicks(HomeAway<Integer> directFreeKicks) {
        this.directFreeKicks = directFreeKicks;
    }

    protected void setEvents(List<ScoutEventEntity> events) {
        this.events = Collections.unmodifiableList(events);
    }

    protected void setFreeKicks(HomeAway<Integer> freeKicks) {
        this.freeKicks = freeKicks;
    }

    protected void setFreeThrows(HomeAway<Integer> freeThrows) {
        this.freeThrows = freeThrows;
    }

    protected void setGoalKicks(HomeAway<Integer> goalKicks) {
        this.goalKicks = goalKicks;
    }

    protected void setGoalkeeperSaves(HomeAway<Integer> goalkeeperSaves) {
        this.goalkeeperSaves = goalkeeperSaves;
    }

    protected void setIceConditions(IceConditions iceConditions) {
        this.iceConditions = iceConditions;
    }

    protected void setInjuries(HomeAway<Integer> injuries) {
        this.injuries = injuries;
    }

    protected void setInnings(InningsEntity innings) {
        this.innings.add(innings);
    }

    protected void setKickoffTeam(Team kickoffTeam) {
        this.kickoffTeam = kickoffTeam;
    }

    protected void setKickoffTeamFirstHalf(Team kickoffTeamFirstHalf) {
        this.kickoffTeamFirstHalf = kickoffTeamFirstHalf;
    }

    protected void setKickoffTeamOt(Team kickoffTeamOt) {
        this.kickoffTeamOt = kickoffTeamOt;
    }

    protected void setKickoffTeamSecondHalf(Team kickoffTeamSecondHalf) {
        this.kickoffTeamSecondHalf = kickoffTeamSecondHalf;
    }

    protected void setMatchFormat(List<FormatEntity> matchFormat) {
        this.matchFormat = matchFormat;
    }

    protected void setMatchHeader(MatchHeaderEntity matchHeader) {
        this.matchHeader = matchHeader;
    }

    protected void setMatchStatusId(int id) {
        this.matchStatusId = id;
    }

    protected void setMatchStatus(ScoutMatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    protected void setMatchStatusStart(DateTime matchStatusStart) {
        this.matchStatusStart = matchStatusStart;
    }

    protected void setOffsides(HomeAway<Integer> offsides) {
        this.offsides = offsides;
    }

    protected void setOpeningFaceoff1StPeriod(Team openingFaceoff1StPeriod) {
        this.openingFaceoff1StPeriod = openingFaceoff1StPeriod;
    }

    protected void setOpeningFaceoff2NdPeriod(Team openingFaceoff2NdPeriod) {
        this.openingFaceoff2NdPeriod = openingFaceoff2NdPeriod;
    }

    protected void setOpeningFaceoff3RdPeriod(Team openingFaceoff3RdPeriod) {
        this.openingFaceoff3RdPeriod = openingFaceoff3RdPeriod;
    }

    protected void setOpeningFaceoffOvertime(Team openingFaceoffOvertime) {
        this.openingFaceoffOvertime = openingFaceoffOvertime;
    }

    protected void setPenalties(HomeAway<Integer> penalties) {
        this.penalties = penalties;
    }

    protected void setPitchConditions(PitchConditions pitchConditions) {
        this.pitchConditions = pitchConditions;
    }

    protected void setPossession(HomeAway<Integer> possession) {
        this.possession = possession;
    }

    protected void setPossessionTeam(Team possessionTeam) {
        this.possessionTeam = possessionTeam;
    }

    protected void setRedCards(HomeAway<Integer> redCards) { this.redCards = redCards; }

    protected void setScore(Map<String, HomeAway<Double>> score) { this.score = Collections.unmodifiableMap(score); }

    protected void setScores(List<ScoreEntity> scores) { this.scores = Collections.unmodifiableList(scores); }

    protected void setServe(Team serve) {
        this.serve = serve;
    }

    protected void setShotsBlocked(HomeAway<Integer> shotsBlocked) {
        this.shotsBlocked = shotsBlocked;
    }

    protected void setShotsOffTarget(HomeAway<Integer> shotsOffTarget) {
        this.shotsOffTarget = shotsOffTarget;
    }

    protected void setShotsOnTarget(HomeAway<Integer> shotsOnTarget) {
        this.shotsOnTarget = shotsOnTarget;
    }

    protected void setSport(IdNameTuple sport) {
        this.sport = sport;
    }

    protected void setSurfaceType(SurfaceType surfaceType) {
        this.surfaceType = surfaceType;
    }

    protected void setSuspensions(SuspensionsEntity suspensions) {
        this.suspensions = suspensions;
    }

    protected void setThrowins(HomeAway<Integer> throwins) {
        this.throwins = throwins;
    }

    protected void setTieBreak(Boolean tieBreak) {
        this.tieBreak = tieBreak;
    }

    protected void setTournament(IdNameTuple tournament) {
        this.tournament = tournament;
    }

    protected void setWeatherConditions(WeatherConditions weatherConditions) { this.weatherConditions = weatherConditions; }

    protected void setYellowCards(HomeAway<Integer> yellowCards) { this.yellowCards = yellowCards; }

    protected void setGold(GoldEntity gold) {
        this.gold = gold;
    }

    protected void setNetWorth(NetWorthEntity netWorth) {
        this.netWorth = netWorth;
    }

    protected void setJerseys(List<JerseyEntity> jerseys) {
        this.jerseys = jerseys;
    }

    protected void setGoals(GoalsEntity goals) {
        this.goals = goals;
    }

    protected void setBehinds(BehindsEntity behinds) {
        this.behinds = behinds;
    }

    protected void setMatchProperties(List<MatchPropertyEntity> matchProperties) { this.matchProperties = matchProperties; }

    protected void setMatchTeams(List<MatchTeamEntity> matchTeams) {
        this.matchTeams = matchTeams;
    }

    protected void setGreenCards(HomeAway<Integer> greenCards) { this.greenCards = greenCards; }
}
