/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import org.joda.time.Duration;

public class NullRateGate extends RateGateBase implements RateGate {

    @Override
    public void waitToProceed(int amount) throws InterruptedException {

    }

    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param amount - number of elements
     * @param wait   - the maximum amount of time to wait, negative means infinity
     * @return true when we passed the gate; false when a timeout occurred
     * @throws InterruptedException when somebody interrupted the waiting
     */
    @Override
    public boolean waitToProceed(int amount, Duration wait) throws InterruptedException {
        return true;
    }

    /**
     * Approximately how long will I have to wait?
     *
     * @param amount - number of elements
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    @Override
    public Duration howLongToWait(int amount) {
        return Duration.ZERO;
    }
}
