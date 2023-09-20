package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * List of matches sent unsolicited from server (uses same format as MatchList for now).
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class MatchListUpdateEntity extends MatchListEntity implements Serializable {

    private static final long serialVersionUID = 6552178870615738909L;

    protected MatchListUpdateEntity() {
        super();
    }
}
