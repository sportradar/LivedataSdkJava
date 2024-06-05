package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.TeamStatsType;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Stats;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Teamstats;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class TeamStatsEntity implements Serializable {

    private final List<StatsEntity> stats;
    private final Team side;
    private final TeamStatsType type;

    TeamStatsEntity(Teamstats teamstats) throws UnknownEnumException {
        this(teamstats.getStats(), teamstats.getSide(), teamstats.getType());
    }

    protected TeamStatsEntity(List<Stats> stats, String side, String type) throws UnknownEnumException {
        this.stats = stats.stream().map(StatsEntity::new).collect(Collectors.toList());
        this.side = Team.getTeamFromLiteralValue(side);
        this.type = TeamStatsType.getTeamStatsTypeFromLiteralValue(type);
    }

    public List<StatsEntity> getStats() {
        return stats;
    }

    public Team getSide() {
        return side;
    }

    public TeamStatsType getType() {
        return type;
    }

}
