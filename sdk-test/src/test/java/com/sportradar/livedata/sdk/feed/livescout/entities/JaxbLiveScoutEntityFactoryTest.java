package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import com.sportradar.livedata.sdk.util.NullSdkLogger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JaxbLiveScoutEntityFactoryTest {
    LiveScoutEntityFactory entityFactory = new JaxbLiveScoutEntityFactory(NullSdkLogger.INSTANCE);

    @Test
    void buildMatchListEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            entityFactory.buildMatchListEntity(null);
        });
    }

    @Test
    void buildMatchListEntity_Empty_Input_Test() throws Exception {
        Matchlist input = new Matchlist();
        MatchListEntity expected = new MatchListEntity();
        expected.setMatches(new ArrayList<>());

        MatchListEntity result = entityFactory.buildMatchListEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildMatchListEntity_OK_Input_Test() throws Exception {
        Matchlist input = new Matchlist();
        input.getMatch().add(LiveScoutProtoEntityFactory.buildMatch(38));

        MatchListEntity expected = new MatchListEntity();
        expected.setMatches(List.of(LiveScoutProtoEntityFactory.buildMatchUpdateEntity(38)));

        MatchListEntity result = entityFactory.buildMatchListEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildMatchListUpdateEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            entityFactory.buildMatchListUpdateEntity(null);
        });
    }

    @Test
    void buildMatchListUpdateEntity_Empty_Input_Test() throws Exception {
        Matchlistupdate input = new Matchlistupdate();
        MatchListUpdateEntity expected = new MatchListUpdateEntity();
        expected.setMatches(new ArrayList<>());

        MatchListUpdateEntity result = entityFactory.buildMatchListUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildMatchListUpdateEntity_OK_Input() throws Exception {
        Matchlistupdate input = new Matchlistupdate();
        input.getMatch().add(LiveScoutProtoEntityFactory.buildMatch(33));

        MatchListUpdateEntity expected = new MatchListUpdateEntity();
        expected.setMatches(List.of(LiveScoutProtoEntityFactory.buildMatchUpdateEntity(33)));

        MatchListUpdateEntity result = entityFactory.buildMatchListUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildMatchUpdateEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            entityFactory.buildMatchUpdateEntity(null);
        });
    }

    @Test
    void buildMatchUpdateEntity_Empty_Input_Test() throws Exception {
        Match input = new Match();
        MatchUpdateEntity expected = new MatchUpdateEntity();
        expected.setMatchHeader(new MatchHeaderEntity());

        MatchUpdateEntity result = entityFactory.buildMatchUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @ParameterizedTest(name = "buildMatchUpdateEntity_OK_Input_{0}_Test")
    @ValueSource(ints = {0,1,2,3,4,33,56})
    void buildMatchUpdateEntity_OK_Input_Test(int valueBase) throws Exception {
        Match input = LiveScoutProtoEntityFactory.buildMatch(valueBase);
        MatchUpdateEntity expected = LiveScoutProtoEntityFactory.buildMatchUpdateEntity(valueBase);

        MatchUpdateEntity result = entityFactory.buildMatchUpdateEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildMatchStopEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            entityFactory.buildMatchStopEntity(null);
        });
    }

    @Test
    void buildMatchStopEntity_Empty_Input_Test() {
        MatchStopEntity result = entityFactory.buildMatchStopEntity(new Matchstop());
        assertThat(result, equalTo(new MatchStopEntity()));
    }

    @Test
    void buildMatchStopEntity_OK_Input_Test() {
        Matchstop input = LiveScoutProtoEntityFactory.buildMatchstop(23);
        MatchStopEntity expected = LiveScoutProtoEntityFactory.buildMatchStopEntity(23);

        MatchStopEntity result = entityFactory.buildMatchStopEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildMatchBookingEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            entityFactory.buildMatchBookingEntity(null);
        });
    }

    @Test
    void buildMatchBookingEntity_Empty_Input_Test() throws Exception {
        MatchBookingEntity result = entityFactory.buildMatchBookingEntity(new Bookmatch());
        assertThat(result, equalTo(new MatchBookingEntity()));
    }

    @Test
    void buildMatchBookingEntity_OK_Input_Test() throws Exception {
        Bookmatch input = LiveScoutProtoEntityFactory.buildBookmatch(71);
        MatchBookingEntity expected = LiveScoutProtoEntityFactory.buildMatchBookingEntity(71);

        MatchBookingEntity result = entityFactory.buildMatchBookingEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildLineupsEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            entityFactory.buildLineupsEntity(null);
        });
    }

    @Test
    void buildLineupsEntity_Empty_Input_Test() throws Exception {
        LineupsEntity result = entityFactory.buildLineupsEntity(new Lineups());
        assertThat(result, equalTo(new LineupsEntity()));
    }

    @ParameterizedTest(name = "buildLineupsEntity_OK_Input_{0}_Test")
    @ValueSource(ints = {0,10,17})
    void buildLineupsEntity_OK_Input_Test(int valueBase) throws Exception {
        Lineups input = LiveScoutProtoEntityFactory.buildLineups(valueBase);
        LineupsEntity expected = LiveScoutProtoEntityFactory.buildLineupsEntity(valueBase);

        LineupsEntity result = entityFactory.buildLineupsEntity(input);
        assertThat(result, equalTo(expected));
    }
}
