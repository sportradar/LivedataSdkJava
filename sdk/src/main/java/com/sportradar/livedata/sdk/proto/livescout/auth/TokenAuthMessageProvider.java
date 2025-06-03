package com.sportradar.livedata.sdk.proto.livescout.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageFactory;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a class used to build token authentication lifecycle requests to be sent to the betradar feed.
 */
public class TokenAuthMessageProvider extends AuthMessageProvider {
    private String accessToken = null;
    private Instant expires = null;

    public TokenAuthMessageProvider(LiveScoutOutgoingMessageFactory factory, AuthSettings settings) {
        super(factory, settings);
    }

    @Override
    public OutgoingMessage buildLoginRequest() {
        return factory.buildLoginRequest(null, null , getAccessToken());
    }

    private String getAccessToken() {
        Instant now = Instant.now();
        if(accessToken == null || now.isAfter(expires)){ // update token is needed
            fetchToken(now);
        }
        return accessToken;
    }

    /*
     * Fetches a new access token from the Auth0 token endpoint using the client credentials grant type.
     */
    private void fetchToken(Instant now) {
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = createRequest(now);

            HttpClientResponseHandler<TokenResponse> responseHandler = response -> {
                int status = response.getCode();
                if (status >= 200 && status < 300) {
                    return mapper.readValue(response.getEntity().getContent(), TokenResponse.class);
                } else {
                    throw new IOException("Unexpected response status: " + status);
                }
            };

            TokenResponse response = client.execute(request, responseHandler);
            accessToken = response.token;
            // by using date created before request we ensure that network latency is not included in the token expiration time
            expires = now.plus(Duration.ofSeconds(response.expiresIn));
        } catch (IOException e) {
            throw new RuntimeException(e);// protocol catches all exceptions anyway
        }
    }

    /*
     * Creates a POST request to the Auth0 token endpoint with the required parameters.
     */
    private HttpPost createRequest(Instant now) {
        List<NameValuePair> params = List.of(
                new BasicNameValuePair("grant_type", "client_credentials"),
                new BasicNameValuePair("audience", settings.getAudience()),
                new BasicNameValuePair("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer"),
                new BasicNameValuePair("client_assertion", createClientAssertion(now))
        );

        HttpPost request = new HttpPost(settings.getAuth0Domain() + "oauth/token");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        return request;
    }

    /*
     * Creates a JWT client assertion.
     */
    private String createClientAssertion(Instant now) {
        Algorithm algorithm = Algorithm.RSA256(null, settings.getPrivateKey());
        return JWT.create()
                .withHeader(Map.of("alg", algorithm.getName(), "kid", settings.getKid()))
                .withIssuer(settings.getClientId())
                .withSubject(settings.getClientId())
                .withAudience(settings.getAuth0Domain())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(Duration.ofMinutes(1)))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    /**
     * Represents a response from the Auth0 token endpoint.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenResponse {
        @JsonProperty("access_token") public String token;
        @JsonProperty("expires_in") public long expiresIn;
    }
}
