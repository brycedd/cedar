package com.dd.product.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Bryce_dd 2023/8/15 02:18
 */
@Intercepts({
        @Signature(type = Executor.class,method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class,method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class })})
@Slf4j
@Component
public class ExecutorPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Executor Plugin 拦截 :"+invocation.getMethod());
        Object[] queryArgs = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) queryArgs[0];
        //获取 ParamMap
        MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) queryArgs[1];
        // 获取SQL
        BoundSql boundSql = mappedStatement.getBoundSql(paramMap);
        String sql = boundSql.getSql();
        log.info("==> ORIGIN SQL: "+sql);
        long startTime = System.currentTimeMillis();
        Configuration configuration = mappedStatement.getConfiguration();
        String sqlId = mappedStatement.getId();

        // 执行原方法
        Object proceed = invocation.proceed();
        long endTime=System.currentTimeMillis();
        long time = endTime - startTime;
        printSqlLog(configuration,boundSql,sqlId,time);
        return proceed;
    }

    public static void printSqlLog(Configuration configuration, BoundSql boundSql, String sqlId, long time){
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql= boundSql.getSql().replaceAll("[\\s]+", " ");
        StringBuffer sb=new StringBuffer("==> PARAM:");
        if (!parameterMappings.isEmpty() && parameterObject!=null){
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", parameterObject.toString());
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        String parameterValue = obj.toString();
                        sql = sql.replaceFirst("\\?", parameterValue);
                        sb.append(parameterValue).append("(").append(obj.getClass().getSimpleName()).append("),");
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        String parameterValue = obj.toString();
                        sql = sql.replaceFirst("\\?", parameterValue);
                        sb.append(parameterValue).append("(").append(obj.getClass().getSimpleName()).append("),");
                    }
                }
            }
            sb.deleteCharAt(sb.length()-1);
        }
        log.info("==> SQL:"+sql);
        log.info(sb.toString());
        log.info("==> SQL TIME:"+time+" ms");
    }
}
