/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common;

import org.joda.time.Duration;

/**
 * Represents a delay specification for the test server together with some speed.
 */
public class TestDelayWithSpeed extends TestDelay {

    protected TestDelayWithSpeed(Integer msgNr, Duration delay) {
        super(msgNr, delay);
    }

    /**
     * Constructs test delay instance
     * @param msgNr msgnr to start the test with
     * @param speed speed with which messages should be send
     * @return test delay instance
     */
    public static TestDelayWithSpeed fromMsgNrWithSpeed(final int msgNr, final Duration speed) {
        return new TestDelayWithSpeed(msgNr, speed);
    }
}
