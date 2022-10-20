package com.sportradar.sdk.feed.common;

import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.sdk.proto.dto.IncomingMessage;

/**
 * Represents a class used to map protocol layer messages to feed layer entities
 */
public interface EntityMapper<I extends IncomingMessage, E extends EntityBase> {

    /**
     * Maps the passed {@link IncomingMessage} derived message to feed entity
     *
     * @param message The {@link IncomingMessage} derived message to be mapped to the feed layer entity.
     * @return The {@link EntityBase} derived class representing the passed message.
     * @throws IllegalArgumentException the {@code message} is a null reference
     * @throws InvalidEntityException wrong entity provided
     */
    E map(I message) throws InvalidEntityException;
}
