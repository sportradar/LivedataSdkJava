/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.livescout;

import com.sportradar.sdk.proto.common.RequestProducer;
import com.sportradar.sdk.proto.common.RequestProducerBase;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import org.joda.time.Duration;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link RequestProducer} for LiveScout that produces alive messages.
 */
public class LiveScoutClientAliveProducer extends RequestProducerBase<OutgoingMessage> {

    private final ScheduledExecutorService executor;
    private final Duration interval;
    private final LiveScoutStatusFactory entityFactory;

    private Future<?> timerCancel;

    public LiveScoutClientAliveProducer(
            final LiveScoutStatusFactory entityFactory,
            final ScheduledExecutorService executor,
            final Duration interval) {

        checkNotNull(entityFactory, "entityFactory cannot be a null reference");
        checkNotNull(executor, "executor cannot be a null reference");
        checkNotNull(interval, "interval cannot be a null reference");
        checkArgument(interval.getMillis() > 0, "interval cannot be negative");

        this.entityFactory = entityFactory;
        this.executor = executor;
        this.interval = interval;
    }

    private void produceAlive() {
        notifyOnRequestReady(entityFactory.buildAlive());
    }

    /**
     * Start invoking ClientAliveProducerListener events
     */
    @Override
    protected void onStarting() {
        timerCancel = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                produceAlive();
            }
        }, interval.getMillis(), interval.getMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Stop invoking ClientAliveProducerListener events
     */
    @Override
    protected void onStopping() {
        if (timerCancel != null) {
            timerCancel.cancel(false);
        }
    }
}
