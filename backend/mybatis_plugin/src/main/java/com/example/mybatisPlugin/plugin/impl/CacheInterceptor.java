package com.example.mybatisPlugin.plugin.impl;

import com.example.mybatisPlugin.plugin.AbstractExecutorInterceptor;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.AssertUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Intercepts(
        {
            @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
            ),
//            @Signature(
//                    type = Executor.class,
//                    method = "query",
//                    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
//            ),
            @Signature(
                    type = Executor.class,
                    method = "query",
                    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
            )
        }
)
public class CacheInterceptor extends AbstractExecutorInterceptor {
    private RedisCache redisCache;
    private final int MAX_COUNT = 5;

    @Override
    public Object doIntercept(Invocation invocation) throws Throwable {
        // 查询语句，将查询到的数据存入redis中，键是由sql和参数组成
        // 这里需要上锁，避免缓存穿透和缓存雪崩
        // 只有使用了缓存才会在非查询方式下将缓存清空
        if(isUseCache(invocation)) {
            if(redisCache == null) {
                redisCache = SpringUtils.getBean(RedisCache.class);
            }
            Object res = null;
            // 判断是否是select语句，只有查询才会查缓存
            if(isSelectType(invocation)) {
                List<Object> cache = selectFromCache(invocation);
                if (cache != null) return cache;

                // 在查询模式下且缓存不存在时才会上锁，避免并发多线程查询数据库
                String lockKey = CacheConstants.DATABASE_CACHE_LOCK +
                        getKey(invocation) +
                        getHashKey(invocation);
                if (redisCache.tryLock(lockKey)) {
                    try {
                        // 执行数据库操作
                        res = invocation.proceed();
                        updateCache(invocation, res);
                        return res;
                    } finally {
                        redisCache.unlock(lockKey);
                    }
                }
                // 查询状态下无法获取锁，在外面等着就好，自旋等待缓存的创建
                else {
                    int count = 0;
                    while (count++ < MAX_COUNT && (res = selectFromCache(invocation)) == null) {
                        // 自旋等待操作数据库的线程插入缓存
                        Thread.sleep(100);
                    }
                    AssertUtils.isTrue(count >= MAX_COUNT, "服务超时，请稍后再试");
                    return res;
                }
            }
            // 非查询模式下，直接清空缓存，不需要设锁
            else  {
                res = invocation.proceed();
                updateCache(invocation, res);
                return res;
            }
        }
        return invocation.proceed();
    }

    private List<Object> selectFromCache(Invocation invocation) {
        return redisCache.hashGetWithExpire(getKey(invocation), getHashKey(invocation), CacheConstants.DATABASE_CACHE_EXPIRE_TIME);
    }

    private void updateCache(Invocation invocation, Object res) {
        String key = getKey(invocation);
        if(isSelectType(invocation)) {
            redisCache.hashPutWithTimeout(key, getHashKey(invocation), (List<?>) res, CacheConstants.DATABASE_CACHE_EXPIRE_TIME);
        }
        else {
            // 只有在成功修改或删除等操作才执行缓存清空操作
            if((Integer)res > 0) redisCache.deleteObject(key);
        }
    }

    private String getKey(Invocation invocation) {
        return CacheConstants.DATABASE_CACHE + getTableName(invocation) + ":";
    }

    private String getHashKey(Invocation invocation) {
        RowBounds rowBounds = getRowBounds(invocation);
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        return getRawSql(invocation) + ":" + getSqlArgs(invocation) + ":[" + offset + "+" + limit + "]";
    }
}
