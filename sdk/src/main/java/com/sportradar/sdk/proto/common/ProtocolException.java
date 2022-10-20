/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;

/**
 * An {@link Exception} derived class which is thrown when an un-expected situation is encountered in the protocol layer code.
 *
 * @author uros.bregar
 */
public class ProtocolException extends SdkException {

    private static final long serialVersionUID = 980839996372197186L;

    /**
     * Initializes a new instance of the {@link ProtocolException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     */
    public ProtocolException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link ProtocolException} class.
     *
     * @param cause the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ProtocolException(Throwable cause) {
        super(cause);
    }

    /**
     * Initializes a new instance of the {@link ProtocolException} class.
     *
     * @param message the detail message (which is saved for later retrieval by the {@code getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {code getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}
