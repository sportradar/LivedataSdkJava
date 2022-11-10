package com.sportradar.livedata.sdk.common.classes.jmx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class LiveScoutStatisticsCounterTest {

    LiveScoutStatisticsCounter liveScoutStatisticsCounter;

    @BeforeEach
    void init() {
        liveScoutStatisticsCounter = new LiveScoutStatisticsCounter();
    }

    @DisplayName("onMatchUpdate() Getter test")
    @Test
    void onMatchUpdateGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnMatchUpdate()).isEqualTo(0);
    }

    @DisplayName("onMatchData() Getter test")
    @Test
    void onMatchDataGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnMatchData()).isEqualTo(0);
    }

    @DisplayName("onMatchData() Getter test")
    @Test
    void onLineupsGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnLineups()).isEqualTo(0);
    }

    @DisplayName("onMatchBooking() Getter test")
    @Test
    void onMatchBookingGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnMatchBooking()).isEqualTo(0);
    }

    @DisplayName("onScoutInfos() Getter test")
    @Test
    void onScoutInfosGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnScoutInfos()).isEqualTo(0);
    }

    @DisplayName("onOddsSuggestion() Getter test")
    @Test
    void onOddsSuggestionGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnOddsSuggestion()).isEqualTo(0);
    }

    @DisplayName("onOnMatchDeltaUpdateDeltaReceived() Getter test")
    @Test
    void onOnMatchDeltaUpdateDeltaReceivedGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnOnMatchDeltaUpdateDeltaReceived()).isEqualTo(0);
    }

    @DisplayName("onOnMatchDeltaUpdateReceived() Getter test")
    @Test
    void onOnMatchDeltaUpdateReceivedGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnOnMatchDeltaUpdateReceived()).isEqualTo(0);
    }

    @DisplayName("onFullMatchUpdateReceived() Getter test")
    @Test
    void onFullMatchUpdateReceivedGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnFullMatchUpdateReceived()).isEqualTo(0);
    }

    @DisplayName("onMatchStop() Getter test")
    @Test
    void onMatchStopGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnMatchStop()).isEqualTo(0);
    }

    @DisplayName("onMatchListUpdate() Getter test")
    @Test
    void onMatchListUpdateGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnMatchListUpdate()).isEqualTo(0);
    }

    @DisplayName("onMatchList() Getter test")
    @Test
    void onMatchListGetterTest() {
        assertThat(liveScoutStatisticsCounter.getOnMatchList()).isEqualTo(0);
    }

    @DisplayName("countUpOnMatchUpdate() test")
    @Test
    void countUpOnMatchUpdateTest() {
        liveScoutStatisticsCounter.countUpOnMatchUpdate();
        assertThat(liveScoutStatisticsCounter.onMatchUpdate).isEqualTo(1);
    }

    @DisplayName("countUpOnMatchData() test")
    @Test
    void countUpOnMatchDataTest() {
        liveScoutStatisticsCounter.countUpOnMatchData();
        assertThat(liveScoutStatisticsCounter.onMatchData).isEqualTo(1);
    }

    @DisplayName("countUpOnLineups() test")
    @Test
    void countUpOnLineupsTest() {
        liveScoutStatisticsCounter.countUpOnLineups();
        assertThat(liveScoutStatisticsCounter.onLineups).isEqualTo(1);
    }

    @DisplayName("countUpOnMatchBooking() test")
    @Test
    void countUpOnMatchBookingTest() {
        liveScoutStatisticsCounter.countUpOnMatchBooking();
        assertThat(liveScoutStatisticsCounter.onMatchBooking).isEqualTo(1);
    }

    @DisplayName("countUpOnScoutInfos() test")
    @Test
    void countUpOnScoutInfosTest() {
        liveScoutStatisticsCounter.countUpOnScoutInfos();
        assertThat(liveScoutStatisticsCounter.onScoutInfos).isEqualTo(1);
    }

    @DisplayName("countUpOnOddsSuggestion() test")
    @Test
    void countUpOnOddsSuggestionTest() {
        liveScoutStatisticsCounter.countUpOnOddsSuggestion();
        assertThat(liveScoutStatisticsCounter.onOddsSuggestion).isEqualTo(1);
    }

    @DisplayName("countUpOnOnMatchDeltaUpdateDeltaReceived() test")
    @Test
    void countUpOnOnMatchDeltaUpdateDeltaReceivedTest() {
        liveScoutStatisticsCounter.countUpOnOnMatchDeltaUpdateDeltaReceived();
        assertThat(liveScoutStatisticsCounter.onOnMatchDeltaUpdateDeltaReceived).isEqualTo(1);
    }

    @DisplayName("countUpOnOnMatchDeltaUpdateReceived() test")
    @Test
    void countUpOnOnMatchDeltaUpdateReceivedTest() {
        liveScoutStatisticsCounter.countUpOnOnMatchDeltaUpdateReceived();
        assertThat(liveScoutStatisticsCounter.onOnMatchDeltaUpdateReceived).isEqualTo(1);
    }

    @DisplayName("countUpOnFullMatchUpdateReceived() test")
    @Test
    void countUpOnFullMatchUpdateReceivedTest() {
        liveScoutStatisticsCounter.countUpOnFullMatchUpdateReceived();
        assertThat(liveScoutStatisticsCounter.onFullMatchUpdateReceived).isEqualTo(1);
    }

    @DisplayName("countUpOnMatchStop() test")
    @Test
    void countUpOnMatchStopTest() {
        liveScoutStatisticsCounter.countUpOnMatchStop();
        assertThat(liveScoutStatisticsCounter.onMatchStop).isEqualTo(1);
    }

    @DisplayName("countUpOnMatchListUpdate() test")
    @Test
    void countUpOnMatchListUpdateTest() {
        liveScoutStatisticsCounter.countUpOnMatchListUpdate();
        assertThat(liveScoutStatisticsCounter.onMatchListUpdate).isEqualTo(1);
    }

    @DisplayName("countUpOnMatchList() test")
    @Test
    void countUpOnMatchListTest() {
        liveScoutStatisticsCounter.countUpOnMatchList();
        assertThat(liveScoutStatisticsCounter.onMatchList).isEqualTo(1);
    }

    @DisplayName("resetCounters() test")
    @Test
    void resetCountersTest() {
        Random random = new Random();

        liveScoutStatisticsCounter.onMatchUpdate = random.nextInt();
        liveScoutStatisticsCounter.onMatchData = random.nextInt();
        liveScoutStatisticsCounter.onLineups = random.nextInt();
        liveScoutStatisticsCounter.onMatchBooking = random.nextInt();
        liveScoutStatisticsCounter.onScoutInfos = random.nextInt();
        liveScoutStatisticsCounter.onOddsSuggestion = random.nextInt();
        liveScoutStatisticsCounter.onOnMatchDeltaUpdateDeltaReceived = random.nextInt();
        liveScoutStatisticsCounter.onOnMatchDeltaUpdateReceived = random.nextInt();
        liveScoutStatisticsCounter.onFullMatchUpdateReceived = random.nextInt();
        liveScoutStatisticsCounter.onMatchStop = random.nextInt();
        liveScoutStatisticsCounter.onMatchListUpdate = random.nextInt();
        liveScoutStatisticsCounter.onMatchList = random.nextInt();

        liveScoutStatisticsCounter.resetCounters();

        assertThat(liveScoutStatisticsCounter.onMatchUpdate).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onMatchData).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onLineups).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onMatchBooking).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onScoutInfos).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onOddsSuggestion).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onOnMatchDeltaUpdateDeltaReceived).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onOnMatchDeltaUpdateReceived).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onFullMatchUpdateReceived).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onMatchStop).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onMatchListUpdate).isEqualTo(0);
        assertThat(liveScoutStatisticsCounter.onMatchList).isEqualTo(0);
    }
}