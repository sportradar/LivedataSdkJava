package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Ice conditions enumeration
 */
public enum IceConditions implements EntityEnum {

    /**
     * Good
     */
    GOOD("GOOD"),

    /**
     * Medium
     */
    MEDIUM("MEDIUM"),
    /**
     * Bad
     */
    BAD("BAD"),
    /**
     * Unknown
     */
    UNKNOWN("UNKNOWN");


    private String literalValue;

    IceConditions(String literalValue) {
        this.literalValue = literalValue;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return IceConditions enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static IceConditions getIceConditionsFromLiteralValue(String value) throws UnknownEnumException {
        IceConditions result = EntityEnumHelper.getValueFromLiteralValue(IceConditions.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(IceConditions.class.getSimpleName(), value);
        }
        return result;
    }


}
