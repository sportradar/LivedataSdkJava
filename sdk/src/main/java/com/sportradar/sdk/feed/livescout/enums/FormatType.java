package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Format type
 */
public enum FormatType implements EntityEnum {
    /**
     * Limited overs
     */
    LIMITED_OVERS("limitedovers"),
    /**
     * Overs
     */
    OVERS("overs"),
    /**
     * Days
     */
    DAYS("days"),
    /**
     * Mandatory powerplay overs
     */
    MANDATORY_POWERPLAY_OVERS("mandatorypowerplayovers"),
    /**
     * Batting powerplay overs
     */
    BATTING_POWERPLAY_OVERS("battingpowerplayovers"),
    /**
     * Powerplay 1 overs
     */
    POWERPLAY1OVERS("powerplay1overs"),
    /**
     * Powerplay 2 overs
     */
    POWERPLAY2OVERS("powerplay2overs"),
    /**
     * Powerplay 3 overs
     */
    POWERPLAY3OVERS("powerplay3overs"),

    /**
     * Max overs per bowler
     */
    MAX_OVERS_PER_BOWLER("maxoversperbowler"),
    /**
     * Reviews
     */
    REVIEWS("reviews"),
    /**
     * Maps
     */
    MAPS("maps"),
    /**
     * Rounds
     */
    ROUNDS("rounds"),

    /**
     * Overtime rounds
     */
    OVERTIME_ROUNDS("overtimerounds"),
    /**
     * Number of periods
     */
    NUMBER_OF_PERIODS("numberofperiods"),
    /**
     * Period length
     */
    PERIOD_LENGTH("periodlength"),
    /**
     * Try value
     */
    TRY_VALUE("tryvalue"),
    /**
     * Conversion value
     */
    CONVERSION_VALUE("conversionvalue"),
    /**
     * Penalty value
     */
    PENALTY_VALUE("penaltyvalue"),
    /**
     * Drop goal value
     */
    DROP_GOAL_VALUE("dropgoalvalue"),
    /**
     * Penalty try value
     */
    PENALTY_TRY_VALUE("penaltytryvalue"),
    /**
     * Number overtime periods
     */
    NUMBER_OVERTIME_PERIODS("numberovertimeperiods"),
    /**
     * Length overtime periods
     */
    LENGTH_OVERTIME_PERIODS("lengthovertimeperiods"),
    /**
     * Length sudden death
     */
    LENGTH_SUDDEN_DEATH("lengthsuddendeath"),
    /**
     * Penalty shootout
     */
    PENALTY_SHOOTOUT("penaltyshootout"),
    /**
     * Rule set
     */
    RULE_SET("ruleset"),
    /**
     * Two point conversion yard line
     */
    TWO_POINT_CONVERSION_YARD_LINE("twopointconversionyardline"),
    /**
     * Extra point yard line
     */
    EXTRA_POINT_YARD_LINE("extrapointyardline"),
    /**
     * Penalty value of no ball
     */
    NO_BALL_PENALTY_VALUE("noballpenaltyvalue"),
    /**
     * How a tied match is resolved, if at all
     */
    TIE_BREAKER_METHOD("tiebreakermethod"),
    /**
     * Number of regular innings in MLB
     */
    REGULAR_INNINGS("regularinnings")

    ;

    private String literalValue;

    FormatType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return FormatType enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static FormatType getFormatTypeFromLiteralValue(String value) throws UnknownEnumException {
        FormatType result = EntityEnumHelper.getValueFromLiteralValue(FormatType.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(FormatType.class.getSimpleName(), value);
        }
        return result;
    }

    /**
     * Gets literal value
     *
     * @return literal value
     */
    public String getLiteralValue() {
        return literalValue;
    }

    /**
     * Determines whether the passed {@code value} is equal to value associated with the current {@link FormatType}
     *
     * @param value a value to be compared to the value associated with the current {@link FormatType}
     * @return value true if passed {@code value} is equal to the value associated with the current {@link FormatType}. Otherwise false.
     */
    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
