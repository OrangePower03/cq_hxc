package com.example.webCommon.utils.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@DependsOn("redisUtils")
@Component
public class HashRedisUtils extends RedisUtils {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> operations;

    private HashOperations<String, String, Object> getOperations() {
        return operations != null ? operations :
              (operations = redisTemplate.opsForHash());
    }
}
