package com.sportradar.livedata.sdk.feed.common.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Serves as a base class for all client data contracts/entities.
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public abstract class EntityBase implements Serializable {

    private static final long serialVersionUID = 6131509065819903558L;
    private Map<String, String> additionalData;

    protected EntityBase() {
    }

    protected EntityBase(Map<String, String> otherAttributes) {
        if (otherAttributes != null) {
            additionalData = new HashMap<>(otherAttributes.size());
            for (Map.Entry<String, String> entry : otherAttributes.entrySet()) {
                additionalData.put(entry.getKey(), entry.getValue());
            }
            additionalData = Collections.unmodifiableMap(additionalData);
        }
    }

    /**
     * Contains any additional XML attributes found while parsing XML feed messages
     * that are not supported with the current version of the data contracts.
     * These are represented as an XML attribute name-value tuples.
     * @return data than was not parsed successfully
     */
    @Deprecated
    public Map<String, String> getAdditionalData() {
        return additionalData;
    }

    /**
     * Get the unique event id.
     *
     * @return - an event id or null
     */
    public EventIdentifier getEventId() {
        return null;
    }
}