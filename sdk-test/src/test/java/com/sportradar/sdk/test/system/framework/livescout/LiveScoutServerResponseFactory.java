package com.sportradar.sdk.test.system.framework.livescout;

import com.sportradar.sdk.proto.dto.incoming.livescout.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

public class LiveScoutServerResponseFactory {

    public static final String VALID = "valid";
    public static final String INVALID = "invalid";

    public static Bookmatch buildBookmatch(long matchid) {
        Bookmatch response = new ObjectFactory().createBookmatch();

        response.setMatchid(matchid);
        response.setResult(VALID);
        response.setMessage("OK");

        return response;
    }

    public static Ct buildCt() {
        return new Ct();
    }

    public static Infos buildInfos(long matchid) {

        Infos infos = new Infos();
        infos.setMatchid(matchid);

        Info info = new Info();
        info.setHeader("H2H Link");
        info.setLink(true);
        info.setOrder(1);
        info.setValue("http://www.betradar.com/betradar/SystemMain.php?page=oddspage_show&amp;sport=1&amp;match=" + matchid);

        infos.getInfo().add(info);

        return infos;
    }

    public static Login buildLogin(int bookmakerid, boolean loggedin) {
        Login login = new ObjectFactory().createLogin();
        login.setResult(loggedin ? VALID : INVALID);
        if (loggedin) {
            Flashconfig flashconfig = new Flashconfig();

            User user = new User();
            user.setBookmakerid(bookmakerid);
            login.setUser(user);

            Eventconfig eventconfig = new Eventconfig();
            eventconfig.setChangebackgroundcolour(false);
            eventconfig.setChangebackgroundcolourpossible(false);
            eventconfig.setEventtype(30);
            eventconfig.setPlaysound(false);
            eventconfig.setPopup(true);
            flashconfig.getEventconfig().add(eventconfig);

            eventconfig = new Eventconfig();
            eventconfig.setChangebackgroundcolour(false);
            eventconfig.setChangebackgroundcolourpossible(false);
            eventconfig.setEventtype(40);
            eventconfig.setPlaysound(false);
            eventconfig.setPopup(false);
            flashconfig.getEventconfig().add(eventconfig);

            login.setFlashconfig(flashconfig);
        }
        return login;
    }

    public static Match buildMatch(long matchid, String feedType) {

        Match match = new ObjectFactory().createMatch();
        match.setFeedtype(feedType);
        match.setDistance(10);
        match.setMatchid(matchid);
        match.setStart(1244826000000L);
        match.setT1Id(1323037);
        match.setT1Name("1. FC UNION BERLIN");
        match.setT2Id(1323039);
        match.setT2Name("VFL OSNABRÄCK");

        Status status = new Status();
        status.setId(0);
        status.setName("NOT_STARTED");
        status.setStart(0);
        match.getStatusOrScoreOrRed().add(status);

        Score score = new Score();
        score.setT1(0);
        score.setT2(0);
        score.setType("current");
        match.getStatusOrScoreOrRed().add(score);

        Tournament tournament = new Tournament();
        tournament.setId(5862);
        tournament.setName("Bundesliga U19");
        match.getStatusOrScoreOrRed().add(tournament);

        Category category = new Category();
        category.setId(122);
        category.setName("Germany Amateur");
        match.getStatusOrScoreOrRed().add(category);

        Sport sport = new Sport();
        sport.setId(1);
        sport.setName("Soccer");
        match.getStatusOrScoreOrRed().add(sport);


        return match;
    }

    public static Matchlist buildMatchList() {
        Matchlist matchlist = new ObjectFactory().createMatchlist();

        matchlist.getMatch().add(buildMatch(1058815L, "full"));
        matchlist.getMatch().add(buildMatch(1058816L, "full"));

        return matchlist;
    }

    public static Matchstop buildMatchStop(long matchid) {
        Matchstop matchstop = new ObjectFactory().createMatchstop();
        matchstop.setMatchid(matchid);
        matchstop.setReason("User unsubscribed to match");

        return matchstop;
    }

    public static Matchstop buildMatchSubscriptionFailed(long matchid) {
        Matchstop matchstop = new ObjectFactory().createMatchstop();
        matchstop.setMatchid(matchid);
        matchstop.setReason("Bookmaker already has maximum number of subscriptions for this match");

        return matchstop;
    }

    public static Servertime buildServertime() {
        Servertime servertime = new ObjectFactory().createServertime();
        servertime.setValue(DateTime.now().getMillis());

        return servertime;
    }

    public static Dbfail buildDbfail(String request) {
        Dbfail dbfail = new Dbfail();
        dbfail.setRequest(request);

        return dbfail;
    }

    public static OddsSuggestions buildOddsSuggestions(long matchid) {

        OddsSuggestions oddsSuggestions = new OddsSuggestions();
        oddsSuggestions.setMatchid(matchid);
        Odds odds = new Odds();
        odds.setBookId(1);
        odds.setChangenumber(1);
        odds.setDescription("Who wins the rest of the match?");
        odds.setGuthMatchId((int) matchid);
        odds.setManualActive((byte) 1);
        odds.setMatchId(matchid);
        odds.setSpecialOddsValue("0:0");
        odds.setSubtype(4);
        odds.setType(19);
        odds.setValidDate(1244794244648L);
        oddsSuggestions.getOdds().add(odds);

        OddsField oddsField = new OddsField();
        oddsField.setDescription("Home");
        oddsField.setSide("home");
        oddsField.setValue(BigDecimal.valueOf(2.5));
        odds.getOddsField().add(oddsField);

        oddsField = new OddsField();
        oddsField.setDescription("Draw");
        oddsField.setSide("draw");
        oddsField.setValue(BigDecimal.valueOf(2.9));
        odds.getOddsField().add(oddsField);

        oddsField = new OddsField();
        oddsField.setDescription("Away");
        oddsField.setSide("away");
        oddsField.setValue(BigDecimal.valueOf(2.85));
        odds.getOddsField().add(oddsField);


        return oddsSuggestions;
    }

    public static Match buildMatchUpdate(long matchid) {

        return buildMatch(matchid, "delta");
    }

    public static Matchlistupdate buildMatchlistupdate(List<Integer> matchids) {
        Matchlistupdate matchlistupdate = new Matchlistupdate();

        for (Integer matchid : matchids) {
            matchlistupdate.getMatch().add(buildMatch(matchid, "delta"));
        }

        return matchlistupdate;
    }

    public static Lineups buildLineups(long matchid) {
        Lineups lineups = new Lineups();

        lineups.setMatchid(matchid);

        Player player = new Player();
        player.setId(521);
        player.setName("Almunia, Manuel");
        player.setShirtnumber(1);
        player.setSubstitute(false);
        player.setTeam(1);
        lineups.getPlayer().add(player);

        player = new Player();
        player.setId(1053);
        player.setName("Sagna, Bacary");
        player.setShirtnumber(3);
        player.setSubstitute(false);
        player.setTeam(1);
        lineups.getPlayer().add(player);


        return lineups;
    }
}
