package com.dd.common.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2022/8/31 00:37
 */
public class CommonThreadPoolUtil {

    public static ThreadPoolExecutor getTestCommonThreadPool() {
        return new ThreadPoolExecutor(
                5,
                10,
                0L,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "common-async-thread-" + r.hashCode()),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
