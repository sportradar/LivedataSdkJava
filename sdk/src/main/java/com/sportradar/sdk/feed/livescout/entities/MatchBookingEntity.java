package com.sportradar.sdk.feed.livescout.entities;


import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.livescout.enums.BookMatchResult;

import java.io.Serializable;
import java.util.Map;

/**
 * Match booking reply. This reply is sent when a client books a match.
 */
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
        return "MatchBookingEntity{" +
                super.toString() +
                "matchId=" + matchId +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    protected void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    protected void setResult(BookMatchResult result) {
        this.result = result;
    }
}
