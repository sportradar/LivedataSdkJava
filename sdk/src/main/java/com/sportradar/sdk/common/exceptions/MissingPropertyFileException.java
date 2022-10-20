package com.sportradar.sdk.common.exceptions;

import java.io.Serializable;

/**
 * Exception that is thrown when sdk.properties property file is not found
 */
public class MissingPropertyFileException extends SdkException implements Serializable {

    private static final long serialVersionUID = -630428491169500047L;

    /**
     * Constructs a new instance of {@link MissingPropertyFileException}
     */
    public MissingPropertyFileException() {
        super("Property file not found");
    }
}
