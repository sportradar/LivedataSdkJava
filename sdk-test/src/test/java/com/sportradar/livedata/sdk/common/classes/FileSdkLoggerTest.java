package com.sportradar.livedata.sdk.common.classes;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.classes.config.FileSdkLoggerFactory;
import com.sportradar.livedata.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

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
    @MethodSource("com.sportradar.livedata.sdk.common.classes.LevelProvider#allLevelsProvider")
    void getLevel_ok_test(Level level) {
        Level resultLevel = FileSdkLogger.getLevel(level.levelStr);

        assertThat(resultLevel).isEqualTo(level);
    }

    @Nested
    @DisplayName("With FileSdkLogger mocked object -> non-static methods tests")
    class NonStaticMethodsTests {
        FileSdkLoggerFactory sdkLoggerFactory = new FileSdkLoggerFactory();
        FileSdkLogger fileSdkLogger;

        @BeforeEach
        void setUp() throws SdkException, IOException {
            /* TODO: figure out how to delete temp directory after tests
            In current implementation it is unclear how to do that. During programmatical deletion attempt error is thrown and
            indicates that some resources are used by system. Issue persists on Windows 10*/
            fileSdkLogger = sdkLoggerFactory.buildFileSdkLogger(Files.createTempDirectory("junit"));
        }

        @Test
        @DisplayName("isDebugEnabled() test -> with alert appenderType")
        void isDebugEnabled_alertAppenderType_test() {
            boolean result = fileSdkLogger.isDebugEnabled(SdkLogAppenderType.ALERT);

            assertTrue(result);
        }

        @ParameterizedTest
        @EnumSource(mode = EXCLUDE, names = { "ALERT" })
        @DisplayName("isDebugEnabled() test -> with all except alert appenderTypes")
        void isDebugEnabled_withAllExceptAlertAppenderTypes_test(SdkLogAppenderType type) {
            boolean result = fileSdkLogger.isDebugEnabled(type);

            assertFalse(result);
        }

        @ParameterizedTest
        @EnumSource
        @DisplayName("isErrorEnabled() test -> with all possible appenderTypes")
        void isErrorEnabled_withAllAppenderTypes_test(SdkLogAppenderType type) {
            boolean result = fileSdkLogger.isErrorEnabled(type);

            assertTrue(result);
        }

        @ParameterizedTest
        @EnumSource
        @DisplayName("isInfoEnabled() test -> with all possible appenderTypes")
        void isInfoEnabled_withAllAppenderTypes_test(SdkLogAppenderType type) {
            boolean result = fileSdkLogger.isInfoEnabled(type);

            assertTrue(result);
        }

        @Test
        @DisplayName("isTraceEnabled() test -> with alert appenderType")
        void isTraceEnabled_alertAppenderType_test() {
            boolean result = fileSdkLogger.isTraceEnabled(SdkLogAppenderType.ALERT);

            assertTrue(result);
        }

        @ParameterizedTest
        @EnumSource(mode = EXCLUDE, names = { "ALERT" })
        @DisplayName("isTraceEnabled() test -> with all except alert appenderTypes")
        void isTraceEnabled_withAllExceptAlertAppenderTypes_test(SdkLogAppenderType type) {
            boolean result = fileSdkLogger.isTraceEnabled(type);

            assertFalse(result);
        }

        @ParameterizedTest
        @EnumSource
        @DisplayName("isWarnEnabled() test -> with all possible appenderTypes")
        void isWarnEnabled_withAllAppenderTypes_test(SdkLogAppenderType type) {
            boolean result = fileSdkLogger.isWarnEnabled(type);

            assertTrue(result);
        }

        @ParameterizedTest
        @MethodSource("com.sportradar.livedata.sdk.common.classes.LevelProvider#mainLevelsProvider")
        @DisplayName("isLevelEnabled() test -> with all possible appenderTypes and main levels")
        void isLevelEnabled_withMainLevelProviders_test(Level level) {
            boolean result = fileSdkLogger.isLevelEnabled(level, SdkLogAppenderType.ALERT);

            assertTrue(result);
        }

        @ParameterizedTest
        @MethodSource("com.sportradar.livedata.sdk.common.classes.LevelProvider#secondaryLevelsProvider")
        @DisplayName("isLevelEnabled() test -> with all possible appenderTypes and main levels")
        void isLevelEnabled_withSecondaryLevelProviders_test(Level level) {
            boolean result = fileSdkLogger.isLevelEnabled(level, SdkLogAppenderType.ALERT);

            assertFalse(result);
        }
    }
}

class LevelProvider {
    static Stream<Level> allLevelsProvider() {
        return Stream.of(Level.ALL,
                Level.TRACE,
                Level.DEBUG,
                Level.INFO,
                Level.WARN,
                Level.ERROR,
                Level.OFF);
    }

    static Stream<Level> mainLevelsProvider() {
        return Stream.of(Level.TRACE,
                Level.DEBUG,
                Level.INFO,
                Level.WARN,
                Level.ERROR);
    }

    static Stream<Level> secondaryLevelsProvider() {
        return Stream.of(Level.OFF, Level.ALL);
    }
}