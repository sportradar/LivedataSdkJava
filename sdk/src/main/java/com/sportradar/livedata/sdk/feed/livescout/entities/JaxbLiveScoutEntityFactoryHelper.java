package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.classes.CommonUtils;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.feed.common.entities.HomeAway;
import com.sportradar.livedata.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.livedata.sdk.feed.livescout.enums.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import org.apache.commons.lang3.BooleanUtils;
import org.joda.time.DateTimeZone;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.sportradar.livedata.sdk.common.classes.Nulls.nte;

@SuppressWarnings("JavaDoc")
public class JaxbLiveScoutEntityFactoryHelper {

    public static AttributeEntity buildAttribute(com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Attribute attribute) {
        AttributeEntity result = new AttributeEntity();
        result.setType(attribute.getType());
        result.setTypeId(attribute.getTypeid());
        result.setValue(attribute.getValue());
        result.setValueId(attribute.getValueid());
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
        return result;
    }

    public static MatchHeaderEntity buildMatchHeaderEntity(Match match) throws InvalidEntityException {
        MatchHeaderEntity result = new MatchHeaderEntity();
        if (match.getActive() != null) {
            result.setActive(BooleanUtils.toBoolean(match.getActive()));
        }
        try {
            result.setBetStatus(MatchBetStatus.getMatchBetStatusFromLiteralValue(match.getBetstatus()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getBetstatus()", match.getBetstatus());
        }
        result.setBooked(BooleanUtils.toBooleanObject(match.getBooked()));
        result.setConnectionStatus(BooleanUtils.toBooleanObject(match.getConnectionstatus()));
        try {
            result.setCoveredFrom(Coverage.getCoverageFromLiteralValue(match.getCoveredfrom()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Match.getCoveredfrom()", match.getCoveredfrom());
        }
        result.setDeepCoverage(BooleanUtils.toBooleanObject(match.getDc()));
        result.setRts(BooleanUtils.toBooleanDefaultIfNull(match.isIsrts(), false));
        result.setDistance(match.getDistance());
        if (match.getTeamsreversed() != null) {
            try {
                result.setTeamsReversed(TeamsReversed.getTeamsReversedFromValue(match.getTeamsreversed()));
            } catch (UnknownEnumException e) {
                throw new InvalidEntityException(e, "Match.getTeamsreversed()", String.valueOf(match.getTeamsreversed()));
            }
        }
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
        result.setTieBreakLastSet(BooleanUtils.toBooleanObject(match.getTiebreaklastset()));
        result.setTimeRunning(BooleanUtils.toBooleanObject(match.getTimerunning()));
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
        if (teamMatch != null) {
            result.setTeamMatch(teamMatch != 0);
        }
        result.setTeamMatchId(match.getTeammatchid());
        Byte isCancelled = match.getIscancelled();
        if (isCancelled != null) {
            result.setCancelled(isCancelled != 0);
        }
        result.setVbpClassification(match.getVbpclassification());
        result.setSTime(match.getStime());
        result.setHomeState(match.getHomestate());
        result.setAwayState(match.getAwaystate());
        result.setVenue(match.getVenue());
        result.setRegion(match.getRegion());
        if (ScoutFeedType.FULL_PAGINATED.equals(result.getFeedType())) {
            result.setPagination(new PaginationEntity(match.getUuid(), match.getPage(), match.getTotalpages()));
        }
        result.setExpectedLatencyLevel(LatencyLevel.getLatencyLevelFromValue(match.getExpectedlatencylevelid()));

        return result;
    }

    public static MatchRoleEntity buildMatchRole(Matchrole matchrole) {
        MatchRoleEntity result = new MatchRoleEntity();
        result.setDescription(matchrole.getDescription());
        result.setId(matchrole.getId());
        return result;
    }

    public static void applyStatusOrScoreOrRed(MatchUpdateEntity result, IncomingMessage item) throws InvalidEntityException {
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
            case "Fouls":
                Fouls fouls = (Fouls) item;
                result.setFouls(new HomeAway<>(fouls.getT1(), fouls.getT2()));
                break;
            case "Court":
                Court court = (Court) item;
                result.setCourt(buildCourtEntity(court));
                break;
            case "Events":
                Events events = (Events) item;
                if (!nte(events.getEvent()).isEmpty()) {
                    // realloc now that we know the exact size
                    result.setEvents(new ArrayList<>(events.getEvent().size()));
                    for (Event event : events.getEvent()) {
                        result.getEvents().add(buildScoutEventEntity(event));
                    }
                }
                break;
            case "Firstkickoffteam1Sthalf":
                Firstkickoffteam1Sthalf k1st = (Firstkickoffteam1Sthalf) item;
                Team teamk1 = parseTeam(k1st.getTeam(), false);
                result.setKickoffTeamFirstHalf(teamk1);
                break;
            case "Firstkickoffteam2Ndhalf":
                Firstkickoffteam2Ndhalf k2ns = (Firstkickoffteam2Ndhalf) item;
                Team teamk2 = parseTeam(k2ns.getTeam(), false);
                result.setKickoffTeamSecondHalf(teamk2);
                break;
            case "Firstkickoffteamot":
                Firstkickoffteamot kiot = (Firstkickoffteamot) item;
                Team teamOt = parseTeam(kiot.getTeam(), false);
                result.setKickoffTeamOt(teamOt);
                break;
            case "Kickoffteam":
                Kickoffteam kot = (Kickoffteam) item;
                Team team = parseTeam(kot.getTeam(), false);
                result.setKickoffTeam(team);
                break;
            case "Openingfaceoff1Stperiod":
                Openingfaceoff1Stperiod of1p = (Openingfaceoff1Stperiod) item;
                Team teamof1p = parseTeam(of1p.getTeam(), true);
                result.setOpeningFaceoff1StPeriod(teamof1p);
                break;
            case "Openingfaceoff2Ndperiod":
                Openingfaceoff2Ndperiod of2p = (Openingfaceoff2Ndperiod) item;
                Team teamof2p = parseTeam(of2p.getTeam(), true);
                result.setOpeningFaceoff2NdPeriod(teamof2p);
                break;
            case "Openingfaceoff3Rdperiod":
                Openingfaceoff3Rdperiod of3p = (Openingfaceoff3Rdperiod) item;
                Team teamof3p = parseTeam(of3p.getTeam(), true);
                result.setOpeningFaceoff3RdPeriod(teamof3p);
                break;
            case "Openingfaceoffovertime":
                Openingfaceoffovertime ofop = (Openingfaceoffovertime) item;
                Team teamofot = parseTeam(ofop.getTeam(), true);
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
                addToList(buildInningsEntity((Innings) item), result::getInnings, result::setInnings);
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
                addToMap(score.getType(), new HomeAway<>(score.getT1(), score.getT2()), result::getScore, result::setScore);
                addToList(new ScoreEntity(score), result::getScores, result::setScores);
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
                    Team powerplay = Team.getTeamFromLiteralValue(String.valueOf(susp.getPowerplay()));
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
                result.setTieBreak(BooleanUtils.toBooleanObject(tb.getValue()));
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
            case "Subteam":
                addToList(new SubteamEntity((Subteam) item), result::getSubteams, result::setSubteams);
                break;
            case "Trycount":
                Trycount tryCount = (Trycount) item;
                try {
                    addToMap(
                            ScoutMatchStatus.getScoutMatchStatusFromLiteralValue(tryCount.getType()),
                            new HomeAway<>(tryCount.getT1(), tryCount.getT2()),
                            result::getTryCounts,
                            result::setTryCounts);
                } catch (UnknownEnumException e) {
                    throw new InvalidEntityException(e, "Match.getStatusOrScoreOrRed().Trycount.getType()", tryCount.getType());
                }
                break;
            case "Green":
                Green green = (Green) item;
                result.setGreenCards(new HomeAway<>(green.getT1(), green.getT2()));
                break;
            default:
                throw new InvalidEntityException("Match.getStatusOrScoreOrRed", "Unknown event", item.getClass().getSimpleName());
        }
    }

    private static <T> void addToList(T item, Supplier<List<T>> getter, Consumer<List<T>> setter) {
        List<T> list = getter.get();
        if (list == null) {
            list = new ArrayList<>();
            setter.accept(list);
        }
        list.add(item);
    }

    private static <K, T> void addToMap(K key, T item, Supplier<Map<K, T>> getter, Consumer<Map<K, T>> setter) {
        Map<K, T> map = getter.get();
        if (map == null) {
            map = new HashMap<>();
            setter.accept(map);
        }
        map.put(key, item);
    }

    //Do not know why it was done like this in a 1st place. Just had put common logic in one place.
    //Much easier would be to allow NONE everywhere and throw exception if value is unknown as it was done everywhere.
    private static Team parseTeam(Integer teamInt, boolean allowNone) {
        Team team = null;
        if (teamInt == 2) {
            team = Team.AWAY;
        } else if (teamInt == 1) {
            team = Team.HOME;
        } else if (teamInt == 0 && allowNone) {
            team = Team.NONE;
        }
        return team;
    }

    private static List<JerseyEntity> buildJerseysEntities(Jerseys jerseys) {
        List<JerseyEntity> result = new ArrayList<>();
        for (Jersey jersey : jerseys.getJersey()) {
            result.add(new JerseyEntity(jersey));
        }
        return result;
    }

    public static PlayerEntity buildPlayerEntity(Player player) throws InvalidEntityException {
        PlayerEntity result = new PlayerEntity();
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
            for (com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Attribute attribute : player.getAttributes().getAttribute()) {
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
        if (player.getSpecificcontracts() != null) {
            List<String> scs = nte(player.getSpecificcontracts().getSpecificcontract()).stream()
                    .map(Specificcontract::getValue).filter(Objects::nonNull).collect(Collectors.toList());
            result.setSpecificContracts(scs);
        }
        result.setNickname(player.getNickname());
        result.setOrder(player.getOrder());
        return result;
    }

    public static ScoutEventEntity buildScoutEventEntity(Event event) throws InvalidEntityException {
        ScoutEventEntity result = new ScoutEventEntity();
        result.setAutomatic(event.getAutomatic());
        result.setPointsUntilSnookerNeeded(event.getPointsuntilsnookerneeded());
        result.setBreakScore(event.getBreakscore());
        result.setCorrectedFrom(event.getCorrectedfrom());
        result.setCorrectedTo(event.getCorrectedto());
        result.setBallNumber(event.getBallnumber());
        result.setDismissalsInInnings(event.getDismissalsininnings());
        result.setEndScore(event.getEndscore());
        result.setSpot(event.getSpot());
        result.setExtraInfo(event.getExtrainfo());
        result.setExtraInfoBasketball(event.getExtrainfobasketball());
        result.setExtraInfoBowls(event.getExtrainfobowls());
        result.setExtraInfoCricket(event.getExtrainfocricket());
        result.setExtraInfoCsGo(event.getExtrainfocsgo());
        result.setExtraInfoMoba(event.getExtrainfomoba());
        result.setExtraInfoDarts(event.getExtrainfodarts());
        result.setExtraInfoFootball(event.getExtrainfofootball());
        result.setExtraInfoSoccer(event.getExtrainfosoccer());
        result.setExtraInfoHandball(event.getExtrainfohandball());
        result.setExtraInfoIceHockey(event.getExtrainfoicehockey());
        result.setExtraInfoSnooker(event.getExtrainfosnooker());
        result.setExtraInfoVolleyball(event.getExtrainfovolleyball());
        result.setExtraInfoBaseball(event.getExtrainfobaseball());
        result.setExtraInfoKabaddi(event.getExtrainfokabaddi());
        result.setExtraInfoWaterPolo(event.getExtrainfowaterpolo());
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
        result.setBallEventType(event.getBallevent() != null ?
                BallEventType.getEventTypeFromValue(event.getBallevent()) : null);
        result.setPlayerId(event.getPlayerid());
        result.setPlayer1Id(event.getPlayer1());
        result.setPlayer2Id(event.getPlayer2());
        result.setPosX(event.getPosx());
        result.setPosY(event.getPosy());
        TrackEntity.apply(event, result);
        result.setRemainingTimeInPeriod(event.getRemainingtimeperiod());
        result.setRunsInInnings(event.getRunsininnings());
        if (event.getStime() > 0) {
            result.setServerTime(CommonUtils.fromTimestamp(event.getStime()));
        }
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
        result.setSecondScoreType(event.getSecondscoretype());
        result.setScoreTypeQualifier(event.getScoretypequalifier());
        if (event.getHappenedat() != null) {
            result.setHappenedAt(CommonUtils.fromTimestamp(event.getHappenedat()));
        }
        result.setRefsTime(event.getRefstime());
        result.setShotDistance(event.getShotdistance());
        result.setShotProjectedGoalRate(event.getShotprojectedgoalrate());
        result.setShotRatingCategory(event.getShotratingcategory());
        result.setShotSpeed(event.getShotspeed());
        result.setShotType(event.getShottype());
        result.setPossibleBreak(event.getPossiblebreak());
        result.setAssists(event.getAssists());
        result.setDeaths(event.getDeaths());
        result.setErrorsAway(event.getErrorsaway());
        result.setErrorsHome(event.getErrorshome());
        result.setExperience(event.getExperience());
        Byte firstBaseLoaded = event.getFirstbaseloaded();
        if (firstBaseLoaded != null) {
            result.setFirstBaseLoaded(firstBaseLoaded != 0);
        }
        Byte secondBaseLoaded = event.getSecondbaseloaded();
        if (secondBaseLoaded != null) {
            result.setSecondBaseLoaded(secondBaseLoaded != 0);
        }
        Byte thirdBaseLoaded = event.getThirdbaseloaded();
        if (thirdBaseLoaded != null) {
            result.setThirdBaseLoaded(thirdBaseLoaded != 0);
        }
        result.setFirstBasePlayer(event.getFirstbaseplayer());
        result.setSecondBasePlayer(event.getSecondbaseplayer());
        result.setThirdBasePlayer(event.getThirdbaseplayer());
        result.setHeroesAliveAway(event.getHeroesaliveaway());
        result.setHeroesAliveHome(event.getHeroesalivehome());
        result.setHitsAway(event.getHitsaway());
        result.setHitsHome(event.getHitshome());
        result.setInningsScore(buildInningsScores(event.getInningscore()));
        result.setKills(event.getKills());
        result.setLastHits(event.getLasthits());
        result.setLevel(event.getLevel());
        result.setNetWorth(event.getNetworth());
        result.setPitchingSubstitution(event.getPitchingsubstitution());
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
            result.setConversionType(conversionType);
        }
        Byte touchdownType = event.getTouchdowntype();
        if (touchdownType != null) {
            result.setTouchdownType(touchdownType);
        }
        result.setGoals(event.getGoals());
        result.setBehinds(event.getBehinds());
        result.setMatchStatus(event.getMatchstatus());

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
        result.setHomePlayerStats(event.getHomeplayerstats());
        result.setAwayPlayerStats(event.getAwayplayerstats());
        result.setHomePlayerStatsRec(event.getHomeplayerstatsrec());
        result.setAwayPlayerStatsRec(event.getAwayplayerstatsrec());
        result.setPuntDistance(event.getPuntdistance());
        result.setDamage(event.getDamage());
        result.setHealthRemaining(event.getHealthremaining());
        result.setPointsRemaining(event.getPointsremaining());
        result.setReds(event.getReds());
        result.setRanOutOfBoundsAt(event.getRanoutofboundsat());
        result.setDriveInfoStatus(event.getDriveinfostatus());
        result.setFieldGoalDistance(event.getFieldgoaldistance());
        result.setHomeTimeOnIce(event.getHometimeonice());
        result.setAwayTimeOnIce(event.getAwaytimeonice());
        result.setAttackingPlayers(event.getAttackingplayers());
        result.setUnavailablePlayersHome(event.getUnavailableplayershome());
        result.setUnavailablePlayersAway(event.getUnavailableplayersaway());
        result.setPlayerStatistics(PlayerStatisticsEntity.tryCreate(event));
        result.setHomeTimeOnCourt(event.getHometimeoncourt());
        result.setAwayTimeOnCourt(event.getAwaytimeoncourt());
        result.setHomePlayers(event.getHomeplayers());
        result.setAwayPlayers(event.getAwayplayers());
        result.setAtBatNumber(event.getAtbatnumber());
        result.setTotalPitchCount(event.getTotalpitchcount());
        result.setAtBatPitchNumber(event.getAtbatpitchnumber());
        result.setBatBallDistance(event.getBatballdistance());
        result.setBatBallX(event.getBatballx());
        result.setBatBallY(event.getBatbally());
        result.setFieldingPlayers(event.getFieldingplayers());
        result.setPreliminaryMatchStatistics(event.getPreliminarymatchstatistics());
        result.setActualMatchStatistics(event.getActualmatchstatistics());
        result.setTeamStatistics(TeamStatisticsEntity.tryCreate(event));
        result.setFreeKickReason(event.getFreekickreason());
        result.setMaxBreakFrame(event.getMaxbreakframe());
        result.setHomeRunsHome(event.getHomerunshome());
        result.setMaxBreakMatch(event.getMaxbreakmatch());
        result.setHomeRunsAway(event.getHomerunsaway());
        result.setNextBatter(event.getNextbatter());
        result.setStrokeType(event.getStroketype());
        if (event.getScorernotconfirmed() != null) {
            result.setScorerNotConfirmed(event.getScorernotconfirmed() != 0);
        }
        result.setSpin(event.getSpin());
        result.setFoulTypeDescriptor(event.getFoultypedescriptor());
        result.setFoulTypeQualifier(event.getFoultypequalifier());

        result.setOnDeck(event.getOndeck());
        result.setInHole(event.getInhole());
        result.setNumberOfShots(event.getNumberofshots());
        result.setPointOutcome(event.getPointoutcome());
        result.setSituation(event.getSituation());
        result.setErrorType(event.getErrortype());
        result.setShotSequence(event.getShotsequence());
        result.setPrimaryShotType(event.getPrimaryshottype());
        result.setSecondaryShotType(event.getSecondaryshottype());
        result.setFinalConfidence(BooleanUtils.toBooleanObject(event.getFinalconfidence()));
        result.setPointInGameNumber(event.getPointingamenumber());

        result.setExtraInfoTennis(event.getExtrainfotennis());
        result.setLastStroke(event.getLaststroke());
        result.setSupervisorAction(event.getSupervisoraction());

        if (event.getStatistics() != null) {
            try {
                result.setStatistics(new StatisticsEntity(event.getStatistics()));
            } catch (UnknownEnumException e) {
                throw new InvalidEntityException(e, "Event.getStatistics()", event.getStatistics().toString());
            }
        }

        return result;
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
        return result;
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
        if (!nte(inningscores).isEmpty()) {
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
            for (com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Matchproperty property : properties.getMatchproperty()) {
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
            for (com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Team team : teams.getTeam()) {
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
