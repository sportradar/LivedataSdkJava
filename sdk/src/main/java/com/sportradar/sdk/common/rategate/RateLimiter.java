/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

public class RateLimiter {

    protected RateGate loginLimiter;
    protected RateGate requestLimiter;
    protected RateGate matchRequestLimiter;

    public RateLimiter(
            RateGate loginLimiter,
            RateGate requestLimiter,
            RateGate matchRequestLimiter
    ) {
        this.loginLimiter = loginLimiter;
        this.requestLimiter = requestLimiter;
        this.matchRequestLimiter = matchRequestLimiter;
    }


    public RateGate getLoginLimiter() {
        return loginLimiter;
    }

    public RateGate getRequestLimiter() {
        return requestLimiter;
    }

    public RateGate getMatchRequestLimiter() {
        return matchRequestLimiter;
    }

}
