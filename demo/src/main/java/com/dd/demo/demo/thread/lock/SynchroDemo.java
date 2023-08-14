package com.dd.demo.demo.thread.lock;

/**
 * @author Bryce_dd 2023/7/27 23:25
 */
public class SynchroDemo {
    private final Object obj = new Object();

    public void func00() {
        synchronized (obj) {
            System.out.println("this is func00");
        }
    }

    private synchronized void func01() {
        System.out.println("this is func01");
    }
}
