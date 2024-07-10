package com.example.webCommon.utils.object;

import java.util.Objects;

public class ObjectUtils {
    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    public static boolean nonNull(Object obj) {
        return Objects.nonNull(obj);
    }

    public static boolean isEquals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    public static <T> T requireNonNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    public static boolean hasSuper(Object obj) {
        return obj.getClass().getSuperclass() != Object.class;
    }

    public static boolean hasSuper(Class<?> clazz) {
        return clazz.getSuperclass() != Object.class;
    }
}
