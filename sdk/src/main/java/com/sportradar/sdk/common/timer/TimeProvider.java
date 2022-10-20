/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.timer;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * Represents a base class for all date-time providers which are used to obtain time.
 *
 * @author uros.bregar
 */
public abstract class TimeProvider extends TimeProviderBase {

    /**
     * Sets the currently used {@link TimeProvider} instance.
     *
     * @param provider The {@link TimeProvider} to be used.
     * @throws IllegalArgumentException The {@code provider} is a null reference.
     */
    public static void setCurrent(TimeProvider provider) {
        checkNotNull(provider, "The provider cannot be a null reference");

        current = provider;
    }
}
