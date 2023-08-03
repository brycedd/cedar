package com.dd.bff.demo.thread.disruptor;

import lombok.Data;

/**
 * @author Bryce_dd 2023/8/4 00:42
 */
@Data
public class OrderEvent {
    private long value;
    private String name;
}
