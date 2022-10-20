package com.sportradar.sdk.dispatch.livescout;

import com.lmax.disruptor.EventFactory;
import com.sportradar.sdk.dispatch.DispatcherContainer;
import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A container used by the {@link LiveScoutDispatcher} to store un-processed live-scout related messages.
 * Each {@link LiveScoutDispatcherContainer} represents a single message.
 */
public class LiveScoutDispatcherContainer implements DispatcherContainer<LiveScoutDispatcherContainer> {

    /**
     * A {@link EventFactory} used to initialize empty {@link LiveScoutDispatcherContainer} instances.
     */
    public static final EventFactory<LiveScoutDispatcherContainer> CONTAINER_FACTORY = new LiveScoutDispatcherContainerFactory();

    /**
     * The {@link LiveScoutEntityBase} representing the message held be the current {@link LiveScoutDispatcherContainer}
     */
    private LiveScoutEntityBase entity;

    private boolean valid = false;
    /**
     * Checks if is container valid
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Initializes a new instance of the {@link LiveScoutDispatcherContainer} class.
     * </p>
     * <p>
     * This explicit default constructor is used to allow the {@link LiveScoutDispatcherContainerFactory} to build
     * empty instances of the {@link LiveScoutDispatcherContainer} class.
     * </p>
     */
    private LiveScoutDispatcherContainer() {

    }

    /**
     * Initializes a new instance of the {@link LiveScoutDispatcherContainer} class.
     *
     * @param entity The {@link LiveScoutEntityBase} instance to be held by the constructed container.
     * @throws IllegalArgumentException the {@code entity} is a null reference.
     */
    public LiveScoutDispatcherContainer(LiveScoutEntityBase entity) {
        checkNotNull(entity, "entity cannot be a null reference");

        this.entity = entity;
    }

    /**
     * Gets the {@link LiveScoutEntityBase} representing the message held be the current {@link LiveScoutDispatcherContainer}
     *
     * @return the {@link LiveScoutEntityBase} representing the message held be the current {@link LiveScoutDispatcherContainer}
     */
    public LiveScoutEntityBase getEntity() {
        return entity;
    }

    /**
     * Copies the content of the passed {@link DispatcherContainer} to the current one.
     *
     * @param other The {@link DispatcherContainer} whose content will be copied to the current one.
     * @throws IllegalArgumentException the {@code other} is a null reference.
     */
    @Override
    public void copy(LiveScoutDispatcherContainer other) {
        this.entity = other.entity;
        this.valid = true;
    }

    /**
     * Clears the content of the current {@link DispatcherContainer}.
     */
    @Override
    public void clear() {
        this.valid = false;
        this.entity = null;
    }

    /**
     * A factory used to build empty {@link LiveScoutDispatcherContainer} instances.
     */
    private static class LiveScoutDispatcherContainerFactory implements EventFactory<LiveScoutDispatcherContainer> {

        @Override
        public LiveScoutDispatcherContainer newInstance() {
            LiveScoutDispatcherContainer result = new LiveScoutDispatcherContainer();
            result.clear();
            return result;
        }
    }
}
