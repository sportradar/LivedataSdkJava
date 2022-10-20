package com.sportradar.sdk.test.feed.feed.entities;

import com.sportradar.sdk.common.classes.CommonUtils;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.common.entities.HomeAway;
import com.sportradar.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.feed.livescout.enums.*;
import com.sportradar.sdk.proto.dto.incoming.livescout.*;
import org.junit.Test;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"unchecked", "JavaDoc"})
public class JaxbLiveScoutEntityFactoryHelperTest {

    public JaxbLiveScoutEntityFactoryHelperTest() {
    }

    private void assertThatMatchBookEntityEqualToBookmatch(MatchBookingEntity result, Bookmatch input) {
        assertThat(result.getMessage(), equalTo(input.getMessage()));
        if (result.getResult() != null) {
            assertThat(result.getResult().isLiteralValueEqual(input.getResult()), equalTo(true));
        } else {
            assertThat(input.getResult(), equalTo(null));
        }
        assertThat(result.getEventId(), equalTo(null));
        assertThat(result.getAdditionalData().size(), equalTo(0));
    }

    private void assertThatInfoEqualToInfo(Info result, Info input) {
        if (result == null || input == null) {
            return;
        }
        assertThat(result.getValue(), equalTo(input.getValue()));
        assertThat(result.getHeader(), equalTo(input.getHeader()));
        assertThat(result.getOrder(), equalTo(input.getOrder()));
    }

    private void assertThatInfoEqualToInfo(List<Info> result, List<Info> input) {
        assertThat(result.size(), equalTo(input.size()));
        for (int i = 0; i < result.size(); ++i) {
            assertThatInfoEqualToInfo(result.get(i), input.get(i));
        }
    }


    private void assertThatMatchStopEntityEqualToMatchStop(MatchStopEntity result, Matchstop input) {
        assertThat(result.getReason(), equalTo(input.getReason()));
        if (result.getEventId() != null) {
            assertThat(result.getEventId().getEventId(), equalTo(input.getMatchid()));
        } else {
            assertThat(input.getMatchid(), equalTo(null));
        }
        assertThat(result.getAdditionalData().size(), equalTo(0));
    }

    private <T> List<T> extractItemsFromList(List<?> list, Class<T> clazz) {
        List<T> results = new ArrayList<>();
        for (Object o : list) {
            if (clazz.isInstance(o)) {
                results.add(clazz.cast(o));
            }
        }
        return results;
    }

    private void assertThatScoutEventEntityEqualToEvent(ScoutEventEntity result, Event input) throws UnknownEnumException {
        if (result == null && input == null) {
            return;
        }
        assertThat(result.getId(), equalTo(input.getId()));
        assertThat(result.getAutomatic(), equalTo(input.getAutomatic()));
        assertThat(result.getBreakScore(), equalTo(input.getBreakscore()));
        assertThat(result.getExtraInfo(), equalTo(input.getExtrainfo()));
        assertThat(result.getBallNumber(), equalTo(input.getBallnumber()));
        assertThat(result.getDismissalsInInnings(), equalTo(input.getDismissalsininnings()));
        assertThat(result.getEndScore(), equalTo(input.getEndscore()));
        assertThat(result.getExtraInfoBasketball(), equalTo(input.getExtrainfobasketball()));
        assertThat(result.getExtraInfoBowls(), equalTo(input.getExtrainfobowls()));
        assertThat(result.getExtraInfoCricket(), equalTo(input.getExtrainfocricket()));
        assertThat(result.getExtraInfoDarts(), equalTo(input.getExtrainfodarts()));
        assertThat(result.getExtraInfoFootball(), equalTo(input.getExtrainfofootball()));
        assertThat(result.getExtraInfoSnooker(), equalTo(input.getExtrainfosnooker()));
        assertThat(result.getFrameNumber(), equalTo(input.getFramenumber()));
        assertThat(result.getFrameScore(), equalTo(input.getFramescore()));
        assertThat(result.getGameNumber(), equalTo(input.getGamenumber()));
        assertThat(result.getGameScore(), equalTo(input.getGamescore()));
        assertThat(result.getInfo(), equalTo(input.getInfo()));
        assertThat(result.getLegScore(), equalTo(input.getLegscore()));
        assertThat(result.getMatchScore(), equalTo(input.getMatchscore()));
        assertThat(result.getMatchTime(), equalTo(input.getMtime()));
        assertThat(result.getPeriodNumber(), equalTo(input.getPeriodnumber()));
        assertThat(result.getPlayer1Id(), equalTo(input.getPlayer1()));
        assertThat(result.getPlayer2Id(), equalTo(input.getPlayer2()));
        assertThat(result.getPosX(), equalTo(input.getPosx()));
        assertThat(result.getPosY(), equalTo(input.getPosy()));
        assertThat(result.getRemainingTimeInPeriod(), equalTo(input.getRemainingtimeperiod()));
        assertThat(result.getRunsInInnings(), equalTo(input.getRunsininnings()));
        assertThat(result.getServer(), equalTo(Team.getTeamFromLiteralValue(input.getServer())));
        assertThat(result.getServerTime().getMillis(), equalTo(input.getStime()));
        assertThat(result.getSetNumber(), equalTo(input.getSetnumber()));
        assertThat(result.getSetScore(), equalTo(input.getSetscore()));
        assertThat(result.getSide().name().toLowerCase(), equalTo(input.getSide()));
        assertThat(result.getType().getTypeId(), equalTo(Integer.toString(input.getType())));
    }

    private void assertThatScoutEventEntityEqualToEvent(List<ScoutEventEntity> result, List<Event> input) throws UnknownEnumException {
        if (result == null && input == null) {
            return;
        }
        assertThat(result.size(), equalTo(input.size()));
        for (int i = 0; i < result.size(); ++i) {
            assertThatScoutEventEntityEqualToEvent(result.get(i), input.get(i));
        }
    }

    private void assertThatMatchUpdateEntityEqualToMatch(List<MatchUpdateEntity> result, List<Match> input, Class clazz) throws UnknownEnumException {
        assertThat(result.size(), equalTo(input.size()));

        for (int i = 0; i < result.size(); ++i) {
            assertThatMatchUpdateEntityEqualToMatch(result.get(i), input.get(i), clazz);
        }
    }

    private void assertThatMatchListEntityEqualToMatchlist(MatchListEntity result, Matchlist input, Class clazz) throws UnknownEnumException {
        if (result == null && input == null) {
            return;
        }
        assertThatMatchUpdateEntityEqualToMatch(result.getMatches(), input.getMatch(), clazz);
    }

    private void assertThatMatchListUpdateEntityEqualToMatchlistupdate(MatchListUpdateEntity result,
                                                                       Matchlistupdate input,
                                                                       Class clazz) throws UnknownEnumException {
        assertThat(result.getEventId(), equalTo(null));
        assertThatMatchUpdateEntityEqualToMatch(result.getMatches(), input.getMatch(), clazz);
    }

    private void assertThatScoutOddsEntityEqualToOdds(ScoutOddsEntity result,
                                                      Odds input) {
        assertThat(result.getMatchId(), equalTo(input.getMatchId()));
        assertThat(result.getAlsoOdds(), equalTo(input.getAlsoOdds()));
        assertThat(result.getDescription(), equalTo(input.getDescription()));
        assertThat(result.getGuthMatchId(), equalTo(input.getGuthMatchId()));
        assertThat(result.getSpecialOddsValue(), equalTo(input.getSpecialOddsValue()));
        assertThat(result.getSubType(), equalTo(input.getSubtype()));
        assertThat(result.getType(), equalTo(input.getType()));
        assertThat(result.getValidDate().getMillis(), equalTo(input.getValidDate()));
        assertThat(result.isManualActive(), equalTo(input.getManualActive()!=0));
        assertThat(input.getOddsField().size(), equalTo(1));
        OddsField oddsField = input.getOddsField().get(0);
        for (Map.Entry<String, ScoutOddsFieldEntity> e : result.getValues().entrySet()) {
            ScoutOddsFieldEntity scoutOddsFieldValue = e.getValue();
            assertThat(scoutOddsFieldValue.getSide(), equalTo(oddsField.getSide()));
            assertThat(scoutOddsFieldValue.getDescription(), equalTo(oddsField.getDescription()));
            assertThat(scoutOddsFieldValue.getValue(), equalTo(oddsField.getValue().toString()));
        }

    }

    private void assertThatScoutOddsEntityEqualToOdds(List<ScoutOddsEntity> result,
                                                      List<Odds> input) {
        if (result == null && input == null) {
            return;
        }
        for (int i = 0; i < result.size(); ++i) {
            assertThatScoutOddsEntityEqualToOdds(result.get(i), input.get(i));
        }
    }

    private void assertThatMapQnameStringEqualToMapStringString(Map<String, String> result,
                                                                Map<QName, String> input) {
        for (Map.Entry<QName, String> e : input.entrySet()) {
            String value = result.get(e.getKey().getLocalPart());
            assertThat(e.getValue(), equalTo(value));
        }
    }

    private void assertThatOddsSuggestionsEntityEqualToOddsSuggestions(OddsSuggestionsEntity result,
                                                                       OddsSuggestions input) {
        Map<String, String> mapAdditionalData = result.getAdditionalData();
        assertThat(mapAdditionalData.size(), equalTo(0));
        assertThat(result.getEventId().getEventId(), equalTo(input.getMatchid()));
        assertThat(result.getMatchId(), equalTo(input.getMatchid()));
        assertThatScoutOddsEntityEqualToOdds(result.getOdds(), input.getOdds());
    }

    private void assertThatPlayerEntityEqualToPlayer(PlayerEntity result, Player input) {
        assertThat(result.getId(), equalTo(input.getId()));
        assertThat(result.getName(), equalTo(input.getName()));
        assertThat(result.getPosition(), equalTo(input.getPosition()));
        assertThat(result.isSubstitute(), equalTo(input.isSubstitute()));
        assertThat(result.getShirtNumber(), equalTo(input.getShirtnumber()));
        assertThat(result.getTeam().isLiteralValueEqual(Integer.toString(input.getTeam())), equalTo(true));
        assertThat(result.getEventId(), equalTo(null));
    }

    private void assertThatPlayerEntityEqualToPlayer(List<PlayerEntity> result,
                                                     List<Player> input) {
        if (result == null && input == null) {
            return;
        }
        assertThat(result.size(), equalTo(input.size()));

        for (int i = 0; i < result.size(); ++i) {
            assertThatPlayerEntityEqualToPlayer(result.get(i), input.get(i));
        }
    }

    private void assertThatLineupsEntityEqualToLineups(LineupsEntity result, Lineups input) {
        assertThat(result.getMatchId(), equalTo(input.getMatchid()));
        assertThat(result.getEventId(), equalTo(null));
        assertThatPlayerEntityEqualToPlayer(result.getPlayers(), input.getPlayer());
    }

    private void assertThatMatchHeaderEntityEqualToMatch(MatchHeaderEntity result,
                                                         Match input) {
        assertThat(result.isActive(), equalTo(CommonUtils.integerToBoolean(input.getActive().intValue())));
        assertThat(result.getBetStatus().name(), equalTo(input.getBetstatus()));
        assertThat(result.isBooked(), equalTo(CommonUtils.integerToBoolean(input.getBooked())));
        assertThat(result.getConnectionStatus(), equalTo(CommonUtils.integerToBoolean(input.getConnectionstatus())));
        assertThat(result.getCoveredFrom().isLiteralValueEqual(input.getCoveredfrom()), equalTo(true));
        assertThat(result.isDeepCoverage(), equalTo(CommonUtils.integerToBoolean(input.getDc())));
        assertThat(result.getDistance(), equalTo(input.getDistance()));
        assertThat(result.getExtraInfo(), equalTo(input.getExtrainfo()));
        assertThat(result.getFeedType().isLiteralValueEqual(input.getFeedtype()), equalTo(true));
        assertThat(result.getFirstServe().isLiteralValueEqual(input.getFirstserve()), equalTo(true));
        assertThat(result.getFirstServeTieBreak().isLiteralValueEqual(input.getFirstservetiebreak()), equalTo(true));
        assertThat(result.getMatchId(), equalTo(input.getMatchid()));
        assertThat(result.getMatchTime(), equalTo(input.getMatchtime()));
        assertThat(result.getNumberOfSets(), equalTo(input.getNumberofsets()));
        assertThat(result.getSex().isLiteralValueEqual(Integer.toString(input.getSex())), equalTo(true));
        assertThat(result.getSt1Id(), equalTo(input.getSt1Id()));
        assertThat(result.getSt2Id(), equalTo(input.getSt2Id()));
        assertThat(result.getStart().getMillis(), equalTo(input.getStart()));
        IdNameTuple idNameTuple = result.getTeam1();
        assertThat(idNameTuple, not(equalTo(null)));
        assertThat(idNameTuple.getId().intValue(), equalTo(input.getT1Id()));
        assertThat(idNameTuple.getName().getInternational(), equalTo(input.getT1Name()));
        idNameTuple = result.getTeam2();
        assertThat(idNameTuple, not(equalTo(null)));
        assertThat(idNameTuple.getId().intValue(), equalTo(input.getT2Id()));
        assertThat(idNameTuple.getName().getInternational(), equalTo(input.getT2Name()));
        assertThat(result.isTieBreakLastSet(), equalTo(CommonUtils.integerToBoolean(input.getTiebreaklastset())));
        assertThat(result.isTimeRunning(), equalTo(CommonUtils.integerToBoolean(input.getTimerunning())));
        assertThat(result.getWonJumpBall().isLiteralValueEqual(input.getWonjumpball()), equalTo(true));
    }

    private void assertThatScoutOddsFieldEntityEqualToOddsFieldEntity(ScoutOddsFieldEntity result,
                                                                      OddsField input) {
        if (result == null && input == null) {
            return;
        }
        assertThat(result.getValue(), equalTo(input.getValue().toString()));
        assertThat(result.getDescription(), equalTo(input.getDescription()));
        assertThat(result.getSide(), equalTo(input.getSide()));
    }

    @Test(expected = NullPointerException.class)
    public void buildMatchBookingEntity_Null_Input_Test() throws Exception {
        Bookmatch input = null;

        JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(input);
    }

    @Test
    public void buildMatchBookingEntity_Empty_Input_Test() throws Exception {
        Bookmatch input = new Bookmatch();

        MatchBookingEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(input);

        assertThat(result, not(equalTo(null)));
        assertThat(result.getEventId(), equalTo(null));
        assertThat(result.getAdditionalData().size(), equalTo(0));
        assertThat(result.getMessage(), equalTo(null));
        assertThat(result.getResult(), equalTo(null));
    }

    @Test
    public void buildMatchBookingEntity_OK_Input_Test() throws Exception {
        Bookmatch input = LiveScoutProtoEntityFactory.buildBookmatch();

        MatchBookingEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(input);

        assertThatMatchBookEntityEqualToBookmatch(result, input);
    }



    @Test(expected = NullPointerException.class)
    public void buildMatchStopEntity_Null_Input_Test() {
        Matchstop input = null;

        JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(input);
    }

    @Test
    public void buildMatchStopEntity_Empty_Input_Test() {
        Matchstop input = new Matchstop();

        MatchStopEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(input);

        assertThat(result, not(equalTo(null)));
        assertThat(result.getMatchId(), equalTo(0l));
        assertThat(result.getAdditionalData().size(), equalTo(0));
        EventIdentifier eventIdentifier = result.getEventId();
        assertThat(eventIdentifier, not(equalTo(null)));
        assertThat(eventIdentifier.getEventId(), equalTo(0l));
        assertThat(result.getReason(), equalTo(null));
    }

    @Test
    public void buildMatchStopEntity_OK_Input_Test() {
        Matchstop input = LiveScoutProtoEntityFactory.buildMatchstop();

        MatchStopEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(input);

        assertThatMatchStopEntityEqualToMatchStop(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildMatchListEntity_Null_Input_Test() throws Exception {
        Matchlist input = null;

        JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);
    }

    @Test
    public void buildMatchListEntity_Empty_Input_Test() throws Exception {
        Matchlist input = new Matchlist();

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThat(result, not(equalTo(null)));
        assertThat(result.getEventId(), equalTo(null));
        assertThat(result.getMatches().size(), equalTo(0));
    }

    private <T> void assertThatMatchUpdateEntityEqualToMatch(MatchUpdateEntity result, Match input, Class clazz) throws UnknownEnumException {
        List<T> incomingMessages = extractItemsFromList(input.getStatusOrScoreOrRed(), clazz);
        T element = null;
        if (incomingMessages.size() != 0) {
            assertThat(incomingMessages.size(), equalTo(1));
            element = incomingMessages.get(0);
        }
        HomeAway<Integer> homeAway;
        IdNameTuple idNameTuple;

        if (element instanceof Attacks) {
            Attacks e = (Attacks) element;
            homeAway = result.getAttacks();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getAttacks(), equalTo(null));
        }

        if (element instanceof Black) {
            Black e = (Black) element;
            homeAway = result.getBlackCards();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));
        } else {
            assertThat(result.getBlackCards(), equalTo(null));
        }

        if (element instanceof Category) {
            Category e = (Category) element;
            idNameTuple = result.getCategory();
            assertThat(idNameTuple, not(equalTo(null)));
            assertThat(idNameTuple.getId().intValue(), equalTo(e.getId()));
            assertThat(idNameTuple.getName().getInternational(), equalTo(e.getName()));
        } else {
            assertThat(result.getCategory(), equalTo(null));
        }

        if (element instanceof Corners) {
            Corners e = (Corners) element;
            homeAway = result.getCorners();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getCorners(), equalTo(null));
        }

        if (element instanceof Court) {
            Court e = (Court) element;
            CourtEntity court = result.getCourt();
            assertThat(court, not(equalTo(null)));
            assertThat(court.getId(), equalTo(e.getId()));
            assertThat(court.getName(), equalTo(e.getName()));

        }

        if (element instanceof Dangerousattacks) {
            Dangerousattacks e = (Dangerousattacks) element;
            homeAway = result.getDangerousAttacks();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getDangerousAttacks(), equalTo(null));
        }

        if (element instanceof Directfoulsperiod) {
            Directfoulsperiod e = (Directfoulsperiod) element;
            homeAway = result.getDirectFoulsPeriod();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getDirectFoulsPeriod(), equalTo(null));
        }

        if (element instanceof Directfreekicks) {
            Directfreekicks e = (Directfreekicks) element;
            homeAway = result.getDirectFreeKicks();
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getDirectFreeKicks(), equalTo(null));
        }

        if (element instanceof Events) {
            Events e = (Events) element;
            List<ScoutEventEntity> scoutEventEntities = result.getEvents();
            List<Event> events = e.getEvent();
            assertThatScoutEventEntityEqualToEvent(scoutEventEntities, events);
        } else {
            assertThat(result.getEvents().size(), equalTo(0));
        }

        if (element instanceof Freekicks) {
            Freekicks e = (Freekicks) element;
            homeAway = result.getFreeKicks();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getFreeKicks(), equalTo(null));
        }

        if (element instanceof Freethrows) {
            Freethrows e = (Freethrows) element;
            homeAway = result.getFreeThrows();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getFreeThrows(), equalTo(null));
        }

        if (element instanceof Goalkeepersaves) {
            Goalkeepersaves e = (Goalkeepersaves) element;
            homeAway = result.getGoalkeeperSaves();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getGoalkeeperSaves(), equalTo(null));
        }

        if (element instanceof Goalkicks) {
            Goalkicks e = (Goalkicks) element;
            homeAway = result.getGoalKicks();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));
        } else {
            assertThat(result.getGoalKicks(), equalTo(null));
        }

        if (element instanceof Injuries) {
            Injuries e = (Injuries) element;
            homeAway = result.getInjuries();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));
        } else {
            assertThat(result.getInjuries(), equalTo(null));
        }

        if (element instanceof Kickoffteam) {
            Kickoffteam e = (Kickoffteam) element;
            assertThat(Team.HOME, equalTo(result.getKickoffTeam()));

        } else {
            assertThat(result.getKickoffTeam(), equalTo(null));
        }

        if (element instanceof Offsides) {
            Offsides e = (Offsides) element;
            homeAway = result.getOffsides();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getOffsides(), equalTo(null));
        }

        if (element instanceof Penalties) {
            Penalties e = (Penalties) element;
            homeAway = result.getPenalties();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getPenalties(), equalTo(null));
        }

        if (element instanceof Pitchconditions) {
            Pitchconditions e = (Pitchconditions) element;
            PitchConditions r = result.getPitchConditions();
            assertThat(e.getName(), equalTo(r.name()));
            assertThat(e.getId(), not(equalTo(null)));

        } else {
            assertThat(result.getPitchConditions(), equalTo(null));
        }

        if (element instanceof Possession) {
            Possession e = (Possession) element;
            homeAway = result.getPossession();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getPossession(), equalTo(null));
        }

        if (element instanceof Red) {
            Red e = (Red) element;
            homeAway = result.getRedCards();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getRedCards(), equalTo(null));
        }

        if (element instanceof Score) {
            Score e = (Score) element;
            Map<String, HomeAway<Double>> map = result.getScore();
            for (HomeAway<Double> hAway : map.values()) {
                assertThat(hAway, not(equalTo(null)));
                assertThat(e.getT1(), equalTo(hAway.getHome()));
                assertThat(e.getT2(), equalTo(hAway.getAway()));

                assertThat(e.getScore(), equalTo(null));
                assertThat(e.getType(), not(equalTo(null)));
            }
        } else {
            assertThat(result.getScore().size(), equalTo(0));
        }

        if (element instanceof Serve) {
            Serve e = (Serve) element;
            Team r = result.getServe();
            assertThat(e.getTeam(), equalTo(r.name().toLowerCase()));

        } else {
            assertThat(result.getServe(), equalTo(null));
        }

        if (element instanceof Shotsblocked) {
            Shotsblocked e = (Shotsblocked) element;
            homeAway = result.getShotsBlocked();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getShotsBlocked(), equalTo(null));
        }

        if (element instanceof Shotsofftarget) {
            Shotsofftarget e = (Shotsofftarget) element;
            homeAway = result.getShotsOffTarget();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getShotsOffTarget(), equalTo(null));
        }

        if (element instanceof Shotsontarget) {
            Shotsontarget e = (Shotsontarget) element;
            homeAway = result.getShotsOnTarget();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getShotsOnTarget(), equalTo(null));
        }

        if (element instanceof Sport) {
            Sport e = (Sport) element;
            idNameTuple = result.getSport();
            assertThat(idNameTuple, not(equalTo(null)));
            assertThat(idNameTuple.getId().intValue(), equalTo(e.getId()));
            assertThat(idNameTuple.getName().getInternational(), equalTo(e.getName()));

        } else {
            assertThat(result.getSport(), equalTo(null));
        }

        if (element instanceof Status) {
            Status e = (Status) element;
            ScoutMatchStatus r = result.getMatchStatus();
            assertThat(r.name(), equalTo(e.getName()));

            assertThat(e.getId(), not(equalTo(null)));
            assertThat(e.getStart(), not(equalTo(null)));
        } else {
            assertThat(result.getMatchStatus(), equalTo(null));
        }

        if (element instanceof Surfacetype) {
            Surfacetype e = (Surfacetype) element;
            SurfaceType r = result.getSurfaceType();

            assertThat(e.getName(), equalTo(r.name()));
            assertThat(e.getId(), not(equalTo(null)));
        } else {
            assertThat(result.getSurfaceType(), equalTo(null));
        }

        if (element instanceof Suspensions) {
            Suspensions e = (Suspensions) element;
            SuspensionsEntity entity = result.getSuspensions();
            assertThat(entity, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(entity.getHome()));
            assertThat(e.getT2(), equalTo(entity.getAway()));
            assertTrue(entity.getPowerplay().isLiteralValueEqual(e.getPowerplay() + ""));

        } else {
            assertThat(result.getSuspensions(), equalTo(null));
        }

        if (element instanceof Throwins) {
            Throwins e = (Throwins) element;
            homeAway = result.getThrowins();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getThrowins(), equalTo(null));
        }

        if (element instanceof Tiebreak) {
            Tiebreak e = (Tiebreak) element;
            assertThat(e.getValue() != 0, equalTo(result.isTieBreak()));

        } else {
            assertThat(result.isTieBreak(), equalTo(null));
        }

        if (element instanceof Tournament) {
            Tournament e = (Tournament) element;
            idNameTuple = result.getTournament();
            assertThat(idNameTuple, not(equalTo(null)));
            assertThat(idNameTuple.getName().getInternational(), equalTo(e.getName()));
            assertThat(idNameTuple.getId().intValue(), equalTo(e.getId()));
        } else {
            assertThat(result.getTournament(), equalTo(null));
        }

        if (element instanceof Weatherconditions) {
            Weatherconditions e = (Weatherconditions) element;
            WeatherConditions r = result.getWeatherConditions();
            assertThat(e.getName(), equalTo(r.name()));

            assertThat(e.getId(), not(equalTo(null)));
        } else {
            assertThat(result.getWeatherConditions(), equalTo(null));
        }

        if (element instanceof Yellow) {
            Yellow e = (Yellow) element;
            homeAway = result.getYellowCards();
            assertThat(homeAway, not(equalTo(null)));
            assertThat(e.getT1(), equalTo(homeAway.getHome()));
            assertThat(e.getT2(), equalTo(homeAway.getAway()));

        } else {
            assertThat(result.getYellowCards(), equalTo(null));
        }

        MatchHeaderEntity matchHeaderEntity = result.getMatchHeader();
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestAttack() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Attacks.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Attacks.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestBlack() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Black.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Black.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestCategory() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Category.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Category.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestCorners() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Corners.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Corners.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestCourt() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Court.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Court.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestDangerousattacks() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Dangerousattacks.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Dangerousattacks.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestDirectfoulsperiod() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Directfoulsperiod.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Directfoulsperiod.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestDirectfreekicks() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Directfreekicks.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Directfreekicks.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestEvents() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Events.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Events.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestFreekicks() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Freekicks.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Freekicks.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestFreethrows() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Freethrows.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Freethrows.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestGoalkeepersaves() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Goalkeepersaves.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Goalkeepersaves.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestGoalkicks() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Goalkicks.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Goalkicks.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestInjuries() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Injuries.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Injuries.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestKickoffteam() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Kickoffteam.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Kickoffteam.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestOffsides() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Offsides.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Offsides.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestPenalties() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Penalties.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Penalties.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestPitchconditions() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Pitchconditions.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Pitchconditions.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestPossession() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Possession.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Possession.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestRed() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Red.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Red.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestScore() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Score.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Score.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestServe() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Serve.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Serve.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestShotsblocked() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Shotsblocked.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Shotsblocked.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestShotsofftarget() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Shotsofftarget.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Shotsofftarget.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestShotsontarget() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Shotsontarget.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Shotsontarget.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestSport() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Sport.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Sport.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestStatus() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Status.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Status.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestSurfacetype() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Surfacetype.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Surfacetype.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestSuspensions() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Suspensions.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Suspensions.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestThrowins() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Throwins.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Throwins.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestTiebreak() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Tiebreak.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Tiebreak.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestTournament() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Tournament.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Tournament.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestWeatherconditions() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Weatherconditions.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Weatherconditions.class);
    }

    @Test
    public void buildMatchListEntity_OK_Input_TestYellow() throws Exception {
        Matchlist input = LiveScoutProtoEntityFactory.buildMatchlist(Yellow.class);

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);

        assertThatMatchListEntityEqualToMatchlist(result, input, Yellow.class);
    }

    @Test(expected = NullPointerException.class)
    public void buildMatchListUpdateEntity_Null_Input_Test() throws Exception {
        Matchlistupdate input = null;

        JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);
    }

    @Test
    public void buildMatchListUpdateEntity_Empty_Input_Test() throws Exception {
        Matchlistupdate input = new Matchlistupdate();

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThat(result, not(equalTo(null)));
        assertThat(result.getEventId(), equalTo(null));
        assertThat(result.getMatches().size(), equalTo(0));
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestAttacks() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Attacks.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Attacks.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestCategory() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Category.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Category.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestCorners() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Corners.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Corners.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestDangerousattacks() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Dangerousattacks.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Dangerousattacks.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestDirectfoulsperiod() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Directfoulsperiod.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Directfoulsperiod.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestDirectfreekicks() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Directfreekicks.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Directfreekicks.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestBlacks() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Black.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Black.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestCourt() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Court.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Court.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestEvents() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Events.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Events.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestFreekicks() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Freekicks.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Freekicks.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestFreethrows() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Freethrows.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Freethrows.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestGoalkeepersaves() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Goalkeepersaves.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Goalkeepersaves.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestGoalkicks() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Goalkicks.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Goalkicks.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestInjuries() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Injuries.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Injuries.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestKickoffteam() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Kickoffteam.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Kickoffteam.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestOffsides() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Offsides.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Offsides.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestPenalties() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Penalties.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Penalties.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestPitchconditions() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Pitchconditions.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Pitchconditions.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestPossession() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Possession.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Possession.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestRed() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Red.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Red.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestScore() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Score.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Score.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestServe() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Serve.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Serve.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestShotsblocked() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Shotsblocked.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Shotsblocked.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestShotsofftarger() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Shotsofftarget.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Shotsofftarget.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestShotsontarger() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Shotsontarget.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Shotsontarget.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestSport() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Sport.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Sport.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestStatus() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Status.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Status.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestSurfacetype() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Surfacetype.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Surfacetype.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestSuspensions() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Suspensions.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Suspensions.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestThrowins() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Throwins.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Throwins.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestTiebreak() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Tiebreak.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Tiebreak.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestTournament() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Tournament.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Tournament.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestWeatherconditions() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Weatherconditions.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Weatherconditions.class);
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input_TestYellow() throws Exception {
        Matchlistupdate input = LiveScoutProtoEntityFactory.buildMatchlistupdate(Yellow.class);

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);

        assertThatMatchListUpdateEntityEqualToMatchlistupdate(result, input, Yellow.class);
    }

    @Test(expected = NullPointerException.class)
    public void buildOddsSuggestionsEntity_Null_Input_Test() {
        OddsSuggestions input = null;

        JaxbLiveScoutEntityFactoryHelper.buildOddsSuggestionsEntity(input);
    }

    @Test
    public void buildOddsSuggestionsEntity_Empty_Input_Test() {
        OddsSuggestions input = new OddsSuggestions();

        OddsSuggestionsEntity result = JaxbLiveScoutEntityFactoryHelper.buildOddsSuggestionsEntity(input);

        assertThat(result, not(equalTo(null)));
        assertThat(result.getAdditionalData().size(), equalTo(0));
        EventIdentifier eventIdentifier = result.getEventId();
        assertThat(eventIdentifier, not(equalTo(null)));
        assertThat(eventIdentifier.getEventId(), equalTo(0l));
        assertThat(result.getMatchId(), equalTo(0l));
        assertThat(result.getOdds().size(), equalTo(0));
    }

    @Test
    public void buildOddsSuggestionsEntity_OK_Input_Test() {
        OddsSuggestions input = LiveScoutProtoEntityFactory.buildOddsSuggestions();

        OddsSuggestionsEntity result = JaxbLiveScoutEntityFactoryHelper.buildOddsSuggestionsEntity(input);

        assertThatOddsSuggestionsEntityEqualToOddsSuggestions(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildMatchUpdateEntity_Null_Input_Test() throws Exception {
        Match input = null;

        JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);
    }

    @Test
    public void buildMatchUpdateEntity_Empty_Input_Test() throws Exception {
        Match input = new Match();

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);
        assertThat(result, not(equalTo(null)));
        assertThat(result.getAttacks(), equalTo(null));
        assertThat(result.getBlackCards(), equalTo(null));
        assertThat(result.getCategory(), equalTo(null));
        assertThat(result.getCorners(), equalTo(null));
        assertThat(result.getCourt(), equalTo(null));
        assertThat(result.getDangerousAttacks(), equalTo(null));
        assertThat(result.getDirectFoulsPeriod(), equalTo(null));
        assertThat(result.getDirectFreeKicks(), equalTo(null));
        assertThat(result.getEvents().size(), equalTo(0));
        assertThat(result.getFreeKicks(), equalTo(null));
        assertThat(result.getFreeThrows(), equalTo(null));
        assertThat(result.getGoalKicks(), equalTo(null));
        assertThat(result.getGoalkeeperSaves(), equalTo(null));
        assertThat(result.getInjuries(), equalTo(null));
        assertThat(result.getKickoffTeam(), equalTo(null));

        MatchHeaderEntity matchHeaderEntity = result.getMatchHeader();
        assertThat(result.getMatchStatus(), equalTo(null));
        assertThat(result.getMatchStatusStart(), equalTo(null));
        assertThat(result.getOffsides(), equalTo(null));
        assertThat(result.getPenalties(), equalTo(null));
        assertThat(result.getPitchConditions(), equalTo(null));
        assertThat(result.getPossessionTeam(), equalTo(null));
        assertThat(result.getPossession(), equalTo(null));
        assertThat(result.getRedCards(), equalTo(null));
        assertThat(result.getScore().size(), equalTo(0));
        assertThat(result.getServe(), equalTo(null));
        assertThat(result.getShotsBlocked(), equalTo(null));
        assertThat(result.getShotsOffTarget(), equalTo(null));
        assertThat(result.getShotsOnTarget(), equalTo(null));
        assertThat(result.getSport(), equalTo(null));
        assertThat(result.getSurfaceType(), equalTo(null));
        assertThat(result.getSuspensions(), equalTo(null));
        assertThat(result.getThrowins(), equalTo(null));
        assertThat(result.isTieBreak(), equalTo(null));
        assertThat(result.getTournament(), equalTo(null));
        assertThat(result.getWeatherConditions(), equalTo(null));
        assertThat(result.getYellowCards(), equalTo(null));
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestAttacks() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Attacks.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Attacks.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestCategory() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Category.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Category.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestCorners() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Corners.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Corners.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestDangerousattacks() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Dangerousattacks.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Dangerousattacks.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestDirectfoulsperiod() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Directfoulsperiod.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Directfoulsperiod.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestDirectfreekicks() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Directfreekicks.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Directfreekicks.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestBlack() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Black.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Black.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestCourt() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Court.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Court.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestEvents() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Events.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Events.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestFreekicks() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Freekicks.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Freekicks.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestFreethrows() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Freethrows.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Freethrows.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestGoalkeepersaves() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Goalkeepersaves.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Goalkeepersaves.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestGoalKicks() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Goalkicks.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Goalkicks.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestInjuries() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Injuries.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Injuries.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestKickoffteam() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Kickoffteam.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Kickoffteam.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestOffsides() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Offsides.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Offsides.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestPenalties() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Penalties.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Penalties.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestPitchconditions() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Pitchconditions.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Pitchconditions.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestPossession() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Possession.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Possession.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestRed() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Red.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Red.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestScore() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Score.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Score.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestServe() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Serve.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Serve.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestShotsblocked() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Shotsblocked.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Shotsblocked.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestShotsofftarget() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Shotsofftarget.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Shotsofftarget.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestShotsontarget() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Shotsontarget.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Shotsontarget.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestSport() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Sport.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Sport.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestStatus() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Status.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Status.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestSurfacetype() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Surfacetype.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Surfacetype.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestSuspensions() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Suspensions.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Suspensions.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestThrowins() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Throwins.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Throwins.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestTiebreak() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Tiebreak.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Tiebreak.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestTournament() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Tournament.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Tournament.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestWeatherconditions() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Weatherconditions.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Weatherconditions.class);
    }

    @Test
    public void buildMatchUpdateEntity_OK_Input_TestYellow() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Yellow.class);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);

        assertThatMatchUpdateEntityEqualToMatch(result, input, Yellow.class);
    }

    @Test(expected = NullPointerException.class)
    public void buildLineupsEntity_Null_Input_Test() throws Exception {
        Lineups input = null;

        JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(input);
    }

    @Test
    public void buildLineupsEntity_Empty_Input_Test() throws Exception {
        Lineups input = new Lineups();

        LineupsEntity result = JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(input);
    }

    @Test
    public void buildLineupsEntity_OK_Input_Test() throws Exception {
        Lineups input = LiveScoutProtoEntityFactory.buildLineups();

        LineupsEntity result = JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(input);

        assertThatLineupsEntityEqualToLineups(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildPlayerEntity_Null_Input_Test() throws Exception {
        Player input = null;

        JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(input);
    }

    @Test
    public void buildPlayerEntity_Empty_Input_Test() throws Exception {
        Player input = new Player();

        PlayerEntity result = JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(input);
    }

    @Test
    public void buildPlayerEntity_OK_Input_Test() throws Exception {
        Player input = LiveScoutProtoEntityFactory.buildPlayer();

        PlayerEntity result = JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(input);

        assertThatPlayerEntityEqualToPlayer(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildMatchHeaderEntity_Null_Input_Test() throws Exception {
        Match input = null;

        JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(input);
    }

    @Test
    public void buildMatchHeaderEntity_Empty_Input_Test() throws Exception {
        Match input = new Match();

        MatchHeaderEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(input);
    }

    @Test
    public void buildMatchHeaderEntity_OK_Input_Test() throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(Attacks.class);

        MatchHeaderEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(input);

        assertThatMatchHeaderEntityEqualToMatch(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildScoutEventEntity_Null_Input_Test() throws Exception {
        Event input = null;

        JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);
    }

    @Test
    public void buildScoutEventEntity_Empty_Input_Test() throws Exception {
        Event input = new Event();
        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);
    }

    @Test
    public void buildScoutEventEntity_OK_Input_Test() throws Exception {
        Event input = LiveScoutProtoEntityFactory.buildEvent();

        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);

        assertThatScoutEventEntityEqualToEvent(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildScoutOddsEntity_Null_Input_Test() {
        Odds input = null;

        JaxbLiveScoutEntityFactoryHelper.buildScoutOddsEntity(input);
    }

    @Test
    public void buildScoutOddsEntity_Empty_Test() {
        Odds input = new Odds();

        ScoutOddsEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutOddsEntity(input);
    }

    @Test
    public void buildScoutOddsEntity_OK_Input_Test() {
        Odds input = LiveScoutProtoEntityFactory.buildOdds();

        ScoutOddsEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutOddsEntity(input);

        assertThatScoutOddsEntityEqualToOdds(result, input);
    }

    @Test(expected = NullPointerException.class)
    public void buildScoutOddsFieldEntity_Null_Input_Test() {
        OddsField input = null;

        JaxbLiveScoutEntityFactoryHelper.buildScoutOddsFieldEntity(input);
    }

    @Test
    public void buildScoutOddsFieldEntity_Empty_Input_Test() {
        OddsField input = new OddsField();

        ScoutOddsFieldEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutOddsFieldEntity(input);
    }

    @Test
    public void buildScoutOddsFieldEntity_OK_Input_Test() {
        OddsField input = LiveScoutProtoEntityFactory.buildOddsField();

        ScoutOddsFieldEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutOddsFieldEntity(input);

        assertThatScoutOddsFieldEntityEqualToOddsFieldEntity(result, input);
    }
}
