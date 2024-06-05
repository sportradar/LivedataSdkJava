package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Stats;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public class StatsEntity implements Serializable {

    private final String name;
    private final String value;

    StatsEntity(Stats stats) {
        this(stats.getName(), stats.getValue());
    }

    protected StatsEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
