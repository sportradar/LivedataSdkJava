/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.proto.rategate;

import com.sportradar.sdk.common.rategate.CombinedRateGate;
import com.sportradar.sdk.common.rategate.RateGate;
import com.sportradar.sdk.common.rategate.SimpleRateGate;
import org.joda.time.Duration;
import org.joda.time.ReadableDuration;
import org.junit.Test;

import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class CombinedRateGateTest {

    @Test
    public void honoursAndBehaviourForWaitToProceed() throws InterruptedException {
        RateGate combined = new CombinedRateGate(
                new SimpleRateGate(2, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "test"),
                new SimpleRateGate(3, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "test")
        );

        assertThat(combined.waitToProceed(1, Duration.millis(1)), is(true));
        assertThat(combined.waitToProceed(1, Duration.millis(1)), is(true));
        assertThat(combined.waitToProceed(1, Duration.millis(1)), is(false));
    }

    @Test
    public void returnsMaxForHowLongToWait() throws InterruptedException {
        RateGate combined = new CombinedRateGate(
                new SimpleRateGate(2, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "test"),
                new SimpleRateGate(3, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "test")
        );

        assertThat(combined.howLongToWait(), is((ReadableDuration) Duration.ZERO));
        assertThat(combined.waitToProceed(Duration.millis(1)), is(true));
        assertThat(combined.howLongToWait(), is((ReadableDuration) Duration.ZERO));
        assertThat(combined.waitToProceed(Duration.millis(1)), is(true));
        assertThat(combined.howLongToWait(), is(not((ReadableDuration) Duration.ZERO)));
    }

    @Test
    public void oneUsedUpAffectsCombo() throws InterruptedException {
        RateGate one = new SimpleRateGate(1, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "test");

        RateGate combined = new CombinedRateGate(
                new SimpleRateGate(100, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "test"),
                one);

        assertThat(one.waitToProceed(1, Duration.millis(1)), is(true));
        assertThat(combined.waitToProceed(1, Duration.millis(1)), is(false));
    }
}
