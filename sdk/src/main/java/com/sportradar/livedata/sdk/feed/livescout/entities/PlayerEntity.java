package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.enums.Team;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A player in the lineup.
 */
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class PlayerEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -1593562367717439150L;
    private List<AttributeEntity> attributes;
    private int id;
    private List<MatchRoleEntity> matchRoles;
    private String name;
    private String nickname;
    private String position;
    private int shirtNumber;
    private Boolean substitute;
    private Team team;
    private Integer order;

    public PlayerEntity() {
    }

    /**
     * List of attributes
     *
     * @return attributes
     */
    public List<AttributeEntity> getAttributes() {
        return attributes;
    }

    /**
     * ID of the player.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * List of match roles
     *
     * @return match roles
     */
    public List<MatchRoleEntity> getMatchRoles() {
        return matchRoles;
    }

    /**
     * Name of the player.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets nickname
     *
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the position of the player?
     *
     * @return player position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Shirt number.
     *
     * @return shirt number
     */
    public int getShirtNumber() {
        return shirtNumber;
    }

    /**
     * Team of the player.
     *
     * @return team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Order of the player
     * @return order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Is the player a substitute?
     *
     * @return substitute
     */
    public Boolean isSubstitute() {
        return substitute;
    }
}
