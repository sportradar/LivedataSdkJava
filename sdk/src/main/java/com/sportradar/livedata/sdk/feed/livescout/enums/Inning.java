package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

/**
 * Inning enumeration
 */
public enum Inning implements EntityEnum {
    /**
     * Top
     */
    TOP("T"),
    /**
     * Bottom
     */
    BOTTOM("B");


    private String literalValue;

    Inning(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return Inning enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static Inning getInningFromLiteralValue(String value) throws UnknownEnumException {
        Inning result = EntityEnumHelper.getEnumMemberFromValue(Inning.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(Inning.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isValueEqual(Object value) {
        return literalValue.equals(value);
    }

    @Override
    public String getValue() {
        return literalValue;
    }
}
