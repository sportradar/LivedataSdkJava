package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Match bet status
 */
public enum MatchBetStatus implements EntityEnum {

    /**
     * Not started
     */
    NOT_STARTED("NOT_STARTED"),

    /**
     * Started
     */
    STARTED("STARTED"),

    /**
     * Bet stop
     */
    BETSTOP("BETSTOP");


    private String literalValue;

    MatchBetStatus(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return MatchBetStatus enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static MatchBetStatus getMatchBetStatusFromLiteralValue(String value) throws UnknownEnumException {
        MatchBetStatus result = EntityEnumHelper.getValueFromLiteralValue(MatchBetStatus.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(MatchBetStatus.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
