package com.example.mybatisPlugin.plugin;

import com.example.webCommon.utils.AssertUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import reactor.util.annotation.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class AbstractExecutorInterceptor implements BaseExecutorInterceptor {
    private final Set<String> interceptMethods = Set.of("update", "query");

    @Override
    public void beforeIntercept(Invocation invocation) {
        BaseExecutorInterceptor.super.beforeIntercept(invocation);
        String methodName = invocation.getMethod().getName();
        AssertUtils.isTrue(interceptMethods.contains(methodName), "AbstractExecutorInterceptor拦截方法不符");
    }

    abstract public Object doIntercept(Invocation invocation) throws Throwable;

    @Override
    public MappedStatement getMappedStatement(Invocation invocation) {
        return (MappedStatement) invocation.getArgs()[0];
    }

    public Object getSqlArgs(Invocation invocation) {
        return invocation.getArgs()[1];
    }

    @Override
    public String getRawSql(Invocation invocation) {
        return getMappedStatement(invocation)
              .getSqlSource()
              .getBoundSql(getSqlArgs(invocation)).getSql().toLowerCase().strip();
    }

    public <T> T getSpecificSqlArgs(Invocation invocation, @NonNull Class<T> clazz) {
        Object sqlArgs = getSqlArgs(invocation);
        if(sqlArgs != null) {
            if (sqlArgs instanceof Map) {
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) sqlArgs).entrySet()) {
                    if (entry.getValue().getClass().equals(clazz)) {
                        return clazz.cast(entry.getValue());
                    }
                }
            } else if (sqlArgs.getClass().equals(clazz)) {
                return clazz.cast(sqlArgs);
            }
        }
        return null;
    }

    public <T> boolean setSpecificSqlArgs(Invocation invocation, @NonNull Class<T> clazz, T value) {
        Object sqlArgs = getSqlArgs(invocation);
        boolean res = false;
        if(sqlArgs != null) {
            if (sqlArgs instanceof Map) {
                Map<String, Object> sqlMap = (Map<String, Object>) sqlArgs;
                for (Map.Entry<String, Object> entry : (sqlMap).entrySet()) {
                    if (entry.getValue().getClass().equals(clazz)) {
                        sqlMap.put(entry.getKey(), value);
                        res = true;
                    }
                }
            } else if (sqlArgs.getClass().equals(clazz)) {
                invocation.getArgs()[1] = value;
                res = true;
            }
        }
        return res;
    }

    public <T> boolean removeSpecificSqlArgs(Invocation invocation, @NonNull Class<T> clazz) {
        Object sqlArgs = getSqlArgs(invocation);
        boolean res = false;
        if(sqlArgs != null) {
            if (sqlArgs instanceof Map) {
                Map<String, Object> sqlMap = (Map<String, Object>) sqlArgs;
                for (Map.Entry<String, Object> entry : (sqlMap).entrySet()) {
                    if (entry.getValue().getClass().equals(clazz)) {
                        sqlMap.remove(entry.getKey());
                        res = true;
                    }
                }
            } else if (sqlArgs.getClass().equals(clazz)) {
                invocation.getArgs()[1] = null;
                res = true;
            }
        }
        return res;
    }

    public String getTableName(Invocation invocation) {
        String rawSql = getRawSql(invocation);
        switch(getMappedStatement(invocation).getSqlCommandType()) {
            case SELECT: {
                int start = rawSql.indexOf("from") + 4;
                return rawSql.substring(start).strip().split("\\s+")[0];
            }
            case DELETE: {
                return rawSql.split("\\s+")[2];
            }
            case UPDATE: {
                return rawSql.split("\\s+")[1];
            }
            case INSERT: {
                return rawSql.split("\\s+")[2].split(Pattern.quote("("))[0];
            }
            default: {
                throw new RuntimeException("无法解析的sql语句，无法获取到表名");
            }
        }
    }

    @Deprecated
    public Object getId(Invocation invocation) {
        Object sqlArgs = getSqlArgs(invocation);
        if(sqlArgs != null) {
            if (sqlArgs instanceof Map) {
                Map<String, Object> argsMap = (Map<String, Object>) sqlArgs;
                for (Map.Entry<String, Object> entry : argsMap.entrySet()) {
                    String key = entry.getKey();
                    if (Pattern.matches(".*Id", key) || Pattern.matches("id", key)) {
                        return entry.getValue();
                    }
                }
            } else {
                return sqlArgs;
            }
        }
        return null;
    }
}
