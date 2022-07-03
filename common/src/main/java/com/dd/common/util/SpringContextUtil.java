package com.dd.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Bryce_dd 2022/3/20 16:01
 */
@Slf4j
@Component("SpringContextUtil-core")
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static ClassLoader classLoader;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == SpringContextUtil.applicationContext) {
            SpringContextUtil.applicationContext = applicationContext;
            SpringContextUtil.classLoader = Thread.currentThread().getContextClassLoader();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        if (!isInit()) {
            return null;
        }
        return (T) SpringContextUtil.applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        if (!isInit()) {
            return null;
        }
        return SpringContextUtil.applicationContext.getBean(clazz);
    }

    public static <T> Map<String, T> getBeanByClass(Class<T> tClass) {
        if (!isInit()) {
            return null;
        }
        return SpringContextUtil.applicationContext.getBeansOfType(tClass);
    }

    public static boolean isInit() {
        if (null == SpringContextUtil.applicationContext) {
            return false;
        }
        return true;
    }

    public static ClassLoader getClassLoader() {
        return classLoader;
    }
}
