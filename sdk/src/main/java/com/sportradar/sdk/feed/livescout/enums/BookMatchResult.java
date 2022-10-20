package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Book match result
 */
public enum BookMatchResult implements EntityEnum {
    /**
     * Valid
     */
    VALID("valid"),
    /**
     * Invalid
     */
    INVALID("invalid");


    private String literalValue;


    BookMatchResult(String value) {
        this.literalValue = value;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return BookMatchResult enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static BookMatchResult getBookMatchResultFromLiteralValue(String value) throws UnknownEnumException {
        BookMatchResult result = EntityEnumHelper.getValueFromLiteralValue(BookMatchResult.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(BookMatchResult.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
