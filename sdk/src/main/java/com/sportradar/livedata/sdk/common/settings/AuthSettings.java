package com.sportradar.livedata.sdk.common.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.security.interfaces.RSAPrivateKey;

import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@EqualsAndHashCode
public class AuthSettings {
    boolean isTokenAuth;
    // legacy parameters
    private final String username;
    private final String password;
    // Common-IAM parameters
    private final String auth0Domain;
    private final String audience;
    private final String clientId;
    private final String kid;
    private final RSAPrivateKey privateKey;

    public AuthSettings(boolean isTest,
                        String auth0Domain,
                        String audience,
                        String clientId,
                        String kid,
                        RSAPrivateKey privateKey) {
        this.isTokenAuth = true;

        this.username = null;
        this.password = null;
        this.auth0Domain = isNullOrEmpty(auth0Domain) ? "https://auth.sportradar.com/" : auth0Domain;
        if(isNullOrEmpty(audience)){
            this.audience = isTest ? "livedata-replay" : "livedata-feed";
        } else {
            this.audience = audience;
        }
        this.clientId = clientId;
        this.kid = kid;
        this.privateKey = privateKey;
    }

    public AuthSettings(String username, String password) {
        this.isTokenAuth = false;

        this.username = username;
        this.password = password;
        this.auth0Domain = null;
        this.audience = null;
        this.clientId = null;
        this.kid = null;
        this.privateKey = null;
    }
}
