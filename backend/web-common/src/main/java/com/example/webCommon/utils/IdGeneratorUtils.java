package com.example.webCommon.utils;


import java.util.UUID;

/**
 * 后期学完雪花算法再考虑优化这个全局id生成器
 */
public class IdGeneratorUtils {

    public static String getGeneratorId() {
        return UUID.randomUUID().toString();
    }
}
