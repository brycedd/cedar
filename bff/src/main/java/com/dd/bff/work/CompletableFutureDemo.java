package com.dd.bff.work;

import com.dd.common.util.CommonThreadPoolUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Bryce_dd 2022/8/30 23:20
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor commonThreadPool = CommonThreadPoolUtil.getTestCommonThreadPool();
        System.out.println("start one>>>>>>>>>>>");
        long t1 = System.currentTimeMillis();
        CompletableFuture.allOf(
                        CompletableFuture.runAsync(CompletableFutureDemo::doSomething, commonThreadPool),
                        CompletableFuture.runAsync(CompletableFutureDemo::doSomething2, commonThreadPool),
                        CompletableFuture.runAsync(CompletableFutureDemo::doSomething3, commonThreadPool))
                .join();
        System.out.println("第一次耗时：" + (System.currentTimeMillis() - t1));
        System.out.println("start two>>>>>>>>>>>");
        long t2 = System.currentTimeMillis();
        doSomething();
        doSomething2();
        doSomething3();
        System.out.println("第二次耗时：" + (System.currentTimeMillis() - t2));
    }

    public static void doSomething() {
        try {
            Thread.sleep(10 * 1000);
            System.out.println(Thread.currentThread().getName() + " :doSomething done >>>>>>>>>>");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSomething2() {
        try {
            Thread.sleep(5 * 1000);
            System.out.println(Thread.currentThread().getName() + " :doSomething2 done >>>>>>>>>>");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSomething3() {
        try {
            Thread.sleep(10 * 1000);
            System.out.println(Thread.currentThread().getName() + " :doSomething3 done >>>>>>>>>>");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
