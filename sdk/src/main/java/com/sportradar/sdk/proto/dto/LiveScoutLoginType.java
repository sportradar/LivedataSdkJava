/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.dto;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Enumerates available values of the {@link com.sportradar.sdk.proto.dto.incoming.livescout.Login} result
 */
public enum LiveScoutLoginType implements EntityEnum {

    VALID("valid"),
    INVALID("invalid");

    /**
     * Value associated with the current instance.
     */
    private String literalValue;

    /**
     * Initializes a new instance of the {@link LiveScoutLoginType} class
     *
     * @param literalValue
     */
    LiveScoutLoginType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets the string value associated with the current instance.
     *
     * @return the string value associated with the current instance.
     */
    public String getLiteralValue() {
        return this.literalValue;
    }

    /**
     * Determines whether the string value associated with the current instance is equal to passed {@code value}
     *
     * @param value The value to be compared.
     * @return True if {@code value} is equal to value associated with the current instance. Otherwise false.
     */
    @Override
    public boolean isLiteralValueEqual(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return this.literalValue.equals(value);
    }

    /**
     * Constructs and return {@link LiveScoutLoginType} enum member from passed {@code value}
     *
     * @param value The value used to construct the returned {@link LiveScoutLoginType} instance.
     * @return The constructed {@link LiveScoutLoginType} instance or a null reference.
     */
    public static LiveScoutLoginType getTypeFromLiteralValue(String value) {
        return EntityEnumHelper.getValueFromLiteralValue(LiveScoutLoginType.values(), value);
    }
}
