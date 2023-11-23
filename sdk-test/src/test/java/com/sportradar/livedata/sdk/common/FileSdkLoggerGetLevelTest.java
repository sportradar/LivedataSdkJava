/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.classes.FileSdkLogger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class FileSdkLoggerGetLevelTest {

    @ParameterizedTest
    @MethodSource("getData")
    void testGetLevel(Level inputLevel) {
        Level outPutLevel = FileSdkLogger.getLevel(inputLevel.levelStr);
        assertThat(outPutLevel, equalTo(inputLevel));
    }

    static Collection<Object[]> getData() {
        Object[][] data = new Object[][]{
                {Level.ALL},
                {Level.TRACE},
                {Level.DEBUG},
                {Level.INFO},
                {Level.WARN},
                {Level.ERROR},
                {Level.OFF},
        };
        return Arrays.asList(data);
    }
}
