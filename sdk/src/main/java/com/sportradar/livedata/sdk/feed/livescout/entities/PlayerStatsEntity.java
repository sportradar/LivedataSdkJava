package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Playerstats;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Stats;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class PlayerStatsEntity implements Serializable {

    private final List<StatsEntity> stats;
    private final Long pid;

    PlayerStatsEntity(Playerstats playerstats) {
        this(playerstats.getStats(), playerstats.getPid());
    }

    protected PlayerStatsEntity(List<Stats> stats, Long pid) {
        this.stats = stats.stream().map(StatsEntity::new).collect(Collectors.toList());
        this.pid = pid;
    }

    public List<StatsEntity> getStats() {
        return stats;
    }

    public Long getPid() {
        return pid;
    }

}
