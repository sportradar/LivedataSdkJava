package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;
import com.sportradar.livedata.sdk.feed.common.entities.HomeAway;
import com.sportradar.livedata.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.*;

public class LiveScoutProtoEntityFactory {
    //------Match------>>>>>>-------------------------------------------------------------------------------------------
    static Match buildMatch(final int valueBase) {
        Match match = new Match();
        insertMatchHeaderData(match, valueBase);
        addIncomingMessages(match, valueBase);
        return match;
    }

    static MatchUpdateEntity buildMatchUpdateEntity(final int valueBase) {
        MatchUpdateEntity result = new MatchUpdateEntity();

        applyIncomingMessage(result, valueBase);
        result.setMatchHeader(buildMatchHeaderEntity(valueBase));
        return result;
    }
    //------MatchHeader------>>>>>>--------------------------------------------------------------<<<<<<------Match------
    static Match insertMatchHeaderData(Match result, final int valueBase) {
        result.setT2Name("match" + valueBase + " t2 name");
        result.setT2Id(valueBase);
        result.setT1Name("match" + valueBase + " t1 name");
        result.setT1Id(2 + valueBase);
        result.setStart(3L + valueBase);
        result.setMatchid(4L + valueBase);
        result.setFeedtype(getEnumValue(ScoutFeedType.values(), valueBase).name().toLowerCase());
        result.setExtrainfo(5 + valueBase);
        result.setDistance(6 + valueBase);
        result.setBetstatus(getEnumValue(MatchBetStatus.values(), valueBase).name());
        result.setFirstserve(getEnumValue(Team.values(), valueBase).name().toLowerCase());
        result.setFirstservetiebreak(getEnumValue(Team.values(), valueBase).name().toLowerCase());
        result.setNumberofsets(7 + valueBase);
        result.setTiebreaklastset(valueBase % 2 == 1 ? 8 : 0);//false == 0, true non 0
        result.setConnectionstatus(valueBase % 2 == 0 ? 9 : 0);
        result.setDc(valueBase % 2 == 1 ? 10 : 0);
        result.setCoveredfrom(getEnumValue(Coverage.values(), valueBase).name().toLowerCase());
        result.setTimerunning(valueBase % 2 == 0 ? 11 : 0);
        result.setWonjumpball(getEnumValue(Team.values(), valueBase).name().toLowerCase());
        result.setBooked(valueBase % 2 == 1 ? 12 : 0);
        result.setSt1Id(13 + valueBase);
        result.setSt2Id(14 + valueBase);
        result.setMatchtime((15 + valueBase) + ":" + valueBase);
        result.setSex(getEnumValue(Sex.values(), valueBase).ordinal());
        result.setActive((byte) (valueBase % 2 == 0 ? 16 : 0));
        result.setUuid("23rfc1" + valueBase);
        result.setPage(valueBase);
        result.setTotalpages(19 + valueBase);
        return result;
    }

    static MatchHeaderEntity buildMatchHeaderEntity(final int valueBase) {
        MatchHeaderEntity result = new MatchHeaderEntity();

        result.setTeam2(new IdNameTuple(valueBase, "match" + valueBase + " t2 name"));
        result.setTeam1(new IdNameTuple(2L + valueBase, "match" + valueBase + " t1 name"));
        result.setStart(new DateTime(3L + valueBase, DateTimeZone.getDefault()));
        result.setMatchId(4L + valueBase);
        result.setFeedType(getEnumValue(ScoutFeedType.values(), valueBase));
        result.setExtraInfo(5 + valueBase);
        result.setDistance(6 + valueBase);
        result.setBetStatus(getEnumValue(MatchBetStatus.values(), valueBase));
        result.setFirstServe(getEnumValue(Team.values(), valueBase));
        result.setFirstServeTieBreak(getEnumValue(Team.values(), valueBase));
        result.setNumberOfSets(7 + valueBase);
        result.setTieBreakLastSet(valueBase % 2 == 1);
        result.setConnectionStatus(valueBase % 2 == 0);
        result.setDeepCoverage(valueBase % 2 == 1);
        result.setCoveredFrom(getEnumValue(Coverage.values(), valueBase));
        result.setTimeRunning(valueBase % 2 == 0);
        result.setWonJumpBall(getEnumValue(Team.values(), valueBase));
        result.setBooked(valueBase % 2 == 1);
        result.setSt1Id(13 + valueBase);
        result.setSt2Id(14 + valueBase);
        result.setMatchTime((15 + valueBase) + ":" + valueBase);
        result.setSex(getEnumValue(Sex.values(), valueBase));
        result.setActive(valueBase % 2 == 0);
        if(ScoutFeedType.FULL_PAGINATED.equals(result.getFeedType())) {
            result.setPagination(new PaginationEntity("23rfc1" + valueBase, valueBase, 19 + valueBase));
        }

        return result;
    }
    //------IncomingMessage------>>>>>>----------------------------------------------------<<<<<<------MatchHeader------
    static Match addIncomingMessages(Match match, final int valueBase) {
        List<IncomingMessage> msgs = match.getStatusOrScoreOrRed();
    //Attacks
        Attacks attacks = new Attacks();
        attacks.setT1(11 + valueBase);
        attacks.setT2(22 + valueBase);
        msgs.add(attacks);
    //Black
        Black black = new Black();
        black.setT1(333 + valueBase);
        black.setT2(444 + valueBase);
        msgs.add(black);
    //Category
        Category category = new Category();
        category.setId(33 + valueBase);
        category.setName("category"+ valueBase + " name");
        msgs.add(category);
    //Corners
        Corners corners = new Corners();
        corners.setT1(44 + valueBase);
        corners.setT2(55 + valueBase);
        msgs.add(corners);
    //Dangerousattacks
        Dangerousattacks dangerousattacks = new Dangerousattacks();
        dangerousattacks.setT1(66 + valueBase);
        dangerousattacks.setT2(77 + valueBase);
        msgs.add(dangerousattacks);
    //Directfoulsperiod
        Directfoulsperiod directfoulsperiod = new Directfoulsperiod();
        directfoulsperiod.setT1(88 + valueBase);
        directfoulsperiod.setT2(99 + valueBase);
        msgs.add(directfoulsperiod);
    //Directfreekicks
        Directfreekicks directfreekicks = new Directfreekicks();
        directfreekicks.setT1(111 + valueBase);
        directfreekicks.setT2(222 + valueBase);
        msgs.add(directfreekicks);
    //Court
        Court court = new Court();
        court.setId(555 + valueBase);
        court.setName("court" + valueBase + " name");
        court.setCourtseq(7 + valueBase);
        msgs.add(court);
    //Events
        msgs.add(buildEvents(valueBase));
    //Firstkickoffteam1Sthalf
        Firstkickoffteam1Sthalf k1st = new Firstkickoffteam1Sthalf();
        k1st.setTeam(Integer.parseInt(getEnumValue(Team.values(), valueBase).getValue()[0]));
        msgs.add(k1st);
    //Kickoffteam
        Kickoffteam kickoffteam = new Kickoffteam();
        kickoffteam.setTeam(Integer.parseInt(getEnumValue(Team.values(), valueBase).getValue()[0]));
        msgs.add(kickoffteam);
    //Freekicks
        Freekicks freekicks = new Freekicks();
        freekicks.setT1(666 + valueBase);
        freekicks.setT2(777 + valueBase);
        msgs.add(freekicks);
    //Freethrows
        Freethrows freethrows = new Freethrows();
        freethrows.setT1(888 + valueBase);
        freethrows.setT2(999 + valueBase);
        msgs.add(freethrows);
    //Goalkeepersaves
        Goalkeepersaves goalkeepersaves = new Goalkeepersaves();
        goalkeepersaves.setT1(1111 + valueBase);
        goalkeepersaves.setT2(2222 + valueBase);
        msgs.add(goalkeepersaves);
    //Goalkicks
        Goalkicks goalkicks = new Goalkicks();
        goalkicks.setT1(3333 + valueBase);
        goalkicks.setT2(4444 + valueBase);
        msgs.add(goalkicks);
    //Injuries
        Injuries injuries = new Injuries();
        injuries.setT1(5555 + valueBase);
        injuries.setT2(6666 + valueBase);
        msgs.add(injuries);
    //Offsides
        Offsides offsides = new Offsides();
        offsides.setT1(8888 + valueBase);
        offsides.setT2(9999 + valueBase);
        msgs.add(offsides);
    //Penalties
        Penalties penalties = new Penalties();
        penalties.setT1(11111 + valueBase);
        penalties.setT2(22222 + valueBase);
        msgs.add(penalties);
    //Pitchconditions
        Pitchconditions pitchconditions = new Pitchconditions();
        pitchconditions.setId(33333 + valueBase);
        pitchconditions.setName(getEnumValue(PitchConditions.values(), valueBase).getValue());
        msgs.add(pitchconditions);
    //Possession
        Possession possession = new Possession();
        possession.setT1(44444 + valueBase);
        possession.setT2(55555 + valueBase);
        possession.setTeam(getEnumValue(Team.values(), valueBase).getValue()[1]);
        msgs.add(possession);
    //Red
        Red red = new Red();
        red.setT1(66666 + valueBase);
        red.setT2(77777 + valueBase);
        msgs.add(red);
    //Score
        addScores(msgs, valueBase);
    //Serve
        Serve serve = new Serve();
        serve.setTeam(getEnumValue(Team.values(), valueBase).getValue()[1]);
        msgs.add(serve);
    //Shotsblocked
        Shotsblocked shotsblocked = new Shotsblocked();
        shotsblocked.setT1(111111 + valueBase);
        shotsblocked.setT2(222222 + valueBase);
        msgs.add(shotsblocked);
    //Shotsofftarget
        Shotsofftarget shotsofftarget = new Shotsofftarget();
        shotsofftarget.setT1(333333 + valueBase);
        shotsofftarget.setT2(444444 + valueBase);
        msgs.add(shotsofftarget);
    //Shotsontarget
        Shotsontarget shotsontarget = new Shotsontarget();
        shotsontarget.setT1(555555 + valueBase);
        shotsontarget.setT2(666666 + valueBase);
        msgs.add(shotsontarget);
    //Sport
        Sport sport = new Sport();
        sport.setId(777777 + valueBase);
        sport.setName("sport" + valueBase + " name");
        msgs.add(sport);
    //Status
        Status status = new Status();
        status.setId(888888 + valueBase);
        status.setName(getEnumValue(ScoutMatchStatus.values(), valueBase).name());
        status.setStart(999999 + valueBase);
        msgs.add(status);
    //Surfacetype
        Surfacetype surfacetype = new Surfacetype();
        surfacetype.setId(1111111 + valueBase);
        surfacetype.setName(getEnumValue(SurfaceType.values(), valueBase).getValue());
        msgs.add(surfacetype);
    //Suspensions
        Suspensions suspensions = new Suspensions();
        suspensions.setT1(2222222 + valueBase);
        suspensions.setT2(3333333 + valueBase);
        suspensions.setPowerplay(Integer.parseInt(getEnumValue(Team.values(), valueBase).getValue()[0]));
        msgs.add(suspensions);
    //Throwins
        Throwins throwins = new Throwins();
        throwins.setT1(4444444 + valueBase);
        throwins.setT2(5555555 + valueBase);
        msgs.add(throwins);
    //Tiebreak
        Tiebreak tiebreak = new Tiebreak();
        tiebreak.setValue(valueBase % 3 == 0 ? 33 : 0);
        msgs.add(tiebreak);
    //Tournament
        Tournament tournament = new Tournament();
        tournament.setId(7777777 + valueBase);
        tournament.setName("tournament"+ valueBase + " name");
        msgs.add(tournament);
    //Weatherconditions
        Weatherconditions weatherconditions = new Weatherconditions();
        weatherconditions.setId(8888888 + valueBase);
        weatherconditions.setName(getEnumValue(WeatherConditions.values(), valueBase).getValue());
        msgs.add(weatherconditions);
    //Yellow
        Yellow yellow = new Yellow();
        yellow.setT1(9999999 + valueBase);
        yellow.setT2(11111111 + valueBase);
        msgs.add(yellow);
    //Subteams
        addSubteams(msgs, valueBase);

        return match;
    }

    static void applyIncomingMessage(final MatchUpdateEntity result, final int valueBase) {
    //Attacks
        result.setAttacks(new HomeAway<>(11 + valueBase, 22 + valueBase));
    //Black
        result.setBlackCards(new HomeAway<>(333 + valueBase, 444 + valueBase));
    //Category
        result.setCategory(new IdNameTuple(33 + valueBase, "category"+ valueBase + " name"));
    //Corners
        result.setCorners(new HomeAway<>(44 + valueBase, 55 + valueBase));
    //Dangerousattacks
        result.setDangerousAttacks(new HomeAway<>(66 + valueBase, 77 + valueBase));
    //Directfoulsperiod
        result.setDirectFoulsPeriod(new HomeAway<>(88 + valueBase, 99 + valueBase));
    //Directfreekicks
        result.setDirectFreeKicks(new HomeAway<>(111 + valueBase, 222 + valueBase));
    //Court
        CourtEntity court = new CourtEntity();
        court.setCourtSeq(7 + valueBase);
        court.setId(555 + valueBase);
        court.setName("court" + valueBase + " name");
        result.setCourt(court);
    //Events
        result.setEvents(buildScoutEventEntities(valueBase));
    //Firstkickoffteam1Sthalf
        result.setKickoffTeamFirstHalf(getSpecificTeam(valueBase));
    //Kickoffteam
        result.setKickoffTeam(getSpecificTeam(valueBase));
    //Freekicks
        result.setFreeKicks(new HomeAway<>(666 + valueBase, 777 + valueBase));
    //Freethrows
        result.setFreeThrows(new HomeAway<>(888 + valueBase, 999 + valueBase));
    //Goalkeepersaves
        result.setGoalkeeperSaves(new HomeAway<>(1111 + valueBase, 2222 + valueBase));
    //Goalkicks
        result.setGoalKicks(new HomeAway<>(3333 + valueBase, 4444 + valueBase));
    //Injuries
        result.setInjuries(new HomeAway<>(5555 + valueBase, 6666 + valueBase));
    //Offsides
        result.setOffsides(new HomeAway<>(8888 + valueBase, 9999 + valueBase));
    //Penalties
        result.setPenalties(new HomeAway<>(11111 + valueBase, 22222 + valueBase));
    //Pitchconditions
        result.setPitchConditions(getEnumValue(PitchConditions.values(), valueBase));
    //Possession
        result.setPossessionTeam(getEnumValue(Team.values(), valueBase));
        result.setPossession(new HomeAway<>(44444 + valueBase, 55555 + valueBase));
    //Red
        result.setRedCards(new HomeAway<>(66666 + valueBase, 77777 + valueBase));
    //Score
        addScores(result, valueBase);
    //Serve
        result.setServe(getEnumValue(Team.values(), valueBase));
    //Shotsblocked
        result.setShotsBlocked(new HomeAway<>(111111 + valueBase, 222222 + valueBase));
    //Shotsofftarget
        result.setShotsOffTarget(new HomeAway<>(333333 + valueBase, 444444 + valueBase));
    //Shotsontarget
        result.setShotsOnTarget(new HomeAway<>(555555 + valueBase, 666666 + valueBase));
    //Sport
        result.setSport(new IdNameTuple(777777 + valueBase, "sport" + valueBase + " name"));
    //Status
        result.setMatchStatus(getEnumValue(ScoutMatchStatus.values(), valueBase));
        result.setMatchStatusId(888888 + valueBase);
        result.setMatchStatusStart(new DateTime(999999 + valueBase, DateTimeZone.UTC));
    //Surfacetype
        result.setSurfaceType(getEnumValue(SurfaceType.values(), valueBase));
    //Suspensions
        result.setSuspensions(new SuspensionsEntity(2222222 + valueBase, 3333333 + valueBase,
                getEnumValue(Team.values(), valueBase)));
    //Throwins
        result.setThrowins(new HomeAway<>(4444444 + valueBase, 5555555 + valueBase));
    //Tiebreak
        result.setTieBreak(valueBase % 3 == 0);
    //Tournament
        result.setTournament(new IdNameTuple(7777777 + valueBase, "tournament" + valueBase + " name"));
    //Weatherconditions
        result.setWeatherConditions(getEnumValue(WeatherConditions.values(), valueBase));
    //Yellow
        result.setYellowCards(new HomeAway<>(9999999 + valueBase, 11111111 + valueBase));
    //Subteams
        result.setSubteams(buildSubteams(valueBase));
    }
    //------IncomingMessage------Event------>>>>>>-------------------------------------<<<<<<------IncomingMessage------
    static Events buildEvents(final int valueBase) {
        Events events = new Events();
        for(int i = 0; i < valueBase; i++) {
            events.getEvent().add(buildEvent(i));
        }
        return events;
    }

    static List<ScoutEventEntity> buildScoutEventEntities(final int valueBase) {
        List<ScoutEventEntity> eventsList = new ArrayList<>();
        for(int i = 0; i < valueBase; i++) {
            eventsList.add(buildScoutEventEntity(i));
        }
        return eventsList;
    }

    static Event buildEvent(final int valueBase) {
        Event event = new Event();
        event.setId(11337799 + valueBase);
        event.setAutomatic(101102 + valueBase);
        event.setBreakscore("event" + valueBase + " breakscore");
        event.setExtrainfo(74123L + valueBase);
        event.setExtrainfodarts(valueBase % 2 == 0 ? "extra info darts" : null);
        event.setExtrainfosnooker(valueBase % 3 == 0 ? "extra info snooker" : null);
        event.setFramenumber(14523 + valueBase);
        event.setFramescore((5 + valueBase) + ":" + valueBase);
        event.setGamenumber(11554422 + valueBase);
        event.setGamescore((15 + valueBase) + ":" + valueBase);
        event.setInfo("event" + valueBase + " info");
        event.setLegscore((3 + valueBase) + ":" + valueBase);
        event.setMatchscore((13 + valueBase) + ":" + valueBase);
        event.setMtime((876 + valueBase) + ":" + valueBase);
        event.setPeriodnumber(77440 + valueBase);
        event.setPlayer1(1 + valueBase);
        event.setPlayer2(2 + valueBase);
        event.setPosx(3 + valueBase);
        event.setPosy(4 + valueBase);
        event.setRemainingtimeperiod((123 + valueBase) + ":" + valueBase);
        event.setSetnumber(5 + valueBase);
        event.setSetscore((13 + valueBase) + ":" + valueBase);
        event.setInninghalf(getEnumValue(Inning.values(), valueBase).getValue());
        event.setServer(getEnumValue(Team.values(), valueBase).getValue()[0]);
        event.setSide(getEnumValue(Team.values(), valueBase).getValue()[1]);
        event.setType(1126);// cannot valueBase, because of EventType enum. EventType has duplicates
        event.setHappenedat(12234556L + valueBase);
        event.setRefstime(valueBase % 2 == 0 ? (long) valueBase : null);
        event.setFirstbaseloaded((byte)(valueBase % 3 == 0 ? 9 : 0));
        event.setSecondbaseloaded((byte)(valueBase % 2 == 0 ? 7 : 0));
        event.setThirdbaseloaded((byte)(valueBase % 4 == 0 ? 8 : 0));
        event.setConversiontype((byte)(1 - valueBase));
        event.setTouchdowntype((byte)(7 + valueBase));
        event.setStime(17 + valueBase);
        if(valueBase % 3 != 0){
            event.setScorernotconfirmed(valueBase % 2 == 0 ? 1 : 0);
        }

        return event;
    }

    static Statistics buildScoutEventStatistics() {
        Playerstats playerstats1 = new Playerstats();
        playerstats1.setPid(1L);
        playerstats1.getStats().add(buildStats("stats1", "value1"));
        playerstats1.getStats().add(buildStats("stats2", "value2"));

        Playerstats playerstats2 = new Playerstats();
        playerstats2.setPid(2L);
        playerstats2.getStats().add(buildStats("stats3", "value3"));
        playerstats2.getStats().add(buildStats("stats4", "value4"));

        Battersstatstotal battersstatstotal = new Battersstatstotal();
        battersstatstotal.setSide("home");
        battersstatstotal.getPlayerstats().add(playerstats1);
        battersstatstotal.getPlayerstats().add(playerstats2);

        Pitchersstatstotal pitchersstatstotal = new Pitchersstatstotal();
        pitchersstatstotal.setSide("away");
        pitchersstatstotal.getPlayerstats().add(playerstats1);
        pitchersstatstotal.getPlayerstats().add(playerstats2);

        Teamstats teamstats1 = new Teamstats();
        teamstats1.getStats().add(buildStats("stats5", "value5"));
        teamstats1.getStats().add(buildStats("stats6", "value6"));
        teamstats1.setSide("home");
        teamstats1.setType("total");

        Teamstats teamstats2 = new Teamstats();
        teamstats2.getStats().add(buildStats("stats7", "value7"));
        teamstats2.getStats().add(buildStats("stats8", "value8"));
        teamstats2.setSide("away");
        teamstats2.setType("inning5");

        Statistics statistics = new Statistics();
        statistics.setBattersstatstotal(battersstatstotal);
        statistics.setPitchersstatstotal(pitchersstatstotal);
        statistics.getTeamstats().add(teamstats1);
        statistics.getTeamstats().add(teamstats2);

        return statistics;
    }

    static Stats buildStats(String name, String value) {
        Stats stats = new Stats();
        stats.setName(name);
        stats.setValue(value);
        return stats;
    }

    static ScoutEventEntity buildScoutEventEntity(final int valueBase) {
        ScoutEventEntity result = new ScoutEventEntity();

        result.setId(11337799 + valueBase);
        result.setAutomatic(101102 + valueBase);
        result.setBreakScore("event" + valueBase + " breakscore");
        result.setExtraInfo(74123L + valueBase);
        result.setExtraInfoDarts(valueBase % 2 == 0 ? "extra info darts" : null);
        result.setExtraInfoSnooker(valueBase % 3 == 0 ? "extra info snooker" : null);
        result.setFrameNumber(14523 + valueBase);
        result.setFrameScore((5 + valueBase) + ":" + valueBase);
        result.setGameNumber(11554422 + valueBase);
        result.setGameScore((15 + valueBase) + ":" + valueBase);
        result.setInfo("event" + valueBase + " info");
        result.setLegScore((3 + valueBase) + ":" + valueBase);
        result.setMatchScore((13 + valueBase) + ":" + valueBase);
        result.setMatchTime((876 + valueBase) + ":" + valueBase);
        result.setPeriodNumber(77440 + valueBase);
        result.setPlayer1Id(1 + valueBase);
        result.setPlayer2Id(2 + valueBase);
        result.setPosX(3 + valueBase);
        result.setPosY(4 + valueBase);
        result.setRemainingTimeInPeriod((123 + valueBase) + ":" + valueBase);
        result.setSetNumber(5 + valueBase);
        result.setSetScore((13 + valueBase) + ":" + valueBase);
        result.setInningHalf(getEnumValue(Inning.values(), valueBase));
        result.setServer(getEnumValue(Team.values(), valueBase));
        result.setSide(getEnumValue(Team.values(), valueBase));
        result.setType(EventType.ATTACK);//TODO that enum should be removed
        result.setTypeId(1126);// + valueBase);
        result.setHappenedAt(new DateTime(12234556L + valueBase, DateTimeZone.UTC));
        result.setRefsTime(valueBase % 2 == 0 ? (long) valueBase : null);
        result.setFirstBaseLoaded(valueBase % 3 == 0);
        result.setSecondBaseLoaded(valueBase % 2 == 0);
        result.setThirdBaseLoaded(valueBase % 4 == 0);
        result.setConversionType(1 - valueBase);
        result.setTouchdownType(7 + valueBase);
        result.setServerTime(new DateTime(17 + valueBase, DateTimeZone.UTC));
        if(valueBase % 3 != 0){
            result.setScorerNotConfirmed(valueBase % 2 == 0 ? true : false);
        }
        return result;
    }
    //------IncomingMessage------Score------>>>>>>--------------------------<<<<<<------IncomingMessage------Event------
    static void addScores(List<IncomingMessage> msgs, final int valueBase) {
        for(int i = 0; i < valueBase % 3; i++){
            msgs.add(buildScore(Math.abs(valueBase - i)));
        }
    }

    static void addScores(final MatchUpdateEntity result, final int valueBase) {
        result.setScore(result.getScore() != null ? result.getScore() : new HashMap<>());
        result.setScores(result.getScores() != null ? result.getScores() : new ArrayList<>());

        for(int i = 0; i < valueBase % 3; i++){
            ScoreEntity score = buildScoreEntitty(Math.abs(valueBase - i));
            if(score != null) {
                result.getScore().put(score.getType(), new HomeAway<>(score.getTeam1(), score.getTeam2()));
                result.getScores().add(score);
            }
        }
    }

    static Score buildScore(final int valueBase) {
        Score score = null;
        for(int i = 0; i < valueBase; i++) {
            Score newScore = new Score();
            newScore.setT1(80000 + i);
            newScore.setT2(90000 + i);
            newScore.setType("score" + i + " type");
            newScore.setScore(score);
            score = newScore;
        }
        return score;
    }

    static ScoreEntity buildScoreEntitty(final int valueBase){
        ScoreEntity score = null;
        for(int i = 0; i < valueBase; i++) {
            score = new ScoreEntity("score" + i + " type", 80000 + i, 90000 + i, score);
        }
        return score;
    }
    //------IncomingMessage------others------>>>>>>-------------------------<<<<<<------IncomingMessage------Score------
    static Lineups buildLineups(final int valueBase) {
        Lineups result = new Lineups();
        result.setMatchid(741852 + valueBase);
        result.setPreliminary(valueBase % 2 != 0 ? 1 : null);
        for(int i = 0; i < valueBase; i++) {
            result.getPlayer().add(buildPlayer(i));
        }
        for(int i = 0; i < valueBase; i++) {
            result.getManager().add(buildManager(i));
        }
        for(int i = 0; i < valueBase; i++) {
            result.getTeamofficial().add(buildTeamOfficial(i));
        }
        return result;
    }

    static LineupsEntity buildLineupsEntity(final int valueBase) {
        LineupsEntity result = new LineupsEntity();
        result.setMatchId(741852 + valueBase);
        result.setPreliminary(valueBase % 2 != 0 ? true : null);
        for(int i = 0; i < valueBase; i++) {
            result.getPlayers().add(buildPlayerEntity(i));
        }
        for(int i = 0; i < valueBase; i++) {
            result.getManagers().add(buildManagerEntity(i));
        }
        for(int i = 0; i < valueBase; i++) {
            result.getTeamOfficials().add(buildTeamOfficialEntity(i));
        }
        return result;
    }

    static Manager buildManager(final int valueBase) {
        Manager result = new Manager();
        result.setId(452 + valueBase);
        result.setName("manager" + valueBase + " name");
        result.setTeam(Integer.parseInt(getEnumValue(Team.values(), valueBase).getValue()[0]));
        return result;
    }

    static ManagerEntity buildManagerEntity(final int valueBase) {
        ManagerEntity result = new ManagerEntity();
        result.setId(452 + valueBase);
        result.setName("manager" + valueBase + " name");
        result.setTeam(getEnumValue(Team.values(), valueBase));
        return result;
    }

    static Teamofficial buildTeamOfficial(final int valueBase) {
        Teamofficial result = new Teamofficial();
        result.setId(878 + valueBase);
        result.setName("teamofficial" + valueBase + " name");
        result.setTeam(Integer.parseInt(getEnumValue(Team.values(), valueBase).getValue()[0]));
        return result;
    }

    static TeamOfficialEntity buildTeamOfficialEntity(final int valueBase) {
        TeamOfficialEntity result = new TeamOfficialEntity();
        result.setId(878 + valueBase);
        result.setName("teamofficial" + valueBase + " name");
        result.setTeam(getEnumValue(Team.values(), valueBase));
        return result;
    }

    static Player buildPlayer(final int valueBase) {
        Player result = new Player();
        result.setId(113355 + valueBase);
        result.setName("player" + valueBase + " name");
        result.setShirtnumber(123654789 + valueBase);
        result.setSubstitute(valueBase % 2 == 0);
        result.setTeam(Integer.parseInt(getEnumValue(Team.values(), valueBase).getValue()[0]));
        result.setPosition((12 + valueBase) + ":" + (7 + valueBase));
        result.setNickname("player" + valueBase + " nickname");
        result.setOrder(valueBase);

        Attributes attributes = new Attributes();
        for(int i = 0; i < valueBase; i++) {
            attributes.getAttribute().add(buildAttribute(valueBase));
        }
        result.setAttributes(attributes);

        Matchroles matchroles = new Matchroles();
        for(int i = 0; i < valueBase; i++) {
            Matchrole matchrole = new Matchrole();
            matchrole.setId(17 + i);
            matchrole.setDescription("matchrole" + i + " description");
            matchroles.getMatchrole().add(matchrole);
        }
        result.setMatchroles(matchroles);

        return result;
    }

    static PlayerEntity buildPlayerEntity(final int valueBase) {
        PlayerEntity result = new PlayerEntity();
        result.setId(113355 + valueBase);
        result.setName("player" + valueBase + " name");
        result.setShirtNumber(123654789 + valueBase);
        result.setSubstitute(valueBase % 2 == 0);
        result.setTeam(getEnumValue(Team.values(), valueBase));
        result.setPosition((12 + valueBase) + ":" + (7 + valueBase));
        result.setNickname("player" + valueBase + " nickname");
        result.setOrder(valueBase);

        List<AttributeEntity> attributes = new ArrayList<>();
        for(int i = 0; i < valueBase; i++) {
            attributes.add(buildAttributeEntity(valueBase));
        }
        result.setAttributes(attributes);

        List<MatchRoleEntity> matchRoles = new ArrayList<>();
        for(int i = 0; i < valueBase; i++) {
            MatchRoleEntity matchRole = new MatchRoleEntity();
            matchRole.setId(17 + i);
            matchRole.setDescription("matchrole" + i + " description");
            matchRoles.add(matchRole);
        }
        result.setMatchRoles(matchRoles);

        return result;
    }

    static Attribute buildAttribute(final int valueBase) {
        Attribute result = new Attribute();
        result.setType("Attribute" + valueBase + " type");
        result.setTypeid(166+ valueBase);
        result.setValue("Attribute" + valueBase + " value");
        result.setValueid(661 + valueBase);
        return result;
    }

    static AttributeEntity buildAttributeEntity(final int valueBase) {
        AttributeEntity result = new AttributeEntity();
        result.setType("Attribute" + valueBase + " type");
        result.setTypeId(166+ valueBase);
        result.setValue("Attribute" + valueBase + " value");
        result.setValueId(661 + valueBase);
        return result;
    }

    static Matchstop buildMatchstop(final int valueBase) {
        Matchstop matchstop = new Matchstop();
        matchstop.setMatchid(112211 + valueBase);
        matchstop.setReason("matchstop"+ valueBase + " reason");
        return matchstop;
    }

    static MatchStopEntity buildMatchStopEntity(final int valueBase) {
        MatchStopEntity result = new MatchStopEntity();
        result.setMatchId(112211 + valueBase);
        result.setReason("matchstop"+ valueBase + " reason");
        return result;
    }

    static Bookmatch buildBookmatch(final int valueBase) {
        Bookmatch bookmatch = new Bookmatch();
        bookmatch.setMatchid(54223L + valueBase);
        bookmatch.setMessage("bookmatch" + valueBase + " message");
        bookmatch.setResult(getEnumValue(BookMatchResult.values(), valueBase).getValue());
        return bookmatch;
    }

    static MatchBookingEntity buildMatchBookingEntity(final int valueBase) {
        MatchBookingEntity result = new MatchBookingEntity();
        result.setMatchId(54223L + valueBase);
        result.setMessage("bookmatch" + valueBase + " message");
        result.setResult(getEnumValue(BookMatchResult.values(), valueBase));
        return result;
    }

    static void addSubteams(List<IncomingMessage> msgs, final int valueBase) {
        for(int i = 0; i < valueBase % 3; i++){
            Subteam team = new Subteam();
            team.setId(12 + valueBase);
            team.setName("team" + valueBase);
            team.setParent(134 + valueBase);
            msgs.add(team);
        }
    }

    static List<SubteamEntity> buildSubteams(final int valueBase) {
        List<SubteamEntity> subs = new ArrayList<>();
        for(int i = 0; i < valueBase % 3; i++){
            subs.add(new SubteamEntity(12 + valueBase, "team" + valueBase, 134 + valueBase));
        }
        return subs;
    }
    //------Utils------>>>>>>----------------------------------------------<<<<<<------IncomingMessage------simple------
    private static <T extends EntityEnum> T getEnumValue(T[] members, int valueBase) {
        int index = valueBase % members.length;
        return members[index];
    }
    //hack for kickoff team fields
    private static Team getSpecificTeam(int valueBase) {
        Team team = getEnumValue(Team.values(), valueBase);
        return team == Team.NONE ? null : team;
    }
}
