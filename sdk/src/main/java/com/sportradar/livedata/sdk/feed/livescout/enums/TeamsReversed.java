package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;

public enum TeamsReversed {
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

    public int getValue() {
        return value;
    }

    /**
     * Gets enum from int value
     *
     * @param value value representation of enum
     * @return TeamsReversed enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static TeamsReversed findByValue(int value) throws UnknownEnumException {
        for (TeamsReversed teamsReversed : values()) {
            if (teamsReversed.value == value) {
                return teamsReversed;
            }
        }
        throw new UnknownEnumException(TeamsReversed.class.getSimpleName(), String.valueOf(value));
    }
}
