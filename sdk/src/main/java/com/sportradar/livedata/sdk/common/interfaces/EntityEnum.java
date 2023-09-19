/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common.interfaces;

/**
 * Represents an entity enumeration
 *
 * @author uros.bregar
 */
public interface EntityEnum {

    /**
     * Determines whether the passed {@code value} is equal to value associated with the current {@link EntityEnum}
     *
     * @param value a value to be compared to the value associated with the current {@link EntityEnum}
     * @return value true if passed {@code value} is equal to the value associated with the current {@link EntityEnum}. Otherwise false.
     */
    boolean isValueEqual(Object value);

    /**
     * Returns value of concrete enum member.
     *
     * @return enum value
     */
    Object getValue();
}
