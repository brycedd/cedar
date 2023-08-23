package com.dd.demo.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Bryce_dd 2023/8/15 00:44
 */
@Component
public class A implements InitializingBean, BeanPostProcessor {

    @Autowired
    private B b;


    public void func01() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>A<<<<");
    }

    public void func02() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.dd.demo.demo.spring");
        A a = context.getBean(A.class);
        a.func01();
    }

    @PostConstruct
    public void postFunc() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>do PostConstruct: A postFunc");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>do afterPropertiesSet function");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>do postProcessAfterInitialization");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
