package com.dd.demo.demo.thread.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author Bryce_dd 2023/8/4 00:43
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {

    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
