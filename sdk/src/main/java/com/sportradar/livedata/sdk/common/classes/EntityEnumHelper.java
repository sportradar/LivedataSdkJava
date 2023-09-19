/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common.classes;

import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;

/**
 * Helper class containing static helper methods for {@link EntityEnum} class.
 *
 * @author uros.bregar
 */
public class EntityEnumHelper {

    /**
     * Gets the enumeration member associated with the passed {@code literalValue}
     *
     * @param <T>     The type of the enum
     * @param members The members of the target enumeration.
     * @param value   The value to be compared with the value associated with the members
     * @return The member of the target enum or a null reference.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntityEnum> T getEnumMemberFromValue(T[] members, Object value) {
        if (value == null) {
            return null;
        }

        for (T member : members) {
            if (member.isValueEqual(value)) {
                return member;
            }
        }
        return null;
    }
}

