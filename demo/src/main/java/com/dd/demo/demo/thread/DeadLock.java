package com.dd.demo.demo.thread;

/**
 * @author Bryce_dd 2023/7/11 15:28
 */
public class DeadLock {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("线程：" + Thread.currentThread().getName() + "获取到 lock1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "获取到 lock2");
                }
            }
        }, "t1>>>");
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("线程：" + Thread.currentThread().getName() + "获取到 lock2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "获取到 lock1");
                }
            }
        }, "t2>>>");
        t1.start();
        t2.start();
        System.out.println("done");
    }
}
