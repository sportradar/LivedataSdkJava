package com.sportradar.sdk.test;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

import java.util.Comparator;

public class LiveScoutStatusComparator implements Comparator<OutgoingMessage> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' embeddedentities prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(OutgoingMessage first, OutgoingMessage second) {

        if ((first != null && second != null && first.getClass().equals(second.getClass()))
                || first == second) {//in case both are null
            return 0;
        }

        return -1;
    }

}
