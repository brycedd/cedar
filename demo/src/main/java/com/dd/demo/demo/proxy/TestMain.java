package com.dd.demo.demo.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author Bryce_dd 2023/8/11 01:53
 */
public class TestMain {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        func01();
        func02();
    }

    private static void func02() {
        ProxyDemoService service = (ProxyDemoService) Proxy.newProxyInstance(ProxyDemoService.class.getClassLoader(),// 加载接口的类加载器
                new Class[]{ProxyDemoService.class},// 一组接口
                new DemoInvocationHandler(new ProxyDemoServiceImpl()));// 自定义handler
        service.doFunc();

    }

    private static void func01() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 2、获取动态代理类
        Class proxyClazz = Proxy.getProxyClass(ProxyDemoService.class.getClassLoader(),ProxyDemoService.class);
        // 3、获得代理类的构造函数，并传入参数类型InvocationHandler.class
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        // 4、通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
        ProxyDemoService ProxyDemoService1 = (ProxyDemoService) constructor.newInstance(new DemoInvocationHandler(new ProxyDemoServiceImpl()));
        // 5、通过代理对象调用目标方法
        ProxyDemoService1.doFunc();
    }
}
