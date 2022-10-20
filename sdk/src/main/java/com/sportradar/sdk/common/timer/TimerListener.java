/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/
package com.sportradar.sdk.common.timer;

/**
 * Represents a listener used to observe the {@link Timer} implementations
 *
 * @author uros.bregar
 */
public interface TimerListener {

    /**
     * Invoked by the observed {@link Timer} implementation when the timer's interval elapses.
     */
    public void onTick();
}
