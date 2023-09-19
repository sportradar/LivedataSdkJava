package com.sportradar.livedata.sdk.feed.livescout.enums;

import com.sportradar.livedata.sdk.common.classes.EntityEnumHelper;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

/**
 * Surface type
 */
public enum SurfaceType implements EntityEnum {

    /**
     * Sand
     */
    SAND("SAND"),
    /**
     * Hard court
     */
    HARDCOURT("HARDCOURT"),
    /**
     * Grass
     */
    GRASS("GRASS"),
    /**
     * Carpet
     */
    CARPET("CARPET"),
    /**
     * Unknown
     */
    UNKNOWN("UNKNOWN");


    private String literalValue;

    SurfaceType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return SurfaceType enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static SurfaceType getSurfaceTypeFromLiteralValue(String value) throws UnknownEnumException {
        SurfaceType result = EntityEnumHelper.getEnumMemberFromValue(SurfaceType.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(SurfaceType.class.getSimpleName(), value);
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
