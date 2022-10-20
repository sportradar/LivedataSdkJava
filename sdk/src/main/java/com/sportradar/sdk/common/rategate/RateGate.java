/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import org.joda.time.Duration;

public interface RateGate {


    /**
     * Block when configured rate-limits are exceeded.
     *
     * @throws InterruptedException when somebody interrupted the waiting
     */
    void waitToProceed() throws InterruptedException;


    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param wait - the maximum amount of time to wait, negative means infinity
     * @return true when we passed the gate; false when a timeout occurred
     * @throws InterruptedException when somebody interrupted the waiting
     */
    boolean waitToProceed(Duration wait) throws InterruptedException;

    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param amount - number of elements
     * @throws InterruptedException when somebody interrupted the waiting
     */
    void waitToProceed(int amount) throws InterruptedException;


    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param amount - number of elements
     * @param wait   - the maximum amount of time to wait, negative means infinity
     * @return true when we passed the gate; false when a timeout occurred
     * @throws InterruptedException when somebody interrupted the waiting
     */
    boolean waitToProceed(int amount, Duration wait) throws InterruptedException;

    /**
     * Approximately how long will I have to wait?
     *
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    Duration howLongToWait();

    /**
     * Approximately how long will I have to wait?
     *
     * @param amount - number of elements
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    Duration howLongToWait(int amount);

    /**
     * Set the event listener
     *
     * @param listener - new listener
     */
    void setListener(RateGateListener listener);
}
