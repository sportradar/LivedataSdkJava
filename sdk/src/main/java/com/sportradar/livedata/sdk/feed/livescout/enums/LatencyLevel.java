package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;
import lombok.Getter;
import lombok.Setter;

public enum LatencyLevel implements EntityEnum {
    UNKNOWN(0, "unknown"), // seems that LDS won't send anything if NOT SET
    LOW(1, "low"),
    MODERATE(2, "moderate"),
    HIGH(3, "high"),
    VERY_HIGH(4, "very_high"),
    EXCEPTIONAL(5, "exceptional");

    private int value; // basically id of latency

    private String name; // in case of new latency level, we also will have its name saved

    LatencyLevel(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static LatencyLevel getLatencyLevelFromValue(int value) {
        LatencyLevel result = EntityEnumHelper.getEnumMemberFromValue(LatencyLevel.values(), value);
        return result == null ? UNKNOWN : result;
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

    public String getName() {
        return name;
    }

}
