package com.sportradar.livedata.sdk.common.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissingPropertyFileExceptionTest {

    @Test
    @DisplayName("Constructor test -> should show expected message")
    void constructorTest() {
        try {
            throw new MissingPropertyFileException();
        } catch (MissingPropertyFileException ex) {
            assertThat(ex.getMessage()).isEqualTo("Property file not found");
        }
    }
}