package com.dd.demo.snowflake;

/**
 * @author Bryce_dd 2023/9/7 16:08
 */
public class SnowflakeIdWorker {

    // 记录开始时间为：2023-09-07 23:59:59
    private final long epoch = 1694102399000L;
    // 机器码占用5位
    private final long workerIdBits = 5L;
    // 数据标识id占5bit
    private final long datacenterIdBits = 5L;
    // 序列id占12bit
    private final long seqBits = 12L;
    // 最大的机器ID
    private final long maxWorkerId = ~(-1L << workerIdBits); // 2^5 - 1 = 31
    // 最大数据标识
    private final long maxDatacenterId = ~(-1L << datacenterIdBits); // 2^5 -1 = 31
    // 最大序号
    private final long maxSeqId = ~(-1L << seqBits);
    //机器id向左移12bit
    private final long workerIdShift = seqBits;
    // 数据标识id左移 12 + 5 位
    private final long datacenterIdShift = seqBits + workerIdBits;
    // 时间戳位移 12 + 5 + 5；
    private final long timestampLeftShift = seqBits + workerIdBits + datacenterIdBits;

    // 当前worker工作环境
    private long workerId;
    private long datacenterId;
    // 毫秒内序列
    private long seqId = 0L;
    // 上次生成ID的时间戳
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId < 0 || datacenterId < 0) {
            throw new IllegalArgumentException("workerId or datacenterId can not be null");
        }
        if (workerId > maxWorkerId || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("workerId or datacenterId too big");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        // 若当前时间小于上一次ID生成的时间戳，说明系统时钟回退，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回退异常");
        }
        // 若时间相同，增加seq序号
        if (timestamp == lastTimestamp) {
            seqId = (seqId + 1) & maxSeqId; // 此处与一下，若大于最大值了，会与成0
            if (seqId == 0) {
                // 溢出，阻塞等待下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            seqId = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampLeftShift) // 时间戳差值
                | (datacenterId << datacenterIdShift)  // 数据中心值
                | (workerId << workerIdShift)  // wokerId
                | seqId;


    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        System.out.println("11110111101001000111100001");
        long l1 = Long.parseLong("11110111101001000111100001", 2);
        System.out.println(l1);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.parseLong("1111111111111111111111111111111", 2));
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 2);
//        for (int i = 0; i < 1000; i++) {
//            long l = snowflakeIdWorker.nextId();
//            System.out.println(l);
//            System.out.println(Long.toBinaryString(l));
//            // 11110111101001000111100000 00010 00001 000011000001
//        }
    }


}
