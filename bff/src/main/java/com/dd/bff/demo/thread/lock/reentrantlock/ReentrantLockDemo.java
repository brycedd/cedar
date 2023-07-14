package com.dd.bff.demo.thread.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bryce_dd 2023/7/14 00:18
 */
public class ReentrantLockDemo {
    public static int stock = 8;
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    String name = Thread.currentThread().getName();
                    if (stock > 0) {
                        System.out.println(name + ": 扣减第" + stock + "件库存成功～～～");
                        stock = stock - 1;
                    } else {
                        System.out.println(name + "扣减失败！");
                    }
                } finally {
                    lock.unlock();
                }
            }, "线程 " + i + ">>>").start();
        }
    }
}
