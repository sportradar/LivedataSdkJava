package com.sportradar.sdk.feed.livescout.entities;

import com.google.common.base.Preconditions;
import com.sportradar.sdk.feed.common.enums.Team;

import java.io.Serializable;

/**
 * A match team representation
 */
public class MatchTeamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Team side;
    private final String nationality;

    MatchTeamEntity(com.sportradar.sdk.proto.dto.incoming.livescout.Team team) {
        Preconditions.checkNotNull(team);

        if(team.getSide() != null)
        {
            if(team.getSide().equalsIgnoreCase("home"))
            {
                this.side = Team.HOME;
            }
            else if(team.getSide().equalsIgnoreCase("away"))
            {
                this.side = Team.AWAY;
            }
            else{
                this.side = Team.NONE;
            }
        }
        else{
            this.side = Team.NONE;
        }
        this.nationality = team.getNationality();
    }

    /**
     * Returns the side of the team
     * @return the side of the team
     */
    public Team getSide() { return side; }

    /**
     * Returns the team nationality
     *
     * @return the team nationality
     */
    public String getNationality() { return nationality; }
}
