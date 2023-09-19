package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Contains information about innings
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class InningsEntity implements Serializable {

    private static final long serialVersionUID = 2059804753244499408L;
    private int dismissals;
    private int runs;
    private int statusId;
    private String statusName;

    /**
     * For Serializable
     */
    protected InningsEntity() {}

    /**
     * Gets number of dismissals
     *
     * @return dismissals
     */
    public int getDismissals() {
        return dismissals;
    }

    /**
     * Gets number of runs
     *
     * @return number of runs
     */
    public int getRuns() {
        return runs;
    }

    /**
     * Get status id
     *
     * @return status id
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * Gets status name
     *
     * @return status name
     */
    public String getStatusName() {
        return statusName;
    }
}
