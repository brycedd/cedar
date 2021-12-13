package com.dd.common.thread;

import java.util.Objects;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2021/12/13 22:54
 */
public class ThreadPoolUtil {

    private static ThreadPoolExecutor executorService;

    public static void newCachedThreadPool() {
        executorService = new ThreadPoolExecutor(
                2,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    public static void submit(Runnable task) {
        if (Objects.isNull(executorService)) {
            return;
        }
        executorService.submit(task);
    }


}
