package com.dd.demo.demo.thread.exception;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2023/7/11 16:23
 */
public class AfterExecutorDemo implements Runnable{
    @Override
    public void run() {
        int a = 1/0;
    }

    public static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t != null) {
                System.out.println("线程执行发生异常！" + t.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        AfterExecutorDemo demo = new AfterExecutorDemo();
        MyThreadPoolExecutor myThreadPoolExecutor = new MyThreadPoolExecutor(1,
                1,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        myThreadPoolExecutor.execute(demo);
        System.out.println("done");
    }
}
