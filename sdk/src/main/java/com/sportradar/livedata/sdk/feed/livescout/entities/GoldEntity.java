package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Gold;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class GoldEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final int mapNumber;
    private final int team1;
    private final int team2;

    public GoldEntity(Gold gold) {
        this.mapNumber = gold.getMapnr();
        this.team1 = gold.getT1();
        this.team2 = gold.getT2();
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public int getTeam1() {
        return team1;
    }

    public int getTeam2() {
        return team2;
    }
}
