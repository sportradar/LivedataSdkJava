package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Pitchersstatstotal;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Playerstats;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class PitchersStatsTotalEntity implements Serializable {

    private final List<PlayerStatsEntity> playerStats;
    private final Team side;

    PitchersStatsTotalEntity(Pitchersstatstotal pitchersstatstotal) throws UnknownEnumException {
        this(pitchersstatstotal.getPlayerstats(), pitchersstatstotal.getSide());
    }

    protected PitchersStatsTotalEntity(List<Playerstats> playerstats, String side) throws UnknownEnumException {
        this.playerStats = playerstats.stream().map(PlayerStatsEntity::new).collect(Collectors.toList());
        this.side = Team.getTeamFromLiteralValue(side);
    }

    public List<PlayerStatsEntity> getPlayerStats() {
        return playerStats;
    }

    public Team getSide() {
        return side;
    }

}
