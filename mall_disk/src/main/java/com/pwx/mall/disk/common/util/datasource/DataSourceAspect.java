package com.pwx.mall.disk.common.util.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

//@Component("manyDataSourceAspect")
public class DataSourceAspect {  
  
    public void before(JoinPoint point)
    {
        //清除上一次的自定义配置
        DynamicDataSourceHolder.clearJdbcType();
        
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m
                        .getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(data.value());
                System.out.println(data.value());
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    } 
}