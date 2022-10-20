/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test FileSdkLogger class
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileSkdLoggerGetAppenderTypeTest.class,
        FileSdkLoggerGetLevelTest.class,
        FileSdkLoggerTestBase.class,
        MatchingFilterTest.class,
})
public class FileSdkLoggerTest {

    public FileSdkLoggerTest() {
    }
}
