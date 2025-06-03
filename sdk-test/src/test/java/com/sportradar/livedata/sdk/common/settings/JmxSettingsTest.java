package com.sportradar.livedata.sdk.common.settings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JmxSettingsTest {

    @Test
    void testDefaultValues() {
        JmxSettings settings = JmxSettings.builder().build();

        assertFalse(settings.isEnabled());
        assertEquals("localhost", settings.getJmxHost());
        assertEquals(13370, settings.getJmxPort());
        assertNull(settings.getPasswordFile());
        assertNull(settings.getAccessFile());
    }

    @Test
    void testCustomValues() {
        JmxSettings settings = JmxSettings.builder()
                .enabled(true)
                .jmxHost("custom-host")
                .jmxPort(12345)
                .passwordFile("password.txt")
                .accessFile("access.txt")
                .build();

        assertTrue(settings.isEnabled());
        assertEquals("custom-host", settings.getJmxHost());
        assertEquals(12345, settings.getJmxPort());
        assertEquals("password.txt", settings.getPasswordFile());
        assertEquals("access.txt", settings.getAccessFile());
    }
}
