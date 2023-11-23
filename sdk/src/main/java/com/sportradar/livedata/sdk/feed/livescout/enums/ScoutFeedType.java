package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

/**
 * Type of event
 */
public enum ScoutFeedType implements EntityEnum {
    /**
     * Full message
     */
    FULL("full"),
    /**
     * Delta message
     */
    DELTA("delta"),
    /**
     * Delta update message
     */
    DELTAUPDATE("deltaupdate"),
    /**
     *
     */
    PARTIAL("partial");
    //lightweight is not used on customer side


    private String literalValue;

    ScoutFeedType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return ScoutFeedType enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static ScoutFeedType getScoutFeedTypeFromLiteralValue(String value) throws UnknownEnumException {
        ScoutFeedType result = EntityEnumHelper.getEnumMemberFromValue(ScoutFeedType.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(ScoutFeedType.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isValueEqual(Object value) {
        return literalValue.equals(value);
    }

    @Override
    public String getValue() {
        return literalValue;
    }
}
