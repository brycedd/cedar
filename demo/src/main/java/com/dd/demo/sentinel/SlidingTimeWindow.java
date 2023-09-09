package com.dd.demo.sentinel;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Bryce_dd 2023/9/9 23:56
 */
public class SlidingTimeWindow {

    Long counter = 0L;

    volatile boolean isBlock = false;

    LinkedList<Long> slots = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        SlidingTimeWindow timeWindow = new SlidingTimeWindow();

        new Thread(() -> {
            try {
                timeWindow.doCheck();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        while (true) {
            if (timeWindow.isBlock) {
                System.out.println("被限流了,暂停请求>>>>");
                Thread.sleep(100);
            } else {
                timeWindow.counter++;
                Thread.sleep(new Random().nextInt(15));
            }
        }
        /*while (!timeWindow.isBlock) {
            timeWindow.counter++;
            Thread.sleep(new Random().nextInt(15));
        }*/
    }

    private void doCheck() throws InterruptedException {
        while (true) {
            Thread.sleep(100);
            slots.addLast(counter);
            if (slots.size() > 10) {
                slots.removeFirst();
            }
            // 比较最后一个和第一个，相差100以上限流
            if ((slots.peekLast() - slots.peekFirst()) > 100) {
                System.out.println(">>>>限流开始: counter: " + counter + " size: " + slots.size());
                // 限流标识改为true，开始限流
                isBlock = true;
            } else {
                // 限流标识改为false，结束限流
                System.out.println("<<<<限流关闭: counter: " + counter + " size: " + slots.size());
                isBlock = false;

            }
        }
    }
}
