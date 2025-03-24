package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.TeamPlayerStatsType;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Playerstats;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Teamplayerstats;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class TeamPlayerStatsEntity {

    private final List<PlayerStatsEntity> stats;
    private final Team side;
    private final TeamPlayerStatsType type;

    TeamPlayerStatsEntity(Teamplayerstats teamplayerstats) throws UnknownEnumException {
        this(
                teamplayerstats.getPlayerstats(),
                teamplayerstats.getSide(),
                teamplayerstats.getType()
        );
    }

    protected TeamPlayerStatsEntity(List<Playerstats> stats, String side, String type) throws UnknownEnumException {
        this.stats = stats.stream().map(PlayerStatsEntity::new).collect(Collectors.toList());
        this.side = Team.getTeamFromLiteralValue(side);
        this.type = TeamPlayerStatsType.getTeamPlayerStatsTypeFromLiteralValue(type);
    }

    public List<PlayerStatsEntity> getStats() {
        return stats;
    }

    public Team getSide() {
        return side;
    }

    public TeamPlayerStatsType getType() {
        return type;
    }
}
