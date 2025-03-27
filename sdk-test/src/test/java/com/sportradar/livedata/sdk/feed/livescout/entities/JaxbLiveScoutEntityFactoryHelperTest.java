package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.EventType;
import com.sportradar.livedata.sdk.feed.livescout.enums.TeamPlayerStatsType;
import com.sportradar.livedata.sdk.feed.livescout.enums.TeamStatsType;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import com.sportradar.livedata.sdk.util.NullSdkLogger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"unchecked", "JavaDoc"})
class JaxbLiveScoutEntityFactoryHelperTest {

    @Test
    void buildMatchHeaderEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(null);
        });
    }

    @Test
    void buildMatchHeaderEntity_Empty_Input_Test() throws Exception {
        MatchHeaderEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(new Match());
        assertThat(result, equalTo(new MatchHeaderEntity()));
    }

    @Test
    void buildMatchHeaderEntity_OK_Input_Test() throws Exception {
        Match match = new Match();
        LiveScoutProtoEntityFactory.insertMatchHeaderData(match, 57);
        MatchHeaderEntity expected = LiveScoutProtoEntityFactory.buildMatchHeaderEntity(57);

        MatchHeaderEntity result = JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(match);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildScoutEventEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(null);
        });
    }

    @Test
    void buildScoutEventEntity_Empty_Input_Test() throws Exception {
        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(new Event());
        assertThat(result, equalTo(new ScoutEventEntity()));
    }

    @Test
    void buildScoutEventEntity_OK_Input_Test() throws Exception {
        Event input = LiveScoutProtoEntityFactory.buildEvent(27);
        ScoutEventEntity expected = LiveScoutProtoEntityFactory.buildScoutEventEntity(27);

        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildScoutEventEntity_1743_Test() throws Exception {
        Event input = LiveScoutProtoEntityFactory.buildEvent(3);
        input.setType(1743);
        input.setAwayteamstatsi5("Stats 5");
        input.setAwayteamstatstotal("Stats total");
        ScoutEventEntity expected = LiveScoutProtoEntityFactory.buildScoutEventEntity(3);
        expected.setTypeId(1743);
        expected.setType(EventType.UNKNOWN);
        TeamStatisticsEntity stats = new TeamStatisticsEntity();
        stats.setAwayTeamStatsI5("Stats 5");
        stats.setAwayTeamStatsTotal("Stats total");
        expected.setTeamStatistics(stats);

        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildScoutEventEntity_1714_Test() throws Exception {
        Event input = LiveScoutProtoEntityFactory.buildEvent(3);
        input.setType(1714);
        input.setAwaypitchersstatstotal("Pitcher Stats total");
        input.setHomebattersstatstotal("Batter Stats total");
        ScoutEventEntity expected = LiveScoutProtoEntityFactory.buildScoutEventEntity(3);
        expected.setTypeId(1714);
        expected.setType(EventType.UNKNOWN);
        PlayerStatisticsEntity stats = new PlayerStatisticsEntity();
        stats.setAwayPitchersStatsTotal("Pitcher Stats total");
        stats.setHomeBattersStatsTotal("Batter Stats total");
        expected.setPlayerStatistics(stats);

        ScoutEventEntity result = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(input);
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildPlayerEntity_Null_Input_Test() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(null);
        });
    }

    @Test
    void buildPlayerEntity_Empty_Input_Test() throws Exception {
        PlayerEntity expected = new PlayerEntity();
        expected.setTeam(Team.NONE);//because incoming msg is using primitive, which cannot be null, only 0
        PlayerEntity result = JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(new Player());
        assertThat(result, equalTo(expected));
    }

    @Test
    void buildPlayerEntity_OK_Input_Test() throws Exception {
        Player input = LiveScoutProtoEntityFactory.buildPlayer(81);
        PlayerEntity expected = LiveScoutProtoEntityFactory.buildPlayerEntity(81);

        PlayerEntity result = JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(input);
        assertThat(result, equalTo(expected));
    }

    // "assertThat(result, equalTo(expected))" doesn't make any sense in this case ( if any )
    @Test
    void buildScoutEventStatistics_OK_Input_Test() throws Exception {
        Event event = new Event();
        event.setStatistics(LiveScoutProtoEntityFactory.buildScoutEventStatistics());

        ScoutEventEntity entity = JaxbLiveScoutEntityFactoryHelper.buildScoutEventEntity(event);

        assertNotNull(entity);
        assertNotNull(entity.getStatistics());

        StatisticsEntity statistics = entity.getStatistics();
        assertNotNull(statistics.getBattersStatsTotal());
        assertNotNull(statistics.getPitchersStatsTotal());
        assertThat(statistics.getTeamStats().size(), equalTo(2));
        assertThat(statistics.getTeamPlayerStats().size(), equalTo(1));

        BattersStatsTotalEntity battersStats = statistics.getBattersStatsTotal();
        assertThat(battersStats.getPlayerStats().size(), equalTo(2));
        assertThat(battersStats.getSide(), equalTo(Team.HOME));

        List<PlayerStatsEntity> batterPlayersStats = battersStats.getPlayerStats();
        assertThat(batterPlayersStats.get(0).getPid(), equalTo(1L));
        assertNotNull(batterPlayersStats.get(0).getStats());
        assertThat(batterPlayersStats.get(0).getStats().size(), equalTo(2));
        assertThat(batterPlayersStats.get(0).getStats().get(1).getName(), equalTo("stats2"));

        PitchersStatsTotalEntity pitchersStats = statistics.getPitchersStatsTotal();
        assertThat(pitchersStats.getPlayerStats().size(), equalTo(2));
        assertThat(pitchersStats.getSide(), equalTo(Team.AWAY));

        TeamStatsEntity teamStats1 = statistics.getTeamStats().get(0);
        assertThat(teamStats1.getSide(), equalTo(Team.HOME));
        assertThat(teamStats1.getType(), equalTo(TeamStatsType.TOTAL));
        assertThat(teamStats1.getStats().size(), equalTo(2));
        assertThat(teamStats1.getStats().get(1).getValue(), equalTo("value6"));

        TeamStatsEntity teamStats2 = statistics.getTeamStats().get(1);
        assertThat(teamStats2.getSide(), equalTo(Team.AWAY));
        assertThat(teamStats2.getType(), equalTo(TeamStatsType.INNING5));
        assertThat(teamStats2.getStats().size(), equalTo(2));
        assertThat(teamStats2.getStats().get(0).getName(), equalTo("stats7"));

        TeamPlayerStatsEntity teamPlayerStats = statistics.getTeamPlayerStats().get(0);
        assertThat(teamPlayerStats.getSide(), equalTo(Team.HOME));
        assertThat(teamPlayerStats.getType(), equalTo(TeamPlayerStatsType.TOTAL));
        assertThat(teamPlayerStats.getStats().size(), equalTo(2));

        PlayerStatsEntity playerStats1 = teamPlayerStats.getStats().get(0);
        assertThat(playerStats1.getPid(), equalTo(3L));
        assertThat(playerStats1.getStats().size(), equalTo(2));
        checkStatsEntity(playerStats1.getStats().get(0), "stats9", "value9");
        checkStatsEntity(playerStats1.getStats().get(1), "stats10", "value10");

        PlayerStatsEntity playerStats2 = teamPlayerStats.getStats().get(1);
        assertThat(playerStats2.getPid(), equalTo(4L));
        assertThat(playerStats2.getStats().size(), equalTo(2));
        checkStatsEntity(playerStats2.getStats().get(0), "stats11", "value11");
        checkStatsEntity(playerStats2.getStats().get(1), "stats12", "value12");
    }

    private void checkStatsEntity(StatsEntity entity, String key, String value) {
        assertNotNull(entity);
        assertThat(entity.getName(), equalTo(key));
        assertThat(entity.getValue(), equalTo(value));

    }
}
