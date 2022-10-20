package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Gender of team or contestant
 */
public enum Sex implements EntityEnum {
    /**
     * Male team or contestant.
     */
    MALE("0"),
    /**
     * Female team or contestant.
     */
    FEMALE("1"),
    /**
     * Mixed team.
     */
    MIXED("2");


    private String literalValue;

    Sex(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return Sex enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static Sex getSexFromLiteralValue(String value) throws UnknownEnumException {
        Sex result = EntityEnumHelper.getValueFromLiteralValue(Sex.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(Sex.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
