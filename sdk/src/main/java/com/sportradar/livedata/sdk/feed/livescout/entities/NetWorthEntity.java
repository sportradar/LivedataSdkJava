package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Networth;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Holds information about a match net-worth
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class NetWorthEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final int mapNumber;
    private final int team1;
    private final int team2;

    public NetWorthEntity(Networth networth) {
        this.mapNumber = networth.getMapnr();
        this.team1 = networth.getT1();
        this.team2 = networth.getT2();
    }

    /**
     * Returns the map number
     *
     * @return - the map number
     */
    public int getMapNumber() {
        return mapNumber;
    }

    /**
     * Returns the home team net worth
     *
     * @return - the home team net worth
     */
    public int getTeam1() {
        return team1;
    }

    /**
     * Returns the away team net worth
     *
     * @return - the away team net worth
     */
    public int getTeam2() {
        return team2;
    }
}
