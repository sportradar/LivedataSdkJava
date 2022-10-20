/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import org.joda.time.DateTime;

public class RateGateQueueElement {

    private DateTime expirationTime;

    public RateGateQueueElement(DateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public DateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(DateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
