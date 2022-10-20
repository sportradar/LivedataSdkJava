/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import com.sportradar.sdk.common.classes.FileSdkLogger;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class FileSkdLoggerGetAppenderTypeTest {

    private SdkLogAppenderType sdkLogAppenderType;

    public FileSkdLoggerGetAppenderTypeTest(SdkLogAppenderType sdkLogAppenderType) {
        this.sdkLogAppenderType = sdkLogAppenderType;
    }

    @Test
    public void testGetAppenderType() {
        String name = FileSdkLogger.ROOT_NS + this.sdkLogAppenderType.name();
        SdkLogAppenderType sdkLogAppenderType = FileSdkLogger.getAppenderType(name);
        assertThat(this.sdkLogAppenderType, equalTo(sdkLogAppenderType));
    }

    @Parameters
    public static Collection<Object[]> getData() {
        Object[][] data = new Object[SdkLogAppenderType.values().length][1];
        int index = 0;

        for (SdkLogAppenderType e : SdkLogAppenderType.values()) {
            data[index++][0] = e;
        }
        return Arrays.asList(data);
    }
}
