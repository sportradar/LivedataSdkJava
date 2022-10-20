/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.exceptions;

public class UnknownEnumException extends SdkException {

    private static final long serialVersionUID = 7044189673283295618L;
    private final String enumName;

    public UnknownEnumException(String enumName, String inputValue) {
        super(String.format("Unknown value %s for enum %s", inputValue, enumName));
        this.enumName = enumName;
    }

    public String getEnumName() {
        return enumName;
    }
}
