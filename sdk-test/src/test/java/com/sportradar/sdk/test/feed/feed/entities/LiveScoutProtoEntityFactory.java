package com.sportradar.sdk.test.feed.feed.entities;

import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.livescout.enums.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.incoming.livescout.*;

import javax.xml.namespace.QName;
import java.math.BigDecimal;

public class LiveScoutProtoEntityFactory {

    public static Bookmatch buildBookmatch() {
        Bookmatch bookmatch = new Bookmatch();
        bookmatch.setMessage("bookmatch message");
        bookmatch.setResult(BookMatchResult.VALID.name().toLowerCase());
        return bookmatch;
    }

    public static Infos buildInfos() {
        return new Infos();
    }

    public static Matchstop buildMatchstop() {
        Matchstop matchstop = new Matchstop();
        matchstop.setMatchid(112211);
        matchstop.setReason("matchstop reason");
        return matchstop;
    }

    public static Matchlist buildMatchlist(Class clazz) throws Exception {
        Matchlist matchlist = new Matchlist();
        matchlist.getMatch().add(buildMatch(clazz));
        return matchlist;
    }

    public static Matchlistupdate buildMatchlistupdate(Class clazz) throws Exception {
        Matchlistupdate matchlistupdate = new Matchlistupdate();
        matchlistupdate.getMatch().add(buildMatch(clazz));
        return matchlistupdate;
    }

    public static OddsSuggestions buildOddsSuggestions() {
        OddsSuggestions oddsSuggestions = new OddsSuggestions();
        oddsSuggestions.setMatchid(123654);
        oddsSuggestions.getOdds().add(buildOdds());
        return oddsSuggestions;
    }

    public static Attacks buildAttacks() {
        Attacks attacks = new Attacks();
        attacks.setT1(11);
        attacks.setT2(22);
        return attacks;
    }

    public static Category buildCategory() {
        Category category = new Category();
        category.setId(33);
        category.setName("category name");
        return category;
    }

    public static Corners buildCorners() {
        Corners corners = new Corners();
        corners.setT1(44);
        corners.setT2(55);
        return corners;
    }

    public static Dangerousattacks buildDangerousattacks() {
        Dangerousattacks dangerousattacks = new Dangerousattacks();
        dangerousattacks.setT1(66);
        dangerousattacks.setT2(77);
        return dangerousattacks;
    }

    public static Directfoulsperiod buildDirectfoulsperiod() {
        Directfoulsperiod directfoulsperiod = new Directfoulsperiod();
        directfoulsperiod.setT1(88);
        directfoulsperiod.setT2(99);
        return directfoulsperiod;
    }

    public static Directfreekicks buildDirectfreekicks() {
        Directfreekicks directfreekicks = new Directfreekicks();
        directfreekicks.setT1(111);
        directfreekicks.setT2(222);
        return directfreekicks;
    }

    public static Black buildBlack() {
        Black black = new Black();
        black.setT1(333);
        black.setT2(444);
        return black;
    }

    public static Court buildCourt() {
        Court court = new Court();
        court.setId(555);
        court.setName("court name");
        return court;
    }

    public static Events buildEvents() {
        Events events = new Events();
        events.getEvent().add(buildEvent());
        return events;
    }

    public static Freekicks buildFreekicks() {
        Freekicks freekicks = new Freekicks();
        freekicks.setT1(666);
        freekicks.setT2(777);
        return freekicks;
    }

    public static Freethrows buildFreethrows() {
        Freethrows freethrows = new Freethrows();
        freethrows.setT1(888);
        freethrows.setT2(999);
        return freethrows;
    }

    public static Goalkeepersaves buildGoalkeepersaves() {
        Goalkeepersaves goalkeepersaves = new Goalkeepersaves();
        goalkeepersaves.setT1(1111);
        goalkeepersaves.setT2(2222);
        return goalkeepersaves;
    }

    public static Goalkicks buildGoalkicks() {
        Goalkicks goalkicks = new Goalkicks();
        goalkicks.setT1(3333);
        goalkicks.setT2(4444);
        return goalkicks;
    }

    public static Injuries buildInjuries() {
        Injuries injuries = new Injuries();
        injuries.setT1(5555);
        injuries.setT2(6666);
        return injuries;
    }

    public static Kickoffteam buildKickoffteam() {
        Kickoffteam kickoffteam = new Kickoffteam();
        kickoffteam.setTeam(1);
        return kickoffteam;
    }

    public static Offsides buildOffsides() {
        Offsides offsides = new Offsides();
        offsides.setT1(8888);
        offsides.setT2(9999);
        return offsides;
    }

    public static Penalties buildPenalties() {
        Penalties penalties = new Penalties();
        penalties.setT1(11111);
        penalties.setT2(22222);
        return penalties;
    }

    public static Pitchconditions buildPitchconditions() {
        Pitchconditions pitchconditions = new Pitchconditions();
        pitchconditions.setId(33333);
        pitchconditions.setName(PitchConditions.MEDIUM.name());
        return pitchconditions;
    }

    public static Possession buildPossession() {
        Possession possession = new Possession();
        possession.setT1(44444);
        possession.setT2(55555);
        possession.setTeam(Team.AWAY.name().toLowerCase());
        return possession;
    }

    public static Red buildRed() {
        Red red = new Red();
        red.setT1(66666);
        red.setT2(77777);
        return red;
    }

    public static Score buildScore() {
        Score score = new Score();
        score.setT1(88888);
        score.setT2(99999);
        score.setScore(null);
        score.setType("score type");
        return score;
    }

    public static Serve buildServe() {
        Serve serve = new Serve();
        serve.setTeam(Team.NONE.name().toLowerCase());
        return serve;
    }

    public static Shotsblocked buildShotsblocked() {
        Shotsblocked shotsblocked = new Shotsblocked();
        shotsblocked.setT1(111111);
        shotsblocked.setT2(222222);
        return shotsblocked;
    }

    public static Shotsofftarget buildShotsofftarget() {
        Shotsofftarget shotsofftarget = new Shotsofftarget();
        shotsofftarget.setT1(333333);
        shotsofftarget.setT2(444444);
        return shotsofftarget;
    }

    public static Shotsontarget buildShotsontarget() {
        Shotsontarget shotsontarget = new Shotsontarget();
        shotsontarget.setT1(555555);
        shotsontarget.setT2(666666);
        return shotsontarget;
    }

    public static Sport buildSport() {
        Sport sport = new Sport();
        sport.setId(777777);
        sport.setName("sport name");
        return sport;
    }

    public static Status buildStatus() {
        Status status = new Status();
        status.setId(888888);
        status.setName(ScoutMatchStatus.HALFTIME.name());
        status.setStart(999999);
        return status;
    }

    public static Surfacetype buildSurfacetype() {
        Surfacetype surfacetype = new Surfacetype();
        surfacetype.setId(1111111);
        surfacetype.setName(SurfaceType.GRASS.name());
        return surfacetype;
    }

    public static Suspensions buildSuspensions() {
        Suspensions suspensions = new Suspensions();
        suspensions.setT1(2222222);
        suspensions.setT2(3333333);
        suspensions.setPowerplay(0);
        return suspensions;
    }

    public static Throwins buildThrowins() {
        Throwins throwins = new Throwins();
        throwins.setT1(4444444);
        throwins.setT2(5555555);
        return throwins;
    }

    public static Tiebreak buildTiebreak() {
        Tiebreak tiebreak = new Tiebreak();
        tiebreak.setValue(6666666);
        return tiebreak;
    }

    public static Tournament buildTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(7777777);
        tournament.setName("tournament name");
        return tournament;
    }

    public static Weatherconditions buildWeatherconditions() {
        Weatherconditions weatherconditions = new Weatherconditions();
        weatherconditions.setId(8888888);
        weatherconditions.setName(WeatherConditions.UNKNOWN.name());
        return weatherconditions;
    }

    public static Yellow buildYellow() {
        Yellow yellow = new Yellow();
        yellow.setT1(9999999);
        yellow.setT2(11111111);
        return yellow;
    }

    public static IncomingMessage buildIncomingMessage(final Class clazz) throws Exception {
        IncomingMessage incomingMessage = null;
        switch (clazz.getSimpleName()) {
            case "Attacks":
                incomingMessage = buildAttacks();
                break;
            case "Black":
                incomingMessage = buildBlack();
                break;
            case "Category":
                incomingMessage = buildCategory();
                break;
            case "Corners":
                incomingMessage = buildCorners();
                break;
            case "Court":
                incomingMessage = buildCourt();
                break;
            case "Dangerousattacks":
                incomingMessage = buildDangerousattacks();
                break;
            case "Directfoulsperiod":
                incomingMessage = buildDirectfoulsperiod();
                break;
            case "Directfreekicks":
                incomingMessage = buildDirectfreekicks();
                break;
            case "Events":
                incomingMessage = buildEvents();
                break;
            case "Freekicks":
                incomingMessage = buildFreekicks();
                break;
            case "Freethrows":
                incomingMessage = buildFreethrows();
                break;
            case "Goalkeepersaves":
                incomingMessage = buildGoalkeepersaves();
                break;
            case "Goalkicks":
                incomingMessage = buildGoalkicks();
                break;
            case "Injuries":
                incomingMessage = buildInjuries();
                break;
            case "Kickoffteam":
                incomingMessage = buildKickoffteam();
                break;
            case "Offsides":
                incomingMessage = buildOffsides();
                break;
            case "Penalties":
                incomingMessage = buildPenalties();
                break;
            case "Pitchconditions":
                incomingMessage = buildPitchconditions();
                break;
            case "Possession":
                incomingMessage = buildPossession();
                break;
            case "Red":
                incomingMessage = buildRed();
                break;
            case "Score":
                incomingMessage = buildScore();
                break;
            case "Serve":
                incomingMessage = buildServe();
                break;
            case "Shotsblocked":
                incomingMessage = buildShotsblocked();
                break;
            case "Shotsofftarget":
                incomingMessage = buildShotsofftarget();
                break;
            case "Shotsontarget":
                incomingMessage = buildShotsontarget();
                break;
            case "Sport":
                incomingMessage = buildSport();
                break;
            case "Status":
                incomingMessage = buildStatus();
                break;
            case "Surfacetype":
                incomingMessage = buildSurfacetype();
                break;
            case "Suspensions":
                incomingMessage = buildSuspensions();
                break;
            case "Throwins":
                incomingMessage = buildThrowins();
                break;
            case "Tiebreak":
                incomingMessage = buildTiebreak();
                break;
            case "Tournament":
                incomingMessage = buildTournament();
                break;
            case "Weatherconditions":
                incomingMessage = buildWeatherconditions();
                break;
            case "Yellow":
                incomingMessage = buildYellow();
                break;
            default:
                throw new IllegalArgumentException("invalid clazz parameter");
        }
        return incomingMessage;
    }

    public static Match buildMatch(Class clazz) throws Exception {
        Match match = new Match();
        match.getStatusOrScoreOrRed().add(buildIncomingMessage(clazz));
        match.setT2Name("match t2 name");
        match.setT2Id(1);
        match.setT1Name("match t1 name");
        match.setT1Id(2);
        match.setStart(3l);
        match.setMatchid(4);
        match.setFeedtype(ScoutFeedType.FULL.name().toLowerCase());
        match.setExtrainfo(5);
        match.setDistance(6);
        match.setBetstatus(MatchBetStatus.NOT_STARTED.name());
        match.setFirstserve(Team.HOME.name().toLowerCase());
        match.setFirstservetiebreak(Team.AWAY.name().toLowerCase());
        match.setNumberofsets(7);
        match.setTiebreaklastset(8);
        match.setConnectionstatus(9);
        match.setDc(10);
        match.setCoveredfrom(Coverage.VENUE.name().toLowerCase());
        match.setTimerunning(11);
        match.setWonjumpball(Team.AWAY.name().toLowerCase());
        match.setBooked(12);
        match.setSt1Id(13);
        match.setSt2Id(14);
        match.setMatchtime("match time");
        match.setSex(Sex.MIXED.ordinal());
        match.setActive(Byte.parseByte("3"));
        return match;
    }

    public static Lineups buildLineups() {
        Lineups lineups = new Lineups();
        lineups.setMatchid(741852);
        lineups.getPlayer().add(buildPlayer());
        return lineups;
    }

    public static Player buildPlayer() {
        Player player = new Player();
        player.setId(113355);
        player.setName("player name");
        player.setShirtnumber(123654789);
        player.setSubstitute(false);
        player.setTeam(Team.HOME.ordinal());
        return player;
    }

    public static Event buildEvent() {
        Event event = new Event();
        event.setId(11337799);
        event.setAutomatic(101102);
        event.setBreakscore("event breakscore");
        event.setExtrainfo(74123L);
        event.setExtrainfodarts("extra info darts");
        event.setExtrainfosnooker("extra info snooker");
        event.setFramenumber(14523);
        event.setFramescore("event frame score");
        event.setGamenumber(11554422);
        event.setGamescore("event gamescore");
        event.setInfo("event info");
        event.setLegscore("event legscore");
        event.setMatchscore("event matchscore");
        event.setMtime("event mtime");
        event.setPeriodnumber(77440);
        event.setPlayer1(1);
        event.setPlayer2(2);
        event.setPosx(3);
        event.setPosy(4);
        event.setRemainingtimeperiod("event remaining time period");
        event.setSetnumber(5);
        event.setSetscore("event score");
        event.setType(6);
        event.setStime(7);
        event.setSide(Team.HOME.name().toLowerCase());
        return event;
    }

    public static QName buildQName() {
        return new QName("localhost", "80");
    }

    public static String buildString() {
        return "test string";
    }

    public static Odds buildOdds() {
        Odds odds = new Odds();
        odds.getOddsField().add(buildOddsField());
        odds.setValidDate(123456789);
        odds.setType(1122);
        odds.setSubtype(1223);
        odds.setSpecialOddsValue("special odds value");
        odds.setMatchId(10001);
        odds.setManualActive(Byte.parseByte("1"));
        odds.setGuthMatchId(4554);
        odds.setDescription("description");
        odds.setChangenumber(7458);
        odds.setBookId(7423);
        odds.setPref(false);
        odds.setAlsoOdds(80010);
        odds.setSj(12.34d);
        return odds;
    }

    public static OddsField buildOddsField() {
        OddsField oddsField = new OddsField();
        oddsField.setDescription("odds field description");
        oddsField.setSide("odds field side");
        oddsField.setValue(new BigDecimal(123321));
        return oddsField;
    }
}
