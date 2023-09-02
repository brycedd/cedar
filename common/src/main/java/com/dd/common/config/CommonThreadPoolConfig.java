package com.dd.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2022/8/30 23:55
 * <p>
 * <b>AbortPolicy:</b> 直接抛出异常<br/>
 * <b>CallerRunsPolicy:</b>    在任务被拒绝添加后，会用调用execute函数的上层线程去执行被拒绝的任务(可能会阻塞主线程)<br/>
 * <b>DiscardPolicy:</b>       线程池拒绝的任务直接抛弃，不会抛异常也不会执行<br/>
 * <b>DiscardOldestPolicy:</b> 当任务拒绝添加时，会抛弃任务队列中最先加入队列的任务，再把新任务添加进去<br/>
 * <b>RejectedExecutionHandler</b> 实现 RejectedExecutionHandler 可以自定义拒绝策略<br/>
 * <b>allowCoreThreadTimeOut:</b> 开启此配置，核心线程超时关闭
 */
@Configuration
public class CommonThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor commonThreadPool() {
        int cpu = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                cpu * 2,
                50,
                30L,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "common-async-thread-" + r.hashCode()),
                new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    @Bean
    public ThreadPoolExecutor customerThreadPool() {
        return null;
    }
}
