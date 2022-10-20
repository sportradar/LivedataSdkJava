package com.sportradar.sdk.feed.common;

import com.sportradar.sdk.feed.common.entities.EntityBase;

import java.util.Comparator;

/**
 * A {@link Comparator} implementation used to compare {@link MessageProcessor} instances.
 */
public class MessageProcessorComparator<T extends EntityBase> implements Comparator<MessageProcessor<T>> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param processor1 the first object to be compared.
     * @param processor2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException The {@code processor1} is a null reference or {@code processor2} is a null reference.
     * @throws ClassCastException   if the arguments' types prevent them from being compared by this comparator.
     */
    @Override
    public int compare(MessageProcessor<T> processor1, MessageProcessor<T> processor2) {
        return processor1.getIndex() - processor2.getIndex();
    }
}
