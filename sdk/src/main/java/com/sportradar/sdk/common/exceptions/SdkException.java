/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.exceptions;

import java.io.Serializable;

/**
 * Root exception of the SDK hierarchy
 */
public class SdkException extends Exception implements Serializable {

    private static final long serialVersionUID = 3883921153168869825L;

    /**
     * Initializes a new instance of the {@link SdkException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     */
    public SdkException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link SdkException} class.
     *
     * @param cause the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public SdkException(Throwable cause) {
        super(cause);
    }

    /**
     * Initializes a new instance of the {@link SdkException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public SdkException(String message, Throwable cause) {
        super(message, cause);
    }
}

