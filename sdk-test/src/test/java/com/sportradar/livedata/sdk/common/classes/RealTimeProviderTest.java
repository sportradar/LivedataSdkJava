package com.sportradar.livedata.sdk.common.classes;

import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class RealTimeProviderTest {

    RealTimeProvider realTimeProvider;

    @BeforeEach
    void setUp() {
        realTimeProvider = new RealTimeProvider();
    }

    @DisplayName("getDateTime() test")
    @Test
    void getDateTime_test() {
        try (MockedStatic<DateTime> dateTime = Mockito.mockStatic(DateTime.class)) {
            dateTime.when(DateTime::now).thenReturn(new DateTime(1672825417L));
            assertThat(realTimeProvider.getDateTime()).isEqualTo(new DateTime(1672825417L));
        }
    }

    @DisplayName("getDate() test")
    @Test
    void getDate_test() {
        try (MockedStatic<DateTime> dateTime = Mockito.mockStatic(DateTime.class)) {
            dateTime.when(DateTime::now).thenReturn(new DateTime(1672825417L));
            assertThat(realTimeProvider.getDate()).isEqualTo(new Date(1672825417L));
        }
    }
}