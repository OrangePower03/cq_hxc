package com.example.webCommon.utils;

/**
 * 字符串工具类
 */
public class Strings {
    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean nonNull(String str) {
        return str != null;
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || str.isEmpty();
    }

    public static boolean notEmpty(String str) {
        return nonNull(str) && !str.isEmpty();
    }

}
