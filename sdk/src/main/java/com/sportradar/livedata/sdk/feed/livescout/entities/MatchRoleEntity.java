package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Contains player match roles
 */
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class MatchRoleEntity implements Serializable {

    private static final long serialVersionUID = 2074908659633349555L;
    private String description;
    private int id;

    /**
     * For Serializable
     */
    protected MatchRoleEntity() {

    }

    /**
     * Description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets id
     *
     * @return id
     */
    public int getId() {
        return id;
    }
}
