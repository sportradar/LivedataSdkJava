package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.livescout.enums.FormatType;

import java.io.Serializable;

/**
 * Match format
 */
public class FormatEntity implements Serializable {

    private static final long serialVersionUID = -530343858841307170L;
    private FormatType formatType;
    private int value;

    /**
     * For Serializable
     */
    protected FormatEntity() {

    }


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

    protected void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    protected void setValue(int value) {
        this.value = value;
    }
}
