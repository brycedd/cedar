package com.dd.demo.demo.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bryce_dd 2023/8/15 00:44
 */
@Component
public class A implements InitializingBean {

    @Autowired
    private B b;

    public void func01() {
        System.out.println(">>>>A<<<<");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("do afterPropertiesSet function");
    }
}
