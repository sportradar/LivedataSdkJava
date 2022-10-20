/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.proto.rategate;

import com.sportradar.sdk.common.rategate.RateGate;
import com.sportradar.sdk.common.rategate.SimpleRateGate;
import com.sportradar.sdk.common.timer.TimeProvider;
import com.sportradar.sdk.test.FakeTimeProvider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.action.CustomAction;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.ReadableDuration;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SimpleRateGateTest {

    private final Synchroniser synchroniser = new Synchroniser();

    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};

    @Test(expected = IllegalArgumentException.class)
    public void validatesNumberParameter() {
        new SimpleRateGate(-1, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatesDurationParameter() {
        new SimpleRateGate(1, Duration.millis(-1), Executors.newSingleThreadScheduledExecutor(), "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatesRunAfterParameter() {
        new SimpleRateGate(1, Duration.millis(1), null, "name");
    }

    @Test
    public void canReturnFalseWhenWaitToProceedInvokedTooOften() throws InterruptedException {
        RateGate r = new SimpleRateGate(2, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "name");

        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(Duration.millis(1)), is(false));
    }

    @Test
    public void canReturnFalseWhenWaitToProceedInvokedTooOftenUsingDifferentAmounts() throws InterruptedException {
        RateGate r = new SimpleRateGate(4, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "name");

        assertThat(r.waitToProceed(2, Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(1, Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(1, Duration.millis(1)), is(true));

        assertThat(r.waitToProceed(2, Duration.millis(1)), is(false));
        assertThat(r.waitToProceed(1, Duration.millis(1)), is(false));
    }

    @Test
    public void canBlockWithWaitToProceed() throws InterruptedException {
        RateGate r = new SimpleRateGate(2, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "name");

        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        DateTime before = DateTime.now();
        assertThat(r.waitToProceed(Duration.millis(100)), is(false));
        DateTime after = DateTime.now();

        assertThat(new Period(before, after).toStandardDuration(), greaterThan((ReadableDuration) Duration.millis(50)));
    }

    @Test
    public void canBlockWithWaitToProceedUsingDifferentAmounts() throws InterruptedException {
        RateGate r = new SimpleRateGate(4, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "name");
        assertThat(r.waitToProceed(1, Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(2, Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(1, Duration.millis(1)), is(true));

        DateTime before = DateTime.now();
        assertThat(r.waitToProceed(Duration.millis(100)), is(false));
        DateTime after = DateTime.now();

        assertThat(new Period(before, after).toStandardDuration(), greaterThan((ReadableDuration) Duration.millis(50)));
    }


    @Test
    public void reportsTrueAfterWaitToProceedExpiration() throws InterruptedException {
        final FakeTimeProvider fakeTime = new FakeTimeProvider(DateTime.now());
        final ExecutorTrace trace = new ExecutorTrace();
        final ScheduledExecutorService executor = getScheduledExecutorService(trace);

        TimeProvider.setCurrent(fakeTime);

        RateGate r = new SimpleRateGate(2, Duration.standardDays(1), executor, "name");

        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        trace.runLast();

        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        trace.runLast();
        assertThat(r.waitToProceed(Duration.millis(1)), is(false));
        trace.runLast();

        assertAproxEqualDurations(trace, Duration.standardSeconds(5));
        fakeTime.moveForward(Duration.standardDays(2));

        trace.runLast();
        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        assertEqualRunnables(trace);
        TimeProvider.resetToRealTime();
    }

    private ScheduledExecutorService getScheduledExecutorService(final ExecutorTrace trace) {
        final ScheduledExecutorService executor = context.mock(ScheduledExecutorService.class);
        context.checking(new Expectations() {{
            allowing(executor).schedule(with(any(Runnable.class)), with(any(Long.class)), with(TimeUnit.MILLISECONDS));
            will(new CustomAction("invoke runnable") {
                @Override
                public Object invoke(Invocation invocation) throws Throwable {
                    Runnable r = (Runnable) invocation.getParameter(0);
                    Duration d = Duration.millis((Long) invocation.getParameter(1));
                    trace.getRunnableList().add(r);
                    trace.getDurationList().add(d);

                    return null;
                }
            });

        }});

        return executor;
    }

    private void assertAproxEqualDurations(ExecutorTrace trace, Duration eps) {
        List<Duration> list = trace.getDurationList();

        for (int i = 0; i < list.size() - 1; ++i) {
            assertWithinBounds(list.get(i), list.get(i + 1), eps);
        }
    }

    private void assertEqualRunnables(ExecutorTrace trace) {
        List<Runnable> list = trace.getRunnableList();

        for (int i = 0; i < list.size() - 1; ++i) {
            assertThat(list.get(i), is(list.get(i + 1)));
        }
    }

    private void assertWithinBounds(Duration first, Duration second, Duration eps) {
        assertThat(first, greaterThanOrEqualTo((ReadableDuration) second.withDurationAdded(eps, -1)));
        assertThat(first, lessThanOrEqualTo((ReadableDuration) second.withDurationAdded(eps, 1)));
    }

    @Test
    public void canUnblockWaitToProceedAfterExpiration() throws InterruptedException {
        final ExecutorTrace trace = new ExecutorTrace();
        final ScheduledExecutorService executor = getScheduledExecutorService(trace);
        final FakeTimeProvider timeProvider = new FakeTimeProvider(DateTime.now());

        TimeProvider.setCurrent(timeProvider);

        RateGate r = new SimpleRateGate(2, Duration.standardDays(1), executor, "name");

        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        assertThat(r.waitToProceed(Duration.millis(1)), is(true));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return;
                }

                timeProvider.moveForward(Duration.standardDays(2));
                trace.runLast();
            }
        }).start();

        DateTime before = DateTime.now();
        assertThat(r.waitToProceed(Duration.millis(500)), is(true));
        DateTime after = DateTime.now();

        assertThat(new Period(before, after).toStandardDuration(), greaterThan((ReadableDuration) Duration.millis(100)));

        TimeProvider.resetToRealTime();
    }

    @Test
    public void canReturnMeaningfulDataWithHowLongToWait() throws InterruptedException {
        RateGate r = new SimpleRateGate(2, Duration.standardDays(1), Executors.newSingleThreadScheduledExecutor(), "name");

        assertThat(r.howLongToWait(), lessThan((ReadableDuration) Duration.millis(1)));
        assertThat(r.waitToProceed(Duration.millis(1)), is(true));
        assertThat(r.howLongToWait(), lessThan((ReadableDuration) Duration.millis(1)));
        assertThat(r.waitToProceed(Duration.millis(1)), is(true));

        assertWithinBounds(r.howLongToWait(), Duration.standardDays(1), Duration.standardMinutes(2));
    }
}
