package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

/**
 * Represents a class capable of inspecting messages send from the sdk to the betradar feed
 *
 * @param <O> Specifies the type of the message which can be inspected by the current {@link OutgoingMessageInspector}
 */
public interface OutgoingMessageInspector<O extends OutgoingMessage> {

    /**
     * Determines whether the current message should be request rate-limited
     *
     * @param message The {@link OutgoingMessage} representing the message to be inspected.
     * @return True if the passed {@link OutgoingMessage} must be rate-limited. Otherwise false.
     * @throws IllegalArgumentException The {@code message} is a null reference or
     *                                  the type of the {@code message} is not correct
     */
    boolean isRateLimited(O message);

    /**
     * Gets the number of events contained by the passed {@link OutgoingMessage} that must be rate-limited.
     *
     * @param message The {@link OutgoingMessage} representing the message to be inspected.
     * @return the number of events contained by the passed {@link OutgoingMessage} that must be rate-limited.
     * @throws IllegalArgumentException The {@code message} is a null reference or
     *                                  the type of the {@code message} is not correct
     */
    int getEventRequestRateLimitCount(O message);

    /**
     * Determines whether the current message representation should be request rate-limited
     *
     * @param message The string representation of the message
     * @return True if the passed message should be rate limited. Otherwise false.
     * @throws IllegalArgumentException The {@code message} is a null reference
     */
    boolean isRateLimited(String message);

    /**
     * Gets the number of events contained by the passed string representation of the message.
     *
     * @param message The message to be inspected.
     * @return The number of events contained in the passed message that must be rate limited.
     * @throws IllegalArgumentException The {@code message} is a null reference
     */
    int getEventRequestRateLimitCount(String message);
}
