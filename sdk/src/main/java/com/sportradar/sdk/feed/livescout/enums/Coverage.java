package com.sportradar.sdk.feed.livescout.enums;


import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Type of event coverage
 */
public enum Coverage implements EntityEnum {
    /**
     * TV coverage
     */
    TV("tv"),

    /**
     * Coverage from the venue
     */
    VENUE("venue");


    private String literalValue;

    Coverage(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return Coverage enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static Coverage getCoverageFromLiteralValue(String value) throws UnknownEnumException {
        Coverage result = EntityEnumHelper.getValueFromLiteralValue(Coverage.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(Coverage.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
