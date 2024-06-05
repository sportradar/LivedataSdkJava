package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

public enum TeamStatsType implements EntityEnum {

    TOTAL("total"),
    INNING1("inning1"),
    INNING2("inning2"),
    INNING3("inning3"),
    INNING4("inning4"),
    INNING5("inning5"),
    INNING6("inning6"),
    INNING7("inning7"),
    INNING8("inning8"),
    INNING9("inning9"),
    INNINGE("inninge");

    private final String literalValue;

    TeamStatsType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return TeamStatsType enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static TeamStatsType getTeamStatsTypeFromLiteralValue(String value) throws UnknownEnumException {
        TeamStatsType result = EntityEnumHelper.getEnumMemberFromValue(TeamStatsType.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(WeatherConditions.class.getSimpleName(), value);
        }
        return result;
    }


    @Override
    public boolean isValueEqual(Object value) {
        return literalValue.equals(value);
    }

    @Override
    public Object getValue() {
        return literalValue;
    }
}
