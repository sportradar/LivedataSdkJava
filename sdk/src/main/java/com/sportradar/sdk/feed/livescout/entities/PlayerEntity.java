package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.enums.Team;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A player in the lineup.
 */
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

    protected PlayerEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
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

    /**
     * Returns a string that represents the current object.
     * <p>
     * Note : Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "PlayerEntity{" +
                "attributes=" + attributes +
                ", id=" + id +
                ", matchRoles=" + matchRoles +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", position='" + position + '\'' +
                ", shirtNumber=" + shirtNumber +
                ", substitute=" + substitute +
                ", team=" + team +
                ", order=" + order +
                "} " + super.toString();
    }


    protected void setAttributes(List<AttributeEntity> attributes) {
        this.attributes = attributes;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setMatchRoles(List<MatchRoleEntity> matchRoles) {
        this.matchRoles = matchRoles;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }

    protected void setPosition(String position) {
        this.position = position;
    }

    protected void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    protected void setSubstitute(Boolean substitute) { this.substitute = substitute; }

    protected void setTeam(Team team) {
        this.team = team;
    }

    protected void setOrder(Integer order) { this.order = order; }
}
