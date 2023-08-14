package com.dd.demo.demo.base;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Bryce_dd 2023/8/13 01:06
 */
@Slf4j
public class NumberHL {

    public volatile Long flag = 0L;
    private static final Long MINUS_ONE = -1L;
    private static final Long flagOffset;
    private static final Unsafe unsafe;

    static {
        // 初始化unsafe类
        unsafe = reflectGetUnsafe();
        // 获取flag字段位置
        Long flagOffsetTemp = null;
        try {
            flagOffsetTemp = unsafe.objectFieldOffset
                    (NumberHL.class.getDeclaredField("flag"));
        } catch (Exception e) {
            log.error("getFlagOffset error");
        }
        flagOffset = flagOffsetTemp;
    }

    // 构造器
    public NumberHL() {
        if (Objects.isNull(unsafe)) {
            flag = 0L;
        }
        initFlag();
        if (flag == null)
            flag = 0L;
    }

    public Long getFlag() {
        return flag;
    }

    public void setHighNumber(Long num) {
        if (flag == 0)
            flag = num << 32;
        else
            // 重置高位并赋值
            flag = (flag & (MINUS_ONE >>> 32)) | (num << 32);
    }

    public void setLowNumber(Long num) {
        if (flag == 0)
            flag = num;
        else
            // 重置低位并赋值
            flag = (flag & (MINUS_ONE << 32)) | num;
    }

    public void lowAdd(Long num) {
        flag += num;
    }

    public boolean casLowUpdate(Long expect, Long update) {
        return unsafe.compareAndSwapLong(this, flagOffset, expect, update);
    }

    public void highAdd(Long num) {
        if (flag == 0)
            flag &= num << 32;
        else {
            // 取出高位再计算再设置回去
            Long highNumber = getHighNumber();
            setHighNumber(highNumber + num);
        }
    }

    public Long getHighNumber() {
        return flag >> 32;
    }

    public Long getLowNumber() {
        return flag & (MINUS_ONE >>> 32);
    }

    //unsafe function

    public void initFlag() {
        // 初始化标记值
        if (flagOffset != null)
            unsafe.putLong(this, flagOffset, 0L);
        else
            this.flag = 0L;
    }

    public Long unsafeGetLowNumber() {
        long aLong1 = unsafe.getLong(this, flagOffset);
        return aLong1 & (MINUS_ONE >>> 32);
    }

    public Long unsafeGetFlag() {
        return unsafe.getLong(this, flagOffset);
    }

    public static Unsafe reflectGetUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            // ignore
            return null;
        }
    }

}
