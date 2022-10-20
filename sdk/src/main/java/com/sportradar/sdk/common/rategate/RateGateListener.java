/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

public interface RateGateListener {

    /**
     * Signals that a rategate has been passed.
     *
     * @param amount - the amount
     */
    void onRateGatePassed(int amount);
}
