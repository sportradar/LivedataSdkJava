package com.sportradar.sdk.common.exceptions;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * Root exception for all properties related exceptions
 */
public abstract class PropertyException extends SdkException {

    private static final long serialVersionUID = -3093372747491547951L;

    PropertyException(String property, String error) {
        super(error);
        checkNotNull(property);
        this.property = property;
    }

    String property;

    /**
     * Gets property for which error was encountered
     *
     * @return property name
     */
    public String getProperty() {
        return property;
    }
}
