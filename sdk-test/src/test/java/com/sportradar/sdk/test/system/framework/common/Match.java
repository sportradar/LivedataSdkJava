package com.sportradar.sdk.test.system.framework.common;

public class Match {

    protected boolean booked;
    protected boolean subscribed;

    public Match() {
        this.subscribed = false;
        this.booked = true;
    }

    public void book() {
        booked = true;
    }

    public void subscribe() {
        subscribed = true;
    }

    public void unsubscribe() {
        subscribed = false;
    }

    public boolean isBooked() {
        return booked;
    }

    public boolean isSubscribed() {
        return subscribed;
    }
}
