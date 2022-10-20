package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.common.classes.CommonUtils;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.feed.common.entities.HomeAway;
import com.sportradar.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.sdk.feed.livescout.enums.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.incoming.livescout.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

@SuppressWarnings("JavaDoc")
public class JaxbLiveScoutEntityFactoryHelper {

    public static AttributeEntity buildAttribute(com.sportradar.sdk.proto.dto.incoming.livescout.Attribute attribute) {
        AttributeEntity result = new AttributeEntity();
        result.setType(attribute.getType());
        result.setTypeId(attribute.getTypeid());
        result.setValue(attribute.getValue());
        result.setValueId(attribute.getValueid());
        return result;
    }

    public static LineupsEntity buildLineupsEntity(Lineups lineups) throws InvalidEntityException {
        LineupsEntity result = new LineupsEntity(new HashMap<String, String>());
        result.setMatchId(lineups.getMatchid());
        if (lineups.getPlayer() != null) {
            List<PlayerEntity> players = new ArrayList<>(lineups.getPlayer().size());
            for (Player player : lineups.getPlayer()) {
                players.add(buildPlayerEntity(player));
            }
            result.setPlayers(players);
        }
        List<Manager> managerList = lineups.getManager();
        if (managerList != null) {
            List<ManagerEntity> managers = new ArrayList<>(managerList.size());
            for (Manager manager : managerList) {
                managers.add(buildManagerEntity(manager));
            }
            result.setManagers(managers);
        }
        List<Teamofficial> teamOfficialList = lineups.getTeamofficial();
        if (teamOfficialList != null) {
            List<TeamOfficialEntity> teamOfficials = new ArrayList<>(teamOfficialList.size());
            for (Teamofficial teamofficial : teamOfficialList) {
                teamOfficials.add(buildTeamOfficial(teamofficial));
            }
            result.setTeamOfficials(teamOfficials);
        }
        return result;
    }

    public static ManagerEntity buildManagerEntity(Manager manager) throws InvalidEntityException {
        ManagerEntity result = new ManagerEntity();
        result.setName(manager.getName());
        result.setId(manager.getId());
        String teamString = String.valueOf(manager.getTeam());
        try {
            result.setTeam(Team.getTeamFromLiteralValue(teamString));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Manager.getTeam()", teamString);
        }
        return null;
    }

    public static MatchBookingEntity buildMatchBookingEntity(Bookmatch bookMatch) throws InvalidEntityException {
        MatchBookingEntity result = new MatchBookingEntity(new HashMap<String, String>());
        result.setMatchId(bookMatch.getMatchid());
        result.setMessage(bookMatch.getMessage());
        try {
            result.setResult(BookMatchResult.getBookMatchResultFromLiteralValue(bookMatch.getResult()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Bookmatch.getScore()", bookMatch.getResult());
        }
        return result;
    }

    public static MatchDataEntity buildMatchDataEntity(Matchdata matchdata) {
        MatchDataEntity result = new MatchDataEntity(new HashMap<String, String>());
        result.setMatchId(matchdata.getMatchid());
        result.setMatchTime(matchdata.getMtime());
        result.setRemainingTimeInPeriod(matchdata.getRemainingtimeperiod());
        return result;
    }

    public static MatchHeaderEntity buildMatchHeaderEntity(Match match) throws InvalidEntityException {
        MatchHeaderEntity result = new MatchHeaderEntity();
        if (match.getActive() != null) {
            result.setActive(CommonUtils.intToBoolean(match.getActive()));
        }
        try {
            result.setBetStatus(MatchBetStatus.getMatchBetStatusFromLiteralValue(match.getBetstatus()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getBetstatus()", match.getBetstatus());
        }
        result.setBooked(CommonUtils.integerToBoolean(match.getBooked()));
        result.setConnectionStatus(CommonUtils.integerToBoolean(match.getConnectionstatus()));
        try {
            result.setCoveredFrom(Coverage.getCoverageFromLiteralValue(match.getCoveredfrom()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getCoveredfrom()", match.getCoveredfrom());
        }
        result.setDeepCoverage(CommonUtils.integerToBoolean(match.getDc()));
        result.setDistance(match.getDistance());
        result.setExtraInfo(match.getExtrainfo());
        try {
            result.setFeedType(ScoutFeedType.getScoutFeedTypeFromLiteralValue(match.getFeedtype()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getFeedtype()", match.getFeedtype());
        }
        try {
            result.setFirstServe(Team.getTeamFromLiteralValue(match.getFirstserve()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getFirstserve()", match.getFirstserve());
        }
        try {
            result.setFirstServeGoldenSet(Team.getTeamFromLiteralValue(match.getFirstservegoldenset()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getFirstservegoldenset()", match.getFirstservegoldenset());
        }
        try {
            result.setFirstServeTieBreak(Team.getTeamFromLiteralValue(match.getFirstservetiebreak()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getFirstServeTieBreak()", match.getFirstservetiebreak());
        }
        result.setMatchId(match.getMatchid());
        result.setMatchTime(match.getMatchtime());
        result.setNumberOfSets(match.getNumberofsets());
        result.setSetLimit(match.getSetlimit());
        if (match.getSex() != null) {
            try {
                result.setSex(Sex.getSexFromLiteralValue(match.getSex().toString()));
            } catch (UnknownEnumException e) {
                throw new InvalidEntityException(e, "Match.getSex()", match.getSex().toString());
            }
        }

        result.setSt1Id(match.getSt1Id());
        result.setSt2Id(match.getSt2Id());

        if (match.getStart() != null) {
            result.setStart(CommonUtils.fromTimestamp(match.getStart(), DateTimeZone.getDefault()));
        }

        if (match.getT1Id() != null) {
            result.setTeam1(new IdNameTuple(match.getT1Id(), match.getT1Name()));

            if (match.getT1Namenatural() != null) {
                result.setTeam1Neutral(new IdNameTuple(match.getT1Id(), match.getT1Namenatural()));
            }
        }

        if (match.getT2Id() != null) {
            result.setTeam2(new IdNameTuple(match.getT2Id(), match.getT2Name()));

            if (match.getT2Namenatural() != null) {
                result.setTeam2Neutral(new IdNameTuple(match.getT2Id(), match.getT2Namenatural()));
            }
        }

        result.setTeam1Abbreviation(match.getT1Abbr());
        result.setTeam2Abbreviation(match.getT2Abbr());
        result.setTeam1Division(match.getT1Division());
        result.setTeam2Division(match.getT2Division());
        result.setTieBreakLastSet(CommonUtils.integerToBoolean(match.getTiebreaklastset()));
        result.setTimeRunning(CommonUtils.integerToBoolean(match.getTimerunning()));
        try {
            result.setWonJumpBall(Team.getTeamFromLiteralValue(match.getWonjumpball()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getWonJumpBall()", match.getWonjumpball());
        }
        result.setSourceId(match.getSourceid());
        result.setCoverageStatusId(match.getCoveragestatusid());
        result.setDevice(match.getDevice());
        result.setSportId(match.getSportid());
        result.setExtMatchId(match.getExtmatchid());
        result.setVar(match.getVar());
        Byte teamMatch = match.getTeammatch();
        if(teamMatch != null)
        {
            result.setIsTeamMatch(teamMatch != 0);
        }
        result.setTeamMatchId(match.getTeammatchid());
        Byte isCancelled = match.getIscancelled();
        if(isCancelled != null)
        {
            result.setIsCancelled(isCancelled != 0);
        }

        result.setSTime(match.getStime());

        return result;
    }

    public static MatchListEntity buildMatchListEntity(Matchlist matchList) throws InvalidEntityException {
        checkNotNull(matchList.getMatch());
        MatchListEntity result = new MatchListEntity(new HashMap<String, String>());
        ArrayList<MatchUpdateEntity> matches = new ArrayList<>(matchList.getMatch().size());
        for (Match match : matchList.getMatch()) {
            matches.add(buildMatchUpdateEntity(match));
        }
        result.setMatches(matches);
        return result;
    }

    public static MatchListUpdateEntity buildMatchListUpdateEntity(Matchlistupdate matchListUpdate) throws
                                                                                                    InvalidEntityException {
        MatchListUpdateEntity result = new MatchListUpdateEntity(new HashMap<String, String>());
        ArrayList<MatchUpdateEntity> matches = new ArrayList<>(matchListUpdate.getMatch().size());
        for (Match match : matchListUpdate.getMatch()) {
            matches.add(buildMatchUpdateEntity(match));
        }
        result.setMatches(matches);
        return result;
    }

    public static MatchRoleEntity buildMatchRole(Matchrole matchrole) {
        MatchRoleEntity result = new MatchRoleEntity();
        result.setDescription(matchrole.getDescription());
        result.setId(matchrole.getId());
        return result;
    }

    public static MatchStopEntity buildMatchStopEntity(Matchstop matchStop) {
        MatchStopEntity result = new MatchStopEntity(new HashMap<String, String>());
        result.setMatchId(matchStop.getMatchid());
        result.setReason(matchStop.getReason());
        return result;
    }

    public static MatchUpdateEntity buildMatchUpdateEntity(Match match) throws InvalidEntityException {
        MatchUpdateEntity result = new MatchUpdateEntity(new HashMap<String, String>());
        result.setMatchHeader(buildMatchHeaderEntity(match));
        HashMap<String, HomeAway<Double>> scoresOld = new HashMap<>();
        ArrayList<ScoreEntity> scores = new ArrayList<>();
        ArrayList<ScoutEventEntity> eventsList = new ArrayList<>();
        for (IncomingMessage item : match.getStatusOrScoreOrRed()) {
            switch (item.getClass().getSimpleName()) {
                case "Attacks":
                    Attacks attacks = (Attacks) item;
                    result.setAttacks(new HomeAway<>(attacks.getT1(), attacks.getT2()));
                    break;
                case "Black":
                    Black black = (Black) item;
                    result.setBlackCards(new HomeAway<>(black.getT1(), black.getT2()));
                    break;
                case "Category":
                    Category category = (Category) item;
                    result.setCategory(new IdNameTuple(category.getId(), category.getName()));
                    break;
                case "Corners":
                    Corners corners = (Corners) item;
                    result.setCorners(new HomeAway<>(corners.getT1(), corners.getT2()));
                    break;
                case "Dangerousattacks":
                    Dangerousattacks da = (Dangerousattacks) item;
                    result.setDangerousAttacks(new HomeAway<>(da.getT1(), da.getT2()));
                    break;
                case "Directfoulsperiod":
                    Directfoulsperiod dfp = (Directfoulsperiod) item;
                    result.setDirectFoulsPeriod(new HomeAway<>(dfp.getT1(), dfp.getT2()));
                    break;
                case "Directfreekicks":
                    Directfreekicks dfk = (Directfreekicks) item;
                    result.setDirectFreeKicks(new HomeAway<>(dfk.getT1(), dfk.getT2()));
                    break;
                case "Court":
                    Court court = (Court) item;
                    result.setCourt(buildCourtEntity(court));
                    break;
                case "Events":
                    Events events = (Events) item;
                    if (events.getEvent() != null) {
                        // realloc now that we know the exact size
                        eventsList = new ArrayList<>(events.getEvent().size());
                        for (Event event : events.getEvent()) {
                            eventsList.add(buildScoutEventEntity(event));
                        }
                    }
                    break;
                case "Firstkickoffteam1Sthalf":
                    Firstkickoffteam1Sthalf k1st = (Firstkickoffteam1Sthalf) item;
                    Integer teamInt1 = k1st.getTeam();
                    Team teamk1 = null;
                    if (teamInt1 == 2) {
                        teamk1 = Team.AWAY;
                    } else if (teamInt1 == 1) {
                        teamk1 = Team.HOME;
                    }
                    result.setKickoffTeamFirstHalf(teamk1);
                    break;
                case "Firstkickoffteam2Ndhalf":
                    Firstkickoffteam2Ndhalf k2ns = (Firstkickoffteam2Ndhalf) item;
                    Integer teamInt2 = k2ns.getTeam();
                    Team teamk2 = null;
                    if (teamInt2 == 2) {
                        teamk2 = Team.AWAY;
                    } else if (teamInt2 == 1) {
                        teamk2 = Team.HOME;
                    }
                    result.setKickoffTeamSecondHalf(teamk2);
                    break;
                case "Firstkickoffteamot":
                    Firstkickoffteamot kiot = (Firstkickoffteamot) item;
                    Integer teamIntOt = kiot.getTeam();
                    Team teamOt = null;
                    if (teamIntOt == 2) {
                        teamOt = Team.AWAY;
                    } else if (teamIntOt == 1) {
                        teamOt = Team.HOME;
                    }
                    result.setKickoffTeamOt(teamOt);
                    break;
                case "Openingfaceoff1Stperiod":
                    Openingfaceoff1Stperiod of1p = (Openingfaceoff1Stperiod) item;
                    Integer teamIntof1p = of1p.getTeam();
                    Team teamof1p = null;
                    if (teamIntof1p == 2) {
                        teamof1p = Team.AWAY;
                    } else if (teamIntof1p == 1) {
                        teamof1p = Team.HOME;
                    } else if (teamIntof1p == 0) {
                        teamof1p = Team.NONE;
                    }
                    result.setOpeningFaceoff1StPeriod(teamof1p);
                    break;
                case "Openingfaceoff2Ndperiod":
                    Openingfaceoff2Ndperiod of2p = (Openingfaceoff2Ndperiod) item;
                    Integer teamIntof2p = of2p.getTeam();
                    Team teamof2p = null;
                    if (teamIntof2p == 2) {
                        teamof2p = Team.AWAY;
                    } else if (teamIntof2p == 1) {
                        teamof2p = Team.HOME;
                    } else if (teamIntof2p == 0) {
                        teamof2p = Team.NONE;
                    }
                    result.setOpeningFaceoff2NdPeriod(teamof2p);
                    break;
                case "Openingfaceoff3Rdperiod":
                    Openingfaceoff3Rdperiod of3p = (Openingfaceoff3Rdperiod) item;
                    Integer teamIntof3p = of3p.getTeam();
                    Team teamof3p = null;
                    if (teamIntof3p == 2) {
                        teamof3p = Team.AWAY;
                    } else if (teamIntof3p == 1) {
                        teamof3p = Team.HOME;
                    } else if (teamIntof3p == 0) {
                        teamof3p = Team.NONE;
                    }
                    result.setOpeningFaceoff3RdPeriod(teamof3p);
                    break;
                case "Openingfaceoffovertime":
                    Openingfaceoffovertime ofop = (Openingfaceoffovertime) item;
                    Integer teamIntofot = ofop.getTeam();
                    Team teamofot = null;
                    if (teamIntofot == 2) {
                        teamofot = Team.AWAY;
                    } else if (teamIntofot == 1) {
                        teamofot = Team.HOME;
                    } else if (teamIntofot == 0) {
                        teamofot = Team.NONE;
                    }
                    result.setOpeningFaceoffOvertime(teamofot);
                    break;
                case "Freekicks":
                    Freekicks fk = (Freekicks) item;
                    result.setFreeKicks(new HomeAway<>(fk.getT1(), fk.getT2()));
                    break;
                case "Freethrows":
                    Freethrows ft = (Freethrows) item;
                    result.setFreeThrows(new HomeAway<>(ft.getT1(), ft.getT2()));
                    break;
                case "Goalkeepersaves":
                    Goalkeepersaves gks = (Goalkeepersaves) item;
                    result.setGoalkeeperSaves(new HomeAway<>(gks.getT1(), gks.getT2()));
                    break;
                case "Goalkicks":
                    Goalkicks gk = (Goalkicks) item;
                    result.setGoalKicks(new HomeAway<>(gk.getT1(), gk.getT2()));
                    break;
                case "Injuries":
                    Injuries inj = (Injuries) item;
                    result.setInjuries(new HomeAway<>(inj.getT1(), inj.getT2()));
                    break;
                case "Innings":
                    Innings inn = (Innings) item;
                    result.setInnings(buildInningsEntity(inn));
                    break;
                case "Kickoffteam":
                    Kickoffteam kot = (Kickoffteam) item;
                    Integer teamInt = kot.getTeam();
                    Team team = null;
                    if (teamInt == 2) {
                        team = Team.AWAY;
                    } else if (teamInt == 1) {
                        team = Team.HOME;
                    }
                    result.setKickoffTeam(team);
                    break;
                case "Matchformat":
                    Matchformat mf = (Matchformat) item;
                    result.setMatchFormat(buildMatchformat(mf));
                    break;
                case "Offsides":
                    Offsides ofs = (Offsides) item;
                    result.setOffsides(new HomeAway<>(ofs.getT1(), ofs.getT2()));
                    break;
                case "Penalties":
                    Penalties pen = (Penalties) item;
                    result.setPenalties(new HomeAway<>(pen.getT1(), pen.getT2()));
                    break;
                case "Pitchconditions":
                    Pitchconditions pc = (Pitchconditions) item;
                    try {
                        result.setPitchConditions(PitchConditions.getPitchConditionsFromLiteralValue(pc.getName()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Pitchconditions.getName()", pc.getName());
                    }
                    break;
                case "Iceconditions":
                    Iceconditions ic = (Iceconditions) item;
                    try {
                        result.setIceConditions(IceConditions.getIceConditionsFromLiteralValue(ic.getName()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Iceconditions.getName()", ic.getName());
                    }
                    break;
                case "Possession":
                    Possession p = (Possession) item;
                    try {
                        result.setPossessionTeam(Team.getTeamFromLiteralValue(p.getTeam()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Possession.getTeam()", p.getTeam());
                    }
                    result.setPossession(new HomeAway<>(p.getT1(), p.getT2()));
                    break;
                case "Red":
                    Red red = (Red) item;
                    result.setRedCards(new HomeAway<>(red.getT1(), red.getT2()));
                    break;
                case "Score":
                    // Field score.score1 will be ignored
                    Score score = (Score) item;
                    scoresOld.put(score.getType(), new HomeAway<>(score.getT1(), score.getT2()));
                    scores.add(new ScoreEntity(score));
                    break;
                case "Serve":
                    Serve srv = (Serve) item;
                    try {
                        result.setServe(Team.getTeamFromLiteralValue(srv.getTeam()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Serve.getTeam()", srv.getTeam());
                    }
                    break;
                case "Shotsblocked":
                    Shotsblocked sb = (Shotsblocked) item;
                    result.setShotsBlocked(new HomeAway<>(sb.getT1(), sb.getT2()));
                    break;
                case "Shotsofftarget":
                    Shotsofftarget sot = (Shotsofftarget) item;
                    result.setShotsOffTarget(new HomeAway<>(sot.getT1(), sot.getT2()));
                    break;
                case "Shotsontarget":
                    Shotsontarget sont = (Shotsontarget) item;
                    result.setShotsOnTarget(new HomeAway<>(sont.getT1(), sont.getT2()));
                    break;
                case "Sport":
                    Sport sport = (Sport) item;
                    result.setSport(new IdNameTuple(sport.getId(), sport.getName()));
                    break;
                case "Status":
                    Status status = (Status) item;
                    try {
                        result.setMatchStatus(ScoutMatchStatus.getScoutMatchStatusFromLiteralValue(status.getName()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Status.getName()", status.getName());
                    }
                    result.setMatchStatusId(status.getId());
                    result.setMatchStatusStart(CommonUtils.fromTimestamp(status.getStart()));
                    break;
                case "Surfacetype":
                    Surfacetype st = (Surfacetype) item;
                    try {
                        result.setSurfaceType(SurfaceType.getSurfaceTypeFromLiteralValue(st.getName()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().SurfaceType.getName()", st.getName());
                    }
                    break;
                case "Suspensions":
                    Suspensions susp = (Suspensions) item;
                    try {
                        Team powerplay = Team.getTeamFromLiteralValue(susp.getPowerplay() + "");//avoiding nullpointer
                        result.setSuspensions(new SuspensionsEntity(susp.getT1(), susp.getT2(), powerplay));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Suspensions.getPowerplay()", susp.getPowerplay() + "");
                    }
                    break;
                case "Throwins":
                    Throwins thr = (Throwins) item;
                    result.setThrowins(new HomeAway<>(thr.getT1(), thr.getT2()));
                    break;
                case "Tiebreak":
                    Tiebreak tb = (Tiebreak) item;
                    result.setTieBreak(CommonUtils.integerToBoolean(tb.getValue()));
                    break;
                case "Tournament":
                    Tournament tour = (Tournament) item;
                    result.setTournament(new IdNameTuple(tour.getId(), tour.getName()));
                    break;
                case "Weatherconditions":
                    Weatherconditions w = (Weatherconditions) item;
                    try {
                        result.setWeatherConditions(WeatherConditions.getWeatherConditionsFromLiteralValue(w.getName()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().WeatherConditions.getName()", w.getName());
                    }
                    break;
                case "Yellow":
                    Yellow yellow = (Yellow) item;
                    result.setYellowCards(new HomeAway<>(yellow.getT1(), yellow.getT2()));
                    break;
                case "Scout": {
                    Scout scout = (Scout) item;
                    result.setScout(buildScoutEntity(scout));
                    break;
                }
                case "Gold":
                    Gold gold = (Gold) item;
                    result.setGold(new GoldEntity(gold));
                    break;
                case "Networth":
                    Networth networth = (Networth) item;
                    result.setNetWorth(new NetWorthEntity(networth));
                    break;
                case "Jerseys":
                    Jerseys jerseys = (Jerseys) item;
                    result.setJerseys(buildJerseysEntities(jerseys));
                    break;
                case "Goals":
                    Goals goals = (Goals) item;
                    result.setGoals(buildGoalsEntity(goals));
                    break;
                case "Behinds":
                    Behinds behinds = (Behinds) item;
                    result.setBehinds(buildBehindsEntity(behinds));
                    break;
                case "Matchproperties":
                    Matchproperties mp = (Matchproperties) item;
                    result.setMatchProperties(buildMatchProperties(mp));
                    break;
                case "Teams":
                    Teams teams = (Teams) item;
                    result.setMatchTeams(buildMatchTeams(teams));
                    break;
                case "Green":
                    Green green = (Green) item;
                    result.setGreenCards(new HomeAway<>(green.getT1(), green.getT2()));
                    break;
                default:
                    throw new InvalidEntityException("Match.getStatusOrScoreOrRed", "Unknown event", item.getClass().getSimpleName());
            }
        }
        result.setScore(scoresOld);
        result.setScores(scores);
        result.setEvents(eventsList);
        return result;
    }

    private static List<JerseyEntity> buildJerseysEntities(Jerseys jerseys) {
        List<JerseyEntity> result = new ArrayList<>();
        for (Jersey jersey : jerseys.getJersey()) {
            result.add(new JerseyEntity(jersey));
        }
        return result;
    }

    public static OddsSuggestionsEntity buildOddsSuggestionsEntity(OddsSuggestions oddsSuggestions) {
        OddsSuggestionsEntity result = new OddsSuggestionsEntity(new HashMap<String, String>());
        result.setMatchId(oddsSuggestions.getMatchid());
        ArrayList<ScoutOddsEntity> scoutOddsEntities = new ArrayList<>(oddsSuggestions.getOdds().size());
        for (Odds odd : oddsSuggestions.getOdds()) {
            scoutOddsEntities.add(buildScoutOddsEntity(odd));
        }
        result.setOdds(scoutOddsEntities);
        return result;
    }

    public static PlayerEntity buildPlayerEntity(Player player) throws InvalidEntityException {
        PlayerEntity result = new PlayerEntity(new HashMap<String, String>());
        result.setId(player.getId());
        result.setName(player.getName());
        result.setShirtNumber(player.getShirtnumber());
        result.setSubstitute(player.isSubstitute());
        try {
            result.setTeam(Team.getTeamFromLiteralValue(Integer.toString(player.getTeam())));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Player.getTeam()", String.valueOf(player.getTeam()));
        }
        result.setPosition(player.getPosition());
        if (player.getAttributes() != null && player.getAttributes().getAttribute() != null) {
            List<AttributeEntity> attributes = new ArrayList<>(player.getAttributes().getAttribute().size());
            for (com.sportradar.sdk.proto.dto.incoming.livescout.Attribute attribute : player.getAttributes().getAttribute()) {
                attributes.add(buildAttribute(attribute));
            }
            result.setAttributes(attributes);
        }
        if (player.getMatchroles() != null && player.getMatchroles().getMatchrole() != null) {
            List<MatchRoleEntity> matchRoles = new ArrayList<>(player.getMatchroles().getMatchrole().size());
            for (Matchrole matchrole : player.getMatchroles().getMatchrole()) {
                matchRoles.add(buildMatchRole(matchrole));
            }
            result.setMatchRoles(matchRoles);
        }
        result.setNickname(player.getNickname());
        result.setOrder(player.getOrder());
        return result;
    }

    public static ScoutEventEntity buildScoutEventEntity(Event event) throws InvalidEntityException {
        ScoutEventEntity result = new ScoutEventEntity();
        result.setAutomatic(event.getAutomatic());
        result.setBreakScore(event.getBreakscore());
        result.setCorrectedFrom(event.getCorrectedfrom());
        result.setCorrectedTo(event.getCorrectedto());
        result.setBallNumber(event.getBallnumber());
        result.setDismissalsInInnings(event.getDismissalsininnings());
        result.setEndScore(event.getEndscore());
        result.setExtraInfo(event.getExtrainfo());
        result.setExtraInfoBasketball(event.getExtrainfobasketball());
        result.setSpot(event.getSpot());
        result.setExtraInfoBowls(event.getExtrainfobowls());
        result.setExtraInfoCricket(event.getExtrainfocricket());
        result.setExtraInfoCsGo(event.getExtrainfocsgo());
        result.setExtraInfoMoba(event.getExtrainfomoba());
        result.setExtraInfoDarts(event.getExtrainfodarts());
        result.setExtraInfoFootball(event.getExtrainfofootball());
        result.setExtraInfoHandball(event.getExtrainfohandball());
        result.setExtraInfoIceHockey(event.getExtrainfoicehockey());
        result.setExtraInfoSnooker(event.getExtrainfosnooker());
        result.setExtraInfoVolleyball(event.getExtrainfovolleyball());
        result.setExtraInfoBaseball(event.getExtrainfobaseball());
        result.setFrameNumber(event.getFramenumber());
        result.setFrameScore(event.getFramescore());
        result.setGameNumber(event.getGamenumber());
        result.setGameScore(event.getGamescore());
        result.setId(event.getId());
        result.setInfo(event.getInfo());
        result.setLegScore(event.getLegscore());
        result.setMapNumber(event.getMapnumber());
        result.setMapScore(event.getMapscore());
        result.setMatchScore(event.getMatchscore());
        result.setPeriodScore(event.getPeriodscore());
        result.setBaseInfo(event.getBaseinfo());
        result.setRoundNumber(event.getRoundnumber());
        result.setRuns(event.getRuns());
        result.setBalls(event.getBalls());
        result.setStrikes(event.getStrikes());
        result.setOuts(event.getOuts());
        result.setPitchCount(event.getPcount());
        result.setErrors(event.getErrors());
        result.setMatchTime(event.getMtime());
        result.setPeriodNumber(event.getPeriodnumber());
        result.setPlayer1Id(event.getPlayer1());
        result.setPlayer2Id(event.getPlayer2());
        result.setPosX(event.getPosx());
        result.setPosY(event.getPosy());
        result.setRemainingTimeInPeriod(event.getRemainingtimeperiod());
        result.setRunsInInnings(event.getRunsininnings());
        result.setServerTime(CommonUtils.fromTimestamp(event.getStime()));
        result.setSetNumber(event.getSetnumber());
        result.setSetScore(event.getSetscore());
        try {
            if (event.getInninghalf() != null) {
                result.setInningHalf(Inning.getInningFromLiteralValue(event.getInninghalf()));
            }
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Event.getInninghalf()", event.getInninghalf());
        }
        try {
            if (event.getServer() != null) {
                result.setServer(Team.getTeamFromLiteralValue(event.getServer()));
            }
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Event.getServer()", event.getServer());
        }
        try {
            result.setSide(Team.getTeamFromLiteralValue(event.getSide()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Event.getSide()", event.getSide());
        }
        result.setTypeId(event.getType());
        result.setType(EventType.getEventTypeFromLiteralValue(Integer.toString(event.getType())));

        result.setSecondScoreType(event.getSecondscoretype());
        result.setScoreTypeQualifier(event.getScoretypequalifier());
        if(event.getHappenedat() != null) {
            result.setHappenedAt(CommonUtils.fromTimestamp(event.getHappenedat()));
        }
        result.setShotDistance(event.getShotdistance());
        result.setAssists(event.getAssists());
        result.setDeaths(event.getDeaths());
        result.setErrorsAway(event.getErrorsaway());
        result.setErrorsHome(event.getErrorshome());
        result.setExperience(event.getExperience());
        Byte firstBaseLoaded = event.getFirstbaseloaded();
        if (firstBaseLoaded != null) {
            result.setFirstBaseLoaded(firstBaseLoaded != 0);
        }
        Byte firstbasecondbaseloadedeloaded = event.getSecondbaseloaded();
        if (firstbasecondbaseloadedeloaded != null) {
            result.setSecondBaseLoaded(firstbasecondbaseloadedeloaded != 0);
        }
        Integer thirdbaseloaded = event.getThirdbaseloaded() == null ? null : event.getThirdbaseloaded().intValue();
        if (thirdbaseloaded != null) {
            result.setThirdBaseLoaded(thirdbaseloaded != 0);
        }
        result.setHeroesAliveAway(event.getHeroesaliveaway());
        result.setHeroesAliveHome(event.getHeroesalivehome());
        result.setHitsAway(event.getHitsaway());
        result.setHitsHome(event.getHitshome());
        result.setInningsScore(buildInningsScores(event.getInningscore()));
        result.setKills(event.getKills());
        result.setLastHits(event.getLasthits());
        result.setLevel(event.getLevel());
        result.setNetWorth(event.getNetworth());
        result.setRunsAway(event.getRunsaway());
        result.setRunsHome(event.getRunshome());
        result.setWeaponId(event.getWeaponid());
        result.setWeaponName(event.getWeaponname());
        result.setBallSpotting(event.isBallspotting());
        result.setEarlyBetstartType(event.getEarlybetstarttype());
        result.setHeroId(event.getHeroid());
        result.setHeroName(event.getHeroname());
        result.setKillsAway(event.getKillsaway());
        result.setKillsHome(event.getKillshome());
        result.setDragonkills(event.getDragonkills());
        result.setBaronkills(event.getBaronkills());
        result.setInhibitorkills(event.getInhibitorkills());
        result.setTowerkills(event.getTowerkills());
        result.setDoublekills(event.getDoublekills());
        result.setTriplekills(event.getTriplekills());
        result.setQuadrakills(event.getQuadrakills());
        result.setPentakills(event.getPentakills());
        result.setDenies(event.getDenies());
        result.setPlayer3Id(event.getPlayer3());
        result.setPlayer4Id(event.getPlayer4());
        result.setTippedTo(event.getTippedto());
        result.setMapName(event.getMapname());
        result.setItemName(event.getItemname());
        Byte conversionType = event.getConversiontype();
        if (conversionType != null) {
            result.setConversionType(Integer.valueOf(conversionType));
        }
        Byte touchdownType = event.getTouchdowntype();
        if (touchdownType != null) {
            result.setTouchdownType(Integer.valueOf(touchdownType));
        }
        if (event.getGoals() != null) {
            result.setGoals(event.getGoals());
        }
        if (event.getBehinds() != null) {
            result.setBehinds(event.getBehinds());
        }
        if(event.getMatchstatus() != null){
            result.setMatchStatus(event.getMatchstatus());
        }

        result.setUuId(event.getUuid());
        result.setPitchType(event.getPitchtype());
        result.setPitchSpeed(event.getPitchspeed());
        result.setBattingAverages(event.getBattingaverages());
        result.setPositionPlayerPitching(event.isPositionplayerpitching());
        result.setBatBallSpeed(event.getBatballspeed());
        result.setBatBallAngle(event.getBatballangle());
        result.setBatBallDirection(event.getBatballdirection());
        result.setStructure(event.getStructure());
        result.setMonsterType(event.getMonstertype());
        result.setDragonType(event.getDragontype());
        result.setWardsPlaced(event.getWardsplaced());
        result.setChampionDamage(event.getChampiondamage());

        result.setRefUuId(event.getRefuuid());
        result.setRoundScore(event.getRoundscore());
        result.setKickedBy(event.getKickedby());
        result.setSnappedBy(event.getSnappedby());
        result.setReceivedBy(event.getReceivedby());
        result.setMuffedBy(event.getMuffedby());
        result.setDownedBy(event.getDownedby());
        result.setCaughtBy(event.getCaughtby());
        result.setTackledBy(event.getTackledby());
        result.setTouchBackAt(event.getTouchbackat());
        result.setCloud(event.getCloud());
        result.setHumidity(event.getHumidity());
        result.setWindChill(event.getWindchill());
        result.setWindDirection(event.getWinddirection());
        result.setWindSpeed(event.getWindspeed());
        result.setTemperature(event.getTemperature());
        result.setSpottedAt(event.getSpottedat());
        result.setDirection(event.getDirection());
        result.setPushedBy(event.getPushedby());
        result.setTotalYardsGained(event.getTotalyardsgained());
        result.setIntendedReceiver(event.getIntendedreceiver());
        result.setThrownTo(event.getThrownto());
        result.setDefensedBy(event.getDefensedby());
        result.setHomeTeamStats(event.getHometeamstats());
        result.setAwayTeamStats(event.getAwayteamstats());
        result.setBlockedBy(event.getBlockedby());
        result.setYardLine(event.getYardline());
        result.setPassedBy(event.getPassedby());
        result.setRushedBy(event.getRushedby());
        result.setRushSpeed(event.getRushspeed());
        result.setInterceptedBy(event.getInterceptedby());
        result.setTurfType(event.getTurftype());
        result.setStadiumType(event.getStadiumtype());
        result.setSackedBy(event.getSackedby());
        result.setSafetyBy(event.getSafetyby());
        result.setFumbledBy(event.getFumbledby());
        result.setRecoveredBy(event.getRecoveredby());
        result.setCausedBy(event.getCausedby());
        result.setPenaltyDistance(event.getPenaltydistance());
        result.setPenaltyReason(event.getPenaltyreason());
        result.setPenaltyNoPlay(event.getPenaltynoplay());
        result.setPlacedAt(event.getPlacedat());
        result.setInjuryPlayer(event.getInjuryplayer());
        result.setInjuryReturn(event.getInjuryreturn());
        result.setDrivePlayInfo(event.getDriveplayinfo());
        result.setHomePlayerStatsDef(event.getHomeplayerstatsdef());
        result.setAwayPlayerStatsDef(event.getAwayplayerstatsdef());
        result.setHomePlayerStatsPass(event.getHomeplayerstatspass());
        result.setAwayPlayerStatsPass(event.getAwayplayerstatspass());
        result.setHomePlayerStatsRush(event.getHomeplayerstatsrush());
        result.setAwayPlayerStatsRush(event.getAwayplayerstatsrush());
        result.setHomePlayerStatsRec(event.getHomeplayerstatsrec());
        result.setAwayPlayerStatsRec(event.getAwayplayerstatsrec());
        result.setPuntDistance(event.getPuntdistance());
        result.setDamage(event.getDamage());
        result.setHealthRemaining(event.getHealthremaining());
        result.setRanOutOfBoundsAt(event.getRanoutofboundsat());
        result.setDriveInfoStatus(event.getDriveinfostatus());
        result.setFieldGoalDistance(event.getFieldgoaldistance());
        result.setHomeTimeOnIce(event.getHometimeonice());
        result.setAwayTimeOnIce(event.getAwaytimeonice());
        result.setAttackingPlayers(event.getAttackingplayers());
        result.setUnavailablePlayersHome(event.getUnavailableplayershome());
        result.setUnavailablePlayersAway(event.getUnavailableplayersaway());
        result.setHomePlayerStatsTotal(event.getHomeplayerstatstotal());
        result.setAwayPlayerStatsTotal(event.getAwayplayerstatstotal());
        result.setHomePlayerStatsP1(event.getHomeplayerstatsp1());
        result.setAwayPlayerStatsP1(event.getAwayplayerstatsp1());
        result.setHomePlayerStatsP2(event.getHomeplayerstatsp2());
        result.setAwayPlayerStatsP2(event.getAwayplayerstatsp2());
        result.setHomePlayerStatsP3(event.getHomeplayerstatsp3());
        result.setAwayPlayerStatsP3(event.getAwayplayerstatsp3());
        result.setHomePlayerStatsP4(event.getHomeplayerstatsp4());
        result.setAwayPlayerStatsP4(event.getAwayplayerstatsp4());
        result.setHomePlayerStatsOt(event.getHomeplayerstatsot());
        result.setAwayPlayerStatsOt(event.getAwayplayerstatsot());
        result.setHomeTimeOnCourt(event.getHometimeoncourt());
        result.setAwayTimeOnCourt(event.getAwaytimeoncourt());
        result.setHomePlayers(event.getHomeplayers());
        result.setAwayPlayers(event.getAwayplayers());
        if(event.getAtbatnumber() != null){
            result.setAtBatNumber(event.getAtbatnumber());
        }
        if(event.getAtbatpitchnumber() != null){
            result.setAtBatPitchNumber(event.getAtbatpitchnumber());
        }
        if(event.getBatballdistance() != null){
            result.setBatBallDistance(event.getBatballdistance());
        }
        if(event.getBatballx() != null){
            result.setBatBallX(event.getBatballx());
        }
        if(event.getBatbally() != null){
            result.setBatBallY(event.getBatbally());
        }
        result.setExtraInfoKabaddi(event.getExtrainfokabaddi());
        result.setFieldingPlayers(event.getFieldingplayers());
        result.setPreliminaryMatchStatistics(event.getPreliminarymatchstatistics());
        result.setActualMatchStatistics(event.getActualmatchstatistics());
        result.setHomeTeamStatsTotal(event.getHometeamstatstotal());
        result.setAwayTeamStatsTotal(event.getAwayteamstatstotal());
        result.setHomeTeamStatsP1(event.getHometeamstatsp1());
        result.setAwayTeamStatsP1(event.getAwayteamstatsp1());
        result.setHomeTeamStatsP2(event.getHometeamstatsp2());
        result.setAwayTeamStatsP2(event.getAwayteamstatsp2());
        result.setHomeTeamStatsP3(event.getHometeamstatsp3());
        result.setAwayTeamStatsP3(event.getAwayteamstatsp3());
        result.setHomeTeamStatsP4(event.getHometeamstatsp4());
        result.setAwayTeamStatsP4(event.getAwayteamstatsp4());
        result.setHomeTeamStatsOt(event.getHometeamstatsot());
        result.setAwayTeamStatsOt(event.getAwayteamstatsot());
        result.setFreeKickReason(event.getFreekickreason());
        result.setFoulTypeDescriptor(event.getFoultypedescriptor());
        result.setFoulTypeQualifier(event.getFoultypequalifier());

        return result;
    }

    public static ScoutOddsEntity buildScoutOddsEntity(Odds odds) {
        ScoutOddsEntity result = new ScoutOddsEntity();
        result.setAlsoOdds(odds.getAlsoOdds());
        result.setDescription(odds.getDescription());
        result.setGuthMatchId(odds.getGuthMatchId());
        result.setManualActive(CommonUtils.intToBoolean(odds.getManualActive()));
        result.setMatchId(odds.getMatchId());
        result.setSpecialOddsValue(odds.getSpecialOddsValue());
        result.setSubType(odds.getSubtype());
        result.setType(odds.getType());
        result.setValidDate(CommonUtils.fromTimestamp(odds.getValidDate()));
        if (odds.getOddsField() != null) {
            HashMap<String, ScoutOddsFieldEntity> oddsFields = new HashMap<>(odds.getOddsField().size());
            for (OddsField oddsField : odds.getOddsField()) {
                oddsFields.put(oddsField.getSide(), buildScoutOddsFieldEntity(oddsField));
            }
            result.setValues(oddsFields);
        }
        return result;
    }

    public static ScoutOddsFieldEntity buildScoutOddsFieldEntity(OddsField oddsField) {
        ScoutOddsFieldEntity result = new ScoutOddsFieldEntity();
        result.setDescription(oddsField.getDescription());
        result.setSide(oddsField.getSide());
        if (oddsField.getValue() != null) {
            result.setValue(oddsField.getValue().toString());
        }
        return result;
    }

    public static ServerTimeEntity buildServerTimeEntity(Servertime servertime) {
        ServerTimeEntity entity = new ServerTimeEntity(new HashMap<String, String>());
        entity.setServerTime(new DateTime(servertime.getValue()));
        return entity;
    }

    public static TeamOfficialEntity buildTeamOfficial(Teamofficial teamofficial) throws InvalidEntityException {
        TeamOfficialEntity result = new TeamOfficialEntity();
        result.setName(teamofficial.getName());
        result.setId(teamofficial.getId());
        String teamString = String.valueOf(teamofficial.getTeam());
        try {
            result.setTeam(Team.getTeamFromLiteralValue(teamString));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "TeamOfficial.getTeam()", teamString);
        }
        return null;
    }

    private static CourtEntity buildCourtEntity(Court court) {
        if (court == null) {
            return null;
        }
        CourtEntity result = new CourtEntity();
        result.setCourtSeq(court.getCourtseq());
        result.setId(court.getId());
        result.setName(court.getName());
        return result;
    }

    private static List<InningScoreEntity> buildInningsScores(List<Inningscore> inningscores) {
        if (inningscores != null) {
            List<InningScoreEntity> result = new ArrayList<>();
            for (Inningscore inningscore : inningscores) {
                InningScoreEntity resultEntry = buildInningScore(inningscore);
                result.add(resultEntry);
            }
            return result;
        }
        return null;
    }

    private static InningScoreEntity buildInningScore(Inningscore inningscore) {
        InningScoreEntity result = new InningScoreEntity();
        result.setInning(inningscore.getInning());
        result.setScore(new HomeAway<>(inningscore.getHome(), inningscore.getAway()));
        return result;
    }

    private static ScoutEntity buildScoutEntity(Scout scout) {
        if (scout != null) {
            ScoutEntity result = new ScoutEntity();
            result.setId(scout.getId());
            return result;
        }
        return null;
    }

    private static InningsEntity buildInningsEntity(Innings inn) {
        if (inn != null) {
            InningsEntity result = new InningsEntity();
            result.setDismissals(inn.getDismissals());
            result.setRuns(inn.getRuns());
            result.setStatusId(inn.getStatusid());
            result.setStatusName(inn.getStatusname());
            return result;
        }
        return null;
    }

    private static List<FormatEntity> buildMatchformat(Matchformat mf) throws InvalidEntityException {
        if (mf != null && mf.getFormat() != null) {
            List<FormatEntity> result = new ArrayList<>();
            for (Format format : mf.getFormat()) {
                if (format != null) {
                    FormatEntity formatEntity = new FormatEntity();
                    try {
                        formatEntity.setFormatType(FormatType.getFormatTypeFromLiteralValue(format.getType()));
                    } catch (UnknownEnumException e) {
                        throw new InvalidEntityException(e, "Match.MatchFormat.Format.getType", format.getType());
                    }
                    formatEntity.setValue(format.getValue());
                    result.add(formatEntity);
                }
            }
            return result;
        }
        return null;
    }

    private static GoalsEntity buildGoalsEntity(Goals goals) {
        return new GoalsEntity(
                goals.getT1(),
                goals.getT2(),
                goals.getType()
        );
    }

    private static BehindsEntity buildBehindsEntity(Behinds behinds) {
        return new BehindsEntity(
                behinds.getT1(),
                behinds.getT2(),
                behinds.getType()
        );
    }

    private static List<MatchPropertyEntity> buildMatchProperties(Matchproperties properties) {
        if (properties != null && properties.getMatchproperty() != null) {
            List<MatchPropertyEntity> result = new ArrayList<>();
            for (com.sportradar.sdk.proto.dto.incoming.livescout.Matchproperty property : properties.getMatchproperty()) {
                if (property != null) {
                    MatchPropertyEntity matchPropertyEntity = new MatchPropertyEntity(property.getType(), property.getValue());
                    result.add(matchPropertyEntity);
                }
            }
            return result;
        }
        return null;
    }

    private static List<MatchTeamEntity> buildMatchTeams(Teams teams) throws InvalidEntityException {
        if (teams != null && teams.getTeam() != null) {
            List<MatchTeamEntity> result = new ArrayList<>();
            for (com.sportradar.sdk.proto.dto.incoming.livescout.Team team : teams.getTeam()) {
                if (team != null) {
                    MatchTeamEntity matchTeamEntity = new MatchTeamEntity(team);
                    result.add(matchTeamEntity);
                }
            }
            return result;
        }
        return null;
    }
}
