package com.sportradar.livedata.sdk.common.settings;

import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.AUTH0_AUDIENCE;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.AUTH0_CLIENT_ID;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.AUTH0_DOMAIN;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.AUTH0_KID;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.AUTH0_PRIVATE_KEY;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.PASSWORD;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.USERNAME;
import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.VALID_RSA_PRIVATE_KEY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sportradar.livedata.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyFileException;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyFileSettingsLoaderTest {

    private Properties mockProperties;

    @BeforeEach
    void setUp() {
        mockProperties = new Properties();
        mockProperties.setProperty(AUTH0_DOMAIN, "example.com");
        mockProperties.setProperty(AUTH0_AUDIENCE, "audience");
        mockProperties.setProperty(AUTH0_CLIENT_ID, "clientId");
        mockProperties.setProperty(AUTH0_KID, "kid");
        mockProperties.setProperty(AUTH0_PRIVATE_KEY, VALID_RSA_PRIVATE_KEY);
        mockProperties.setProperty(USERNAME, "user");
        mockProperties.setProperty(PASSWORD, "pass");
    }

    @Test
    void testConstructorWithValidProperties() {
        assertDoesNotThrow(() -> new PropertyFileSettingsLoader(mockProperties));
    }

    @Test
    void testConstructorDefaultButMissingFile() { // logic has to be refactored to test loading from default file path
        assertThrows(MissingPropertyFileException.class, () -> new PropertyFileSettingsLoader(null));
    }

    @Test
    void testGetLiveScoutSettingsDefaultLogin() throws MissingPropertyException, InvalidPropertyException, MissingPropertyFileException {
        PropertyFileSettingsLoader settingsLoader = new PropertyFileSettingsLoader(mockProperties);

        AuthSettings authSettings = settingsLoader.getLiveScoutSettings().getAuthSettings();
        assertTrue(authSettings.isTokenAuth);
        assertEquals("https://example.com/", authSettings.getAuth0Domain());
        assertEquals("audience", authSettings.getAudience());
        assertEquals("clientId", authSettings.getClientId());
        assertEquals("kid", authSettings.getKid());
        assertNotNull(authSettings.getPrivateKey());
    }

    @Test
    void testGetLiveScoutSettingsFallbackToLegacyLogin() throws MissingPropertyException, InvalidPropertyException, MissingPropertyFileException {
        mockProperties.remove(AUTH0_CLIENT_ID);
        PropertyFileSettingsLoader settingsLoader = new PropertyFileSettingsLoader(mockProperties);

        AuthSettings authSettings = settingsLoader.getLiveScoutSettings().getAuthSettings();
        assertFalse(authSettings.isTokenAuth);
        assertEquals("user", authSettings.getUsername());
        assertEquals("pass", authSettings.getPassword());
    }

    @Test
    void testGetLiveScoutSettingsThrowsMissingPropertyException() throws MissingPropertyFileException {
        mockProperties.remove(AUTH0_KID);
        mockProperties.remove(USERNAME);
        PropertyFileSettingsLoader settingsLoader = new PropertyFileSettingsLoader(mockProperties);
        assertThrows(MissingPropertyException.class, settingsLoader::getLiveScoutSettings);
    }
}