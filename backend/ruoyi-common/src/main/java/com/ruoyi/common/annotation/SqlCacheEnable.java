package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记mapper中所有的方法都开启缓存
 * @exclude: 排除的mapper方法名
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlCacheEnable {
    String[] exclude() default {}; // 排除的mapper方法名
}
