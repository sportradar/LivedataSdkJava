package com.sportradar.sdk.feed.livescout.entities;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Contains information about multiple matches
 */
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

    /**
     * Returns a string that represents the current object.
     * <p>
     * Note : Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return "MatchListEntity{" +
                super.toString() +
                "matches=" + matches +
                '}';
    }

    protected void setMatches(List<MatchUpdateEntity> matches) {
        this.matches = Collections.unmodifiableList(matches);
    }
}
