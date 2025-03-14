package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

public enum LatencyLevel implements EntityEnum {
    INVALID(-1),

    NOT_SET(0),

    MINIMAL(1),

    LOW(2),

    MODERATE(3),

    HIGH(4),

    VERY_HIGH(5);

    private int value;

    LatencyLevel(int value) {
        this.value = value;
    }

    public static LatencyLevel getLatencyLevelFromValue(int value) {
        LatencyLevel result = EntityEnumHelper.getEnumMemberFromValue(LatencyLevel.values(), value);
        if (result == null) {
            result = LatencyLevel.INVALID;
            result.value = value;
        }
        return result;
    }

    @Override
    public boolean isValueEqual(Object value) {
        if (value instanceof Integer) {
            return (Integer) value == this.value;
        }
        return false;
    }

    @Override
    public Integer getValue() {
        return value;
    }

}
