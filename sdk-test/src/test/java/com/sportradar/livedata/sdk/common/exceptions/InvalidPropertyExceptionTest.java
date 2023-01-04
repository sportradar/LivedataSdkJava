package com.sportradar.livedata.sdk.common.exceptions;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class InvalidPropertyExceptionTest {

    @Test
    @DisplayName("Constructor test -> should show expected message")
    void constructorTest() {
        try {
            throw new InvalidPropertyException("error", "property", "inputValue");
        } catch (InvalidPropertyException ex) {
            assertThat(ex.getMessage()).isEqualTo("error Property name : property Input value : inputValue");
        }
    }
}