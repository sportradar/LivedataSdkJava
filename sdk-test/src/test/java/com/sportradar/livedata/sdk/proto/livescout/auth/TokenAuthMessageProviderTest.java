package com.sportradar.livedata.sdk.proto.livescout.auth;

import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageFactory;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URLEncodedUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import static com.sportradar.livedata.sdk.common.settings.PropertyConstants.PRIVATE_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("resource")
class TokenAuthMessageProviderTest {

    private LiveScoutOutgoingMessageFactory factory;
    private TokenAuthMessageProvider provider;

    @BeforeEach
    void setUp() {
        factory = mock(LiveScoutOutgoingMessageFactory.class);
        AuthSettings settings = mock(AuthSettings.class);

        when(settings.getAudience()).thenReturn("test-audience");
        when(settings.getAuth0Domain()).thenReturn("https://test.auth0.com/");
        when(settings.getClientId()).thenReturn("test-client-id");
        when(settings.getKid()).thenReturn("test-kid");
        when(settings.getPrivateKey()).thenReturn(PRIVATE_KEY);

        provider = new TokenAuthMessageProvider(factory, settings);
    }

    @Test
    void testBuildLoginRequestFetchesAccessToken() throws Exception {
        try (MockedStatic<HttpClients> mockedHttpClients = Mockito.mockStatic(HttpClients.class)) {
            CloseableHttpClient mockClient = mock(CloseableHttpClient.class);
            mockedHttpClients.when(HttpClients::createDefault).thenReturn(mockClient);

            ClassicHttpResponse httpResponse = mock(ClassicHttpResponse.class);
            HttpEntity entity = mock(HttpEntity.class);
            InputStream stream = new ByteArrayInputStream("{\"access_token\":\"dummy-token\",\"expires_in\":3600}".getBytes());
            when(entity.getContent()).thenReturn(stream);
            when(httpResponse.getCode()).thenReturn(200);
            when(httpResponse.getEntity()).thenReturn(entity);

            when(mockClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                    .thenAnswer(invocation -> invocation.getArgument(1, HttpClientResponseHandler.class).handleResponse(httpResponse));

            OutgoingMessage outgoingMessage = mock(OutgoingMessage.class);
            when(factory.buildLoginRequest(any(), any(), eq("dummy-token"))).thenReturn(outgoingMessage);

            assertSame(outgoingMessage, provider.buildLoginRequest());
            verify(factory).buildLoginRequest(null, null, "dummy-token");
            validateTokenRequest(mockClient, 1);
        }
    }

    @Test
    void testFetchTokenFailureThrowsIOException() throws Exception {
        try (MockedStatic<HttpClients> mockedHttpClients = Mockito.mockStatic(HttpClients.class)) {
            CloseableHttpClient mockClient = mock(CloseableHttpClient.class);
            mockedHttpClients.when(HttpClients::createDefault).thenReturn(mockClient);

            when(mockClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                    .thenThrow(new IOException("Simulated failure"));

            RuntimeException ex = assertThrows(RuntimeException.class, provider::buildLoginRequest);
            assertEquals("Simulated failure", ex.getCause().getMessage());
            validateTokenRequest(mockClient, 1);
        }
    }

    @Test
    void testNon200ResponseThrowsRuntimeException() throws Exception {
        try (MockedStatic<HttpClients> mockedHttpClients = Mockito.mockStatic(HttpClients.class)) {
            CloseableHttpClient mockClient = mock(CloseableHttpClient.class);
            mockedHttpClients.when(HttpClients::createDefault).thenReturn(mockClient);

            ClassicHttpResponse httpResponse = mock(ClassicHttpResponse.class);
            when(httpResponse.getCode()).thenReturn(400);

            when(mockClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                    .thenAnswer(invocation -> invocation.getArgument(1, HttpClientResponseHandler.class).handleResponse(httpResponse));

            RuntimeException ex = assertThrows(RuntimeException.class, provider::buildLoginRequest);
            assertTrue(ex.getCause().getMessage().contains("Unexpected response status: 400"));
            validateTokenRequest(mockClient, 1);
        }
    }

    @Test
    void testTokenIsRefreshedAfterExpiration() throws Exception {
        try (MockedStatic<HttpClients> mockedHttpClients = Mockito.mockStatic(HttpClients.class)) {
            CloseableHttpClient mockClient = mock(CloseableHttpClient.class);
            mockedHttpClients.when(HttpClients::createDefault).thenReturn(mockClient);

            ClassicHttpResponse httpResponse = mock(ClassicHttpResponse.class);
            HttpEntity entity = mock(HttpEntity.class);

            when(entity.getContent())
                    .thenReturn(new ByteArrayInputStream("{\"access_token\":\"dummy-token-1\",\"expires_in\":1}".getBytes()))
                    .thenReturn(new ByteArrayInputStream("{\"access_token\":\"dummy-token-2\",\"expires_in\":3600}".getBytes()));
            when(httpResponse.getCode()).thenReturn(200);
            when(httpResponse.getEntity()).thenReturn(entity);

            when(mockClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                    .thenAnswer(invocation -> invocation.getArgument(1, HttpClientResponseHandler.class).handleResponse(httpResponse));

            OutgoingMessage message1 = mock(OutgoingMessage.class);
            OutgoingMessage message2 = mock(OutgoingMessage.class);

            when(factory.buildLoginRequest(any(), any(), eq("dummy-token-1"))).thenReturn(message1);
            when(factory.buildLoginRequest(any(), any(), eq("dummy-token-2"))).thenReturn(message2);

            assertSame(message1, provider.buildLoginRequest());

            var expiresField = TokenAuthMessageProvider.class.getDeclaredField("expires");
            expiresField.setAccessible(true);
            expiresField.set(provider, Instant.now().minusSeconds(10));

            assertSame(message2, provider.buildLoginRequest());

            validateTokenRequest(mockClient, 2);
        }
    }

    @Test
    void testDoesNotFetchNewTokenIfNotExpired() throws Exception {
        try (MockedStatic<HttpClients> mockedHttpClients = Mockito.mockStatic(HttpClients.class)) {
            CloseableHttpClient mockClient = mock(CloseableHttpClient.class);
            mockedHttpClients.when(HttpClients::createDefault).thenReturn(mockClient);
            ClassicHttpResponse httpResponse = mock(ClassicHttpResponse.class);
            HttpEntity entity = mock(HttpEntity.class);
            InputStream stream = new ByteArrayInputStream("{\"access_token\":\"dummy-token\",\"expires_in\":3600}".getBytes());
            when(entity.getContent()).thenReturn(stream);
            when(httpResponse.getCode()).thenReturn(200);
            when(httpResponse.getEntity()).thenReturn(entity);

            when(mockClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                    .thenAnswer(invocation -> invocation.getArgument(1, HttpClientResponseHandler.class).handleResponse(httpResponse));

            OutgoingMessage message = mock(OutgoingMessage.class);
            when(factory.buildLoginRequest(any(), any(), eq("dummy-token"))).thenReturn(message);

            assertSame(message, provider.buildLoginRequest());
            assertSame(message, provider.buildLoginRequest());
            validateTokenRequest(mockClient, 1);
        }
    }

    private void validateTokenRequest(CloseableHttpClient mockClient, int times) throws Exception {
        // Capture the HttpPost object
        ArgumentCaptor<HttpPost> httpPostCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(mockClient, times(times)).execute(httpPostCaptor.capture(), any(HttpClientResponseHandler.class));

        // Inspect the captured HttpPost object
        HttpPost capturedPost = httpPostCaptor.getValue();
        assertEquals("https://test.auth0.com/oauth/token", capturedPost.getUri().toString());
        assertTrue(capturedPost.getFirstHeader("Content-Type").getValue().contains("application/x-www-form-urlencoded"));

        // Check the parameters in the entity
        String entityContent = EntityUtils.toString(capturedPost.getEntity());
        @SuppressWarnings("deprecation")
        List<NameValuePair> params = URLEncodedUtils.parse(entityContent, StandardCharsets.UTF_8);

        for(NameValuePair param : params) {
            switch (param.getName()) {
                case "grant_type":
                    assertEquals("client_credentials", param.getValue());
                    break;
                case "audience":
                    assertEquals("test-audience", param.getValue());
                    break;
                case "client_assertion_type":
                    assertEquals("urn:ietf:params:oauth:client-assertion-type:jwt-bearer", param.getValue());
                    break;
                case "client_assertion":
                    assertNotNull(param.getValue(), "Client assertion should not be null");
                    break;
                default:
                    fail("Unexpected parameter: " + param.getName());
            }
        }
    }
}