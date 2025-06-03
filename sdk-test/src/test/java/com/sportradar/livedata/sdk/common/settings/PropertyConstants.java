package com.sportradar.livedata.sdk.common.settings;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public final class PropertyConstants {

    public static final String VALID_RSA_PRIVATE_KEY =
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqPfgaTEWEP3S9w0t\n" +
            "gsicURfo+nLW09/0KfOPinhYZ4ouzU+3xC4pSlEp8Ut9FgL0AgqNslNaK34Kq+NZ\n" +
            "jO9DAQIDAQABAkAgkuLEHLaqkWhLgNKagSajeobLS3rPT0Agm0f7k55FXVt743hw\n" +
            "Ngkp98bMNrzy9AQ1mJGbQZGrpr4c8ZAx3aRNAiEAoxK/MgGeeLui385KJ7ZOYktj\n" +
            "hLBNAB69fKwTZFsUNh0CIQEJQRpFCcydunv2bENcN/oBTRw39E8GNv2pIcNxZkcb\n" +
            "NQIgbYSzn3Py6AasNj6nEtCfB+i1p3F35TK/87DlPSrmAgkCIQDJLhFoj1gbwRbH\n" +
            "/bDRPrtlRUDDx44wHoEhSDRdy77eiQIgE6z/k6I+ChN1LLttwX0galITxmAYrOBh\n" +
            "BVl433tgTTQ=\n" +
            "-----END PRIVATE KEY-----\n";
    public static final RSAPrivateKey PRIVATE_KEY;
    static {
        try {
            byte[] decoded = Base64.getDecoder().decode(VALID_RSA_PRIVATE_KEY.replaceAll("-{5}[\\w\\s]*-{5}|\\s", ""));
            PRIVATE_KEY = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Authentication-related constants
    public static final String AUTH0_DOMAIN = "sdk.livescout.auth0.domain";
    public static final String AUTH0_AUDIENCE = "sdk.livescout.auth0.audience";
    public static final String AUTH0_CLIENT_ID = "sdk.livescout.auth0.client_id";
    public static final String AUTH0_KID = "sdk.livescout.auth0.kid";
    public static final String AUTH0_PRIVATE_KEY = "sdk.livescout.auth0.private_key";

    // Legacy login constants
    public static final String USERNAME = "sdk.livescout.username";
    public static final String PASSWORD = "sdk.livescout.password";

    // Prevent instantiation
    private PropertyConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}