package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"unchecked", "JavaDoc"})
public class JaxbLiveScoutEntityFactoryHelperTest {

    @Test
    public void buildMatchListEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(null);
        });
    }

    @Test
    public void buildMatchListEntity_Empty_Input_Test() throws Exception {
        Matchlist input = new Matchlist();
        MatchListEntity expected = new MatchListEntity();
        expected.setMatches(new ArrayList<>());

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchListEntity_OK_Input_Test() throws Exception {
        Matchlist input = new Matchlist();
        input.getMatch().add(LiveScoutProtoEntityFactory.buildMatch(38));

        MatchListEntity expected = new MatchListEntity();
        expected.setMatches(List.of(LiveScoutProtoEntityFactory.buildMatchUpdateEntity(38)));

        MatchListEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchListUpdateEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(null);
        });
    }

    @Test
    public void buildMatchListUpdateEntity_Empty_Input_Test() throws Exception {
        Matchlistupdate input = new Matchlistupdate();
        MatchListUpdateEntity expected = new MatchListUpdateEntity();
        expected.setMatches(new ArrayList<>());

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchListUpdateEntity_OK_Input() throws Exception {
        Matchlistupdate input = new Matchlistupdate();
        input.getMatch().add(LiveScoutProtoEntityFactory.buildMatch(33));

        MatchListUpdateEntity expected = new MatchListUpdateEntity();
        expected.setMatches(List.of(LiveScoutProtoEntityFactory.buildMatchUpdateEntity(33)));

        MatchListUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchUpdateEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(null);
        });
    }

    @Test
    public void buildMatchUpdateEntity_Empty_Input_Test() throws Exception {
        Match input = new Match();
        MatchUpdateEntity expected = new MatchUpdateEntity();
        expected.setMatchHeader(new MatchHeaderEntity());
        expected.setEvents(new ArrayList<>());
        expected.setScore(new HashMap<>());
        expected.setScores(new ArrayList<>());
        expected.setSubteams(new ArrayList<>());

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @ParameterizedTest(name = "buildMatchUpdateEntity_OK_Input_{0}")
    @ValueSource(ints = {0,1,2,3,4,33,56})
    public void buildMatchUpdateEntity_OK_Input(int valueBase) throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(valueBase);
        MatchUpdateEntity expected = LiveScoutProtoEntityFactory.buildMatchUpdateEntity(valueBase);

        MatchUpdateEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchHeaderEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(null);
        });
    }

    @Test
    public void buildMatchHeaderEntity_Empty_Input_Test() throws Exception {
        MatchHeaderEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(new Match());
        assertThat(result, equalTo(new MatchHeaderEntity()));
    }

    @Test
    public void buildMatchHeaderEntity_OK_Input_Test() throws Exception {
        Match match = new Match();
        LiveScoutProtoEntityFactory.insertMatchHeaderData(match, 57);
        MatchHeaderEntity expected = LiveScoutProtoEntityFactory.buildMatchHeaderEntity(57);

        MatchHeaderEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(match);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildScoutEventEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(null);
        });
    }

    @Test
    public void buildScoutEventEntity_Empty_Input_Test() throws Exception {
        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(new Event());
        assertThat(result, equalTo(new ScoutEventEntity()));
    }

    @Test
    public void buildScoutEventEntity_OK_Input_Test() throws Exception {
        Event input = LiveScoutProtoEntityFactory.buildEvent(27);
        ScoutEventEntity expected = LiveScoutProtoEntityFactory.buildScoutEventEntity(27);

        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchStopEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(null);
        });
    }

    @Test
    public void buildMatchStopEntity_Empty_Input_Test() {
        MatchStopEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(new Matchstop());
        assertThat(result, equalTo(new MatchStopEntity()));
    }

    @Test
    public void buildMatchStopEntity_OK_Input_Test() {
        Matchstop input = LiveScoutProtoEntityFactory.buildMatchstop(23);
        MatchStopEntity expected = LiveScoutProtoEntityFactory.buildMatchStopEntity(23);

        MatchStopEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildMatchBookingEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(null);
        });
    }

    @Test
    public void buildMatchBookingEntity_Empty_Input_Test() throws Exception {
        MatchBookingEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(new Bookmatch());
        assertThat(result, equalTo(new MatchBookingEntity()));
    }

    @Test
    public void buildMatchBookingEntity_OK_Input_Test() throws Exception {
        Bookmatch input = LiveScoutProtoEntityFactory.buildBookmatch(71);
        MatchBookingEntity expected = LiveScoutProtoEntityFactory.buildMatchBookingEntity(71);

        MatchBookingEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildLineupsEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(null);
        });
    }

    @Test
    public void buildLineupsEntity_Empty_Input_Test() throws Exception {
        LineupsEntity result = JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(new Lineups());
        assertThat(result, equalTo(new LineupsEntity()));
    }

    @Test
    public void buildLineupsEntity_OK_Input_Test() throws Exception {
        Lineups input = LiveScoutProtoEntityFactory.buildLineups(17);
        LineupsEntity expected = LiveScoutProtoEntityFactory.buildLineupsEntity(17);

        LineupsEntity result = JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildPlayerEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(null);
        });
    }

    @Test
    public void buildPlayerEntity_Empty_Input_Test() throws Exception {
        PlayerEntity expected = new PlayerEntity();
        expected.setTeam(Team.NONE);//because incoming msg is using primitive, which cannot be null, only 0
        PlayerEntity result = JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(new Player());
        assertThat(result, equalTo(expected));
    }

    @Test
    public void buildPlayerEntity_OK_Input_Test() throws Exception {
        Player input = LiveScoutProtoEntityFactory.buildPlayer(81);
        PlayerEntity expected = LiveScoutProtoEntityFactory.buildPlayerEntity(81);

        PlayerEntity result = JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(input);
        assertThat(result, equalTo(expected));
    }
}
