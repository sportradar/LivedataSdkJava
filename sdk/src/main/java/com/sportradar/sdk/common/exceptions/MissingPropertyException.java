package com.sportradar.sdk.common.exceptions;

import java.io.Serializable;

/**
 * Exception that is thrown when a mandatory property is missing from properties file
 */
public class MissingPropertyException extends PropertyException implements Serializable {

    private static final long serialVersionUID = -4500170616545994517L;

    /**
     * Constructs a new instance of {@link MissingPropertyException}
     *
     * @param missingProperty Name of missing property
     */
    public MissingPropertyException(String missingProperty) {
        super(missingProperty, "Mandatory property not found");
    }
}
