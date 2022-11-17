package com.sportradar.livedata.sdk.common.classes;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSdkLoggerTest {

    @DisplayName("getAppenderType() test -> with invalid characters")
    @Test
    void getAppenderType_InvalidCharacters_test() {
        final SdkLogAppenderType inputAppenderType = SdkLogAppenderType.ALERT;

        SdkLogAppenderType resultAppenderType = FileSdkLogger.getAppenderType(FileSdkLogger.ROOT_NS + inputAppenderType.name() + "\"[!@#$%^");

        assertThat(resultAppenderType).isEqualTo(SdkLogAppenderType.ALERT);
    }

    @DisplayName("getAppenderType() test -> with invalid name")
    @Test
    void getAppenderType_InvalidName_test() {
        final SdkLogAppenderType inputAppenderType = SdkLogAppenderType.ALERT;
        final String suffix = "new";
        final String appenderName = FileSdkLogger.ROOT_NS + inputAppenderType.name() + "." + suffix;
        final String expectedErrorMessage = "No enum constant " + inputAppenderType.getClass().getName() + "." + inputAppenderType.name() + suffix;

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> FileSdkLogger.getAppenderType(appenderName)
        );
        assertThat(ex.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @DisplayName("getAppenderType() test -> with valid enum values")
    @ParameterizedTest
    @EnumSource
    void getAppenderType_ok_test(SdkLogAppenderType type) {
        String name = FileSdkLogger.ROOT_NS + type.name();

        SdkLogAppenderType resultType = FileSdkLogger.getAppenderType(name);

        assertThat(resultType).isEqualTo(type);
    }

    @DisplayName("getAppenderType() test -> if not starts with prefix")
    @Test
    void getAppenderType_startsNotWithPrefix_test() {
        SdkLogAppenderType resultAppenderType = FileSdkLogger.getAppenderType(SdkLogAppenderType.ALERT.name());

        assertNull(resultAppenderType);
    }

    @DisplayName("getLevel() test -> if null param")
    @Test
    void getLevel_isNull_test() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> FileSdkLogger.getLevel(null)
        );
        assertThat(ex.getMessage()).isEqualTo("level");
    }

    @DisplayName("getLevel() test -> if empty param")
    @Test
    void getLevel_isEmpty_test() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> FileSdkLogger.getLevel("")
        );
        assertThat(ex.getMessage()).isEqualTo("level");
    }

    @DisplayName("getLevel() test -> if doesn't match any predefined level")
    @Test
    void getLevel_doesNotMatchAnyPredefined_test() {
        Level level = FileSdkLogger.getLevel("foo bar");

        assertThat(level).isEqualTo(Level.OFF);
    }

    @DisplayName("getLevel() test -> ok params levels")
    @ParameterizedTest
    @MethodSource("levelProvider")
    void getLevel_ok_test(Level level) {
        Level resultLevel = FileSdkLogger.getLevel(level.levelStr);

        assertThat(resultLevel).isEqualTo(level);
    }

    @DisplayName("isDebugEnabled() test -> with valid enum values")
    @ParameterizedTest
    @EnumSource
    void testGetLevel(SdkLogAppenderType type) throws SdkException, NoSuchMethodException {

    }

    static Stream<Level> levelProvider() {
        return Stream.of(Level.ALL,
                Level.TRACE,
                Level.DEBUG,
                Level.INFO,
                Level.WARN,
                Level.ERROR,
                Level.OFF);
    }
}