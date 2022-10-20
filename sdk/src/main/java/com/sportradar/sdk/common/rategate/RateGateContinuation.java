/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

import org.joda.time.Duration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class RateGateContinuation {

    private final RateGate gate;
    private final Executor executor;
    private final Queue<ContinuationQueueElement> waitQueue;

    /**
     * Construct a new RateGateContinuation
     *
     * @param gate     - used rategate
     * @param executor - executor to invoke the continuation with
     */
    public RateGateContinuation(
            final RateGate gate,
            final Executor executor
    ) {
        checkNotNull(gate);
        checkNotNull(executor);

        this.gate = gate;
        this.executor = executor;
        this.waitQueue = new ConcurrentLinkedQueue<>();

        gate.setListener(new RateGateListener() {
            @Override
            public void onRateGatePassed(int amount) {
                ContinuationQueueElement e = waitQueue.peek();
                if (e == null) {
                    return;
                }

                if (gate.howLongToWait(e.getNum()).isEqual(Duration.ZERO)) {
                    executor.execute(e.getRunnable());
                    waitQueue.remove();
                }
            }
        });
    }

    /**
     * Factory method to create a simple continuation.
     *
     * @param gate - for specified rategate
     * @return <p> A RateGateContinuation instance.
     * </p>
     * Usage: RateGateContinuation.after(rg).continueWith(runnable);
     */
    public static RateGateContinuation after(RateGate gate) {
        return new RateGateContinuation(gate, Executors.newSingleThreadExecutor());
    }

    /**
     * Continue executing the continuation when ready
     *
     * @param continuation - desired continuation
     * @throws InterruptedException given thread were interrupted
     */
    public void continueWith(
            Runnable continuation) throws InterruptedException {
        continueWith(1, continuation);
    }

    /**
     * Continue executing the continuation when ready
     *
     * @param amount       - positive amount
     * @param continuation - desired continuation
     * @throws InterruptedException given thread were interrupted
     */
    public void continueWith(
            int amount,
            Runnable continuation) throws InterruptedException {
        checkArgument(amount > 0);

        if (gate.waitToProceed(amount, Duration.ZERO)) {
            executor.execute(continuation);
        } else {
            waitQueue.add(new ContinuationQueueElement(amount, continuation));
        }
    }
}
