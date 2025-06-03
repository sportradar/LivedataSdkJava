package com.sportradar.livedata.sdk.common.settings;

import org.joda.time.Duration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LiveScoutSettingsTest {

    @Test
    void testBuilderWithTestMode() {
        LiveScoutSettings settings = LiveScoutSettings.builder(true).build();

        assertTrue(settings.isTest());
        assertEquals("replay.livedata.betradar.com", settings.getHostName());
        assertEquals(2047, settings.getPort());
    }

    @Test
    void testBuilderWithProductionMode() {
        LiveScoutSettings settings = LiveScoutSettings.builder(false).build();

        assertFalse(settings.isTest());
        assertEquals("livedata.betradar.com", settings.getHostName());
        assertEquals(2017, settings.getPort());
    }

    @Test
    void testDefaultValues() {
        LiveScoutSettings settings = LiveScoutSettings.builder(false).build();

        assertEquals(4, settings.getDispatcherThreadCount());
        assertEquals(16384, settings.getDispatcherQueueSize());
        assertEquals(Duration.standardSeconds(10), settings.getInitialReconnectWait());
        assertEquals(Duration.standardSeconds(20), settings.getReconnectWait());
        assertEquals(1024 * 1024, settings.getReceiveBufferSize());
        assertEquals(20 * 1024 * 1024, settings.getTotalBufferSize());
        assertEquals(Duration.standardMinutes(15), settings.getMaxRequestTimeAllowance());
        assertEquals(100, settings.getMaxMatchIdsPerRequest());
        assertFalse(settings.isDisconnectOnParseError());
        assertTrue(settings.isUseSSL());
        assertEquals(List.of(new LimiterData(450, Duration.standardMinutes(2), "scoutRequestLimit")), settings.getRequestLimiters());
        assertEquals(Duration.standardHours(8), settings.getMatchExpireMaxAge());
        assertEquals(Duration.standardSeconds(20), settings.getClientAliveMsgTimeout());
        assertEquals(Duration.standardSeconds(8), settings.getServerAliveMsgTimeout());
    }

    @Test
    void testCustomValues() {
        LiveScoutSettings settings = LiveScoutSettings.builder(false)
                .dispatcherThreadCount(8)
                .dispatcherQueueSize(32768)
                .initialReconnectWait(Duration.standardSeconds(5))
                .reconnectWait(Duration.standardSeconds(15))
                .receiveBufferSize(2048 * 1024)
                .totalBufferSize(40 * 1024 * 1024)
                .maxRequestTimeAllowance(Duration.standardMinutes(10))
                .maxMatchIdsPerRequest(200)
                .disconnectOnParseError(true)
                .useSSL(false)
                .build();

        assertEquals(8, settings.getDispatcherThreadCount());
        assertEquals(32768, settings.getDispatcherQueueSize());
        assertEquals(Duration.standardSeconds(5), settings.getInitialReconnectWait());
        assertEquals(Duration.standardSeconds(15), settings.getReconnectWait());
        assertEquals(2048 * 1024, settings.getReceiveBufferSize());
        assertEquals(40 * 1024 * 1024, settings.getTotalBufferSize());
        assertEquals(Duration.standardMinutes(10), settings.getMaxRequestTimeAllowance());
        assertEquals(200, settings.getMaxMatchIdsPerRequest());
        assertTrue(settings.isDisconnectOnParseError());
        assertFalse(settings.isUseSSL());
    }
}
