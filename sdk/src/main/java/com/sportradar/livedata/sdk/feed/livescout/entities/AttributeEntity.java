package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Contains players attributes
 */
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class AttributeEntity implements Serializable {

    private static final long serialVersionUID = -3050286847169465168L;
    private String type;
    private int typeId;
    private String value;
    private int valueId;


    /**
     * For Serializable
     */
    protected AttributeEntity() {

    }

    /**
     * Type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Type id
     *
     * @return type id
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Value id
     *
     * @return value id
     */
    public int getValueId() {
        return valueId;
    }
}
