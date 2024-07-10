package com.example.webCommon.utils;

import com.example.webCommon.constants.AppHttpCode;
import com.example.webCommon.exception.GlobalException;
import com.example.webCommon.utils.object.CollectionUtils;
import com.example.webCommon.utils.object.ObjectUtils;

import java.util.Collection;

public class AssertUtils {
    public static void nonNull(Object obj, String msg) {
        isTrue(obj != null, msg);
    }

    public static void nonNull(Object obj, AppHttpCode httpCode) {
        isTrue(obj != null, httpCode);
    }


    public static <T> void isEquals(T o1, T o2, String msg) {
        isTrue(ObjectUtils.isEquals(o1, o2), msg);
    }

    public static <T> void isEquals(T o1, T o2, AppHttpCode httpCode) {
        isTrue(ObjectUtils.isEquals(o1, o2), httpCode);
    }

    public static <T> void isEquals(T o1, T o2, int code, String msg) {
        isTrue(ObjectUtils.isEquals(o1, o2), code, msg);
    }

    public static void isEmpty(Collection<?> col, String msg) {
        isTrue(CollectionUtils.isEmpty(col), msg);
    }

    public static void isTrue(boolean ret, AppHttpCode httpCode) {
        isFalse(!ret, httpCode);
    }

    public static void isTrue(boolean ret, String msg) {
        isFalse(!ret, msg);
    }

    public static void isTrue(boolean ret, int code, String msg) {
        isFalse(!ret, code, msg);
    }

    public static void isFalse(boolean ret, String msg) {
        isFalse(ret, 500, msg);
    }

    public static void isFalse(boolean ret, int code, String msg) {
        if(ret)
            throw new GlobalException(code, msg);
    }

    public static void isFalse(boolean ret, AppHttpCode httpCode) {
        if(ret)
            throw new GlobalException(httpCode);
    }


}
