/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import com.sportradar.sdk.common.interfaces.SequenceGenerator;

import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A {@link SequenceGenerator} implementation where each value is obtained by incrementing
 * the previous one.
 *
 * @author uros.bregar
 */
public class IncrementalSequenceGenerator implements SequenceGenerator {

    /**
     * The {@link AtomicLong} used to increase the value as an atomic operation.
     */
    private final AtomicLong counter;

    /**
     * The min allowed value of the counter
     */
    private final long minValue;

    /**
     * The max allowed value of the counter
     */
    private final long maxValue;

    /**
     * Initializes a new instance of the {@link IncrementalSequenceGenerator} class.
     *
     * @param minValue The min allowed value of the counter.
     * @param maxValue The max allowed value of the counter. When the value is reached the value restarts at minValue.
     */
    public IncrementalSequenceGenerator(
            final long minValue,
            final long maxValue) {
        this(minValue, maxValue, true);
    }

    /**
     * Initializes a new instance of the {@link IncrementalSequenceGenerator} class.
     *
     * @param minValue              The min allowed value of the counter.
     * @param maxValue              The max allowed value of the counter. When the value is reached the value restarts at minValue.
     * @param initialRandomSelected If true, initial value will be randomly selected between minValue and maxValue
     */
    public IncrementalSequenceGenerator(
            final long minValue,
            final long maxValue,
            final boolean initialRandomSelected) {
        checkArgument(maxValue > minValue, "maxValue must be greater than minValue");

        long val;
        // Initial value is selected from interval [minValue, maxValue]
        if (initialRandomSelected) {
            val = minValue + Math.round(Math.random() * (maxValue - minValue));
        } else {
            val = minValue;
        }

        counter = new AtomicLong(val);

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Generates and returns next sequence number.
     *
     * @return next sequence number.
     */
    @Override
    public long getNext() {
        long current;
        long next = -1;

        boolean success = false;

        while (!success) {
            current = counter.get();
            next = current < maxValue
                    ? current + 1
                    : minValue;
            success = counter.compareAndSet(current, next);
        }

        return next;
    }
}
