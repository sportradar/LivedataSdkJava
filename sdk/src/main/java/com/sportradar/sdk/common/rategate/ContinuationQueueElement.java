/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.rategate;

public class ContinuationQueueElement {

    private int num;
    private Runnable runnable;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public ContinuationQueueElement(int num, Runnable runnable) {
        this.num = num;
        this.runnable = runnable;
    }

}
