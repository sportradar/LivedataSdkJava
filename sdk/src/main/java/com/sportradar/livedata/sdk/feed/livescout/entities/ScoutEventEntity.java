package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.EventType;
import com.sportradar.livedata.sdk.feed.livescout.enums.Inning;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Contains information about an event
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class ScoutEventEntity implements Serializable {

    private static final long serialVersionUID = 6151858186845823797L;
    private Integer automatic;
    private Integer assists;
    private String ballNumber;
    private Integer balls;
    private Boolean ballSpotting;
    private String baseInfo;

    private Integer pointsUntilSnookerNeeded;
    private String breakScore;
    private Integer correctedFrom;
    private Integer correctedTo;
    private Integer deaths;
    private Integer denies;
    private Integer dismissalsInInnings;
    private Integer earlyBetstartType;
    private String endScore;
    private Integer errors;
    private Integer errorsAway;
    private Integer errorsHome;
    private Integer experience;
    private String spot;
    private Long extraInfo;
    private String extraInfoBaseball;
    private String extraInfoBasketball;
    private String extraInfoBowls;
    private String extraInfoCricket;
    private String extraInfoCsGo;
    private String extraInfoMoba;
    private String extraInfoDarts;
    private String extraInfoFootball;
    private String extraInfoSoccer;
    private String extraInfoHandball;
    private String extraInfoIceHockey;
    private String extraInfoSnooker;
    private String extraInfoVolleyball;
    private String extraInfoKabaddi;
    private String extraInfoWaterPolo;
    private Boolean firstBaseLoaded;
    private Integer frameNumber;
    private String frameScore;
    private Integer gameNumber;
    private String gameScore;
    private Integer heroId;
    private String heroName;
    private Integer heroesAliveAway;
    private Integer heroesAliveHome;
    private Integer hitsAway;
    private Integer hitsHome;
    private long id;
    private String info;
    private Inning inningHalf;
    private List<InningScoreEntity> inningsScore;
    private Integer kills;
    private Integer killsAway;
    private Integer killsHome;
    private Integer lastHits;
    private String legScore;
    private Integer level;
    private Integer mapNumber;
    private String mapScore;
    private String matchScore;
    private String matchTime;
    private Integer netWorth;
    private Integer outs;
    private Integer periodNumber;
    private String periodScore;
    private Integer pitchCount;
    private Integer player1Id;
    private Integer player2Id;
    private Integer player3Id;
    private Integer player4Id;
    private Integer tippedTo;
    private Integer posX;
    private Integer posY;
    private String remainingTimeInPeriod;
    private Integer roundNumber;
    private Integer pitchingSubstitution;
    private Integer runs;
    private Integer runsAway;
    private Integer runsHome;
    private Integer runsInInnings;
    private Boolean secondBaseLoaded;
    private Integer secondScoreType;
    private String scoreTypeQualifier;
    private DateTime happenedAt;
    private Double shotDistance;
    private Double shotProjectedGoalRate;
    private Integer shotRatingCategory;
    private Double shotSpeed;
    private Integer shotType;
    private Integer possibleBreak;
    private Team server;
    private DateTime serverTime;
    private Integer setNumber;
    private String setScore;
    private Team side;
    private Integer strikes;
    private Boolean thirdBaseLoaded;
    private Long firstBasePlayer;
    private Long secondBasePlayer;
    private Long thirdBasePlayer;
    private EventType type;
    private int typeId;
    private Integer weaponId;
    private String weaponName;
    private Integer dragonkills;
    private Integer baronkills;
    private Integer inhibitorkills;
    private Integer towerkills;
    private Integer doublekills;
    private Integer triplekills;
    private Integer quadrakills;
    private Integer pentakills;
    private String mapName;
    private String itemName;
    private int touchdownType;
    private int conversionType;
    private String goals;
    private String behinds;
    private String matchStatus;
    private String uuId;
    private Integer pitchType;
    private Double pitchSpeed;
    private String battingAverages;
    private Boolean positionPlayerPitching;
    private Double batBallSpeed;
    private Double batBallAngle;
    private Double batBallDirection;
    private String structure;
    private String monsterType;
    private String dragonType;
    private Integer wardsPlaced;
    private Integer championDamage;
    private String refUuId;
    private String roundScore;
    private String kickedBy;
    private String snappedBy;
    private String receivedBy;
    private String muffedBy;
    private String downedBy;
    private String caughtBy;
    private String tackledBy;
    private String touchBackAt;
    private String cloud;
    private Integer humidity;
    private Integer windChill;
    private String windDirection;
    private Integer windSpeed;
    private Integer temperature;
    private String spottedAt;
    private String direction;
    private String pushedBy;
    private Integer totalYardsGained;
    private Integer intendedReceiver;
    private String thrownTo;
    private String defensedBy;
    private String homeTeamStats;
    private String awayTeamStats;
    private String blockedBy;
    private String yardLine;
    private String passedBy;
    private String rushedBy;
    private String rushSpeed;
    private String interceptedBy;
    private String turfType;
    private String stadiumType;
    private String sackedBy;
    private String safetyBy;
    private String fumbledBy;
    private String recoveredBy;
    private String causedBy;
    private String penaltyDistance;
    private String penaltyReason;
    private String penaltyNoPlay;
    private String placedAt;
    private String injuryPlayer;
    private String injuryReturn;
    private String drivePlayInfo;
    private String homePlayerStatsDef;
    private String awayPlayerStatsDef;
    private String homePlayerStatsPass;
    private String awayPlayerStatsPass;
    private String homePlayerStatsRush;
    private String awayPlayerStatsRush;
//    private String homePlayerStats;
//    private String awayPlayerStats;
    private String homePlayerStatsRec;
    private String awayPlayerStatsRec;
    private String puntDistance;
    private Integer damage;
    private Integer healthRemaining;
    private Integer pointsRemaining;
    private Integer reds;
    private String ranOutOfBoundsAt;
    private String driveInfoStatus;
    private String fieldGoalDistance;
    private String homeTimeOnIce;
    private String awayTimeOnIce;
    private String attackingPlayers;
    private String unavailablePlayersHome;
    private String unavailablePlayersAway;
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
    private String homeTimeOnCourt;
    private String awayTimeOnCourt;
    private String homePlayers;
    private String awayPlayers;
    private Integer atBatNumber;
    private Integer totalPitchCount;
    private Integer atBatPitchNumber;
    private Double batBallDistance;
    private Double batBallX;
    private Double batBallY;
    private String fieldingPlayers;
    private String preliminaryMatchStatistics;
    private String actualMatchStatistics;
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
    private Integer freeKickReason;
    private Integer maxBreakFrame;
    private Integer homeRunsHome;
    private Integer maxBreakMatch;
    private Integer homeRunsAway;
    private Integer nextBatter;
    private String strokeType;
    private String spin;
    private Integer foulTypeDescriptor;
    private String foulTypeQualifier;

    /**
     * For Serializable
     */
    protected ScoutEventEntity() {

    }

    /**
     * Gets map number
     *
     * @return map number
     */
    public Integer getMapNumber() { return mapNumber; }

    /**
     * Gets map score
     *
     * @return map score
     */
    public String getMapScore() { return mapScore; }

    /**
     * Gets round number
     *
     * @return round number
     */
    public Integer getRoundNumber() { return roundNumber; }

    /**
     * Identifies whether event was automatically generated or not.
     * <p>
     * Note : Probably not useful.
     * </p>
     *
     * @return flag about automatic generation
     */
    public Integer getAutomatic() { return automatic; }

    /**
     * Gets ball number
     *
     * @return ball number
     */
    public String getBallNumber() { return ballNumber; }

    /**
     * Get number of balls
     *
     * @return number of balls
     */
    public Integer getBalls() { return balls; }

    /**
     * Gets base info
     *
     * @return base info
     */
    public String getBaseInfo() { return baseInfo; }

    /**
     * Points until snooker is needed to win the frame
     *
     * @return points util snooker will be needed
     */
    public Integer getPointsUntilSnookerNeeded() { return pointsUntilSnookerNeeded; }

    /**
     * Gets break score
     * <p>
     * Note: Used in Snooker
     * </p>
     *
     * @return break score
     */
    public String getBreakScore() { return breakScore; }

    /**
     * Gets corrected from, used with score correction event
     *
     * @return corrected from
     */
    public Integer getCorrectedFrom() { return correctedFrom; }

    /**
     * Gets corrected to, used with score correction event
     *
     * @return corrected to
     */
    public Integer getCorrectedTo() { return correctedTo; }

    /**
     * Gets dismissals in innings
     *
     * @return dismissals in innings
     */
    public Integer getDismissalsInInnings() { return dismissalsInInnings; }

    /**
     * End score
     *
     * @return end score
     */
    public String getEndScore() { return endScore; }

    /**
     * Get number of errors
     *
     * @return number of errors
     */
    public Integer getErrors() { return errors; }

    /**
     * information whether a shot/goal was from inside/outside the box
     * 0 - outside the box
     * 1 - inside the box
     * -1 - unknown
     * @return according xsd - string value. Should it be parsed to int?
     */
    public String getSpot() { return spot; }

    /**
     * Integer value, meaning of this attribute depends on event type:
     * <ul>
     * <li>30 (score): 0 or -1 = not specified, 1 = penalty, 2 = own goal, 3 = header</li>
     * <li>1019 (cancel goal): 0 or -1 = not specified, 1 = off side, 2 = foul, 3 = incorrect entry</li>
     * <li>1013 (match status): match status enumeration</li>
     * <li>1025 (tennis full score): tennis point type – 0=standard, 1=ace, 2=double fault opponent</li>
     * <li>158 (injury): Player still injured – 1=yes, 0=no 165 (attendance): Attendance</li>
     * <li>1039 (manual time adjustment): Number of seconds adjusted. Can be both a positive and a negative integer</li>
     * <li>1044 (event deleted): Id of event that was deleted</li>
     * <li>1037 (basketball points): Number of points scored – 1, 2 or 3</li>
     * <li>1038 (basketball points missed): Point attempt missed – 1, 2 or 3 (points)</li>
     * <li>1046 (volleyball full score): volleyball point type – 0=standard, 1=ace, 2=service error</li>
     * <li>1056 (number of free throws): Number of free throws</li>
     * <li>1036 (time start stop): 1 = time is running, 0 = time is stopped</li>
     * <li>43 (suspensions): Number of minutes player is suspended for</li>
     * <li>90 (injury time): Number of minutes injury time added</li>
     * <li>1082 (corner markets): 1 = safe to accept bets, 0 = not safe</li>
     * <li>1083 (booking markets): 1 = safe to accept bets, 0 = not safe</li>
     * <li>1002 (penalty shootout): -1=penalty not taken, 1=penalty scored, 0=penalty missed</li>
     * <li>1060 (direct free kick): -1 = not taken, 0 = miss, 1 = score</li>
     * <li>1102 (coverage status):   0 - match covered,
     * 1 - coverage abandoned (used when abandoning coverage after a match has started),
     * 2 - match will not be covered (used when we are unable to cover a match that we were supposed to cover)</li>
     * </ul>
     * All counter event types (throw in count etc.):
     * Number of events of this type for team specified by “side" attribute
     *
     * @return extra info
     */
    public Long getExtraInfo() { return extraInfo; }

    /**
     * Gets extra info for baseball
     *
     * @return extra info for baseball
     */
    public String getExtraInfoBaseball() { return extraInfoBaseball; }

    /**
     * Gets extra information for basketball match
     *
     * @return extra information for basketball match
     */
    public String getExtraInfoBasketball() { return extraInfoBasketball; }

    /**
     * Extra info for bowls game
     *
     * @return Bowls extra info
     */
    public String getExtraInfoBowls() { return extraInfoBowls; }

    /**
     * Gets extra information for cricket match
     *
     * @return extra information for cricket match
     */
    public String getExtraInfoCricket() { return extraInfoCricket; }

    /**
     * Gets extra info for CS:GO
     *
     * @return extra info for CS:GO
     */
    public String getExtraInfoCsGo() { return extraInfoCsGo; }

    /**
     * Gets extra info for "MOBA" matches
     *
     * @return extra info for "MOBA" matches
     */
    public String getExtraInfoMoba() { return extraInfoMoba; }

    /**
     * Extra info for dart game
     *
     * @return Dart extra info
     */
    public String getExtraInfoDarts() { return extraInfoDarts; }

    /**
     * Extra info for football game
     *
     * @return Football extra info
     */
    public String getExtraInfoFootball() { return extraInfoFootball; }

    /**
     * Extra info for soccer game
     *
     * @return Soccer extra info
     */
    public String getExtraInfoSoccer() { return extraInfoSoccer; }

    /**
     * Gets extra info handball
     *
     * @return extra info handball
     */
    public String getExtraInfoHandball() { return extraInfoHandball; }

    /**
     * Extra info ice hockey
     *
     * @return extra info ice hockey
     */
    public String getExtraInfoIceHockey() { return extraInfoIceHockey; }

    /**
     * Gets snooker extra info
     * <p>
     * Note: Used in Snooker
     * </p>
     *
     * @return Snooker extra info
     */
    public String getExtraInfoSnooker() { return extraInfoSnooker; }

    /**
     * Gets volleyball extra info
     *
     * @return Volleyball extra info
     */
    public String getExtraInfoVolleyball() { return extraInfoVolleyball; }

    /**
     * Gets kabaddi extra info
     *
     * @return Kabaddi extra info
     */
    public String getExtraInfoKabaddi() { return extraInfoKabaddi; }

    /**
     * Gets water polo extra info
     *
     * @return Water polo extra info
     */
    public String getExtraInfoWaterPolo() { return extraInfoWaterPolo; }

    /**
     * Gets frame number
     * <p>
     * Note: Used in Snooker
     * </p>
     *
     * @return frame number
     */
    public Integer getFrameNumber() { return frameNumber; }

    /**
     * Gets frame score
     * <p>
     * Note: Used in Snooker
     * </p>
     *
     * @return frame score
     */
    public String getFrameScore() { return frameScore; }

    /**
     * In tennis, which game number in the set.
     *
     * @return game number
     */
    public Integer getGameNumber() { return gameNumber; }

    /**
     * In tennis, the game score.
     *
     * @return game score
     */
    public String getGameScore() { return gameScore; }

    /**
     * Unique event id.
     *
     * @return id
     */
    public long getId() { return id; }

    /**
     * Text description of event.
     *
     * @return info
     */
    public String getInfo() { return info; }

    /**
     * Get current inning half
     *
     * @return inning half
     */
    public Inning getInningHalf() { return inningHalf; }

    /**
     * Used in darts
     * Leg (game) score
     *
     * @return log score
     */
    public String getLegScore() { return legScore; }

    /**
     * In tennis, the match score.
     *
     * @return match score
     */
    public String getMatchScore() { return matchScore; }

    /**
     * Match time, mm:ss (e.g. 40:00 or 00:00).
     *
     * @return match time
     */
    public String getMatchTime() { return matchTime; }

    /**
     * Get number of outs
     *
     * @return number of outs
     */
    public Integer getOuts() { return outs; }

    /**
     * Period number of match.
     *
     * @return period number
     */
    public Integer getPeriodNumber() { return periodNumber; }

    /**
     * Gets period score
     *
     * @return period score
     */
    public String getPeriodScore() { return periodScore; }

    /**
     * Gets pitch count
     *
     * @return pitch count
     */
    public Integer getPitchCount() { return pitchCount; }

    /**
     * Sportradar player id for player 1 connected to this event.
     * The meaning of this attribute depends on event type:
     * <ul>
     * <li>30 (goal): Goal scorer</li>
     * <li>50, 40, 45 (red, yellow, yellow/red card): Who got card</li>
     * <li>60 (substitution): Outgoing player</li>
     * <li>154 (corner): Who took corner</li>
     * <li>161 (penalty awarded): Who caused penalty – offender</li>
     * <li>155, 156 (shot on/off target): Who shot</li>
     * <li>153 (off side): Who was off side</li>
     * <li>158 (injury): Who was injured</li>
     * </ul>
     *
     * @return player 1 id
     */
    public Integer getPlayer1Id() { return player1Id; }

    /**
     * Sportradar player id for player 1 connected to this event.
     * The meaning of this attribute depends on event type:
     * <ul>
     * <li>60 (substitution): Outgoing player</li>
     * <li>161 (penalty awarded): Who caused penalty – offender</li>
     * </ul>
     *
     * @return player 2 id
     */
    public Integer getPlayer2Id() { return player2Id; }

    /**
     * Sportradar player id for player 3 connected to this event.
     * The meaning of this attribute depends on event type:
     * <ul>
     * <li>30 (goal): player3 = Second assisting player</li>
     * </ul>
     *
     * @return player 3 id
     */
    public Integer getPlayer3Id() { return player3Id; }

    /**
     * Sportradar player id for player 4 connected to this event.
     * The meaning of this attribute depends on event type:
     * <ul>
     * <li>30 (goal): player3 = Second assisting player</li>
     * </ul>
     *
     * @return player 4 id
     */
    public Integer getPlayer4Id() { return player4Id; }

    /**
     * ID of player who got the ball. Only if present.
     *
     * @return ID of player
     */
    public Integer getTippedTo(){
        return tippedTo;
    }

    /**
     * Horizontal position on pitch, posx being a number from 0 to 100.
     * The reference point 0 is at home teams goal.
     *
     * @return position x
     */
    public Integer getPosX() { return posX; }

    /**
     * Vertical position on pitch, posy being a number from 0 to 100.
     * The reference point 0 is on top of pitch when home teams goal is on the left hand side.
     *
     * @return position y
     */
    public Integer getPosY() { return posY; }

    /**
     * Remaining time in period, mm:ss
     *
     * @return remaining time in period
     */
    public String getRemainingTimeInPeriod() { return remainingTimeInPeriod; }

    /**
     * Get number of runs
     *
     * @return number of runs
     */
    public Integer getRuns() { return runs; }

    /**
     * Gets runs in innings
     *
     * @return runs in innings
     */
    public Integer getRunsInInnings() { return runsInInnings; }

    /**
     * Second score type
     *
     * @return second score type
     */
    public Integer getSecondScoreType() { return secondScoreType; }

    /**
     * Scoretypequalifier: Additional score type qualifier.
     * One event can have more than one scoretypequalifier.
     * Enabled by LD XML config ID 46.
     * Can have multiple values (for example 1,2,5). Possible values:
     *
     * 1 = 2nd chance
     * 2 = Fast break
     * 3 = From turnover
     * 4 = Defensive goal tending
     * 5 = Points in the paint
     *
     * @return {@link String} of types
     */
    public String getScoreTypeQualifier() { return scoreTypeQualifier; }

    /**
     * UNIX timestamp indicating when the event actually happened.
     * Available only if the event had to be added after the fact.
     *
     * @return {@link DateTime} of happenedat.
     */
    public DateTime getHappenedAt() { return happenedAt; }

    /**
     * shotdistance
     * -  description: Distance of the shot
     * -  unit: in feet
     *
     * @return {@link Double} of shotdistance.
     */
    public Double getShotDistance() { return shotDistance; }

    public Double getShotProjectedGoalRate() { return shotProjectedGoalRate; }
    public Integer getShotRatingCategory() { return shotRatingCategory; }
    public Double getShotSpeed() { return shotSpeed; }
    public Integer getShotType() { return shotType; }

    /**
     * Highest possible break in the current situation
     *
     * @return {@link Integer} possible break.
     */
    public Integer getPossibleBreak() {
        return possibleBreak;
    }

    /**
     * Who will serve next (in table-tennis).
     *
     * @return serving team
     */
    public Team getServer() { return server; }

    /**
     * Server time of event, time stamp in UTC.
     *
     * @return server time
     */
    public DateTime getServerTime() { return serverTime; }

    /**
     * In tennis, which set number in the match.
     *
     * @return set number
     */
    public Integer getSetNumber() { return setNumber; }

    /**
     * In tennis, the set score.
     *
     * @return set score
     */
    public String getSetScore() { return setScore; }

    /**
     * Which side had an event - home, away or none.
     *
     * @return side
     */
    public Team getSide() { return side; }

    /**
     * Get number of strike
     *
     * @return number of strikes
     */
    public Integer getStrikes() { return strikes; }

    /**
     * Each event type has an id (e.g. yellow card 40, event deleted 1044, early bet status 1091, etc.).
     * See LiveScout documentation to see which event types are supported for each sport.
     * New event types can be offered in the future.
     * @return type
     * @deprecated
     * Please use the appropriate event typeId instead and map typeId to event type based on sport in
     * according to documentation.
     */
    public EventType getType() { return type; }

    /**
     * Each event type has an id (e.g. yellow card 40, event deleted 1044, early bet status 1091, etc.).
     * See LiveScout documentation to see which event types are supported for each sport.
     * New event types can be offered in the future.
     * @return typeId
     */
    public int getTypeId() { return typeId; }
    /**
     * Gets number of assists
     *
     * @return number of assists
     */
    public Integer getAssists() { return assists; }

    /**
     * Gets number of deaths
     *
     * @return number of deaths
     */
    public Integer getDeaths() { return deaths; }

    /**
     * Gets number of errors away
     *
     * @return number of errors away
     */
    public Integer getErrorsAway() { return errorsAway; }

    /**
     * Gets number of errors home
     *
     * @return number of errors home
     */
    public Integer getErrorsHome() { return errorsHome; }

    /**
     * Gets experience
     *
     * @return experience
     */
    public Integer getExperience() { return experience; }

    /**
     * Is first base loaded
     *
     * @return first base status
     */
    public Boolean getFirstBaseLoaded() { return firstBaseLoaded; }

    /**
     * Gets the number of alive away team's heroes
     *
     * @return number of alive away team's heroes
     */
    public Integer getHeroesAliveAway() { return heroesAliveAway; }

    /**
     * Gets the number of alive home team's heroes
     *
     * @return number of alive home team's heroes
     */
    public Integer getHeroesAliveHome() { return heroesAliveHome; }

    /**
     * Gets the number of hits for away team
     *
     * @return number of hits for away team
     */
    public Integer getHitsAway() { return hitsAway; }

    /**
     * Gets the number of hits for home team
     *
     * @return number of hits for home team
     */
    public Integer getHitsHome() { return hitsHome; }

    /**
     * Gets inning scores
     *
     * @return inning scores
     */
    public List<InningScoreEntity> getInningsScore() { return inningsScore; }

    /**
     * Gets number of kills
     *
     * @return number of kills
     */
    public Integer getKills() { return kills; }

    /**
     * Gets number of last hits
     *
     * @return number of last hits
     */
    public Integer getLastHits() { return lastHits; }

    /**
     * Gets level
     *
     * @return level
     */
    public Integer getLevel() { return level; }

    /**
     * Gets net worth
     *
     * @return net worth
     */
    public Integer getNetWorth() { return netWorth; }

    /**
     * Gets pitching substitution
     * @return pitching substitution
     */
    public Integer getPitchingSubstitution(){
        return pitchingSubstitution;
    }

    /**
     * Gets the number of runs for away team
     *
     * @return number of runs for away team
     */
    public Integer getRunsAway() { return runsAway; }

    /**
     * Gets the number of runs for home team
     *
     * @return number of runs for home team
     */
    public Integer getRunsHome() { return runsHome; }

    /**
     * Is second base loaded
     *
     * @return first base status
     */
    public Boolean getSecondBaseLoaded() { return secondBaseLoaded; }

    /**
     * Is first base loaded
     *
     * @return first base status
     */
    public Boolean getThirdBaseLoaded() { return thirdBaseLoaded; }

    /**
     * Get first base player
     *
     * @return first base player
     */
    public Long getFirstBasePlayer() {
        return firstBasePlayer;
    }

    /**
     * Get second base player
     *
     * @return second base player
     */
    public Long getSecondBasePlayer() {
        return secondBasePlayer;
    }

    /**
     * Get third base player
     *
     * @return third base player
     */
    public Long getThirdBasePlayer() {
        return thirdBasePlayer;
    }

    /**
     * Gets weapon id
     *
     * @return weapon id
     */
    public Integer getWeaponId() { return weaponId; }

    /**
     * Gets weapon name
     *
     * @return weapon name
     */
    public String getWeaponName() { return weaponName; }

    /**
     * Gets ball spotting
     *
     * @return ball spotting
     */
    public Boolean getBallSpotting() { return ballSpotting; }

    /**
     * Gets denies
     *
     * @return get denies
     */
    public Integer getDenies() { return denies; }

    /**
     * Gets early betstart type
     *
     * @return early betstart type
     */
    public Integer getEarlyBetstartType() { return earlyBetstartType; }

    /**
     * Gets hero id
     *
     * @return hero id
     */
    public Integer getHeroId() { return heroId; }

    /**
     * Gets hero name
     *
     * @return hero name
     */
    public String getHeroName() { return heroName; }

    /**
     * Gets kills away
     *
     * @return kills away
     */
    public Integer getKillsAway() { return killsAway; }

    /**
     * Gets kills home
     *
     * @return kills home
     */
    public Integer getKillsHome() { return killsHome; }

    /**
     * Gets dragon kills
     *
     * @return dragon kills
     */
    public Integer getDragonkills() { return dragonkills; }

    /**
     * Gets baron kills
     *
     * @return baron kills
     */
    public Integer getBaronkills() { return baronkills; }

    /**
     * Gets inhibitor kills
     *
     * @return inhibitor kills
     */
    public Integer getInhibitorkills() { return inhibitorkills; }

    /**
     * Gets tower kills
     *
     * @return tower kills
     */
    public Integer getTowerkills() { return towerkills; }

    /**
     * Gets double kills
     *
     * @return double kills
     */
    public Integer getDoublekills() { return doublekills; }

    /**
     * Gets triple kills
     *
     * @return triple kills
     */
    public Integer getTriplekills() { return triplekills; }

    /**
     * Gets quadra kills
     *
     * @return quadra kills
     */
    public Integer getQuadrakills() { return quadrakills; }

    /**
     * Gets penta kills
     *
     * @return penta kills
     */
    public Integer getPentakills() { return pentakills; }

    /**
     * Gets map name
     *
     * @return map name
     */
    public String getMapName() { return mapName; }

    /**
     * Gets item name
     *
     * @return item name
     */
    public String getItemName() { return itemName; }

    /**
     * Gets the touchdown type
     * @return touchdown type
     */
    public int getTouchdownType() { return touchdownType; }

    /**
     * Gets the conversion type
     * @return conversion type
     */
    public int getConversionType() { return conversionType; }

    /**
     * Gets the goals
     * @return the goals
     */
    public String getGoals() { return goals; }

    /**
     * Gets the behinds
     * @return the behinds
     */
    public String getBehinds() { return behinds; }

    /**
     * Gets the match status
     * @return the match status
     */
    public String getMatchStatus() { return matchStatus; }

    public String getUuId() { return uuId; }

    /**
     * Gets the type of how was the ball pitched
     * @return the type of the pitch
     */
    public Integer getPitchType() { return pitchType; }

    /**
     * Gets how fast the ball was pitched in MPH numeric values
     * @return the pitch speed
     */
    public Double getPitchSpeed() { return pitchSpeed; }

    /**
     * Gets the batting averages
     * @return the batting averages
     */
    public String getBattingAverages() { return battingAverages; }

    /**
     * Gets the position player pitching
     * @return the position player pitching
     */
    public Boolean getPositionPlayerPitching() { return positionPlayerPitching; }

    /**
     * Gets the speed at which the ball leaves the bat, mph
     * @return the speed at which the ball leaves the bat, mph
     */
    public Double getBatBallSpeed() { return batBallSpeed; }

    /**
     * Gets the angle of the ball, relative from the ground, as the ball leaves the bat
     * @return the angle of the ball, relative from the ground, as the ball leaves the bat
     */
    public Double getBatBallAngle() { return batBallAngle; }

    /**
     * Gets the direction of the ball as it leaves the bat
     * (-45 = left field bounds, 45 = right field bounds, 0 = straight forward)
     * @return the direction of the ball as it leaves the bat
     */
    public Double getBatBallDirection() { return batBallDirection; }

    public String getStructure() { return structure; }

    public String getMonsterType() { return monsterType; }

    public String getDragonType() { return dragonType; }

    public Integer getWardsPlaced() { return wardsPlaced; }

    public Integer getChampionDamage() { return championDamage; }

    public String getRefUuId() {
        return refUuId;
    }

    public String getRoundScore() {
        return roundScore;
    }

    public String getKickedBy() { return kickedBy; }

    public String getSnappedBy() {
        return snappedBy;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public String getMuffedBy() {
        return muffedBy;
    }

    public String getDownedBy() {
        return downedBy;
    }

    public String getCaughtBy() {
        return caughtBy;
    }

    public String getTackledBy() {
        return tackledBy;
    }

    public String getTouchBackAt() {
        return touchBackAt;
    }

    public String getCloud() {
        return cloud;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getWindChill() {
        return windChill;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public String getSpottedAt() {
        return spottedAt;
    }

    public String getDirection() {
        return direction;
    }

    public String getPushedBy() {
        return pushedBy;
    }

    public Integer getTotalYardsGained() {
        return totalYardsGained;
    }

    public Integer getIntendedReceiver() {
        return intendedReceiver;
    }

    public String getThrownTo() {
        return thrownTo;
    }

    public String getDefensedBy() {
        return defensedBy;
    }

    public String getHomeTeamStats() {
        return homeTeamStats;
    }

    public String getAwayTeamStats() {
        return awayTeamStats;
    }

    public String getBlockedBy() {
        return blockedBy;
    }

    public String getYardLine() {
        return yardLine;
    }

    public String getPassedBy() {
        return passedBy;
    }

    public String getRushedBy() {
        return rushedBy;
    }

    public String getRushSpeed() {
        return rushSpeed;
    }

    public String getInterceptedBy() {
        return interceptedBy;
    }

    public String getTurfType() {
        return turfType;
    }

    public String getStadiumType() {
        return stadiumType;
    }

    public String getSackedBy() {
        return sackedBy;
    }

    public String getSafetyBy() {
        return safetyBy;
    }

    public String getFumbledBy() {
        return fumbledBy;
    }

    public String getRecoveredBy() {
        return recoveredBy;
    }

    public String getCausedBy() {
        return causedBy;
    }

    public String getPenaltyDistance() {
        return penaltyDistance;
    }

    public String getPenaltyReason() {
        return penaltyReason;
    }

    public String getPenaltyNoPlay() {
        return penaltyNoPlay;
    }

    public String getPlacedAt() {
        return placedAt;
    }

    public String getInjuryPlayer() {
        return injuryPlayer;
    }

    public String getInjuryReturn() {
        return injuryReturn;
    }

    public String getDrivePlayInfo() {
        return drivePlayInfo;
    }

    public String getHomePlayerStatsDef() {
        return homePlayerStatsDef;
    }

    public String getAwayPlayerStatsDef() {
        return awayPlayerStatsDef;
    }

    public String getHomePlayerStatsPass() {
        return homePlayerStatsPass;
    }

    public String getAwayPlayerStatsPass() {
        return awayPlayerStatsPass;
    }

    public String getHomePlayerStatsRush() {
        return homePlayerStatsRush;
    }

    public String getAwayPlayerStatsRush() {
        return awayPlayerStatsRush;
    }

//    public String getHomePlayerStats() {
//        return homePlayerStats;
//    }
//
//    public String getAwayPlayerStats() {
//        return awayPlayerStats;
//    }

    public String getHomePlayerStatsRec() {
        return homePlayerStatsRec;
    }

    public String getAwayPlayerStatsRec() {
        return awayPlayerStatsRec;
    }

    public String getPuntDistance() {
        return puntDistance;
    }

    protected Integer getDamage( ) {
        return damage;
    }

    protected Integer getHealthRemaining() {
        return healthRemaining;
    }

    /**
     * Get points remaining on the table.
     *
     * @return points remaining
     */
    public Integer getPointsRemaining() {
        return pointsRemaining;
    }

    /**
     * Get number of Reds remaining on the table
     *
     * @return reds
     */
    public Integer getReds() {
        return reds;
    }

    public String getRanOutOfBoundsAt() {
        return ranOutOfBoundsAt;
    }

    public String getDriveInfoStatus() {
        return driveInfoStatus;
    }

    public String getFieldGoalDistance() {
        return fieldGoalDistance;
    }

    public String getHomeTimeOnIce() {
        return homeTimeOnIce;
    }

    public String getAwayTimeOnIce() {
        return awayTimeOnIce;
    }

    public String getAttackingPlayers() { return attackingPlayers; }

    public String getUnavailablePlayersHome() {
        return unavailablePlayersHome;
    }

    public String getUnavailablePlayersAway() { return unavailablePlayersAway; }

    public String getHomePlayerStatsTotal() { return homePlayerStatsTotal; }

    public String getAwayPlayerStatsTotal() { return awayPlayerStatsTotal; }

    public String getHomePlayerStatsP1() { return homePlayerStatsP1; }

    public String getAwayPlayerStatsP1() { return awayPlayerStatsP1; }

    public String getHomePlayerStatsP2() { return homePlayerStatsP2; }

    public String getAwayPlayerStatsP2() { return awayPlayerStatsP2; }

    public String getHomePlayerStatsP3() { return homePlayerStatsP3; }

    public String getAwayPlayerStatsP3() { return awayPlayerStatsP3; }

    public String getHomePlayerStatsP4() { return homePlayerStatsP4; }

    public String getAwayPlayerStatsP4() { return awayPlayerStatsP4; }

    public String getHomePlayerStatsOt() { return homePlayerStatsOt; }

    public String getAwayPlayerStatsOt() { return awayPlayerStatsOt; }

    public String getHomeTimeOnCourt() { return homeTimeOnCourt; }

    public String getAwayTimeOnCourt() { return awayTimeOnCourt; }

    /**
     * Homeplayers: List of home player IDs which are currently on court.
     * Format: "PID;PID;PID" (list of players ids split by semicolon)
     *
     * @return {@link String} of players ids
     */
    public String getHomeplayers() { return homePlayers; }

    /**
     * Awayplayers: List of away player IDs which are currently on court.
     * Format: "PID;PID;PID" (list of players ids split by semicolon)
     *
     * @return {@link String} of players ids
     */
    public String getAwayPlayers() { return awayPlayers; }

    public Integer getAtBatNumber() { return atBatNumber; }

    public Integer getTotalPitchCount() { return totalPitchCount; }
    public Integer getAtBatPitchNumber() { return atBatPitchNumber; }

    public Double getBatBallDistance() { return batBallDistance; }

    public Double getBatBallX() { return batBallX; }

    public Double getBatBallY() { return batBallY; }

    public String getFieldingPlayers() { return fieldingPlayers; }

    public String getPreliminaryMatchStatistics() { return preliminaryMatchStatistics; }

    public String getActualMatchStatistics() { return actualMatchStatistics; }

    public String getHomeTeamStatsTotal() { return homeTeamStatsTotal; }

    public String getAwayTeamStatsTotal() { return awayTeamStatsTotal; }

    public String getHomeTeamStatsP1() { return homeTeamStatsP1; }

    public String getAwayTeamStatsP1() { return awayTeamStatsP1; }

    public String getHomeTeamStatsP2() { return homeTeamStatsP2; }

    public String getAwayTeamStatsP2() { return awayTeamStatsP2; }

    public String getHomeTeamStatsP3() { return homeTeamStatsP3; }

    public String getAwayTeamStatsP3() { return awayTeamStatsP3; }

    public String getHomeTeamStatsP4() { return homeTeamStatsP4; }

    public String getAwayTeamStatsP4() { return awayTeamStatsP4; }

    public String getHomeTeamStatsOt() { return homeTeamStatsOt; }

    public String getAwayTeamStatsOt() { return awayTeamStatsOt; }

    /**
     * FreeKickReason: Description of the free kick. Possible values:
     *
     * 0 = foul
     * 1 = handball
     * 2 = other
     *
     * @return {@link Integer} value of freeKickReason
     */
    public Integer getFreeKickReason() {
        return freeKickReason;
    }

    /**
     * Get highest break in the current frame.
     *
     * @return max break frame
     */
    public Integer getMaxBreakFrame() {
        return maxBreakFrame;
    }

    /**
     * Get home runs home.
     *
     * @return home runs home
     */
    public Integer getHomeRunsHome() {
        return homeRunsHome;
    }

    /**
     * Get highest break in the current match.
     *
     * @return max break match
     */
    public Integer getMaxBreakMatch() {
        return maxBreakMatch;
    }

    /**
     * Get home runs away.
     *
     * @return home runs away
     */
    public Integer getHomeRunsAway() {
        return homeRunsAway;
    }

    /**
     * Get next batter.
     *
     * @return next batter
     */
    public Integer getNextBatter() {
        return nextBatter;
    }

    /**
     * Get stroke type.
     *
     * @return stroke type
     */
    public String getStrokeType() {
        return strokeType;
    }

    /**
     * Get spin.
     *
     * @return spin
     */
    public String getSpin() {
        return spin;
    }

    /**
     * Foultypedescriptor: Description of the foul. Possible values:
     *
     * 1 = charge
     * 2 = off the ball
     * 3 = take
     * 4 = double
     * 5 = shooting
     * 6 = clear path
     * 7 = loose ball
     * 8 = blocking
     * 9 = player control
     * 10 = shooting block
     * 11 = elbow
     * 12 = punching
     * 13 = flagrant type 1
     * 14 = flagrant type 2
     * 15 = away from play
     * 16 = inbound
     * 17 = non-unsportsmanlike
     * 18 = rim hanging
     * 19 = delay
     * 20 = taunting
     * 21 = indirect
     * 22 = excess timeout
     * 23 = too many players
     * 24 = defensive 3 second
     * 25 = flopping
     * 26 = bench
     *
     * @return {@link Integer} value of foultypedescriptor
     */
    public Integer getFoulTypeDescriptor() { return foulTypeDescriptor; }

    /**
     * Foultypequalifier: Additional classification of the foul type.
     * One event can have more than one foultypequalifier. Possible values:
     *
     * 1 = 1 free throw
     * 2 = 2 free throw
     * 3 = 3 free throw
     * 4 = in penalty
     * 5 = team
     *
     * @return {@link String} of types
     */
    public String getFoulTypeQualifier() { return foulTypeQualifier; }
}
