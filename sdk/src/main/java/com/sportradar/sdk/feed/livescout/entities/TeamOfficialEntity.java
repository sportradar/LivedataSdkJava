package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.enums.Team;

/**
 * Entity holding data about team official
 */
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

    /**
     * Returns a string that represents the current object.
     * <p>
     * Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "TeamOfficialEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setTeam(Team team) {
        this.team = team;
    }
}
