package com.dd.demo.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bryce_dd 2023/8/11 01:50
 */
public class DemoInvocationHandler implements InvocationHandler {

    private Object target;

    public DemoInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before>>>>>>>>>>>>>>>");
        Object invoke = method.invoke(target, args);
        System.out.println("after>>>>>>>>>>>>>>>>");
        return invoke;
    }
}
