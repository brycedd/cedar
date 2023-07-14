package com.dd.bff.demo.thread.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2023/7/13 00:40
 */
public class ThreadLocalUnsafe implements Runnable{

    public static ThreadLocal<String> local = new ThreadLocal<>();


    @Override
    public void run() {
        Random random = new Random();
        local.set("dd: " + random.nextInt());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + ">>>" + local.get());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafe()).start();
        }
    }
}
