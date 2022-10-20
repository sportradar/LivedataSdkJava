package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;
import java.util.Map;

/**
 * List of matches sent unsolicited from server (uses same format as MatchList for now).
 */
public class MatchListUpdateEntity extends MatchListEntity implements Serializable {

    private static final long serialVersionUID = 6552178870615738909L;

    protected MatchListUpdateEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
    }

    /**
     * Returns a string that represents the current object.
     * <p>
     * Note : Can be used for diagnostics purposes.
     * </p>
     *
     * @return A string that represents the current object.
     */
    public String toString() {
        return "MatchListUpdateEntity{" +
                super.toString() +
                '}';
    }
}
