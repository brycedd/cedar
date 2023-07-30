package com.dd.bff.demo.jvm;

/**
 * @author Bryce_dd 2023/7/23 15:34
 */
public class DemoClass {
    static {
        System.out.println("demoClass static====");
    }

    public int a = 1;
    public static int A = 100;
    public static final int B = 200;

    public static void staticFunc() {
        System.out.println("DemoClass staticFunc====");
    }
}
