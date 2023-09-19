package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.entities.EntityBase;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

/**
 * Serves as a base class that all LiveScout should be derived from.
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode(callSuper = true)
@ToString
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
}