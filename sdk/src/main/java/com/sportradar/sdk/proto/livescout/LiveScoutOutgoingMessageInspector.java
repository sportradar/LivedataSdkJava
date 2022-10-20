package com.sportradar.sdk.proto.livescout;

import com.sportradar.sdk.proto.common.OutgoingMessageInspector;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.dto.outgoing.livescout.*;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link OutgoingMessageInspector} implementation capable of inspecting message send from sdk to the live-scout feed
 */
public class LiveScoutOutgoingMessageInspector implements OutgoingMessageInspector<OutgoingMessage> {

    private static final String MATCH_MESSAGE_PREFIX = "<match";

    /**
     * Determines whether the current message should be request rate-limited
     *
     * @param message The {@link OutgoingMessage} representing the message to be inspected.
     * @return True if the passed {@link OutgoingMessage} must be rate-limited. Otherwise false.
     * @throws IllegalArgumentException The {@code message} is a null reference or
     *                                  the type of the {@code message} is not correct
     */
    @Override
    public boolean isRateLimited(OutgoingMessage message) {
        checkNotNull(message, "message cannot be a null reference");

        return !(message instanceof Ct ||
                message instanceof com.sportradar.sdk.proto.dto.outgoing.livescout.Login ||
                message instanceof Logout);
    }

    /**
     * Gets the number of events contained by the passed {@link OutgoingMessage} that must be rate-limited.
     *
     * @param message The {@link OutgoingMessage} representing the message to be inspected.
     * @return the number of events contained by the passed {@link OutgoingMessage} that must be rate-limited.
     * @throws IllegalArgumentException The {@code message} is a null reference or
     *                                  the type of the {@code message} is not correct
     */
    @Override
    public int getEventRequestRateLimitCount(OutgoingMessage message) {
        return (message instanceof Bookmatch || message instanceof Match || message instanceof Matchstop)
                ? 1
                : 0;
    }

    /**
     * Determines whether the current message representation should be request rate-limited
     *
     * @param message The string representation of the message
     * @return True if the passed message should be rate limited. Otherwise false.
     * @throws IllegalArgumentException The {@code message} is a null reference
     */
    @Override
    public boolean isRateLimited(String message) {
        checkNotNull(message, "message cannot be a null reference");

        return message.toLowerCase().startsWith(MATCH_MESSAGE_PREFIX);
    }

    /**
     * Gets the number of events contained by the passed string representation of the message.
     *
     * @param message The message to be inspected.
     * @return The number of events contained in the passed message that must be rate limited.
     * @throws IllegalArgumentException The {@code message} is a null reference
     */
    @Override
    public int getEventRequestRateLimitCount(String message) {
        checkNotNull(message, "message cannot be a null reference");

        return message.toLowerCase().startsWith((MATCH_MESSAGE_PREFIX))
                ? 1
                : 0;
    }
}
