package com.sportradar.livedata.sdk.common.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.security.interfaces.RSAPrivateKey;

@Getter
@EqualsAndHashCode
public class AuthSettings {
    // legacy parameters
    private String username;
    private String password;
    // Common-IAM parameters
    private String auth0Domain;
    private String audience;
    private String clientId;
    private String kid;
    private RSAPrivateKey privateKey;

    public AuthSettings(String username, String password, String auth0Domain, String audience, String clientId, String kid, RSAPrivateKey privateKey) {
        this.username = username;
        this.password = password;
        this.auth0Domain = auth0Domain;
        this.audience = audience;
        this.clientId = clientId;
        this.kid = kid;
        this.privateKey = privateKey;
    }
}
