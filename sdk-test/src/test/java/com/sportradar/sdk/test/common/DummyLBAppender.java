/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class DummyLBAppender extends AppenderBase<ILoggingEvent> {

    public List<ILoggingEvent> list = new ArrayList<>();
    public List<String> stringList = new ArrayList<>();

    private PatternLayout layout;

    public DummyLBAppender() {
        this(null);
    }

    public DummyLBAppender(PatternLayout layout) {
        this.layout = layout;
    }

    protected void append(ILoggingEvent e) {
        list.add(e);
        if (layout != null) {
            String s = layout.doLayout(e);
            stringList.add(s);
        }
    }
}
