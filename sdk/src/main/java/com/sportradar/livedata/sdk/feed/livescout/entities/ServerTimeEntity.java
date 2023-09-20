package com.sportradar.livedata.sdk.feed.livescout.entities;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Map;

/**
 * Caries server time
 */
public class ServerTimeEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -6864699162271736628L;
    private DateTime serverTime;


    protected ServerTimeEntity() {
    }

    /**
     * Gets server time
     *
     * @return server time
     */
    public DateTime getServerTime() {
        return serverTime;
    }

    protected void setServerTime(DateTime serverTime) {
        this.serverTime = serverTime;
    }
}
