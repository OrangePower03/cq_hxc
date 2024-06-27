package com.example.webCommon.utils;


import java.util.Collection;
import java.util.Objects;

public class AssertUtils {
    public static void nonNull(Object obj, String msg) {
        isTrue(obj != null, msg);
    }

    public static void isTrue(boolean ret, String msg) {
        isFalse(!ret, msg);
    }

    public static void isFalse(boolean ret, String msg) {
        if(ret) throw new RuntimeException(msg);
    }


    public static <T> void isEquals(T o1, T o2, String msg) {
        isTrue(Objects.equals(o1, o2), msg);
    }

    public static void isEmpty(Collection<?> col, String msg) {
        isTrue(Collections.isEmpty(col), msg);
    }
}
