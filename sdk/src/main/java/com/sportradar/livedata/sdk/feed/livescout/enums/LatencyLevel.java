package com.sportradar.livedata.sdk.feed.livescout.enums;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.classes.LoggerProvider;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

public enum LatencyLevel implements EntityEnum {
    UNKNOWN(0), // seems that LDS won't send anything if NOT SET
    LOW(1),
    MODERATE(2),
    HIGH(3),
    VERY_HIGH(4),
    EXCEPTIONAL(5);

    private final int value; // basically id of latency

    LatencyLevel(int value) {
        this.value = value;
    }

    public static LatencyLevel getLatencyLevelFromValue(Integer value) {
        if (value != null) {
            LatencyLevel result = EntityEnumHelper.getEnumMemberFromValue(LatencyLevel.values(), value);
            if (result == null) {
                LoggerProvider.getLogger().logAlert(Level.WARN, "Unknown latency level value: " + value);
                return UNKNOWN;
            }
            return result;
        } else {
            return null;
        }
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
