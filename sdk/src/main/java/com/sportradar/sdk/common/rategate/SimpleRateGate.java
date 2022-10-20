/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import com.sportradar.sdk.common.timer.TimeProvider;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class SimpleRateGate extends RateGateBase implements RateGate {

    private final static Logger logger = LoggerFactory.getLogger(SimpleRateGate.class);

    private final int number;
    private final Duration duration;
    private final ScheduledExecutorService scheduler;

    private final Semaphore semaphore;
    private final List<RateGateQueueElement> exitTimes;
    private final AtomicInteger waiting = new AtomicInteger(0);

    private final String rateGateName;

    /**
     * Construct a SimpleRateGate.
     *
     * @param number       - number of elements
     * @param duration     - that can pass in this period
     * @param scheduler    - task scheduler
     * @param rateGateName limiter name for easier identification
     */
    public SimpleRateGate(
            final int number,
            final Duration duration,
            final ScheduledExecutorService scheduler,
            final String rateGateName
    ) {
        checkArgument(number > 0, "number must be positive: %s", number);
        checkArgument(duration.getMillis() > 0, "duration must be positive: %s", number);
        checkNotNull(scheduler);

        this.number = number;
        this.duration = duration;
        this.scheduler = scheduler;
        this.rateGateName = rateGateName;

        semaphore = new Semaphore(this.number, true);
        exitTimes = new ArrayList<>();

        scheduler.schedule(new ExitTask(), this.duration.getMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void waitToProceed(int amount) throws InterruptedException {
        checkArgument(amount > 0);

        waiting.addAndGet(amount);

        semaphore.acquire(amount);

        synchronized (exitTimes) {
            for (int i = 0; i < amount; i++) {
                exitTimes.add(new RateGateQueueElement(TimeProvider.getCurrent().getCurrentTime().withDurationAdded(duration, 1)));
            }
        }
        synchronized (waiting) {
            waiting.set(waiting.get() - amount);
        }
    }

    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param amount - number of elements
     * @param wait   - the maximum amount of time to wait, negative means infinity
     * @return true when we passed the gate; false when a timeout occurred
     * @throws InterruptedException when somebody interrupted the waiting
     */
    @Override
    public boolean waitToProceed(
            final int amount,
            final Duration wait)
            throws InterruptedException {

        checkArgument(amount > 0);
        final long waitMillis = wait.getMillis();
        checkArgument(waitMillis > 0, "duration must be positive: %s", number);
        waiting.addAndGet(amount);

        boolean ret = semaphore.tryAcquire(amount, waitMillis, TimeUnit.MILLISECONDS);

        if (ret) {
            synchronized (exitTimes) {
                for (int i = 0; i < amount; i++) {
                    exitTimes.add(new RateGateQueueElement(TimeProvider.getCurrent().getCurrentTime().withDurationAdded(duration, 1)));
                }
            }
        }
        synchronized (waiting) {
            waiting.set(waiting.get() - amount);
        }

        return ret;
    }

    /**
     * Approximately how long will I have to wait?
     *
     * @param amount - number of elements
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    @Override
    public Duration howLongToWait(final int amount) {
        checkArgument(amount > 0);

        synchronized (exitTimes) {
            int stillToEnter = this.waiting.get();
            if (exitTimes.isEmpty() && stillToEnter < 1) {
                return Duration.ZERO;
            }
            if (exitTimes.size() + amount <= number && stillToEnter < 1) {
                return Duration.ZERO;
            }
            long toWait = ((long) Math.floor((((double) stillToEnter + amount) / number)) * duration.getMillis());

            if (!exitTimes.isEmpty()) {
                RateGateQueueElement e = exitTimes.get(exitTimes.size() - 1);
                Period p = new Period(TimeProvider.getCurrent().getCurrentTime(), e.getExpirationTime());
                if (p.toStandardDuration().getMillis() > 0) {
                    toWait = toWait + p.toStandardDuration().getMillis();
                }
            }
            return Duration.millis(toWait);
        }
    }

    private final class ExitTask implements Runnable {

        @Override
        public void run() {
            RateGateQueueElement e = null;

            int removed = 0;

            synchronized (exitTimes) {
                while (true) {
                    if (exitTimes.isEmpty()) {
                        break;
                    }
                    e = exitTimes.get(0);
                    if (e != null && TimeProvider.getCurrent().getCurrentTime().isAfter(e.getExpirationTime())) {
                        semaphore.release(1);
                        exitTimes.remove(0);
                        removed++;
                    } else {
                        break;
                    }
                }
            }
            for (int i = 0; i < removed; i++) {
                synchronized (listenerLock) {
                    if (listener != null) {
                        listener.onRateGatePassed(1);
                    }
                }
            }

            synchronized (exitTimes) {
                if (!exitTimes.isEmpty()) {
                    e = exitTimes.get(0);
                }
            }
            long exp = duration.getMillis();
            if (e != null) {
                exp = new Period(TimeProvider.getCurrent().getCurrentTime(), e.getExpirationTime()).toStandardDuration().getMillis() + 1;
            }

            long delay = Math.max(0, Math.min(exp, duration.getMillis()));
            synchronized (exitTimes) {
                logger.info("{} remaining entries {}, next check in {} ms", rateGateName, exitTimes.size(), delay);
            }

            scheduler.schedule(this, delay, TimeUnit.MILLISECONDS);
        }
    }


}
