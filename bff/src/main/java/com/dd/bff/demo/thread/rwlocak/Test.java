package com.dd.bff.demo.thread.rwlocak;

/**
 * @author Bryce_dd 2023/7/30 16:26
 */
public class Test {
    public static void main(String[] args) {
        new Thread(() -> {
            CacheStampedLock cache1 = new CacheStampedLock();
            cache1.put(1, "dd");
        }).start();
        new Thread(() -> {
            CacheStampedLock cache2 = new CacheStampedLock();
            cache2.put(2, "tt");
        }).start();
        CacheStampedLock cacheStampedLock = new CacheStampedLock();
        System.out.println(cacheStampedLock.get(1));

    }
}
