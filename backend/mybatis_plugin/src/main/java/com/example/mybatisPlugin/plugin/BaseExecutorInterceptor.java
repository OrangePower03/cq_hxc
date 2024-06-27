package com.example.mybatisPlugin.plugin;

import com.example.webCommon.utils.AssertUtils;
import com.ruoyi.common.annotation.SqlCacheDisable;
import com.ruoyi.common.annotation.SqlCacheEnable;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.*;

public interface BaseExecutorInterceptor extends Interceptor {
    MappedStatement getMappedStatement(Invocation invocation);

    Object getSqlArgs(Invocation invocation);

    String getRawSql(Invocation invocation);

    default RowBounds getRowBounds(Invocation invocation) {
        return isSelectType(invocation) ? (RowBounds) invocation.getArgs()[2] : RowBounds.DEFAULT;
    }

    Object doIntercept(Invocation invocation) throws Throwable;

    default void beforeIntercept(Invocation invocation) {
        Class<?> targetClass = invocation.getTarget().getClass();
        Set<Class<?>> interfaces = new HashSet<>();
        while(targetClass != Object.class) {
            interfaces.addAll(Arrays.asList(targetClass.getInterfaces()));
            targetClass = targetClass.getSuperclass();
        }
        AssertUtils.isTrue(interfaces.contains(Executor.class), "AbstractExecutorInterceptor拦截类不符");
    }

    default void afterIntercept(Invocation invocation, Object res) {
    }

    @Override
    default Object intercept(Invocation invocation) throws Throwable {
        Object res = null;
        beforeIntercept(invocation);
        res = doIntercept(invocation);
        afterIntercept(invocation, res);
        return res;
    }

    default boolean isSelectType(Invocation invocation) {
        return SqlCommandType.SELECT.equals(getSqlCommandType(invocation));
    }

    // 查看是否开启了缓存，通过注解或者配置xml文件开启缓存
    default boolean isUseCache(Invocation invocation) {
        // 检查方法是否被 @SqlCacheEnable 和 @SqlCacheDisable 注解标记
        String methodId = getMappedStatement(invocation).getId();
        String className = methodId.substring(0, methodId.lastIndexOf("."));
        String methodName = methodId.substring(methodId.lastIndexOf(".") + 1);
        try {
            Class<?> clazz = Class.forName(className);
            SqlCacheDisable sqlCacheDisable = clazz.getAnnotation(SqlCacheDisable.class);
            if(sqlCacheDisable != null) return false;

            SqlCacheEnable sqlCacheEnable = clazz.getAnnotation(SqlCacheEnable.class);

            if(sqlCacheEnable != null) {
                Object sqlArgs = getSqlArgs(invocation);
                if(sqlArgs != null) {
                    Method method;
                    if (sqlArgs instanceof Map) {
                        List<Class<?>> classList = new ArrayList<>();
                        int i = 1;
                        Map<String, Object> map = (Map<String, Object>) sqlArgs;
                        while (map.containsKey("param" + i)) {
                            classList.add(map.get("param" + i++).getClass());
                        }
                        method = clazz.getMethod(methodName, classList.toArray(new Class[0]));
                    } else {
                        method = clazz.getMethod(methodName, sqlArgs.getClass());
                    }
                    if (method.getAnnotation(SqlCacheDisable.class) != null) return false;
                }
                // 有这个标记后必须不能把这个方法给排除了，排除后不再判断xml的
                return Arrays.stream(sqlCacheEnable.exclude()).noneMatch(methodName::equals);
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        // 没有对应的注解，查看xml是否配置了useCache
        return getMappedStatement(invocation).isUseCache() || getMappedStatement(invocation).isFlushCacheRequired();
    }

    default SqlCommandType getSqlCommandType(Invocation invocation) {
        return getMappedStatement(invocation).getSqlCommandType();
    }
}
