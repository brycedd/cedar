package com.dd.demo.demo.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author Bryce_dd 2023/7/18 20:49
 */
public class MyClassLoaderTest {
    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                // defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节数组
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 若需要指定加载，重写loadClass方法即可
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("/xxx/xxx");
        Class<?> aClass = classLoader.loadClass("com.dd.demo");
        Object o = aClass.getDeclaredConstructor().newInstance();
        Method print = aClass.getDeclaredMethod("print");
        print.invoke(o);
        System.out.println(aClass.getClassLoader().getClass().getName());


    }
}
