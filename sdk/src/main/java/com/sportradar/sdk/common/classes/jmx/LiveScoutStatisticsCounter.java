package com.sportradar.sdk.common.classes.jmx;

import com.j256.simplejmx.common.JmxAttributeMethod;
import com.j256.simplejmx.common.JmxOperation;
import com.j256.simplejmx.common.JmxResource;

@JmxResource(description = "Runtime counter", domainName = "com.java.sportradar.sdk", beanName = "LiveScoutStatisticsCounter")
public class LiveScoutStatisticsCounter {

    protected int onMatchUpdate = 0;
    protected int onLineups = 0;
    protected int onMatchData = 0;
    protected int onMatchBooking = 0;
    protected int onScoutInfos = 0;
    protected int onOddsSuggestion = 0;
    protected int onOnMatchDeltaUpdateDeltaReceived = 0;
    protected int onOnMatchDeltaUpdateReceived = 0;
    protected int onFullMatchUpdateReceived = 0;
    protected int onMatchStop = 0;
    protected int onMatchListUpdate = 0;
    protected int onMatchList = 0;


    @JmxAttributeMethod(description = "Number of onMatchUpdate cals.")
    public int getOnMatchUpdate() {
        return onMatchUpdate;
    }

    @JmxAttributeMethod(description = "Number of onMatchData cals.")
    public int getOnMatchData() {
        return onMatchData;
    }

    @JmxAttributeMethod(description = "Number of onLineups cals.")
    public int getOnLineups() {
        return onLineups;
    }

    @JmxAttributeMethod(description = "Number of onMatchBooking cals.")
    public int getOnMatchBooking() {
        return onMatchBooking;
    }

    @JmxAttributeMethod(description = "Number of onScoutInfos cals.")
    public int getOnScoutInfos() {
        return onScoutInfos;
    }

    @JmxAttributeMethod(description = "Number of onOddsSuggestion cals.")
    public int getOnOddsSuggestion() {
        return onOddsSuggestion;
    }

    @JmxAttributeMethod(description = "Number of onOnMatchDeltaUpdateDeltaReceived cals.")
    public int getOnOnMatchDeltaUpdateDeltaReceived() {
        return onOnMatchDeltaUpdateDeltaReceived;
    }

    @JmxAttributeMethod(description = "Number of onOnMatchDeltaUpdateReceived cals.")
    public int getOnOnMatchDeltaUpdateReceived() {
        return onOnMatchDeltaUpdateReceived;
    }

    @JmxAttributeMethod(description = "Number of onFullMatchUpdateReceived cals.")
    public int getOnFullMatchUpdateReceived() {
        return onFullMatchUpdateReceived;
    }

    @JmxAttributeMethod(description = "Number of onMatchStop cals.")
    public int getOnMatchStop() {
        return onMatchStop;
    }

    @JmxAttributeMethod(description = "Number of onMatchListUpdate cals.")
    public int getOnMatchListUpdate() {
        return onMatchListUpdate;
    }

    @JmxAttributeMethod(description = "Number of onMatchList cals.")
    public int getOnMatchList() {
        return onMatchList;
    }

    public synchronized void countUpOnMatchUpdate() {
        ++onMatchUpdate;
    }

    public synchronized void countUpOnMatchData() {
        ++onMatchData;
    }

    public synchronized void countUpOnLineups() {
        ++onLineups;
    }

    public synchronized void countUpOnMatchBooking() {
        ++onMatchBooking;
    }

    public synchronized void countUpOnScoutInfos() {
        ++onScoutInfos;
    }

    public synchronized void countUpOnOddsSuggestion() {
        ++onOddsSuggestion;
    }

    public synchronized void countUpOnOnMatchDeltaUpdateDeltaReceived() {
        ++onOnMatchDeltaUpdateDeltaReceived;
    }

    public synchronized void countUpOnOnMatchDeltaUpdateReceived() {
        ++onOnMatchDeltaUpdateReceived;
    }

    public synchronized void countUpOnFullMatchUpdateReceived() {
        ++onFullMatchUpdateReceived;
    }

    public synchronized void countUpOnMatchStop() {
        ++onMatchStop;
    }

    public synchronized void countUpOnMatchListUpdate() {
        ++onMatchListUpdate;
    }

    public synchronized void countUpOnMatchList() {
        ++onMatchList;
    }

    @JmxOperation(description = "Reset all counters to 0.")
    public String resetCounters() {
        onMatchUpdate = 0;
        onMatchData = 0;
        onLineups = 0;
        onMatchBooking = 0;
        onScoutInfos = 0;
        onOddsSuggestion = 0;
        onOnMatchDeltaUpdateDeltaReceived = 0;
        onOnMatchDeltaUpdateReceived = 0;
        onFullMatchUpdateReceived = 0;
        onMatchStop = 0;
        onMatchListUpdate = 0;
        onMatchList = 0;

        return "All counter are reset to 0.";
    }
}
