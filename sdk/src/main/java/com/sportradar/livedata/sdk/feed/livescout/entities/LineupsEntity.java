package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The current lineup.
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class LineupsEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = 2690053709380185769L;
    private List<ManagerEntity> managers;
    private long matchId;
    //would prefer boolean, but Utku states that it should be nullable:
    //"1 means it is preliminary. all other states should be null, client should not receive anything"
    private Boolean preliminary;
    private List<PlayerEntity> players;
    private List<TeamOfficialEntity> teamOfficials;

    protected LineupsEntity() {
    }

    /**
     * Gets list of team managers
     *
     * @return team managers
     */
    public List<ManagerEntity> getManagers() {
        if(managers == null){
            managers = new ArrayList<>();
        }
        return managers;
    }

    /**
     * Gets match id.
     *
     * @return match id
     */
    public long getMatchId() {
        return matchId;
    }

    /**
     * Flag for Ice Hockey that show if we are dealing with provisional lineup.
     *
     * @return true if provisional lineup
     */
    public Boolean getPreliminary() {
        return preliminary;
    }

    /**
     * Gets list of players in lineup.
     *
     * @return player list
     */
    public List<PlayerEntity> getPlayers() {
        if(players == null){
            players = new ArrayList<>();
        }
        return players;
    }

    /**
     * Gets list of team officials
     *
     * @return team officials
     */
    public List<TeamOfficialEntity> getTeamOfficials() {
        if(teamOfficials == null){
            teamOfficials = new ArrayList<>();
        }
        return teamOfficials;
    }
}
