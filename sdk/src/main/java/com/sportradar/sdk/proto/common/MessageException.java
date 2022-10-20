/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;

/**
 * An exception thrown when the message cannot be created from the data or message data cannot be written to the target type.
 */
public class MessageException extends SdkException {

    /**
     * Initializes a new instance of the {@link MessageException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     */
    public MessageException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link MessageException} class.
     *
     * @param cause the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public MessageException(Throwable cause) {
        super(cause);
    }

    /**
     * Initializes a new instance of the {@link MessageException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}

