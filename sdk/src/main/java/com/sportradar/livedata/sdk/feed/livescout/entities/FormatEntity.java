package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.livescout.enums.FormatType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Match format
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class FormatEntity implements Serializable {

    private static final long serialVersionUID = -530343858841307170L;
    private FormatType formatType;
    private int value;

    /**
     * For Serializable
     */
    protected FormatEntity() {}


    /**
     * Gets format type
     *
     * @return format type
     */
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * Gets format value
     *
     * @return format value
     */
    public int getValue() {
        return value;
    }
}
