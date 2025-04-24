package com.sportradar.livedata.sdk.common.auth;

import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;

import java.time.Instant;

public class AccessTokenManager {
    private String accessToken = null;
    private Instant expires = null;

    private final LiveScoutSettings settings;

    public AccessTokenManager(LiveScoutSettings settings) {
        this.settings = settings;
    }

    public String get() {
        refreshTokenIfNeeded();
        return accessToken;
    }

    private void refreshTokenIfNeeded() {
        Instant now = Instant.now();
        if(accessToken == null || now.isAfter(expires)){
            fetchToken(now);
        }
    }

    private void fetchToken(Instant now) {

    }
}
