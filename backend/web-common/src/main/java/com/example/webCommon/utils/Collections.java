package com.example.webCommon.utils;

import reactor.util.annotation.NonNull;

import java.util.*;

/**
 * Collection工具类，包含一些常用的方法，如判断集合是否为空、是否不为空等。
 */
public class Collections {

    public static boolean isNull(Collection<?> col) {
        return col == null;
    }

    public static boolean notNull(Collection<?> col) {
        return col != null;
    }

    public static boolean isEmpty(Collection<?> col) {
        return isNull(col) || col.isEmpty();
    }

    public static boolean notEmpty(Collection<?> col) {
        return notNull(col) && !col.isEmpty();
    }

    @SafeVarargs
    public static <T> List<T> asList(T... array) {
        return Arrays.asList(array);
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... array) {
        return new HashSet<>(asList(array));
    }

    public static <T> T[] toArray(Collection<T> col, T[] array) {
        return col.toArray(array);
    }
}
