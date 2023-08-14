package com.dd.demo.demo.thread.lock.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bryce_dd 2023/7/14 00:18
 */
@Slf4j
public class ReentrantLockDemo {
    public static int stock = 8;

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test2() {
        ReentrantLock lock = new ReentrantLock();
        Condition c = lock.newCondition();
        // 线程1
        new Thread(()-> {
            lock.lock();
            try {
                log.info("第一个线程进入锁");
                System.out.println(">>>>>");
                // 计入条件队列
                c.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
                log.info("第一个线程释放锁");
            }
        }).start();
        // 线程2
        new Thread(() -> {
            lock.lock();
            try {
                log.info("第二个线程进入锁");
                System.out.println("<<<<<<");
                //唤醒第一个线程
                c.signal();
            } finally {
                lock.unlock();
                log.info("第二个线程释放锁");
            }

        }).start();
    }


    public static void test1() {
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
