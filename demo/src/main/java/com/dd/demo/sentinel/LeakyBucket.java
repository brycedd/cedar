package com.dd.demo.sentinel;

/**
 * @author Bryce_dd 2023/9/10 01:16
 */
public class LeakyBucket {

    public long timeStamp = System.currentTimeMillis();
    public long capacity;
    public long rate;
    public long water;

    public boolean limit() {
        long now = System.currentTimeMillis();
        water = Math.max(0, water - ((now - timeStamp)/1000) * rate);
        timeStamp = now;
        if ((water + 1) < capacity) {
            // 尝试加水
            water += 1;
            return true;
        } else {
            // 水满了，拒绝
            return false;
        }
    }
}
