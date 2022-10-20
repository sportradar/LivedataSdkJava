/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

/**
 * An {@link ProtocolException} derived class which is thrown when no data is available
 */
public class NoDataProtocolException extends ProtocolException {

    private static final long serialVersionUID = 5877709363892077284L;

    /**
     * Initializes a new instance of the {@link NoDataProtocolException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     */
    public NoDataProtocolException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link NoDataProtocolException} class.
     *
     * @param cause the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public NoDataProtocolException(Throwable cause) {
        super(cause);
    }

    /**
     * Initializes a new instance of the {@link NoDataProtocolException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public NoDataProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}
