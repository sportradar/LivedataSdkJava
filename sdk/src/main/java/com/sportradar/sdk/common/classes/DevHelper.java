/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class containing method used to help the development process
 */
public class DevHelper {

    public static boolean isDebuggerAttached() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    }

    public static <T, D extends T> List<D> ofType(List<? extends T> list, Class<D> type) {
        List<D> result = new ArrayList<>();

        for (T one : list) {
            if (type.isAssignableFrom(one.getClass())) {
                result.add((D) one);
            }
        }
        //test for gitlab
        return result;
    }
}
