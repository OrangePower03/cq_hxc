package com.example.webCommon.utils.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@DependsOn("redisUtils")
@Component
public class StringRedisUtils extends RedisUtils {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> operations;

    private ValueOperations<String, Object> getOperations() {
        return operations != null ? operations :
              (operations = redisTemplate.opsForValue());
    }

    public Object get(String key) {
        return getOperations().get(key);
    }

    public void setWithExpire(String key, Object value, long timeout) {
        setWithExpire(key, value, timeout, TimeUnit.SECONDS);
    }

    public void setWithExpire(String key, Object value, long timeout, TimeUnit timeUnit) {
        getOperations().set(key, value, timeout, timeUnit);
    }
}
