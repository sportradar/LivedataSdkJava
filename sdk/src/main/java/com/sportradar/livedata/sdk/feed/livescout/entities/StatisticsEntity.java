package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class StatisticsEntity implements Serializable {

    private final BattersStatsTotalEntity battersStatsTotal;
    private final PitchersStatsTotalEntity pitchersStatsTotal;
    private final List<TeamStatsEntity> teamStats;
    private final List<TeamPlayerStatsEntity> teamPlayerStats;

    StatisticsEntity(Statistics statistics) throws UnknownEnumException {
        this(
                statistics.getBattersstatstotal(),
                statistics.getPitchersstatstotal(),
                statistics.getTeamstats(),
                statistics.getTeamplayerstats()
        );
    }

    protected StatisticsEntity(
            Battersstatstotal battersstatstotals,
            Pitchersstatstotal pitchersstatstotals,
            List<Teamstats> teamstats,
            List<Teamplayerstats> teamplayerstats
    ) throws UnknownEnumException {
        this.battersStatsTotal = battersstatstotals != null ?
                new BattersStatsTotalEntity(battersstatstotals) : null;

        this.pitchersStatsTotal = pitchersstatstotals != null ?
                new PitchersStatsTotalEntity(pitchersstatstotals): null;

        List<TeamStatsEntity> teamStats = new ArrayList<>();
        for (Teamstats teamstat : teamstats) {
            TeamStatsEntity teamStatsEntity = new TeamStatsEntity(teamstat);
            teamStats.add(teamStatsEntity);
        }

        this.teamStats = teamStats;

        List<TeamPlayerStatsEntity> teamPlayerStats = new ArrayList<>();
        for (Teamplayerstats teamplayerstat : teamplayerstats) {
            TeamPlayerStatsEntity teamPlayerStatsEntity = new TeamPlayerStatsEntity(teamplayerstat);
            teamPlayerStats.add(teamPlayerStatsEntity);
        }

        this.teamPlayerStats = teamPlayerStats;
    }

    public BattersStatsTotalEntity getBattersStatsTotal() {
        return battersStatsTotal;
    }

    public PitchersStatsTotalEntity getPitchersStatsTotal() {
        return pitchersStatsTotal;
    }

    public List<TeamStatsEntity> getTeamStats() {
        return teamStats;
    }

}
