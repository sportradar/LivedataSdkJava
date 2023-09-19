package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.enums.Team;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity holding data about team official
 */
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class TeamOfficialEntity {

    private int id;
    private String name;
    private Team team;

    protected TeamOfficialEntity() {
    }

    /**
     * Team official id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Team official name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Team official team
     *
     * @return home or away team
     */
    public Team getTeam() {
        return team;
    }
}
