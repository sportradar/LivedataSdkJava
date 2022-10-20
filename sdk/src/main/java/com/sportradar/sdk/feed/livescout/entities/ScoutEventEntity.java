package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.livescout.enums.EventType;
import com.sportradar.sdk.feed.livescout.enums.Inning;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Contains information about an event
 */
public class ScoutEventEntity implements Serializable {

    private static final long serialVersionUID = 6151858186845823797L;
    private Integer automatic;
    private Integer assists;
    private String ballNumber;
    private Integer balls;
    private Boolean ballSpotting;
    private String baseInfo;
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
    private Long extraInfo;
    private String extraInfoBaseball;
    private String extraInfoBasketball;
    private String spot;
    private String extraInfoBowls;
    private String extraInfoCricket;
    private String extraInfoCsGo;
    private String extraInfoMoba;
    private String extraInfoDarts;
    private String extraInfoFootball;
    private String extraInfoHandball;
    private String extraInfoIceHockey;
    private String extraInfoSnooker;
    private String extraInfoVolleyball;
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
    private Integer runs;
    private Integer runsAway;
    private Integer runsHome;
    private Integer runsInInnings;
    private Boolean secondBaseLoaded;
    private Integer secondScoreType;
    private String scoreTypeQualifier;
    private DateTime happenedAt;
    private Double shotDistance;
    private Team server;
    private DateTime serverTime;
    private Integer setNumber;
    private String setScore;
    private Team side;
    private Integer strikes;
    private Boolean thirdBaseLoaded;
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
    private String homePlayerStatsRec;
    private String awayPlayerStatsRec;
    private String puntDistance;
    private Integer damage;
    private Integer healthRemaining;
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
    private Integer atBatPitchNumber;
    private Double batBallDistance;
    private Double batBallX;
    private Double batBallY;
    private String extraInfoKabaddi;
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
    private Integer foulTypeDescriptor;
    private String foulTypeQualifier;

    /**
     * For Serializable
     */
    protected ScoutEventEntity() {

    }

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
     * <li>1102 (coverage status):   0 - match covered, 1 - coverage abandoned (used when abandoning coverage after a match has started), 2 - match will not be covered (used when we are unable to cover a match that we were supposed to cover)</li>
     * </ul>
     * All counter event types (throw in count etc.): Number of events of this type for team specified by “side" attribute
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
     * information whether a shot/goal was from inside/outside the box
     * 0 - outside the box
     * 1 - inside the box
     * -1 - unknown
     * @return according xsd - string value. Should it be parsed to int?
     */
    public String getSpot() { return spot; }

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
     * Please use the appropriate event typeId instead and map typeId to event type based on sport in according to documentation
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
     * Gets the direction of the ball as it leaves the bat (-45 = left field bounds, 45 = right field bounds, 0 = straight forward)
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
     * Homeplayers: List of home player IDs which are currently on court. Format: "PID;PID;PID" (list of players ids split by semicolon)
     *
     * @return {@link String} of players ids
     */
    public String getHomeplayers() { return homePlayers; }

    /**
     * Awayplayers: List of away player IDs which are currently on court. Format: "PID;PID;PID" (list of players ids split by semicolon)
     *
     * @return {@link String} of players ids
     */
    public String getAwayPlayers() { return awayPlayers; }

    public Integer getAtBatNumber() { return atBatNumber; }

    public Integer getAtBatPitchNumber() { return atBatPitchNumber; }

    public Double getBatBallDistance() { return batBallDistance; }

    public Double getBatBallX() { return batBallX; }

    public Double getBatBallY() { return batBallY; }

    public String getExtraInfoKabaddi() { return extraInfoKabaddi; }

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
     * Foultypequalifier: Additional classification of the foul type. One event can have more than one foultypequalifier. Possible values:
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
        return "ScoutEventEntity{" +
                "automatic=" + automatic +
                ", assists=" + assists +
                ", ballNumber='" + ballNumber + '\'' +
                ", balls=" + balls +
                ", ballSpotting=" + ballSpotting +
                ", baseInfo='" + baseInfo + '\'' +
                ", breakScore='" + breakScore + '\'' +
                ", correctedFrom=" + correctedFrom +
                ", correctedTo=" + correctedTo +
                ", deaths=" + deaths +
                ", denies=" + denies +
                ", dismissalsInInnings=" + dismissalsInInnings +
                ", earlyBetstartType=" + earlyBetstartType +
                ", endScore='" + endScore + '\'' +
                ", errors=" + errors +
                ", errorsAway=" + errorsAway +
                ", errorsHome=" + errorsHome +
                ", experience=" + experience +
                ", extraInfo=" + extraInfo +
                ", extraInfoBaseball='" + extraInfoBaseball + '\'' +
                ", extraInfoBasketball='" + extraInfoBasketball + '\'' +
                ", spot='" + spot + '\'' +
                ", extraInfoBowls='" + extraInfoBowls + '\'' +
                ", extraInfoCricket='" + extraInfoCricket + '\'' +
                ", extraInfoCsGo='" + extraInfoCsGo + '\'' +
                ", extraInfoDarts='" + extraInfoDarts + '\'' +
                ", extraInfoFootball='" + extraInfoFootball + '\'' +
                ", extraInfoHandball='" + extraInfoHandball + '\'' +
                ", extraInfoIceHockey='" + extraInfoIceHockey + '\'' +
                ", extraInfoSnooker='" + extraInfoSnooker + '\'' +
                ", extraInfoVolleyball='" + extraInfoVolleyball + '\'' +
                ", firstBaseLoaded=" + firstBaseLoaded +
                ", frameNumber=" + frameNumber +
                ", frameScore='" + frameScore + '\'' +
                ", gameNumber=" + gameNumber +
                ", gameScore='" + gameScore + '\'' +
                ", heroId=" + heroId +
                ", heroName='" + heroName + '\'' +
                ", heroesAliveAway=" + heroesAliveAway +
                ", heroesAliveHome=" + heroesAliveHome +
                ", hitsAway=" + hitsAway +
                ", hitsHome=" + hitsHome +
                ", id=" + id +
                ", info='" + info + '\'' +
                ", inningHalf=" + inningHalf +
                ", inningsScore=" + inningsScore +
                ", kills=" + kills +
                ", killsAway=" + killsAway +
                ", killsHome=" + killsHome +
                ", lastHits=" + lastHits +
                ", legScore='" + legScore + '\'' +
                ", level=" + level +
                ", mapNumber=" + mapNumber +
                ", mapScore='" + mapScore + '\'' +
                ", matchScore='" + matchScore + '\'' +
                ", matchTime='" + matchTime + '\'' +
                ", netWorth=" + netWorth +
                ", outs=" + outs +
                ", periodNumber=" + periodNumber +
                ", periodScore='" + periodScore + '\'' +
                ", pitchCount=" + pitchCount +
                ", player1Id=" + player1Id +
                ", player2Id=" + player2Id +
                ", player3Id=" + player3Id +
                ", player4Id=" + player4Id +
                ", tippedTo=" + tippedTo +
                ", posX=" + posX +
                ", posY=" + posY +
                ", remainingTimeInPeriod='" + remainingTimeInPeriod + '\'' +
                ", roundNumber=" + roundNumber +
                ", runs=" + runs +
                ", runsAway=" + runsAway +
                ", runsHome=" + runsHome +
                ", runsInInnings=" + runsInInnings +
                ", secondBaseLoaded=" + secondBaseLoaded +
                ", secondScoreType=" + secondScoreType +
                ", scoreTypeQualifier=" + scoreTypeQualifier +
                ", happenedAt=" + happenedAt +
                ", shotDistance=" + shotDistance +
                ", server=" + server +
                ", serverTime=" + serverTime +
                ", setNumber=" + setNumber +
                ", setScore='" + setScore + '\'' +
                ", side=" + side +
                ", strikes=" + strikes +
                ", thirdBaseLoaded=" + thirdBaseLoaded +
                ", type=" + type +
                ", typeId=" + typeId +
                ", weaponId=" + weaponId +
                ", weaponName='" + weaponName + '\'' +
                ", dragonkills=" + dragonkills +
                ", baronkills=" + baronkills +
                ", inhibitorkills=" + inhibitorkills +
                ", towerkills=" + towerkills +
                ", doublekills=" + doublekills +
                ", triplekills=" + triplekills +
                ", quadrakills=" + quadrakills +
                ", pentakills=" + pentakills +
                ", mapName='" + mapName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", conversionType='" + conversionType + '\'' +
                ", touchdownType='" + touchdownType + '\'' +
                ", goals='" + goals + '\'' +
                ", behinds='" + behinds + '\'' +
                ", matchStatus='" + matchStatus + '\'' +
                ", uuId='" + uuId + '\'' +
                ", pitchType=" + pitchType +
                ", pitchSpeed=" + pitchSpeed +
                ", battingAverages='" + battingAverages +
                ", positionPlayerPitching='" + positionPlayerPitching + '\'' +
                ", batBallSpeed=" + batBallSpeed +
                ", batBallAngle=" + batBallAngle +
                ", batBallDirection=" + batBallDirection +
                ", structure='" + structure + '\'' +
                ", monsterType='" + monsterType + '\'' +
                ", dragonType='" + dragonType + '\'' +
                ", wardsPlaced=" + wardsPlaced +
                ", refUuId=" + refUuId +
                ", roundScore=" + roundScore +
                ", kickedBy=" + kickedBy +
                ", snappedBy=" + snappedBy +
                ", receivedBy=" + receivedBy +
                ", muffedBy=" + muffedBy +
                ", downedBy=" + downedBy +
                ", caughtBy=" + caughtBy +
                ", tackledBy=" + tackledBy +
                ", touchBackAt=" + touchBackAt +
                ", cloud=" + cloud +
                ", humidity=" + humidity +
                ", windChill=" + windChill +
                ", windDirection=" + windDirection +
                ", windSpeed=" + windSpeed +
                ", temperature=" + temperature +
                ", spottedAt=" + spottedAt +
                ", direction=" + direction +
                ", pushedBy=" + pushedBy +
                ", totalYardsGained=" + totalYardsGained +
                ", intendedReceiver=" + intendedReceiver +
                ", thrownTo=" + thrownTo +
                ", defensedBy=" + defensedBy +
                ", homeTeamStats=" + homeTeamStats +
                ", awayTeamStats=" + awayTeamStats +
                ", blockedBy=" + blockedBy +
                ", yardLine=" + yardLine +
                ", passedBy=" + passedBy +
                ", rushedBy=" + rushedBy +
                ", rushSpeed=" + rushSpeed +
                ", interceptedBy=" + interceptedBy +
                ", turfType=" + turfType +
                ", stadiumType=" + stadiumType +
                ", sackedBy=" + sackedBy +
                ", safetyBy=" + safetyBy +
                ", fumbledBy=" + fumbledBy +
                ", recoveredBy=" + recoveredBy +
                ", causedBy=" + causedBy +
                ", penaltyDistance=" + penaltyDistance +
                ", penaltyReason=" + penaltyReason +
                ", penaltyNoPlay=" + penaltyNoPlay +
                ", placedAt=" + placedAt +
                ", injuryPlayer=" + injuryPlayer +
                ", injuryReturn=" + injuryReturn +
                ", drivePlayInfo=" + drivePlayInfo +
                ", homePlayerStatsDef=" + homePlayerStatsDef +
                ", awayPlayerStatsDef=" + awayPlayerStatsDef +
                ", homePlayerStatsPass=" + homePlayerStatsPass +
                ", awayPlayerStatsPass=" + awayPlayerStatsPass +
                ", homePlayerStatsRush=" + homePlayerStatsRush +
                ", awayPlayerStatsRush=" + awayPlayerStatsRush +
                ", homePlayerStatsRec=" + homePlayerStatsRec +
                ", awayPlayerStatsRec=" + awayPlayerStatsRec +
                ", puntDistance=" + puntDistance +
                ", damage=" + damage +
                ", healthRemaining=" + healthRemaining +
                ", ranOutOfBoundsAt=" + ranOutOfBoundsAt +
                ", driveInfoStatus=" + driveInfoStatus +
                ", fieldGoalDistance=" + fieldGoalDistance +
                ", homeTimeOnIce=" + homeTimeOnIce +
                ", awayTimeOnIce=" + awayTimeOnIce +
                ", attackingPlayers=" + attackingPlayers +
                ", unavailablePlayersHome=" + unavailablePlayersHome +
                ", unavailablePlayersAway=" + unavailablePlayersAway +
                ", homePlayerStatsTotal=" + homePlayerStatsTotal +
                ", awayPlayerStatsTotal=" + awayPlayerStatsTotal +
                ", homePlayerStatsP1=" + homePlayerStatsP1 +
                ", awayPlayerStatsP1=" + awayPlayerStatsP1 +
                ", homePlayerStatsP2=" + homePlayerStatsP2 +
                ", awayPlayerStatsP2=" + awayPlayerStatsP2 +
                ", homePlayerStatsP3=" + homePlayerStatsP3 +
                ", awayPlayerStatsP3=" + awayPlayerStatsP3 +
                ", homePlayerStatsP4=" + homePlayerStatsP4 +
                ", awayPlayerStatsP4=" + awayPlayerStatsP4 +
                ", homePlayerStatsOt=" + homePlayerStatsOt +
                ", awayPlayerStatsOt=" + awayPlayerStatsOt +
                ", homeTimeOnCourt=" + homeTimeOnCourt +
                ", awayTimeOnCourt=" + awayTimeOnCourt +
                ", homePlayers=" + homePlayers +
                ", awayPlayers=" + awayPlayers +
                ", atBatNumber=" + atBatNumber +
                ", atBatPitchNumber=" + atBatPitchNumber +
                ", batBallDistance=" + batBallDistance +
                ", batBallX=" + batBallX +
                ", batBallY=" + batBallY +
                ", extraInfoKabaddi=" + extraInfoKabaddi +
                ", fieldingPlayers=" + fieldingPlayers +
                ", preliminaryMatchStatistics=" + preliminaryMatchStatistics +
                ", actualMatchStatistics=" + actualMatchStatistics +
                ", homeTeamStatsTotal=" + homeTeamStatsTotal +
                ", awayTeamStatsTotal=" + awayTeamStatsTotal +
                ", homeTeamStatsP1=" + homeTeamStatsP1 +
                ", awayTeamStatsP1=" + awayTeamStatsP1 +
                ", homeTeamStatsP2=" + homeTeamStatsP2 +
                ", awayTeamStatsP2=" + awayTeamStatsP2 +
                ", homeTeamStatsP3=" + homeTeamStatsP3 +
                ", awayTeamStatsP3=" + awayTeamStatsP3 +
                ", homeTeamStatsP4=" + homeTeamStatsP4 +
                ", awayTeamStatsP4=" + awayTeamStatsP4 +
                ", homeTeamStatsOt=" + homeTeamStatsOt +
                ", awayTeamStatsOt=" + awayTeamStatsOt +
                ", freeKickReason=" + freeKickReason +
                ", foulTypeDescriptor=" + foulTypeDescriptor +
                ", foulTypeQualifier=" + foulTypeQualifier +
                '}';
    }

    protected void setBallSpotting(Boolean ballSpotting) {
        this.ballSpotting = ballSpotting;
    }

    protected void setDenies(Integer denies) {
        this.denies = denies;
    }

    protected void setEarlyBetstartType(Integer earlyBetstartType) {
        this.earlyBetstartType = earlyBetstartType;
    }

    protected void setHeroId(Integer heroId) {
        this.heroId = heroId;
    }

    protected void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    protected void setKillsAway(Integer killsAway) {
        this.killsAway = killsAway;
    }

    protected void setKillsHome(Integer killsHome) {
        this.killsHome = killsHome;
    }

    protected void setExtraInfoCsGo(String extraInfoCsGo) {
        this.extraInfoCsGo = extraInfoCsGo;
    }

    protected void setMapNumber(Integer mapNumber) {
        this.mapNumber = mapNumber;
    }

    protected void setMapScore(String mapScore) {
        this.mapScore = mapScore;
    }

    protected void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    protected void setAutomatic(Integer automatic) {
        this.automatic = automatic;
    }

    protected void setBallNumber(String ballNumber) {
        this.ballNumber = ballNumber;
    }

    protected void setBalls(Integer balls) {
        this.balls = balls;
    }

    protected void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    protected void setBreakScore(String breakScore) {
        this.breakScore = breakScore;
    }

    protected void setCorrectedFrom(Integer correctedFrom) {
        this.correctedFrom = correctedFrom;
    }

    protected void setCorrectedTo(Integer correctedTo) {
        this.correctedTo = correctedTo;
    }

    protected void setDismissalsInInnings(Integer dismissalsInInnings) { this.dismissalsInInnings = dismissalsInInnings; }

    protected void setEndScore(String endScore) {
        this.endScore = endScore;
    }

    protected void setErrors(Integer errors) {
        this.errors = errors;
    }

    protected void setExtraInfo(Long extraInfo) {
        this.extraInfo = extraInfo;
    }

    protected void setExtraInfoBaseball(String extraInfoBaseball) {
        this.extraInfoBaseball = extraInfoBaseball;
    }

    protected void setExtraInfoBasketball(String extraInfoBasketball) { this.extraInfoBasketball = extraInfoBasketball; }

    protected void setSpot(String spot) { this.spot = spot; }

    protected void setExtraInfoBowls(String extraInfoBowls) {
        this.extraInfoBowls = extraInfoBowls;
    }

    protected void setExtraInfoCricket(String extraInfoCricket) {
        this.extraInfoCricket = extraInfoCricket;
    }

    protected void setExtraInfoDarts(String extraInfoDarts) {
        this.extraInfoDarts = extraInfoDarts;
    }

    protected void setExtraInfoFootball(String extraInfoFootball) {
        this.extraInfoFootball = extraInfoFootball;
    }

    protected void setExtraInfoHandball(String extraInfoHandball) {
        this.extraInfoHandball = extraInfoHandball;
    }

    protected void setExtraInfoIceHockey(String extraInfoIceHockey) {
        this.extraInfoIceHockey = extraInfoIceHockey;
    }

    protected void setExtraInfoSnooker(String extraInfoSnooker) {
        this.extraInfoSnooker = extraInfoSnooker;
    }

    protected void setExtraInfoVolleyball(String extraInfoVolleyball) { this.extraInfoVolleyball = extraInfoVolleyball; }

    protected void setFrameNumber(Integer frameNumber) {
        this.frameNumber = frameNumber;
    }

    protected void setFrameScore(String frameScore) {
        this.frameScore = frameScore;
    }

    protected void setGameNumber(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }

    protected void setGameScore(String gameScore) {
        this.gameScore = gameScore;
    }

    protected void setId(long id) {
        this.id = id;
    }

    protected void setInfo(String info) {
        this.info = info;
    }

    protected void setInningHalf(Inning inningHalf) {
        this.inningHalf = inningHalf;
    }

    protected void setLegScore(String legScore) {
        this.legScore = legScore;
    }

    protected void setMatchScore(String matchScore) {
        this.matchScore = matchScore;
    }

    protected void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    protected void setOuts(Integer outs) {
        this.outs = outs;
    }

    protected void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    protected void setPeriodScore(String periodScore) {
        this.periodScore = periodScore;
    }

    protected void setPitchCount(Integer pCount) {
        this.pitchCount = pCount;
    }

    protected void setPlayer1Id(Integer player1Id) {
        this.player1Id = player1Id;
    }

    protected void setPlayer2Id(Integer player2Id) {
        this.player2Id = player2Id;
    }

    protected void setPlayer3Id(Integer player3Id) {
        this.player3Id = player3Id;
    }

    protected void setPlayer4Id(Integer player4Id) {
        this.player4Id = player4Id;
    }

    protected void setTippedTo(Integer tippedTo){
        this.tippedTo = tippedTo;
    }

    protected void setPosX(Integer posX) {
        this.posX = posX;
    }

    protected void setPosY(Integer posY) {
        this.posY = posY;
    }

    protected void setRemainingTimeInPeriod(String remainingTimeInPeriod) { this.remainingTimeInPeriod = remainingTimeInPeriod; }

    protected void setRuns(Integer runs) {
        this.runs = runs;
    }

    protected void setRunsInInnings(Integer runsInInnings) {
        this.runsInInnings = runsInInnings;
    }

    protected void setSecondScoreType(Integer secondScoreType) {
        this.secondScoreType = secondScoreType;
    }

    protected void setScoreTypeQualifier(String scoreTypeQualifier) {
        this.scoreTypeQualifier = scoreTypeQualifier;
    }

    protected void setHappenedAt(DateTime happenedAt) {
        this.happenedAt = happenedAt;
    }

    protected void setShotDistance(Double shotDistance) {
        this.shotDistance = shotDistance;
    }

    protected void setServer(Team server) {
        this.server = server;
    }

    protected void setServerTime(DateTime serverTime) { this.serverTime = serverTime; }

    protected void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    protected void setSetScore(String setScore) {
        this.setScore = setScore;
    }

    protected void setSide(Team side) {
        this.side = side;
    }

    protected void setStrikes(Integer strikes) {
        this.strikes = strikes;
    }

    protected void setType(EventType type) { this.type = type; }

    protected void setTypeId(int typeId) { this.typeId = typeId; }

    protected void setAssists(Integer assists) {
        this.assists = assists;
    }

    protected void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    protected void setErrorsAway(Integer errorsAway) {
        this.errorsAway = errorsAway;
    }

    protected void setErrorsHome(Integer errorsHome) {
        this.errorsHome = errorsHome;
    }

    protected void setExperience(Integer experience) {
        this.experience = experience;
    }

    protected void setFirstBaseLoaded(Boolean firstBaseLoaded) {
        this.firstBaseLoaded = firstBaseLoaded;
    }

    protected void setHeroesAliveAway(Integer heroesAliveAway) {
        this.heroesAliveAway = heroesAliveAway;
    }

    protected void setHeroesAliveHome(Integer heroesAliveHome) {
        this.heroesAliveHome = heroesAliveHome;
    }

    protected void setHitsAway(Integer hitsAway) {
        this.hitsAway = hitsAway;
    }

    protected void setHitsHome(Integer hitsHome) {
        this.hitsHome = hitsHome;
    }

    protected void setInningsScore(List<InningScoreEntity> inningsScore) {
        this.inningsScore = inningsScore;
    }

    protected void setKills(Integer kills) {
        this.kills = kills;
    }

    protected void setLastHits(Integer lastHits) {
        this.lastHits = lastHits;
    }

    protected void setLevel(Integer level) {
        this.level = level;
    }

    protected void setNetWorth(Integer netWorth) {
        this.netWorth = netWorth;
    }

    protected void setRunsAway(Integer runsAway) {
        this.runsAway = runsAway;
    }

    protected void setRunsHome(Integer runsHome) {
        this.runsHome = runsHome;
    }

    protected void setSecondBaseLoaded(Boolean secondBaseLoaded) {
        this.secondBaseLoaded = secondBaseLoaded;
    }

    protected void setThirdBaseLoaded(Boolean thirdBaseLoaded) {
        this.thirdBaseLoaded = thirdBaseLoaded;
    }

    protected void setWeaponId(Integer weaponId) {
        this.weaponId = weaponId;
    }

    protected void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    protected void setDragonkills(Integer dragonkills) {
        this.dragonkills = dragonkills;
    }

    protected void setBaronkills(Integer baronkills) {
        this.baronkills = baronkills;
    }

    protected void setInhibitorkills(Integer inhibitorkills) {
        this.inhibitorkills = inhibitorkills;
    }

    protected void setTowerkills(Integer towerkills) {
        this.towerkills = towerkills;
    }

    protected void setDoublekills(Integer doublekills) {
        this.doublekills = doublekills;
    }

    protected void setTriplekills(Integer triplekills) {
        this.triplekills = triplekills;
    }

    protected void setQuadrakills(Integer quadrakills) {
        this.quadrakills = quadrakills;
    }

    protected void setPentakills(Integer pentakills) {
        this.pentakills = pentakills;
    }

    protected void setMapName(String mapName) {
        this.mapName = mapName;
    }

    protected void setItemName(String itemName) {
        this.itemName = itemName;
    }

    protected void setConversionType (int value) { this.conversionType = Integer.valueOf(value); }

    protected void setTouchdownType(int value) {
        this.touchdownType = Integer.valueOf(value);
    }

    protected void setExtraInfoMoba(String extraInfoMoba) {
        this.extraInfoMoba = extraInfoMoba;
    }

    protected void setGoals(String goals) {
        this.goals = goals;
    }

    protected void setBehinds(String behinds) {
        this.behinds = behinds;
    }

    protected void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    protected void setUuId(String uuid) { this.uuId = uuid; }

    protected void setPitchType(Integer pitchType) { this.pitchType = pitchType; }

    protected void setPitchSpeed(Double pitchSpeed) { this.pitchSpeed = pitchSpeed; }

    protected void setBattingAverages(String battingAverages) { this.battingAverages = battingAverages; }

    protected void setPositionPlayerPitching(Boolean positionPlayerPitching) { this.positionPlayerPitching = positionPlayerPitching; }

    protected void setBatBallSpeed(Double batBallSpeed) { this.batBallSpeed = batBallSpeed; }

    protected void setBatBallAngle(Double batBallAngle) { this.batBallAngle = batBallAngle; }

    protected void setBatBallDirection(Double batBallDirection) { this.batBallDirection = batBallDirection; }

    protected void setStructure(String structure) { this.structure = structure; }

    protected void setMonsterType(String monsterType) { this.monsterType = monsterType; }

    protected void setDragonType(String dragonType) { this.dragonType = dragonType; }

    protected void setWardsPlaced(Integer wardsPlaced) { this.wardsPlaced = wardsPlaced; }

    protected void setChampionDamage(Integer championDamage) { this.championDamage = championDamage; }

    protected void setRefUuId(String refUuId) {
        this.refUuId = refUuId;
    }

    protected void setRoundScore(String roundScore) {
        this.roundScore = roundScore;
    }

    protected void setKickedBy(String kickedBy) {
        this.kickedBy = kickedBy;
    }

    protected void setSnappedBy(String snappedBy) {
        this.snappedBy = snappedBy;
    }

    protected void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    protected void setMuffedBy(String muffedBy) {
        this.muffedBy = muffedBy;
    }

    protected void setDownedBy(String downedBy) {
        this.downedBy = downedBy;
    }

    protected void setCaughtBy(String caughtBy) {
        this.caughtBy = caughtBy;
    }

    protected void setTackledBy(String tackledBy) {
        this.tackledBy = tackledBy;
    }

    protected void setTouchBackAt(String touchBackAt) {
        this.touchBackAt = touchBackAt;
    }

    protected void setCloud(String cloud) {
        this.cloud = cloud;
    }

    protected void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    protected void setWindChill(Integer windChill) {
        this.windChill = windChill;
    }

    protected void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    protected void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    protected void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    protected void setSpottedAt(String spottedAt) {
        this.spottedAt = spottedAt;
    }

    protected void setDirection(String direction) {
        this.direction = direction;
    }

    protected void setPushedBy(String pushedBy) {
        this.pushedBy = pushedBy;
    }

    protected void setTotalYardsGained(Integer totalYardsGained) {
        this.totalYardsGained = totalYardsGained;
    }

    protected void setIntendedReceiver(Integer intendedReceiver) {
        this.intendedReceiver = intendedReceiver;
    }

    protected void setThrownTo(String thrownTo) {
        this.thrownTo = thrownTo;
    }

    protected void setDefensedBy(String defensedBy) {
        this.defensedBy = defensedBy;
    }

    protected void setHomeTeamStats(String homeTeamStats) {
        this.homeTeamStats = homeTeamStats;
    }

    protected void setAwayTeamStats(String awayTeamStats) {
        this.awayTeamStats = awayTeamStats;
    }

    protected void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }

    protected void setYardLine(String yardLine) {
        this.yardLine = yardLine;
    }

    protected void setPassedBy(String passedBy) {
        this.passedBy = passedBy;
    }

    protected void setRushedBy(String rushedBy) {
        this.rushedBy = rushedBy;
    }

    protected void setRushSpeed(String rushSpeed) {
        this.rushSpeed = rushSpeed;
    }

    protected void setInterceptedBy(String interceptedBy) {
        this.interceptedBy = interceptedBy;
    }

    protected void setTurfType(String turfType) {
        this.turfType = turfType;
    }

    protected void setStadiumType(String stadiumType) {
        this.stadiumType = stadiumType;
    }

    protected void setSackedBy(String sackedBy) {
        this.sackedBy = sackedBy;
    }

    protected void setSafetyBy(String safetyBy) {
        this.safetyBy = safetyBy;
    }

    protected void setFumbledBy(String fumbledBy) {
        this.fumbledBy = fumbledBy;
    }

    protected void setRecoveredBy(String recoveredBy) {
        this.recoveredBy = recoveredBy;
    }

    protected void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    protected void setPenaltyDistance(String penaltyDistance) {
        this.penaltyDistance = penaltyDistance;
    }

    protected void setPenaltyReason(String penaltyReason) {
        this.penaltyReason = penaltyReason;
    }

    protected void setPenaltyNoPlay(String penaltyNoPlay) {
        this.penaltyNoPlay = penaltyNoPlay;
    }

    protected void setPlacedAt(String placedAt) {
        this.placedAt = placedAt;
    }

    protected void setInjuryPlayer(String injuryPlayer) {
        this.injuryPlayer = injuryPlayer;
    }

    protected void setInjuryReturn(String injuryReturn) {
        this.injuryReturn = injuryReturn;
    }

    protected void setDrivePlayInfo(String drivePlayInfo) {
        this.drivePlayInfo = drivePlayInfo;
    }

    protected void setHomePlayerStatsDef(String homePlayerStatsDef) {
        this.homePlayerStatsDef = homePlayerStatsDef;
    }

    protected void setAwayPlayerStatsDef(String awayPlayerStatsDef) {
        this.awayPlayerStatsDef = awayPlayerStatsDef;
    }

    protected void setHomePlayerStatsPass(String homePlayerStatsPass) {
        this.homePlayerStatsPass = homePlayerStatsPass;
    }

    protected void setAwayPlayerStatsPass(String awayPlayerStatsPass) {
        this.awayPlayerStatsPass = awayPlayerStatsPass;
    }

    protected void setHomePlayerStatsRush(String homePlayerStatsRush) {
        this.homePlayerStatsRush = homePlayerStatsRush;
    }

    protected void setAwayPlayerStatsRush(String awayPlayerStatsRush) {
        this.awayPlayerStatsRush = awayPlayerStatsRush;
    }

    protected void setHomePlayerStatsRec(String homePlayerStatsRec) {
        this.homePlayerStatsRec = homePlayerStatsRec;
    }

    protected void setAwayPlayerStatsRec(String awayPlayerStatsRec) {
        this.awayPlayerStatsRec = awayPlayerStatsRec;
    }

    protected void setPuntDistance(String puntDistance) { this.puntDistance = puntDistance; }

    protected void setDamage(Integer damage) {
        this.damage = damage;
    }

    protected void setHealthRemaining(Integer healthRemaining) {
        this.healthRemaining = healthRemaining;
    }

    protected void setRanOutOfBoundsAt(String ranOutOfBoundsAt) {
        this.ranOutOfBoundsAt = ranOutOfBoundsAt;
    }

    protected void setDriveInfoStatus(String driveInfoStatus) {
        this.driveInfoStatus = driveInfoStatus;
    }

    protected void setFieldGoalDistance(String fieldGoalDistance) {
        this.fieldGoalDistance = fieldGoalDistance;
    }

    protected void setHomeTimeOnIce(String homeTimeOnIce) {
        this.homeTimeOnIce = homeTimeOnIce;
    }

    protected void setAwayTimeOnIce(String awayTimeOnIce) {
        this.awayTimeOnIce = awayTimeOnIce;
    }

    protected void setAttackingPlayers(String attackingPlayers) {
        this.attackingPlayers = attackingPlayers;
    }

    protected void setUnavailablePlayersHome(String unavailablePlayersHome) {
        this.unavailablePlayersHome = unavailablePlayersHome;
    }

    protected void setUnavailablePlayersAway(String unavailablePlayersAway) {
        this.unavailablePlayersAway = unavailablePlayersAway;
    }

    protected void setHomePlayerStatsTotal(String homePlayerStatsTotal) {
        this.homePlayerStatsTotal = homePlayerStatsTotal;
    }

    protected void setAwayPlayerStatsTotal(String awayPlayerStatsTotal) {
        this.awayPlayerStatsTotal = awayPlayerStatsTotal;
    }

    protected void setHomePlayerStatsP1(String homePlayerStatsP1) {
        this.homePlayerStatsP1 = homePlayerStatsP1;
    }

    protected void setAwayPlayerStatsP1(String awayPlayerStatsP1) {
        this.awayPlayerStatsP1 = awayPlayerStatsP1;
    }

    protected void setHomePlayerStatsP2(String homePlayerStatsP2) {
        this.homePlayerStatsP2 = homePlayerStatsP2;
    }

    protected void setAwayPlayerStatsP2(String awayPlayerStatsP2) {
        this.awayPlayerStatsP2 = awayPlayerStatsP2;
    }

    protected void setHomePlayerStatsP3(String homePlayerStatsP3) {
        this.homePlayerStatsP3 = homePlayerStatsP3;
    }

    protected void setAwayPlayerStatsP3(String awayPlayerStatsP3) {
        this.awayPlayerStatsP3 = awayPlayerStatsP3;
    }

    protected void setHomePlayerStatsP4(String homePlayerStatsP4) {
        this.homePlayerStatsP4 = homePlayerStatsP4;
    }

    protected void setAwayPlayerStatsP4(String awayPlayerStatsP4) {
        this.awayPlayerStatsP4 = awayPlayerStatsP4;
    }

    protected void setHomePlayerStatsOt(String homePlayerStatsOt) {
        this.homePlayerStatsOt = homePlayerStatsOt;
    }

    protected void setAwayPlayerStatsOt(String awayPlayerStatsOt) {
        this.awayPlayerStatsOt = awayPlayerStatsOt;
    }

    protected void setHomeTimeOnCourt(String homeTimeOnCourt) {
        this.homeTimeOnCourt = homeTimeOnCourt;
    }

    protected void setAwayTimeOnCourt(String awayTimeOnCourt) {
        this.awayTimeOnCourt = awayTimeOnCourt;
    }

    protected void setHomePlayers(String homePlayers) {
        this.homePlayers = homePlayers;
    }

    protected void setAwayPlayers(String awayPlayers) {
        this.awayPlayers = awayPlayers;
    }

    protected void setAtBatNumber(Integer atBatNumber) {
        this.atBatNumber = atBatNumber;
    }

    protected void setAtBatPitchNumber(Integer atBatPitchNumber) {
        this.atBatPitchNumber = atBatPitchNumber;
    }

    protected void setBatBallDistance(Double batBallDistance) {
        this.batBallDistance = batBallDistance;
    }

    protected void setBatBallX(Double batBallX) {
        this.batBallX = batBallX;
    }

    protected void setBatBallY(Double batBallY) {
        this.batBallY = batBallY;
    }

    protected void setExtraInfoKabaddi(String extraInfoKabaddi) {
        this.extraInfoKabaddi = extraInfoKabaddi;
    }

    protected void setFieldingPlayers(String fieldingPlayers) {
        this.fieldingPlayers = fieldingPlayers;
    }

    protected void setPreliminaryMatchStatistics(String preliminaryMatchStatistics) {
        this.preliminaryMatchStatistics = preliminaryMatchStatistics;
    }

    protected void setActualMatchStatistics(String actualMatchStatistics) {
        this.actualMatchStatistics = actualMatchStatistics;
    }

    protected void setHomeTeamStatsTotal(String homeTeamStatsTotal) {
        this.homeTeamStatsTotal = homeTeamStatsTotal;
    }

    protected void setAwayTeamStatsTotal(String awayTeamStatsTotal) {
        this.awayTeamStatsTotal = awayTeamStatsTotal;
    }

    protected void setHomeTeamStatsP1(String homeTeamStatsP1) {
        this.homeTeamStatsP1 = homeTeamStatsP1;
    }

    protected void setAwayTeamStatsP1(String awayTeamStatsP1) {
        this.awayTeamStatsP1 = awayTeamStatsP1;
    }

    protected void setHomeTeamStatsP2(String homeTeamStatsP2) {
        this.homeTeamStatsP2 = homeTeamStatsP2;
    }

    protected void setAwayTeamStatsP2(String awayTeamStatsP2) {
        this.awayTeamStatsP2 = awayTeamStatsP2;
    }

    protected void setHomeTeamStatsP3(String homeTeamStatsP3) {
        this.homeTeamStatsP3 = homeTeamStatsP3;
    }

    protected void setAwayTeamStatsP3(String awayTeamStatsP3) {
        this.awayTeamStatsP3 = awayTeamStatsP3;
    }

    protected void setHomeTeamStatsP4(String homeTeamStatsP4) {
        this.homeTeamStatsP4 = homeTeamStatsP4;
    }

    protected void setAwayTeamStatsP4(String awayTeamStatsP4) {
        this.awayTeamStatsP4 = awayTeamStatsP4;
    }

    protected void setHomeTeamStatsOt(String homeTeamStatsOt) {
        this.homeTeamStatsOt = homeTeamStatsOt;
    }

    protected void setAwayTeamStatsOt(String awayTeamStatsOt) {
        this.awayTeamStatsOt = awayTeamStatsOt;
    }

    protected void setFreeKickReason(Integer freeKickReason) {
        this.freeKickReason = freeKickReason;
    }

    protected void setFoulTypeDescriptor(Integer foulTypeDescriptor) {
        this.foulTypeDescriptor = foulTypeDescriptor;
    }

    protected void setFoulTypeQualifier(String foulTypeQualifier) {
        this.foulTypeQualifier = foulTypeQualifier;
    }
}
