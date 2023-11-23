/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common;

import com.sportradar.livedata.sdk.common.classes.FileSdkLogger;
import com.sportradar.livedata.sdk.common.enums.SdkLogAppenderType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileSkdLoggerGetAppenderTypeTest {

    @ParameterizedTest
    @MethodSource("getData")
    void testGetAppenderType(SdkLogAppenderType expected) {
        String name = FileSdkLogger.ROOT_NS + expected.name();
        SdkLogAppenderType sdkLogAppenderType = FileSdkLogger.getAppenderType(name);
        assertThat(expected, equalTo(sdkLogAppenderType));
    }

    static Collection<Object[]> getData() {
        Object[][] data = new Object[SdkLogAppenderType.values().length][1];
        int index = 0;

        for (SdkLogAppenderType e : SdkLogAppenderType.values()) {
            data[index++][0] = e;
        }
        return Arrays.asList(data);
    }
}
