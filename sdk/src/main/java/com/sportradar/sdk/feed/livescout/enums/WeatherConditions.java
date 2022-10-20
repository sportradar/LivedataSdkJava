package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Status of weather conditions
 */
public enum WeatherConditions implements EntityEnum {
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
    UNKNOWN("UNKNOWN"),
    /**
     * Indoor
     */
    INDOOR("INDOOR"),
    /**
     * Extreme
     */
    EXTREME("EXTREME");


    private String literalValue;

    WeatherConditions(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return WeatherConditions enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static WeatherConditions getWeatherConditionsFromLiteralValue(String value) throws UnknownEnumException {
        WeatherConditions result = EntityEnumHelper.getValueFromLiteralValue(WeatherConditions.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(WeatherConditions.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }
}
