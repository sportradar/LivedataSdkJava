package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.enums.Team;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity holding data about team manager
 */
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class ManagerEntity {

    private int id;
    private String name;
    private Team team;

    protected ManagerEntity() {
    }

    /**
     * Manager id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Manager name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Manager team
     *
     * @return home or away team
     */
    public Team getTeam() {
        return team;
    }
}
