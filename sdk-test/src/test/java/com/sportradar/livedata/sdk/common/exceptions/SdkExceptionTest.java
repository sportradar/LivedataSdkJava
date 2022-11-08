package com.sportradar.livedata.sdk.common.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SdkExceptionTest {

    @Test
    @DisplayName("Constructor test -> arguments: message")
    void constructorTest_withMessageArgument() {
        try {
            throw new SdkException("message");
        } catch (SdkException ex) {
            assertThat(ex.getMessage()).isEqualTo("message");
        }
    }

    @Test
    @DisplayName("Constructor test -> arguments: throwable")
    void constructorTest_withThrowableArgument() {
        Throwable throwable = new Throwable();
        try {

            throw new SdkException(throwable);
        } catch (SdkException ex) {
            assertThat(ex.getCause()).isEqualTo(throwable);
        }
    }

    @Test
    @DisplayName("Constructor test -> arguments: message, throwable")
    void constructorTest_withMessageAndThrowableArgument() {
        Throwable throwable = new Throwable();
        try {
            throw new SdkException("message", throwable);
        } catch (SdkException ex) {
            assertThat(ex.getMessage()).isEqualTo("message");
            assertThat(ex.getCause()).isEqualTo(throwable);
        }
    }
}