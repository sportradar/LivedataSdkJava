/**
 * ************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 * *************************************************************
 */

package com.sportradar.sdk.feed.common.entities;

import java.io.Serializable;

/**
 * Contains type and value
 */
public class TypeValueTuple implements Serializable {

    private static final long serialVersionUID = -2583705848146796033L;
    String type;
    String value;

    public TypeValueTuple(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * For Serializable
     */
    protected TypeValueTuple() {

    }

    /**
     * Gets type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeValueTuple that = (TypeValueTuple) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }
}
