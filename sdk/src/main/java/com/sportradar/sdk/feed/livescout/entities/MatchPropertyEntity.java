package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;

/**
 * A match property representation
 */
public class MatchPropertyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String type;
    private final String value;

    MatchPropertyEntity(String type, String value) {
//       Preconditions.checkNotNull();

        this.type = type;
        this.value = value;
    }

    /**
     * Returns the property type
     *
     * @return the property type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the property value
     *
     * @return the property value
     */
    public String getValue() {
        return value;
    }
}
