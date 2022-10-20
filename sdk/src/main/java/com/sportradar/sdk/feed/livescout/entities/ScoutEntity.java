package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;

/**
 * Hold information about scout
 */
public class ScoutEntity implements Serializable {


    private static final long serialVersionUID = -6777561882085506160L;

    private int id;

    /**
     * Gets scout id
     *
     * @return scout id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ScoutEntity{" +
                "id=" + id +
                '}';
    }

    protected void setId(int id) {
        this.id = id;
    }
}
