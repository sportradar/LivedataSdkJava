package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;
import java.util.Map;

/**
 * Connection test.
 */
public class ConnectionTestEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -4504343003126345374L;

    protected ConnectionTestEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
    }


    /**
     * Returns a string that represents the current object.
     * <p>
     * Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    public String toString() {
        return "ConnectionTestEntity{" +
                super.toString() +
                '}';
    }
}
