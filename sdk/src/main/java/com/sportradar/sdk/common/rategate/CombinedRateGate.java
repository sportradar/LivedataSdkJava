/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.concurrent.Phaser;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class CombinedRateGate extends RateGateBase implements RateGate {

    private final static Logger logger = LoggerFactory.getLogger(CombinedRateGate.class);
    private final RateGate[] rateGates;
    private final HashSet<Integer> listnerData;

    /**
     * Constuct a combined rategate.
     *
     * @param rateGates array of {@link RateGate} to combine
     */
    public CombinedRateGate(final RateGate... rateGates) {
        checkNotNull(rateGates);
        int i = 0;
        listnerData = new HashSet<>();

        for (final RateGate r : rateGates) {
            final int ii = i;
            checkNotNull(r);

            r.setListener(new RateGateListener() {
                @Override
                public void onRateGatePassed(int amount) {
                    synchronized (listenerLock) {
                        listnerData.add(ii);

                        if (listnerData.size() == rateGates.length) {
                            // Collected data from all rategates
                            if (listener != null) {
                                listener.onRateGatePassed(amount);
                            }

                            listnerData.clear();
                        }
                    }
                }
            });

            i++;
        }
        this.rateGates = rateGates;
    }

    /**
     * Constuct a combined rategate.
     *
     * @param r1 - first rategate
     * @param r2 - second rategate
     */
    public CombinedRateGate(final RateGate r1, final RateGate r2) {
        this(new RateGate[]{r1, r2});
    }


    private class OperationResult {

        private boolean success;
        private boolean interrupted;

        public OperationResult() {
            success = true;
        }

        public synchronized boolean isSuccess() {
            return success;
        }

        public synchronized void addSuccess(boolean success) {
            this.success &= success;
        }

        public synchronized boolean isInterrupted() {
            return interrupted;
        }

        public synchronized void setInterrupted() {
            this.interrupted = true;
        }
    }

    @Override
    public void waitToProceed(final int amount) throws InterruptedException {
        checkArgument(amount > 0);

        final Phaser phaser = new Phaser(1) {
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase >= 2 || registeredParties == 0;
            }
        };

        final OperationResult result = new OperationResult();

        for (final RateGate rateGate : rateGates) {
            phaser.register();
            new Thread() {
                public void run() {
                    phaser.arriveAndAwaitAdvance();
                    try {
                        rateGate.waitToProceed(amount);
                        result.addSuccess(true);
                    } catch (InterruptedException e) {
                        logger.warn("Caught exception", e);
                        result.setInterrupted();
                    }

                    phaser.arriveAndDeregister();
                }
            }.start();
        }

        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndAwaitAdvance();

        if (result.isInterrupted()) {
            throw new InterruptedException();
        }
    }

    /**
     * Block when configured rate-limits are exceeded.
     *
     * @param amount - number of elements
     * @param wait   - when specified this means the maximum amount of time to wait
     * @return true when we passed the gate; false when a timeout occured
     * @throws InterruptedException when somebody interrupted the waiting
     */
    @Override
    public boolean waitToProceed(
            final int amount,
            final Duration wait)
            throws InterruptedException {

        checkArgument(amount > 0);

        final Phaser phaser = new Phaser(1) {
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase >= 2 || registeredParties == 0;
            }
        };

        final OperationResult result = new OperationResult();

        for (final RateGate rateGate : rateGates) {
            phaser.register();
            new Thread() {
                public void run() {
                    phaser.arriveAndAwaitAdvance();
                    try {
                        result.addSuccess(rateGate.waitToProceed(amount, wait));
                    } catch (InterruptedException e) {
                        logger.warn("Caught exception", e);
                        result.setInterrupted();
                    }

                    phaser.arriveAndDeregister();
                }
            }.start();
        }

        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndAwaitAdvance();

        if (result.isInterrupted()) {
            throw new InterruptedException();
        }

        return result.isSuccess();
    }

    /**
     * Approximately how long will I have to wait?
     *
     * @param amount - number of elements
     * @return - a non-negative amount of time, zero means no need to wait.
     */
    @Override
    public Duration howLongToWait(final int amount) {
        Duration maximum = Duration.ZERO;

        for (RateGate r : rateGates) {
            Duration one = r.howLongToWait(amount);
            if (one.isLongerThan(maximum)) {
                maximum = one;
            }
        }

        return maximum;
    }
}
