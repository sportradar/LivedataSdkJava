package com.sportradar.livedata.sdk.common.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyExceptionTest {

    @Test
    @DisplayName("Constructor test -> should show expected message")
    void constructorMessageTest() {
        try {
            throw new PropertyExceptionChild("property", "error");
        } catch (PropertyExceptionChild ex) {
            assertThat(ex.getMessage()).isEqualTo("error");
        }
    }

    @Test
    @DisplayName("Constructor test -> should throw exception if property is null")
    void constructorTest_shouldThrowExceptionIfPropertyIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            throw new PropertyExceptionChild(null, "error");
        });
    }

    @Test
    @DisplayName("Getter test")
    void getterTest() {
        PropertyExceptionChild propertyExceptionChild = new PropertyExceptionChild("property", "error");
        try {
            throw propertyExceptionChild;
        } catch (PropertyExceptionChild ex) {
            assertThat(ex.getProperty()).isEqualTo("property");
        }
    }

    static class PropertyExceptionChild extends PropertyException {
        PropertyExceptionChild(String property, String error) {
            super(property, error);
        }
    }
}