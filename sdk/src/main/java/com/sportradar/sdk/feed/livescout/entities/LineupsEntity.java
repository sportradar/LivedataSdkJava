package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The current lineup.
 */
public class LineupsEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = 2690053709380185769L;
    private List<ManagerEntity> managers;
    private long matchId;
    private List<PlayerEntity> players;
    private List<TeamOfficialEntity> teamOfficials;

    protected LineupsEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
    }

    /**
     * Gets list of team managers
     *
     * @return team managers
     */
    public List<ManagerEntity> getManagers() {
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
     * Gets list of players in lineup.
     *
     * @return player list
     */
    public List<PlayerEntity> getPlayers() {
        return players;
    }

    /**
     * Gets list of team officials
     *
     * @return team officials
     */
    public List<TeamOfficialEntity> getTeamOfficials() {
        return teamOfficials;
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
        return "LineupsEntity{" +
                "managers=" + managers +
                ", matchId=" + matchId +
                ", players=" + players +
                ", teamOfficials=" + teamOfficials +
                '}';
    }

    protected void setManagers(List<ManagerEntity> managers) {
        this.managers = managers;
    }

    protected void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    protected void setPlayers(List<PlayerEntity> players) {
        this.players = Collections.unmodifiableList(players);
    }

    protected void setTeamOfficials(List<TeamOfficialEntity> teamOfficials) {
        this.teamOfficials = teamOfficials;
    }


}
