package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Hold information about scout
 */
//It is better to leave getters as is for javadoc purpose.
// Do we need such entity?
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
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
}
