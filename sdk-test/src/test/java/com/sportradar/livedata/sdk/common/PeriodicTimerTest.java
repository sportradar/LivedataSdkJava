/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common;

import com.sportradar.livedata.sdk.common.classes.BlockingList;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import com.sportradar.livedata.sdk.common.timer.TimerListener;
import org.joda.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PeriodicTimerTest} class.
 */
public class PeriodicTimerTest {

    private PeriodicTimerDriver driver = null;

    @BeforeEach
    public void setUp() {
        driver = new PeriodicTimerDriver(new PeriodicTimer(Executors.newScheduledThreadPool(1)));
    }

    @Test
    void elapsesExpectedNumberOfTimes() throws InterruptedException {
        driver.receiveEvents(200, 5);
    }

    @Test
    void stopsBeforeFirstEventIsRaised() throws InterruptedException {
        driver.stopsBeforeFirstEvent(200, 20);
    }

    @Test
    void stopsBeforeOneShotIsRaised() throws InterruptedException {
        driver.stopsBeforeOneShot(200);
    }

    @Test
    void timerCanBeReused() throws InterruptedException {
        driver.receiveEvents(200, 5);
        driver.receiveEvents(200, 5);
    }

    @Test
    void timerDoesNotThrowWhenStoppedMultipleTimes() {
        Timer timer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        timer.stop();
        timer.stop();
    }

    private class PeriodicTimerDriver {

        private final Logger logger = LoggerFactory.getLogger(PeriodicTimerDriver.class);
        private final long tolerance = 100;
        private final BlockingList<String> events = new BlockingList<>();

        private final Timer timer;

        public PeriodicTimerDriver(PeriodicTimer timer) {
            checkNotNull(timer, "The timer cannot be a null reference");

            this.timer = timer;
            this.timer.setListener(new TimerListener() {
                @Override
                public void onTick() {
                    logger.debug("tick event raised");
                    events.add("tick");
                }
            });
        }

        public void receiveEvents(long timeout, int eventCount) throws InterruptedException {
            int currentCount = eventCount;
            timer.schedule(Duration.millis(timeout), Duration.millis(timeout));
            while (currentCount > 0) {
                events.compareAndRemove("tick", timeout + tolerance);
                logger.info("received tick event confirmation");
                currentCount--;
            }
            timer.stop();
        }

        public void stopsBeforeOneShot(long initialDelay) throws InterruptedException {
            this.timer.scheduleOneShot(Duration.millis(initialDelay));
            Thread.sleep(initialDelay - tolerance);
            timer.stop();
            Thread.sleep(initialDelay);
            assertThat(events.size(), is(0));
        }

        public void stopsBeforeFirstEvent(long initialDelay, long period) throws InterruptedException {
            this.timer.schedule(Duration.millis(initialDelay), Duration.millis(period));
            Thread.sleep(initialDelay - tolerance);
            timer.stop();
            Thread.sleep(initialDelay);

            assertThat(events.size(), is(0));
        }
    }
}
