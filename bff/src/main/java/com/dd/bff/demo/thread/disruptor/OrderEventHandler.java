package com.dd.bff.demo.thread.disruptor;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2023/8/4 00:46
 * 创建 OrderEventHandler 类，并实现 EventHandler<T> ，作为消费者。
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        // TODO 消费逻辑
        TimeUnit.SECONDS.sleep(1);
        System.out.println("消费者获取数据value:"+ event.getValue()+",name:"+event.getName());
    }
}