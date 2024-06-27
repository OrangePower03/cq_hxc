package com.example.webCommon.utils;

import com.ruoyi.common.utils.uuid.IdUtils;

/**
 * 后期学完雪花算法再考虑优化这个全局id生成器
 */
public class IdGeneratorUtils {

    public static String getUniqueId() {
        return IdUtils.fastUUID();
    }
}
