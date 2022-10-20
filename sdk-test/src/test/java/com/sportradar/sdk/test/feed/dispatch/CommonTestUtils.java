package com.sportradar.sdk.test.feed.dispatch;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonTestUtils {

    private static Field getFieldRecursively(Class<?> clazz, String fieldName) {
        Class<?> klass = clazz;
        Field field = null;
        do {
            try {
                field = klass.getDeclaredField(fieldName);
                klass = null;
            } catch (NoSuchFieldException e) {
                klass = klass.getSuperclass();
            }
        } while (klass != null);

        if (field instanceof Field) {
            return field;
        }
        return null;
    }

    public static Field setFieldValue(Class<?> clazz, String fieldName, Object obj, Object val) throws Exception {
        Field field = getFieldRecursively(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(obj, val);
        }
        return field;
    }

    public static Object getFieldValue(Class<?> clazz, String fieldName, Object object) throws Exception {
        Field field = getFieldRecursively(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    public static <T> Object invokeMethod(Class klass, T object, String methodName, Object... arguments) {
        Method[] methods = klass.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                method.setAccessible(true);
                try {
                    return method.invoke(object, arguments);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
