package com.dd.bff.demo.thread.exception;

/**
 * @author Bryce_dd 2023/7/11 16:18
 * 通过设置线程UncaughtExceptionHandler 实现发生未知异常后的自定义处理
 */
public class HandlerExceptionDemo implements Runnable{
    @Override
    public void run() {
        int a = 1/0;
    }

    public static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("执行异常！" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HandlerExceptionDemo demo = new HandlerExceptionDemo();
        Thread thread = new Thread(demo);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
    }
}
