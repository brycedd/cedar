package com.dd.bff.demo.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bryce_dd 2023/7/13 16:21
 */
public class CounterReentrantLock {

    private final ReentrantLock lock = new ReentrantLock();

    public void recursiveCall(int num) {
        lock.lock();
        try {
            if (num == 0) {
                return;
            }
            System.out.println(Thread.currentThread().getName() + ">>: 递归执行，num=" + num);
            recursiveCall(num - 1);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        CounterReentrantLock count = new CounterReentrantLock();
        int num = 10;
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                count.recursiveCall(num);
            }, "thread: " + i).start();
        }
    }
}
