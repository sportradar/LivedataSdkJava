package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

public enum TeamsReversed implements EntityEnum {
    /**
     * Order not confirmed
     */
    ORDER_NOT_CONFIRMED(1),

    /**
     * Order reversed on official
     */
    ORDER_REVERSED(2);

    private final int value;

    TeamsReversed(int value) {
        this.value = value;
    }

    /**
     * Gets enum from int value
     *
     * @param value value representation of enum
     * @return TeamsReversed enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static TeamsReversed getTeamsReversedFromValue(int value) throws UnknownEnumException {
        TeamsReversed result = EntityEnumHelper.getEnumMemberFromValue(TeamsReversed.values(), value);
        if (result == null) {
            throw new UnknownEnumException(TeamsReversed.class.getSimpleName(), String.valueOf(value));
        }
        return result;
    }

    @Override
    public boolean isValueEqual(Object value) {
        //the result of the instanceof operator is true if the value of the RelationalExpression is not null
        // and the reference could be cast to the ReferenceType without raising a ClassCastException.
        // Otherwise the result is false.
        if(value instanceof Integer){
            //If the operands of an equality operator are both of numeric type, or one is of numeric type and
            // the other is convertible to numeric type, binary numeric promotion is performed on the operands.
            return (Integer) value == this.value;
        }
        return false;
    }

    public Integer getValue() {
        return value;
    }
}
