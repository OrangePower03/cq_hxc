package com.example.webCommon.utils.object;

public class StringUtils extends ObjectUtils {
    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        return isNull(str) || str.isEmpty();
    }

    public static boolean nonEmpty(String str) {
        return !isEmpty(str);
    }
}
