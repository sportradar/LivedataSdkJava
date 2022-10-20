package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Status of pitch conditions
 */
public enum PitchConditions implements EntityEnum {
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

    PitchConditions(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return PitchConditions enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static PitchConditions getPitchConditionsFromLiteralValue(String value) throws UnknownEnumException {
        PitchConditions result = EntityEnumHelper.getValueFromLiteralValue(PitchConditions.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(PitchConditions.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
