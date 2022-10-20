/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.proto.rategate;

import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

public class ExecutorTrace {

    private List<Runnable> runnableList;
    private List<Duration> durationList;

    public ExecutorTrace() {
        runnableList = new ArrayList<>();
        durationList = new ArrayList<>();
    }

    /**
     * Obtain the runnable list.
     *
     * @return - list of runnables added using runAfter()
     */
    public List<Runnable> getRunnableList() {
        return runnableList;
    }

    /**
     * Obtain the duration list.
     *
     * @return - list of durations added using runAfter()
     */
    public List<Duration> getDurationList() {
        return durationList;
    }

    public void runLast() {
        if (runnableList.size() > 0) {
            Runnable r = runnableList.get(runnableList.size() - 1);
            r.run();
        }
    }
}
