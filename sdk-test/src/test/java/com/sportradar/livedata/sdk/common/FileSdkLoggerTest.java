/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test FileSdkLogger class
 */
@Suite
@SelectClasses({
        FileSkdLoggerGetAppenderTypeTest.class,
        FileSdkLoggerGetLevelTest.class,
        FileSdkLoggerTestBase.class,
        MatchingFilterTest.class,
})
public class FileSdkLoggerTest {

    public FileSdkLoggerTest() {
    }
}
