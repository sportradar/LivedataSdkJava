/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.exceptions;

import java.io.Serializable;

/**
 * SDK exception that is fatal
 */
public class SdkFatalException extends SdkException implements Serializable {

    private static final long serialVersionUID = -6963428816521206755L;

    /**
     * Initializes a new instance of the {@link SdkFatalException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     */
    public SdkFatalException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link SdkFatalException} class.
     *
     * @param cause the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public SdkFatalException(Throwable cause) {
        super(cause);
    }

    /**
     * Initializes a new instance of the {@link SdkFatalException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public SdkFatalException(String message, Throwable cause) {
        super(message, cause);
    }
}

