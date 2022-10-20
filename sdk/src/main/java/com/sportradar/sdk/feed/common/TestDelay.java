/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common;

import org.joda.time.Duration;

/**
 * Represents a delay specification for the test server.
 */
public class TestDelay {

    protected final Integer msgNr;
    protected final Duration delay;

    protected TestDelay(final Integer msgNr, final Duration delay) {
        this.msgNr = msgNr;
        this.delay = delay;
    }

    /**
     * Constructs test delay instance
     * @param skip interval to skip
     * @return test delay instance
     */
    public static TestDelay fromDelay(final Duration skip) {
        return new TestDelay(null, skip);
    }

    /**
     * Constructs test delay instance
     * @param msgNr msg nr
     * @return test delay instance
     */
    public static TestDelay fromMsgNr(final int msgNr) {
        return new TestDelay(msgNr, Duration.ZERO);
    }

    /**
     * Constructs test delay instance
     * @param msgNr msg nr
     * @param skip interval to skip
     * @return test delay instance
     */
    public static TestDelay fromMsgNrAndSkip(final int msgNr, final Duration skip) {
        return new TestDelay(msgNr, skip);
    }

    /**
     * Gets test skip
     * @return test skip
     */
    public Duration getDelay() {
        return delay;
    }

    /**
     * Gets msngr to start with
     * @return starting msgnr
     */
    public Integer getMsgNr() {
        return msgNr;
    }
}
