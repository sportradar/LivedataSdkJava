package com.sportradar.sdk.common.exceptions;


import java.io.Serializable;

/**
 * Exception that is thrown when a invalid property in properties file is found
 */
public class InvalidPropertyException extends PropertyException implements Serializable {

    private static final long serialVersionUID = 5378913177873500837L;

    /**
     * Constructs a new instance of {@link InvalidPropertyException}
     *
     * @param error      Error description
     * @param property   Property name
     * @param inputValue Input value that caused error
     */
    public InvalidPropertyException(String error, String property, String inputValue) {
        super(property, String.format("%s Property name : %s Input value : %s", error, property, inputValue));
    }
}
