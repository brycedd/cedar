package com.dd.bff.demo.thread.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 * @author Bryce_dd 2023/8/4 00:41
 */
public class DisruptorDemo {

    public static void main(String[] args) throws Exception {

        //创建disruptor
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                new OrderEventFactory(),
                16,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE, //单生产者
                new YieldingWaitStrategy()  //等待策略
        );
        //设置消费者用于处理RingBuffer的事件
        disruptor.handleEventsWith(new OrderEventHandler());
        disruptor.start();

        //创建ringbuffer容器
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        //创建生产者
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        //发送消息
        for(int i=0;i<20;i++){
            eventProducer.onData(i,"Fox"+i);
        }

        disruptor.shutdown();
    }
}