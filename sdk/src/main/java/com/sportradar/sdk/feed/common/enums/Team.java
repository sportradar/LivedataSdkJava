package com.sportradar.sdk.feed.common.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Present Home, Away or None team
 */
public enum Team implements EntityEnum {

    /**
     * Away team
     */
    HOME("1", "home"),

    /**
     * Home team
     */
    AWAY("2", "away"),

    /**
     * None (neither home or away)
     */
    NONE("-1", "0", "none", "null");

    private String[] literalValues;

    Team(String... literalValues) {
        this.literalValues = literalValues;
    }


    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return Team enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static Team getTeamFromLiteralValue(String value) throws UnknownEnumException {
        Team result = EntityEnumHelper.getValueFromLiteralValue(Team.values(), value);
        if (result == null && value != null && !value.isEmpty()) {
            throw new UnknownEnumException(Team.class.getSimpleName(), value);
        }
        return result;
    }

    /**
     * Determines whether the passed {@code value} is equal to value associated with the current {@link EntityEnum}
     *
     * @param value a value to be compared to the value associated with the current {@link EntityEnum}
     * @return value true if passed {@code value} is equal to the value associated with the current {@link EntityEnum}. Otherwise false.
     */
    @Override
    public boolean isLiteralValueEqual(String value) {
        for (String literalValue : literalValues) {
            if (literalValue.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
