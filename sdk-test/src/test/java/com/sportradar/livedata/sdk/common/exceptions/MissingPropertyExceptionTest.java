package com.sportradar.livedata.sdk.common.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissingPropertyExceptionTest {

    @Test
    @DisplayName("Constructor test -> should show expected message")
    void constructorTest() {
        try {
            throw new MissingPropertyException("property");
        } catch (MissingPropertyException ex) {
            assertThat(ex.getMessage()).isEqualTo("Mandatory property not found");
        }
    }
}