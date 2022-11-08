package com.sportradar.livedata.sdk.common.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnknownEnumExceptionTest {

    @Test
    @DisplayName("Constructor test -> should show expected message")
    void constructorMessageTest() {
        try {
            throw new UnknownEnumException("enumName", "inputValue");
        } catch (UnknownEnumException ex) {
            assertThat(ex.getMessage()).isEqualTo("Unknown value inputValue for enum enumName");
        }
    }

    @Test
    @DisplayName("Getter test")
    void getterTest() {
        UnknownEnumException unknownEnumException = new UnknownEnumException("enumName", "inputValue");
        try {
            throw unknownEnumException;
        } catch (UnknownEnumException ex) {
            assertThat(ex.getEnumName()).isEqualTo("enumName");
        }
    }
}