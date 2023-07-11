package com.dd.bff.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bryce_dd 2023/7/10 13:08
 */
public class MainTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);

        ReentrantLock reentrantLock = new ReentrantLock(true);

        Callable<String> callable = () -> {
            Thread.sleep(1000);
            String name = Thread.currentThread().getName();
            System.out.println("thread:" + name + ":call >>>>>>1");
            return "success";
        };
        FutureTask<String> future = new FutureTask<>(callable);
        Thread thread = new Thread(future);
        thread.start();
        System.out.println("main 01");
        String result = future.get();
        System.out.println("call result: " + result);
        System.out.println("main 02");
    }
}
