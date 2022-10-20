package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;

/**
 * Contains players attributes
 */
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

    /**
     * Returns a string that represents the current object.
     * <p>
     * Note : Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "Attribute{" +
                "type='" + type + '\'' +
                ", typeId=" + typeId +
                ", value=" + value +
                ", valueId='" + valueId + '\'' +
                '}';
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    protected void setValue(String value) {
        this.value = value;
    }

    protected void setValueId(int valueId) {
        this.valueId = valueId;
    }
}
