package com.sportradar.livedata.sdk.feed.livescout.entities;


import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.livedata.sdk.feed.livescout.enums.BookMatchResult;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * Match booking reply. This reply is sent when a client books a match.
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class MatchBookingEntity extends LiveScoutEntityBase implements Serializable {

    private static final long serialVersionUID = -4192096017579932803L;
    private Long matchId;
    private String message;
    private BookMatchResult result;


    protected MatchBookingEntity(Map<String, String> otherAttributes) {
        super(otherAttributes);
    }

    /**
     * Get the unique event id.
     *
     * @return - an event id or null
     */
    @Override
    public EventIdentifier getEventId() {
        if (matchId == null) {
            return null;
        }
        return EventIdentifier.id(matchId);
    }

    /**
     * If the match booking fails for any reason (match already bought, match finished etc.),
     * the message will contain an explanation.
     *
     * @return reply message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Result of match booking (either valid or invalid)
     *
     * @return booking result
     */
    public BookMatchResult getResult() {
        return result;
    }
}
