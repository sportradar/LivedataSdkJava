package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;

/**
 * Contains information about innings
 */
public class InningsEntity implements Serializable {

    private static final long serialVersionUID = 2059804753244499408L;
    private int dismissals;
    private int runs;
    private int statusId;
    private String statusName;

    /**
     * For Serializable
     */
    protected InningsEntity() {
    }

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
        return "Innings{" +
                "dismissals=" + dismissals +
                ", runs=" + runs +
                ", statusId=" + statusId +
                ", statusName='" + statusName + '\'' +
                '}';
    }

    protected void setDismissals(int dismissals) {
        this.dismissals = dismissals;
    }

    protected void setRuns(int runs) {
        this.runs = runs;
    }

    protected void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    protected void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
