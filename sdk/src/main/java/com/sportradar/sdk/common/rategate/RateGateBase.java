/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import org.joda.time.Duration;

public abstract class RateGateBase implements RateGate {

    protected final Object listenerLock = new Object();
    protected RateGateListener listener;

    public RateGateBase() {
        listener = null;
    }

    @Override
    public void waitToProceed() throws InterruptedException {
        waitToProceed(1);
    }

    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param wait - when specified this means the maximum amount of time to wait
     * @return true when we passed the gate; false when a timeout occured
     * @throws InterruptedException when somebody interrupted the waiting
     */
    @Override
    public boolean waitToProceed(final Duration wait) throws InterruptedException {
        return waitToProceed(1, wait);
    }

    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param amount - number of elements
     * @param wait   - the maximum amount of time to wait, negative means infinity
     * @return true when we passed the gate; false when a timeout occured
     * @throws InterruptedException when somebody interrupted the waiting
     */
    @Override
    public abstract boolean waitToProceed(
            final int amount,
            final Duration wait)
            throws InterruptedException;

    /**
     * Approximately how long will I have to wait?
     *
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    @Override
    public Duration howLongToWait() {
        return howLongToWait(1);
    }

    /**
     * Approximately how long will I have to wait?
     *
     * @param amount - number of elements
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    @Override
    public abstract Duration howLongToWait(final int amount);

    /**
     * Set the event listener
     *
     * @param listener - new listener
     */
    @Override
    public void setListener(RateGateListener listener) {
        synchronized (listenerLock) {
            this.listener = listener;
        }
    }
}
