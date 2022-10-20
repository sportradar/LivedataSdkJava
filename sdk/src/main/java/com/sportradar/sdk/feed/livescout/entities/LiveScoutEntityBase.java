package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.entities.EntityBase;

import java.util.Map;

/**
 * Serves as a base class that all LiveScout should be derived from.
 */
public abstract class LiveScoutEntityBase extends EntityBase {

    private static final long serialVersionUID = 435631727372382886L;

    /**
     * For Serializable
     */
    protected LiveScoutEntityBase() {
        super(null);
    }

    protected LiveScoutEntityBase(Map<String, String> otherAttributes) {
        super(otherAttributes);
    }

    @Override
    public String toString() {
        return "LiveScoutEntityBase{" +
                super.toString() +
                '}';
    }
}