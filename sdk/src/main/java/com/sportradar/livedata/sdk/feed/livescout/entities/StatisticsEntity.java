package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Battersstatstotal;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Pitchersstatstotal;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Statistics;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Teamstats;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class StatisticsEntity implements Serializable {

    private final BattersStatsTotalEntity battersStatsTotal;
    private final PitchersStatsTotalEntity pitchersStatsTotal;
    private final List<TeamStatsEntity> teamStats;

    StatisticsEntity(Statistics statistics) throws UnknownEnumException {
        this(statistics.getBattersstatstotal(), statistics.getPitchersstatstotal(), statistics.getTeamstats());
    }

    protected StatisticsEntity(
            Battersstatstotal battersstatstotals,
            Pitchersstatstotal pitchersstatstotals,
            List<Teamstats> teamstats
    ) throws UnknownEnumException {
        this.battersStatsTotal = new BattersStatsTotalEntity(battersstatstotals);
        this.pitchersStatsTotal = new PitchersStatsTotalEntity(pitchersstatstotals);

        List<TeamStatsEntity> list = new ArrayList<>();
        for (Teamstats teamstat : teamstats) {
            TeamStatsEntity teamStatsEntity = new TeamStatsEntity(teamstat);
            list.add(teamStatsEntity);
        }
        this.teamStats = list;
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
