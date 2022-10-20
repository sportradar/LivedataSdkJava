/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import com.sportradar.sdk.common.timer.TimeProvider;
import org.joda.time.DateTime;

import java.util.Date;

public class RealTimeProvider extends TimeProvider {

    /**
     * Get current time.
     *
     * @return current time
     */
    @Override
    public DateTime getDateTime() {
        return DateTime.now();
    }

    @Override
    public Date getDate() {
        return DateTime.now().toDate();
    }
}
