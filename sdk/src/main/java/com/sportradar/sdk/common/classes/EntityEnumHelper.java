/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Helper class containing static helper methods for {@link EntityEnum} class.
 *
 * @author uros.bregar
 */
public class EntityEnumHelper {

    /**
     * Gets the enumeration member associated with the passed {@code literalValue}
     *
     * @param members      The members of the target enumeration.
     * @param literalValue The literal value to be compared with the literal value associated with the members
     * @param <T>          The type of the enum
     * @return The member of the target enum or a null reference.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueFromLiteralValue(EntityEnum[] members, String literalValue) {
        if (literalValue == null || literalValue.isEmpty()) {
            return null;
        }

        for (EntityEnum member : members) {
            if (member.isLiteralValueEqual(literalValue)) {
                return (T) member;
            }
        }
        return null;
    }
}

