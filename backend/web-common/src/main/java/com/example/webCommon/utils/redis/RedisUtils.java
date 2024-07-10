package com.example.webCommon.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("redisUtils")
public class RedisUtils {
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    public boolean expire(String key, long timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TimeUnit.SECONDS));
    }

    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

}