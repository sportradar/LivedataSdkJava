/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.interfaces;

/**
 * Represents a class used to generate distinct values(numbers)
 */
public interface SequenceGenerator {

    /**
     * Generates and returns next sequence number.
     *
     * @return next sequence number.
     */
    long getNext();
}
