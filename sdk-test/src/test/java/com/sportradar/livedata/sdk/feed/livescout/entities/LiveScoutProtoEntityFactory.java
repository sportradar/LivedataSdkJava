package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;
import com.sportradar.livedata.sdk.feed.common.entities.HomeAway;
import com.sportradar.livedata.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.livedata.sdk.feed.livescout.enums.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.*;

public class LiveScoutProtoEntityFactory {
    //------Match------>>>>>>-------------------------------------------------------------------------------------------
    static Match buildMatch(final int dataSet) {
        Match match = new Match();
        insertMatchHeaderData(match, dataSet);
        addIncomingMessages(match, dataSet);
        return match;
    }

    static MatchUpdateEntity buildMatchUpdateEntity(final int dataSet) {
        MatchUpdateEntity result = new MatchUpdateEntity(new HashMap<>());

        applyIncomingMessage(result, dataSet);
        result.setMatchHeader(buildMatchHeaderEntity(dataSet));
        return result;
    }
    //------MatchHeader------>>>>>>--------------------------------------------------------------<<<<<<------Match------
    static Match insertMatchHeaderData(Match result, final int dataSet) {
        result.setT2Name("match" + dataSet + " t2 name");
        result.setT2Id(dataSet);
        result.setT1Name("match" + dataSet + " t1 name");
        result.setT1Id(2 + dataSet);
        result.setStart(3L + dataSet);
        result.setMatchid(4L + dataSet);
        result.setFeedtype(getEnumValue(ScoutFeedType.values(), dataSet).name().toLowerCase());
        result.setExtrainfo(5 + dataSet);
        result.setDistance(6 + dataSet);
        result.setBetstatus(getEnumValue(MatchBetStatus.values(), dataSet).name());
        result.setFirstserve(getEnumValue(Team.values(), dataSet).name().toLowerCase());
        result.setFirstservetiebreak(getEnumValue(Team.values(), dataSet).name().toLowerCase());
        result.setNumberofsets(7 + dataSet);
        result.setTiebreaklastset(dataSet % 2 == 1 ? 8 : 0);//false == 0, true non 0
        result.setConnectionstatus(dataSet % 2 == 0 ? 9 : 0);
        result.setDc(dataSet % 2 == 1 ? 10 : 0);
        result.setCoveredfrom(getEnumValue(Coverage.values(), dataSet).name().toLowerCase());
        result.setTimerunning(dataSet % 2 == 0 ? 11 : 0);
        result.setWonjumpball(getEnumValue(Team.values(), dataSet).name().toLowerCase());
        result.setBooked(dataSet % 2 == 1 ? 12 : 0);
        result.setSt1Id(13 + dataSet);
        result.setSt2Id(14 + dataSet);
        result.setMatchtime((15 + dataSet) + ":" + dataSet);
        result.setSex(getEnumValue(Sex.values(), dataSet).ordinal());
        result.setActive((byte) (dataSet % 2 == 0 ? 16 : 0));
        return result;
    }

    static MatchHeaderEntity buildMatchHeaderEntity(final int dataSet) {
        MatchHeaderEntity result = new MatchHeaderEntity();

        result.setTeam2(new IdNameTuple(dataSet, "match" + dataSet + " t2 name"));
        result.setTeam1(new IdNameTuple(2L + dataSet, "match" + dataSet + " t1 name"));
        result.setStart(new DateTime(3L + dataSet, DateTimeZone.getDefault()));
        result.setMatchId(4L + dataSet);
        result.setFeedType(getEnumValue(ScoutFeedType.values(), dataSet));
        result.setExtraInfo(5 + dataSet);
        result.setDistance(6 + dataSet);
        result.setBetStatus(getEnumValue(MatchBetStatus.values(), dataSet));
        result.setFirstServe(getEnumValue(Team.values(), dataSet));
        result.setFirstServeTieBreak(getEnumValue(Team.values(), dataSet));
        result.setNumberOfSets(7 + dataSet);
        result.setTieBreakLastSet(dataSet % 2 == 1);
        result.setConnectionStatus(dataSet % 2 == 0);
        result.setDeepCoverage(dataSet % 2 == 1);
        result.setCoveredFrom(getEnumValue(Coverage.values(), dataSet));
        result.setTimeRunning(dataSet % 2 == 0);
        result.setWonJumpBall(getEnumValue(Team.values(), dataSet));
        result.setBooked(dataSet % 2 == 1);
        result.setSt1Id(13 + dataSet);
        result.setSt2Id(14 + dataSet);
        result.setMatchTime((15 + dataSet) + ":" + dataSet);
        result.setSex(getEnumValue(Sex.values(), dataSet));
        result.setActive(dataSet % 2 == 0);

        return result;
    }
    //------IncomingMessage------>>>>>>----------------------------------------------------<<<<<<------MatchHeader------
    static Match addIncomingMessages(Match match, final int dataSet) {
        List<IncomingMessage> msgs = match.getStatusOrScoreOrRed();
    //Attacks
        Attacks attacks = new Attacks();
        attacks.setT1(11 + dataSet);
        attacks.setT2(22 + dataSet);
        msgs.add(attacks);
    //Black
        Black black = new Black();
        black.setT1(333 + dataSet);
        black.setT2(444 + dataSet);
        msgs.add(black);
    //Category
        Category category = new Category();
        category.setId(33 + dataSet);
        category.setName("category"+ dataSet + " name");
        msgs.add(category);
    //Corners
        Corners corners = new Corners();
        corners.setT1(44 + dataSet);
        corners.setT2(55 + dataSet);
        msgs.add(corners);
    //Dangerousattacks
        Dangerousattacks dangerousattacks = new Dangerousattacks();
        dangerousattacks.setT1(66 + dataSet);
        dangerousattacks.setT2(77 + dataSet);
        msgs.add(dangerousattacks);
    //Directfoulsperiod
        Directfoulsperiod directfoulsperiod = new Directfoulsperiod();
        directfoulsperiod.setT1(88 + dataSet);
        directfoulsperiod.setT2(99 + dataSet);
        msgs.add(directfoulsperiod);
    //Directfreekicks
        Directfreekicks directfreekicks = new Directfreekicks();
        directfreekicks.setT1(111 + dataSet);
        directfreekicks.setT2(222 + dataSet);
        msgs.add(directfreekicks);
    //Court
        Court court = new Court();
        court.setId(555 + dataSet);
        court.setName("court" + dataSet + " name");
        court.setCourtseq(7 + dataSet);
        msgs.add(court);
    //Events
        msgs.add(buildEvents(dataSet));
    //Firstkickoffteam1Sthalf
        Firstkickoffteam1Sthalf k1st = new Firstkickoffteam1Sthalf();
        k1st.setTeam(Integer.parseInt(getEnumValue(Team.values(), dataSet).getValue()[0]));
        msgs.add(k1st);
    //Kickoffteam
        Kickoffteam kickoffteam = new Kickoffteam();
        kickoffteam.setTeam(Integer.parseInt(getEnumValue(Team.values(), dataSet).getValue()[0]));
        msgs.add(kickoffteam);
    //Freekicks
        Freekicks freekicks = new Freekicks();
        freekicks.setT1(666 + dataSet);
        freekicks.setT2(777 + dataSet);
        msgs.add(freekicks);
    //Freethrows
        Freethrows freethrows = new Freethrows();
        freethrows.setT1(888 + dataSet);
        freethrows.setT2(999 + dataSet);
        msgs.add(freethrows);
    //Goalkeepersaves
        Goalkeepersaves goalkeepersaves = new Goalkeepersaves();
        goalkeepersaves.setT1(1111 + dataSet);
        goalkeepersaves.setT2(2222 + dataSet);
        msgs.add(goalkeepersaves);
    //Goalkicks
        Goalkicks goalkicks = new Goalkicks();
        goalkicks.setT1(3333 + dataSet);
        goalkicks.setT2(4444 + dataSet);
        msgs.add(goalkicks);
    //Injuries
        Injuries injuries = new Injuries();
        injuries.setT1(5555 + dataSet);
        injuries.setT2(6666 + dataSet);
        msgs.add(injuries);
    //Offsides
        Offsides offsides = new Offsides();
        offsides.setT1(8888 + dataSet);
        offsides.setT2(9999 + dataSet);
        msgs.add(offsides);
    //Penalties
        Penalties penalties = new Penalties();
        penalties.setT1(11111 + dataSet);
        penalties.setT2(22222 + dataSet);
        msgs.add(penalties);
    //Pitchconditions
        Pitchconditions pitchconditions = new Pitchconditions();
        pitchconditions.setId(33333 + dataSet);
        pitchconditions.setName(getEnumValue(PitchConditions.values(), dataSet).getValue());
        msgs.add(pitchconditions);
    //Possession
        Possession possession = new Possession();
        possession.setT1(44444 + dataSet);
        possession.setT2(55555 + dataSet);
        possession.setTeam(getEnumValue(Team.values(), dataSet).getValue()[1]);
        msgs.add(possession);
    //Red
        Red red = new Red();
        red.setT1(66666 + dataSet);
        red.setT2(77777 + dataSet);
        msgs.add(red);
    //Score
        Score score = buildScore(dataSet);
        if(score != null) {
            msgs.add(buildScore(dataSet));
        }
    //Serve
        Serve serve = new Serve();
        serve.setTeam(getEnumValue(Team.values(), dataSet).getValue()[1]);
        msgs.add(serve);
    //Shotsblocked
        Shotsblocked shotsblocked = new Shotsblocked();
        shotsblocked.setT1(111111 + dataSet);
        shotsblocked.setT2(222222 + dataSet);
        msgs.add(shotsblocked);
    //Shotsofftarget
        Shotsofftarget shotsofftarget = new Shotsofftarget();
        shotsofftarget.setT1(333333 + dataSet);
        shotsofftarget.setT2(444444 + dataSet);
        msgs.add(shotsofftarget);
    //Shotsontarget
        Shotsontarget shotsontarget = new Shotsontarget();
        shotsontarget.setT1(555555 + dataSet);
        shotsontarget.setT2(666666 + dataSet);
        msgs.add(shotsontarget);
    //Sport
        Sport sport = new Sport();
        sport.setId(777777 + dataSet);
        sport.setName("sport" + dataSet + " name");
        msgs.add(sport);
    //Status
        Status status = new Status();
        status.setId(888888 + dataSet);
        status.setName(getEnumValue(ScoutMatchStatus.values(), dataSet).name());
        status.setStart(999999 + dataSet);
        msgs.add(status);
    //Surfacetype
        Surfacetype surfacetype = new Surfacetype();
        surfacetype.setId(1111111 + dataSet);
        surfacetype.setName(getEnumValue(SurfaceType.values(), dataSet).getValue());
        msgs.add(surfacetype);
    //Suspensions
        Suspensions suspensions = new Suspensions();
        suspensions.setT1(2222222 + dataSet);
        suspensions.setT2(3333333 + dataSet);
        suspensions.setPowerplay(Integer.parseInt(getEnumValue(Team.values(), dataSet).getValue()[0]));
        msgs.add(suspensions);
    //Throwins
        Throwins throwins = new Throwins();
        throwins.setT1(4444444 + dataSet);
        throwins.setT2(5555555 + dataSet);
        msgs.add(throwins);
    //Tiebreak
        Tiebreak tiebreak = new Tiebreak();
        tiebreak.setValue(dataSet % 3 == 0 ? 33 : 0);
        msgs.add(tiebreak);
    //Tournament
        Tournament tournament = new Tournament();
        tournament.setId(7777777 + dataSet);
        tournament.setName("tournament"+ dataSet + " name");
        msgs.add(tournament);
    //Weatherconditions
        Weatherconditions weatherconditions = new Weatherconditions();
        weatherconditions.setId(8888888 + dataSet);
        weatherconditions.setName(getEnumValue(WeatherConditions.values(), dataSet).getValue());
        msgs.add(weatherconditions);
    //Yellow
        Yellow yellow = new Yellow();
        yellow.setT1(9999999 + dataSet);
        yellow.setT2(11111111 + dataSet);
        msgs.add(yellow);

        return match;
    }

    static void applyIncomingMessage(final MatchUpdateEntity result, final int dataSet) {
    //Attacks
        result.setAttacks(new HomeAway<>(11 + dataSet, 22 + dataSet));
    //Black
        result.setBlackCards(new HomeAway<>(333 + dataSet, 444 + dataSet));
    //Category
        result.setCategory(new IdNameTuple(33 + dataSet, "category"+ dataSet + " name"));
    //Corners
        result.setCorners(new HomeAway<>(44 + dataSet, 55 + dataSet));
    //Dangerousattacks
        result.setDangerousAttacks(new HomeAway<>(66 + dataSet, 77 + dataSet));
    //Directfoulsperiod
        result.setDirectFoulsPeriod(new HomeAway<>(88 + dataSet, 99 + dataSet));
    //Directfreekicks
        result.setDirectFreeKicks(new HomeAway<>(111 + dataSet, 222 + dataSet));
    //Court
        CourtEntity court = new CourtEntity();
        court.setCourtSeq(7 + dataSet);
        court.setId(555 + dataSet);
        court.setName("court" + dataSet + " name");
        result.setCourt(court);
    //Events
        result.setEvents(buildScoutEventEntities(dataSet));
    //Firstkickoffteam1Sthalf
        result.setKickoffTeamFirstHalf(getSpecificTeam(dataSet));
    //Kickoffteam
        result.setKickoffTeam(getSpecificTeam(dataSet));
    //Freekicks
        result.setFreeKicks(new HomeAway<>(666 + dataSet, 777 + dataSet));
    //Freethrows
        result.setFreeThrows(new HomeAway<>(888 + dataSet, 999 + dataSet));
    //Goalkeepersaves
        result.setGoalkeeperSaves(new HomeAway<>(1111 + dataSet, 2222 + dataSet));
    //Goalkicks
        result.setGoalKicks(new HomeAway<>(3333 + dataSet, 4444 + dataSet));
    //Injuries
        result.setInjuries(new HomeAway<>(5555 + dataSet, 6666 + dataSet));
    //Offsides
        result.setOffsides(new HomeAway<>(8888 + dataSet, 9999 + dataSet));
    //Penalties
        result.setPenalties(new HomeAway<>(11111 + dataSet, 22222 + dataSet));
    //Pitchconditions
        result.setPitchConditions(getEnumValue(PitchConditions.values(), dataSet));
    //Possession
        result.setPossessionTeam(getEnumValue(Team.values(), dataSet));
        result.setPossession(new HomeAway<>(44444 + dataSet, 55555 + dataSet));
    //Red
        result.setRedCards(new HomeAway<>(66666 + dataSet, 77777 + dataSet));
    //Score
        addScore(result, dataSet);
    //Serve
        result.setServe(getEnumValue(Team.values(), dataSet));
    //Shotsblocked
        result.setShotsBlocked(new HomeAway<>(111111 + dataSet, 222222 + dataSet));
    //Shotsofftarget
        result.setShotsOffTarget(new HomeAway<>(333333 + dataSet, 444444 + dataSet));
    //Shotsontarget
        result.setShotsOnTarget(new HomeAway<>(555555 + dataSet, 666666 + dataSet));
    //Sport
        result.setSport(new IdNameTuple(777777 + dataSet, "sport" + dataSet + " name"));
    //Status
        result.setMatchStatus(getEnumValue(ScoutMatchStatus.values(), dataSet));
        result.setMatchStatusId(888888 + dataSet);
        result.setMatchStatusStart(new DateTime(999999 + dataSet, DateTimeZone.UTC));
    //Surfacetype
        result.setSurfaceType(getEnumValue(SurfaceType.values(), dataSet));
    //Suspensions
        result.setSuspensions(new SuspensionsEntity(2222222 + dataSet, 3333333 + dataSet,
                getEnumValue(Team.values(), dataSet)));
    //Throwins
        result.setThrowins(new HomeAway<>(4444444 + dataSet, 5555555 + dataSet));
    //Tiebreak
        result.setTieBreak(dataSet % 3 == 0);
    //Tournament
        result.setTournament(new IdNameTuple(7777777 + dataSet, "tournament" + dataSet + " name"));
    //Weatherconditions
        result.setWeatherConditions(getEnumValue(WeatherConditions.values(), dataSet));
    //Yellow
        result.setYellowCards(new HomeAway<>(9999999 + dataSet, 11111111 + dataSet));
    }
    //------IncomingMessage------Event------>>>>>>-------------------------------------<<<<<<------IncomingMessage------
    static Events buildEvents(final int dataSet) {
        Events events = new Events();
        for(int i = 0; i < dataSet; i++) {
            events.getEvent().add(buildEvent(i));
        }
        return events;
    }

    static List<ScoutEventEntity> buildScoutEventEntities(final int dataSet) {
        List<ScoutEventEntity> eventsList = new ArrayList<>();
        for(int i = 0; i < dataSet; i++) {
            eventsList.add(buildScoutEventEntity(i));
        }
        return eventsList;
    }

    static Event buildEvent(final int dataSet) {
        Event event = new Event();
        event.setId(11337799 + dataSet);
        event.setAutomatic(101102 + dataSet);
        event.setBreakscore("event" + dataSet + " breakscore");
        event.setExtrainfo(74123L + dataSet);
        event.setExtrainfodarts(dataSet % 2 == 0 ? "extra info darts" : null);
        event.setExtrainfosnooker(dataSet % 3 == 0 ? "extra info snooker" : null);
        event.setFramenumber(14523 + dataSet);
        event.setFramescore((5 + dataSet) + ":" + dataSet);
        event.setGamenumber(11554422 + dataSet);
        event.setGamescore((15 + dataSet) + ":" + dataSet);
        event.setInfo("event" + dataSet + " info");
        event.setLegscore((3 + dataSet) + ":" + dataSet);
        event.setMatchscore((13 + dataSet) + ":" + dataSet);
        event.setMtime((876 + dataSet) + ":" + dataSet);
        event.setPeriodnumber(77440 + dataSet);
        event.setPlayer1(1 + dataSet);
        event.setPlayer2(2 + dataSet);
        event.setPosx(3 + dataSet);
        event.setPosy(4 + dataSet);
        event.setRemainingtimeperiod((123 + dataSet) + ":" + dataSet);
        event.setSetnumber(5 + dataSet);
        event.setSetscore((13 + dataSet) + ":" + dataSet);
        event.setInninghalf(getEnumValue(Inning.values(), dataSet).getValue());
        event.setServer(getEnumValue(Team.values(), dataSet).getValue()[0]);
        event.setSide(getEnumValue(Team.values(), dataSet).getValue()[1]);
        event.setType(1126);// + dataSet);
        event.setHappenedat(12234556L + dataSet);
        event.setFirstbaseloaded((byte)(dataSet % 3 == 0 ? 9 : 0));
        event.setSecondbaseloaded((byte)(dataSet % 2 == 0 ? 7 : 0));
        event.setThirdbaseloaded((byte)(dataSet % 4 == 0 ? 8 : 0));
        event.setConversiontype((byte)(1 - dataSet));
        event.setTouchdowntype((byte)(7 + dataSet));
        event.setStime(17 + dataSet);
        return event;
    }

    static ScoutEventEntity buildScoutEventEntity(final int dataSet) {
        ScoutEventEntity result = new ScoutEventEntity();

        result.setId(11337799 + dataSet);
        result.setAutomatic(101102 + dataSet);
        result.setBreakScore("event" + dataSet + " breakscore");
        result.setExtraInfo(74123L + dataSet);
        result.setExtraInfoDarts(dataSet % 2 == 0 ? "extra info darts" : null);
        result.setExtraInfoSnooker(dataSet % 3 == 0 ? "extra info snooker" : null);
        result.setFrameNumber(14523 + dataSet);
        result.setFrameScore((5 + dataSet) + ":" + dataSet);
        result.setGameNumber(11554422 + dataSet);
        result.setGameScore((15 + dataSet) + ":" + dataSet);
        result.setInfo("event" + dataSet + " info");
        result.setLegScore((3 + dataSet) + ":" + dataSet);
        result.setMatchScore((13 + dataSet) + ":" + dataSet);
        result.setMatchTime((876 + dataSet) + ":" + dataSet);
        result.setPeriodNumber(77440 + dataSet);
        result.setPlayer1Id(1 + dataSet);
        result.setPlayer2Id(2 + dataSet);
        result.setPosX(3 + dataSet);
        result.setPosY(4 + dataSet);
        result.setRemainingTimeInPeriod((123 + dataSet) + ":" + dataSet);
        result.setSetNumber(5 + dataSet);
        result.setSetScore((13 + dataSet) + ":" + dataSet);
        result.setInningHalf(getEnumValue(Inning.values(), dataSet));
        result.setServer(getEnumValue(Team.values(), dataSet));
        result.setSide(getEnumValue(Team.values(), dataSet));
        result.setType(EventType.ATTACK);//TODO that enum should be removed
        result.setTypeId(1126);// + dataSet);
        result.setHappenedAt(new DateTime(12234556L + dataSet, DateTimeZone.UTC));
        result.setFirstBaseLoaded(dataSet % 3 == 0);
        result.setSecondBaseLoaded(dataSet % 2 == 0);
        result.setThirdBaseLoaded(dataSet % 4 == 0);
        result.setConversionType(1 - dataSet);
        result.setTouchdownType(7 + dataSet);
        result.setServerTime(new DateTime(17 + dataSet, DateTimeZone.UTC));
        return result;
    }
    //------IncomingMessage------others------>>>>>>-------------------------<<<<<<------IncomingMessage------Event------
    static Score buildScore(final int dataSet) {
        Score score = null;
        for(int i = 0; i < dataSet; i++) {
            Score newScore = new Score();
            newScore.setT1(80000 + i);
            newScore.setT2(90000 + i);
            newScore.setType("score" + i + " type");
            newScore.setScore(score);
            score = newScore;
        }
        return score;
    }

    static void addScore(final MatchUpdateEntity result, final int dataSet) {
        result.setScore(result.getScore() != null ? result.getScore() : new HashMap<>());
        result.setScores(result.getScores() != null ? result.getScores() : new ArrayList<>());

        ScoreEntity score = null;
        for(int i = 0; i < dataSet; i++) {
            score = new ScoreEntity("score" + i + " type", 80000 + i, 90000 + i, score);
        }
        if(score != null) {
            result.getScore().put(score.getType(), new HomeAway<>(score.getTeam1(), score.getTeam2()));
            result.getScores().add(score);

        }
    }

    static Lineups buildLineups(final int dataSet) {
        Lineups result = new Lineups();
        result.setMatchid(741852 + dataSet);
        for(int i = 0; i < dataSet; i++) {
            result.getPlayer().add(buildPlayer(i));
        }
        for(int i = 0; i < dataSet; i++) {
            result.getManager().add(buildManager(i));
        }
        for(int i = 0; i < dataSet; i++) {
            result.getTeamofficial().add(buildTeamOfficial(i));
        }
        return result;
    }

    static LineupsEntity buildLineupsEntity(final int dataSet) {
        LineupsEntity result = new LineupsEntity(new HashMap<>());
        result.setMatchId(741852 + dataSet);
        for(int i = 0; i < dataSet; i++) {
            result.getPlayers().add(buildPlayerEntity(i));
        }
        for(int i = 0; i < dataSet; i++) {
            result.getManagers().add(buildManagerEntity(i));
        }
        for(int i = 0; i < dataSet; i++) {
            result.getTeamOfficials().add(buildTeamOfficialEntity(i));
        }
        return result;
    }

    static Manager buildManager(final int dataSet) {
        Manager result = new Manager();
        result.setId(452 + dataSet);
        result.setName("manager" + dataSet + " name");
        result.setTeam(Integer.parseInt(getEnumValue(Team.values(), dataSet).getValue()[0]));
        return result;
    }

    static ManagerEntity buildManagerEntity(final int dataSet) {
        ManagerEntity result = new ManagerEntity();
        result.setId(452 + dataSet);
        result.setName("manager" + dataSet + " name");
        result.setTeam(getEnumValue(Team.values(), dataSet));
        return result;
    }

    static Teamofficial buildTeamOfficial(final int dataSet) {
        Teamofficial result = new Teamofficial();
        result.setId(878 + dataSet);
        result.setName("teamofficial" + dataSet + " name");
        result.setTeam(Integer.parseInt(getEnumValue(Team.values(), dataSet).getValue()[0]));
        return result;
    }

    static TeamOfficialEntity buildTeamOfficialEntity(final int dataSet) {
        TeamOfficialEntity result = new TeamOfficialEntity();
        result.setId(878 + dataSet);
        result.setName("teamofficial" + dataSet + " name");
        result.setTeam(getEnumValue(Team.values(), dataSet));
        return result;
    }

    static Player buildPlayer(final int dataSet) {
        Player result = new Player();
        result.setId(113355 + dataSet);
        result.setName("player" + dataSet + " name");
        result.setShirtnumber(123654789 + dataSet);
        result.setSubstitute(dataSet % 2 == 0);
        result.setTeam(Integer.parseInt(getEnumValue(Team.values(), dataSet).getValue()[0]));
        result.setPosition((12 + dataSet) + ":" + (7 + dataSet));
        result.setNickname("player" + dataSet + " nickname");
        result.setOrder(dataSet);

        Attributes attributes = new Attributes();
        for(int i = 0; i < dataSet; i++) {
            attributes.getAttribute().add(buildAttribute(dataSet));
        }
        result.setAttributes(attributes);

        Matchroles matchroles = new Matchroles();
        for(int i = 0; i < dataSet; i++) {
            Matchrole matchrole = new Matchrole();
            matchrole.setId(17 + i);
            matchrole.setDescription("matchrole" + i + " description");
            matchroles.getMatchrole().add(matchrole);
        }
        result.setMatchroles(matchroles);

        return result;
    }

    static PlayerEntity buildPlayerEntity(final int dataSet) {
        PlayerEntity result = new PlayerEntity(new HashMap<>());
        result.setId(113355 + dataSet);
        result.setName("player" + dataSet + " name");
        result.setShirtNumber(123654789 + dataSet);
        result.setSubstitute(dataSet % 2 == 0);
        result.setTeam(getEnumValue(Team.values(), dataSet));
        result.setPosition((12 + dataSet) + ":" + (7 + dataSet));
        result.setNickname("player" + dataSet + " nickname");
        result.setOrder(dataSet);

        List<AttributeEntity> attributes = new ArrayList<>();
        for(int i = 0; i < dataSet; i++) {
            attributes.add(buildAttributeEntity(dataSet));
        }
        result.setAttributes(attributes);

        List<MatchRoleEntity> matchRoles = new ArrayList<>();
        for(int i = 0; i < dataSet; i++) {
            MatchRoleEntity matchRole = new MatchRoleEntity();
            matchRole.setId(17 + i);
            matchRole.setDescription("matchrole" + i + " description");
            matchRoles.add(matchRole);
        }
        result.setMatchRoles(matchRoles);

        return result;
    }

    static Attribute buildAttribute(final int dataSet) {
        Attribute result = new Attribute();
        result.setType("Attribute" + dataSet + " type");
        result.setTypeid(166+ dataSet);
        result.setValue("Attribute" + dataSet + " value");
        result.setValueid(661 + dataSet);
        return result;
    }

    static AttributeEntity buildAttributeEntity(final int dataSet) {
        AttributeEntity result = new AttributeEntity();
        result.setType("Attribute" + dataSet + " type");
        result.setTypeId(166+ dataSet);
        result.setValue("Attribute" + dataSet + " value");
        result.setValueId(661 + dataSet);
        return result;
    }

    static Matchstop buildMatchstop(final int dataSet) {
        Matchstop matchstop = new Matchstop();
        matchstop.setMatchid(112211 + dataSet);
        matchstop.setReason("matchstop"+ dataSet + " reason");
        return matchstop;
    }

    static MatchStopEntity buildMatchStopEntity(final int dataSet) {
        MatchStopEntity result = new MatchStopEntity(new HashMap<>());
        result.setMatchId(112211 + dataSet);
        result.setReason("matchstop"+ dataSet + " reason");
        return result;
    }

    static Bookmatch buildBookmatch(final int dataSet) {
        Bookmatch bookmatch = new Bookmatch();
        bookmatch.setMatchid(54223L + dataSet);
        bookmatch.setMessage("bookmatch" + dataSet + " message");
        bookmatch.setResult(getEnumValue(BookMatchResult.values(), dataSet).getValue());
        return bookmatch;
    }

    static MatchBookingEntity buildMatchBookingEntity(final int dataSet) {
        MatchBookingEntity result = new MatchBookingEntity(new HashMap<>());
        result.setMatchId(54223L + dataSet);
        result.setMessage("bookmatch" + dataSet + " message");
        result.setResult(getEnumValue(BookMatchResult.values(), dataSet));
        return result;
    }
    //------Utils------>>>>>>----------------------------------------------<<<<<<------IncomingMessage------simple------
    private static <T extends EntityEnum> T getEnumValue(T[] members, int dataSet) {
        int index = dataSet % members.length;
        return members[index];
    }
    //hack for kickoff team fields
    private static Team getSpecificTeam(int dataSet) {
        Team team = getEnumValue(Team.values(), dataSet);
        return team == Team.NONE ? null : team;
    }
}
