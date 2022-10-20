package com.sportradar.sdk.dispatch;

/**
 * Represents a container used by the {@link DisruptorDispatcher} used to store un-processed messages
 *
 * @param <T> specifies the type of the container.
 */
public interface DispatcherContainer<T> {

    /**
     * Checks if is container valid
     * @return
     */
    boolean isValid();

    /**
     * Copies the content of the passed {@link DispatcherContainer} to the current one.
     *
     * @param other The {@link DispatcherContainer} whose content will be copied to the current one.
     * @throws IllegalArgumentException the {@code other} is a null reference.
     */
    void copy(T other);

    /**
     * Clears the content of the current {@link DispatcherContainer}.
     */
    void clear();
}
