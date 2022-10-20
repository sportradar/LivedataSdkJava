package com.sportradar.sdk.feed.common.exceptions;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;

public class InvalidEntityException extends SdkException {

    private static final long serialVersionUID = 5727512767776844931L;
    private Object actualValue;
    private String errorDetails;
    private Object expectedValue;
    private String propertyName;

    public InvalidEntityException(
            String propertyName,
            String expectedValue,
            String actualValue) {
        this(String.format("For %s got %s, was expecting %s", propertyName, actualValue, expectedValue),
                propertyName,
                expectedValue,
                actualValue);
    }

    public InvalidEntityException(
            String propertyName,
            String errorDetails,
            Object expectedValue,
            Object actualValue) {
        this(propertyName, errorDetails);
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;

    }


    public InvalidEntityException(
            String propertyName,
            String errorDetails) {
        super(String.format("Error : %s for : %s", errorDetails, propertyName));
        this.propertyName = propertyName;
        this.errorDetails = errorDetails;
    }

    public InvalidEntityException(
            UnknownEnumException enumException,
            String propertyName,
            String input) {
        super(String.format("Invalid value for enum : %s, property: %s, input : %s", enumException.getEnumName(), propertyName, input));
    }

    public Object getActualValue() {
        return actualValue;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public Object getExpectedValue() {
        return expectedValue;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
