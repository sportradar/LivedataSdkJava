package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;

/**
 * Contains player match roles
 */
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
        return "MatchRole{" +
                "description='" + description + '\'' +
                ", id=" + id +
                '}';
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setId(int id) {
        this.id = id;
    }
}
