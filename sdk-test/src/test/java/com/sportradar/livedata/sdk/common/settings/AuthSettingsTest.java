package com.sportradar.livedata.sdk.common.settings;

import org.junit.jupiter.api.Test;

import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.PRIVATE_KEY;
import static org.junit.jupiter.api.Assertions.*;

class AuthSettingsTest {

    @Test
    void testTokenAuthDefaultValues() {
        AuthSettings settings = new AuthSettings(false, null, null, "clientId", "kid", PRIVATE_KEY);

        assertTrue(settings.isTokenAuth());
        assertNull(settings.getUsername());
        assertNull(settings.getPassword());
        assertEquals("https://auth.sportradar.com/", settings.getAuth0Domain());
        assertEquals("livedata-feed", settings.getAudience());
        assertEquals("clientId", settings.getClientId());
        assertEquals("kid", settings.getKid());
        assertEquals(PRIVATE_KEY, settings.getPrivateKey());
    }

    @Test
    void testTokenAuthDefaultValuesWithTestFlag() {
        AuthSettings settings = new AuthSettings(true, null, null, "clientId", "kid", PRIVATE_KEY);

        assertTrue(settings.isTokenAuth());
        assertNull(settings.getUsername());
        assertNull(settings.getPassword());
        assertEquals("https://auth.sportradar.com/", settings.getAuth0Domain());
        assertEquals("livedata-replay", settings.getAudience());
        assertEquals("clientId", settings.getClientId());
        assertEquals("kid", settings.getKid());
        assertEquals(PRIVATE_KEY, settings.getPrivateKey());
    }

    @Test
    void testTokenAuthCustomValues() {
        AuthSettings settings = new AuthSettings(false,
                "custom-domain", "custom-audience", "custom-clientId", "custom-kid", PRIVATE_KEY);

        assertTrue(settings.isTokenAuth());
        assertNull(settings.getUsername());
        assertNull(settings.getPassword());
        assertEquals("custom-domain", settings.getAuth0Domain());
        assertEquals("custom-audience", settings.getAudience());
        assertEquals("custom-clientId", settings.getClientId());
        assertEquals("custom-kid", settings.getKid());
        assertEquals(PRIVATE_KEY, settings.getPrivateKey());
    }

    @Test
    void testLegacyAuth() {
        AuthSettings settings = new AuthSettings("username", "password");

        assertFalse(settings.isTokenAuth());
        assertEquals("username", settings.getUsername());
        assertEquals("password", settings.getPassword());
        assertNull(settings.getAuth0Domain());
        assertNull(settings.getAudience());
        assertNull(settings.getClientId());
        assertNull(settings.getKid());
        assertNull(settings.getPrivateKey());
    }
}
