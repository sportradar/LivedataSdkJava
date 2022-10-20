package com.sportradar.sdk.common.classes;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p> Basically this is versions of 2 methods: etn (empty to null) and nte (null to
 * empty). If you are calling Nulls.nte(), compiler will find needed version
 * itself by collection you had passed. For example: Nulls.nte(new List()) will
 * call public static &lt;T&gt; List&lt;T&gt; nte(final List&lt;T&gt; list).
 * </p>
 * As a result, code: if(list != null){ for(obj : list){} } will turn into:
 * for(obj : Nulls.nte(list)){}
 */
public final class Nulls {

    private static final String EMPTY_STRING = "";

    public static final String[] EMPTY_STRINGS = {};

    public static final Object[] EMPTY_OBJECTS = {};

    public static final byte[] EMPTY_BYTES = {};

    public static final int[] EMPTY_INTS = {};

    public static final long[] EMPTY_LONGS = {};

    private Nulls() {
        super();
    }

    public static <T extends Number> T nullToZero(T value) {
        return value != null ? value : (T)Integer.valueOf(0);
    }

    /**
     * Convert {@code null} string to empty string. Non-null string returned as
     * is.
     *
     * @param str test value
     * @return empty or given value.
     */
    public static String nte(final String str) {
        return nullToEmpty(str);
    }

    public static String nullToEmpty(final String str) {
        return firstNonNull(str, EMPTY_STRING);
    }

    public static String[] nte(final String[] strings) {
        return nullToEmpty(strings);
    }

    public static String[] nullToEmpty(final String[] strings) {
        return firstNonNull(strings, EMPTY_STRINGS);
    }

    public static Object[] nte(final Object[] objects) {
        return nullToEmpty(objects);
    }

    public static Object[] nullToEmpty(final Object[] objects) {
        return firstNonNull(objects, EMPTY_OBJECTS);
    }

    public static byte[] nte(final byte[] bytes) {
        return nullToEmpty(bytes);
    }

    public static byte[] nullToEmpty(final byte[] bytes) {
        return firstNonNull(bytes, EMPTY_BYTES);
    }

    public static int[] nte(final int[] ints) {
        return nullToEmpty(ints);
    }

    public static int[] nullToEmpty(final int[] ints) {
        return firstNonNull(ints, EMPTY_INTS);
    }

    public static long[] nte(final long[] longs) {
        return nullToEmpty(longs);
    }

    public static long[] nullToEmpty(final long[] longs) {
        return firstNonNull(longs, EMPTY_LONGS);
    }

    public static <T> Collection<T> nte(final Collection<T> collection) {
        return nullToEmpty(collection);
    }

    public static <T> Collection<T> nullToEmpty(final Collection<T> collection) {
        return firstNonNull(collection, Collections.<T>emptyList());
    }

    public static <T> List<T> nte(final List<T> list) {
        return nullToEmpty(list);
    }

    public static <T> List<T> nullToEmpty(final List<T> list) {
        return firstNonNull(list, Collections.<T>emptyList());
    }

    public static <T> Set<T> nte(final Set<T> set) {
        return nullToEmpty(set);
    }

    public static <T> Set<T> nullToEmpty(final Set<T> set) {
        return firstNonNull(set, Collections.<T>emptySet());
    }

    public static <K, V> Map<K, V> nte(final Map<K, V> map) {
        return nullToEmpty(map);
    }

    public static <K, V> Map<K, V> nullToEmpty(final Map<K, V> map) {
        return firstNonNull(map, Collections.<K, V>emptyMap());
    }

    //

    public static String etn(final String str) {
        return emptyToNull(str);
    }

    public static String emptyToNull(final String str) {
        return str == null || str.isEmpty() ? null : str;
    }

    public static <T> List<T> etn(final List<T> list) {
        return emptyToNull(list);
    }

    public static <T> List<T> emptyToNull(final List<T> list) {
        return list == null || list.isEmpty() ? null : list;
    }

    public static <T> Set<T> etn(final Set<T> set) {
        return emptyToNull(set);
    }

    public static <T> Set<T> emptyToNull(final Set<T> set) {
        return set == null || set.isEmpty() ? null : set;
    }

    public static <K, V> Map<K, V> etn(final Map<K, V> map) {
        return emptyToNull(map);
    }

    public static <K, V> Map<K, V> emptyToNull(final Map<K, V> map) {
        return map == null || map.isEmpty() ? null : map;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean hasNulls(final T... objs) {
        if (objs == null) {
            return true;
        }
        for (final T obj : objs) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean hasNotNulls(final T... objs) {
        if (objs == null) {
            return false;
        }
        for (final T obj : objs) {
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    public static <T> T firstNonNullIfExists(T... values) {
        for(T value : values){
            if(value != null){
                return value;
            }
        }
        return null;
    }

    public static <T> T firstNonNull(final T first, final T second) {
        return first != null ? first : checkNotNull(second);
    }

    public static <T> T checkNotNull(T reference) {
        checkArgument(reference != null);
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        checkArgument(reference != null, errorMessage);
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
        checkArgument(reference != null, errorMessageTemplate, errorMessageArgs);
        return reference;
    }

}
