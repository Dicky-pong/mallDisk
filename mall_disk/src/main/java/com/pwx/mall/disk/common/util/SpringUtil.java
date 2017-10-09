package com.pwx.mall.disk.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringUtil implements ApplicationContextAware {  
    
    private static ApplicationContext applicationContext;  
      
    @Override  
    public void setApplicationContext(ApplicationContext context)  
        throws BeansException {  
        SpringUtil.applicationContext = context;  
    }
    
    /**
     * 
     * TODO 根据注入service获取服务.
     * @param name
     * @return
     */
    public static Object getBean(String name){  
        return applicationContext.getBean(name);  
    }  
}  