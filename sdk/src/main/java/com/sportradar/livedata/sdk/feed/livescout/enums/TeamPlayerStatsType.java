package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

public enum TeamPlayerStatsType implements EntityEnum {

    TOTAL("total"),
    SET1("set1"),
    SET2("set2"),
    SET3("set3"),
    SET4("set4"),
    SET5("set5");

    private final String literalValue;

    TeamPlayerStatsType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return TeamStatsType enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static TeamPlayerStatsType getTeamPlayerStatsTypeFromLiteralValue(String value) throws UnknownEnumException {
        // TODO Move this crap to EntityEnumHelper and name this method simply - fromString(String value)
        // It is copy pasted in every enum class atm, while it can be squeezed into one line
        TeamPlayerStatsType result = EntityEnumHelper.getEnumMemberFromValue(TeamPlayerStatsType.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(TeamPlayerStatsType.class.getSimpleName(), value);
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
