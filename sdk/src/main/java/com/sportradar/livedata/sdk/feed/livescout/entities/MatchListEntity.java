package com.sportradar.livedata.sdk.feed.livescout.entities;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Contains information about multiple matches
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class MatchListEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = 4362493347787349301L;
    private List<MatchUpdateEntity> matches;

    protected MatchListEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
    }

    /**
     * Gets match list
     *
     * @return match list
     */
    public List<MatchUpdateEntity> getMatches() {
        return matches;
    }
}
