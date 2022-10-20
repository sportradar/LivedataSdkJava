/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.classes.FileSdkLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class FileSdkLoggerGetLevelTest {

    private Level level;

    public FileSdkLoggerGetLevelTest(Level level) {
        this.level = level;
    }

    @Test
    public void testGetLevel() {
        Level inputLevel = this.level;
        Level outPutLevel = FileSdkLogger.getLevel(inputLevel.levelStr);
        assertThat(outPutLevel, equalTo(inputLevel));
    }

    @Parameters
    public static Collection<Object[]> getData() {
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
