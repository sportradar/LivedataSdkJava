package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Ball event type enum
 */
public enum BallEventType implements EntityEnum {

    UNKNOWN(0),
    FIRST(1),
    HIT(2),
    NET(3),
    CLIP(4),
    CROSSING(5),
    BOUNCE(6),
    LAST(7);

    private int value;

    BallEventType(int value) {
        this.value = value;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return EventType enum
     */
    public static BallEventType getEventTypeFromValue(int value) {
        BallEventType result = EntityEnumHelper.getEnumMemberFromValue(BallEventType.values(), value);
        if (result == null) {
            result = BallEventType.UNKNOWN;
            result.value = value;
        }
        return result;
    }

    @Override
    public boolean isValueEqual(Object value) {
        if(value instanceof Integer){
            return (Integer) value == this.value;
        }
        return false;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
