package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Behinds
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class BehindsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int t1;
    private final int t2;
    private final String type;

    BehindsEntity(int t1, int t2, String type) {
        this.t1 = t1;
        this.t2 = t2;
        this.type = type;
    }

    /**
     * Returns the team one behind indication
     *
     * @return the team one behind indication
     */
    public int getT1() {
        return t1;
    }

    /**
     * Returns the team two behind indication
     *
     * @return the team two behind indication
     */
    public int getT2() {
        return t2;
    }

    /**
     * Returns the behind type indication
     *
     * @return the behind type indication
     */
    public String getType() {
        return type;
    }
}
