package com.dd.demo.demo.base;

import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;

/**
 * @author Bryce_dd 2023/7/31 19:48
 */
public class BaseDemo {

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
//        bitwiseTest();
//        move();
//        testNumberHL();
//        testNumberHL2();
//        testNumberHL3();
        testNumberCas();

    }

    public static void testNumberCas() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        NumberHL numberHL = new NumberHL();
        Runnable runnable = () -> {
            int i = 0;
            while (i < 100) {
                Long aLong = numberHL.unsafeGetFlag();
                Long update = aLong + 1L;
                if (numberHL.casLowUpdate(aLong, update)) {
                    i++;
                }
            }
            countDownLatch.countDown();
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        countDownLatch.await();
        System.out.println(numberHL.getLowNumber());
    }

    public static void testNumberHL3() {
        int i = 0;
        NumberHL numberHL = new NumberHL();
        while (i < 100) {
            Long aLong = numberHL.unsafeGetFlag();
            Long update = aLong + 1L;
            if (numberHL.casLowUpdate(aLong, update)) {
                i++;
            }
        }
        Unsafe unsafe = NumberHL.reflectGetUnsafe();
        long aLong = unsafe.getLong(numberHL, 12L);
    }

    public static void unsafeTest() throws NoSuchFieldException {
        NumberHL numberHL = new NumberHL();
        Unsafe unsafe = NumberHL.reflectGetUnsafe();
        long l = unsafe.objectFieldOffset(NumberHL.class.getDeclaredField("flag"));
        System.out.println(l);
        unsafe.putLong(numberHL, l, 10L);
        System.out.println(unsafe.getLong(numberHL, l));
    }

    public static void testNumberHL2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        NumberHL numberHL = new NumberHL();
        Runnable runnable = () -> {
            int i = 0;
            while (i < 100) {
                Long lowNumber = numberHL.getLowNumber();
                lowNumber += 1;
                numberHL.setLowNumber(lowNumber);
                i++;
            }
            countDownLatch.countDown();
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        countDownLatch.await();
        System.out.println(numberHL.getLowNumber());
    }

    public static void testNumberHL() {
        NumberHL numberHL = new NumberHL();
        numberHL.setHighNumber(2L);
        numberHL.setLowNumber(2L);
        System.out.println(Long.toBinaryString(numberHL.getFlag()));
        numberHL.highAdd(100L);
        numberHL.lowAdd(50L);
        System.out.println(Long.toBinaryString(numberHL.getFlag()));
        System.out.println(numberHL.getHighNumber());
        System.out.println(numberHL.getLowNumber());
        System.out.println(numberHL.getFlag());
    }

    public static void bitwiseTest() {
        int a = -10;
        System.out.println(10 >>> 1);
        System.out.println(10 / 2);
    }

    public static void move() {
        Long a = 2L;
        a <<= 32;
        System.out.println("a: " + Long.toBinaryString(a));
        Long b = 100L;
        System.out.println("b: " + Long.toBinaryString(b));
        Long c = a | b;
        System.out.println("c: " + Long.toBinaryString(c));
        System.out.println("c: " + c);
        c++;
        System.out.println("c: " + c);
        // 从c中获取a、b的值
        Long cToa = c >> 32;
        System.out.println("获取高位：c to a： " + cToa);
        // 高位加一
        cToa++;
        // 重置高位
        c = c & (-1 >>> 32);
        c = c | (cToa << 32);
        System.out.println("获取高位：c to a： " + (c >> 32));
        Long cTob = (-1L >>> 32) & c;
        System.out.println("-1 :" + Long.toBinaryString(-1L));
        System.out.println("-1 >> 32 :" + Long.toBinaryString((-1L >>> 32)));
        System.out.println("获取低位：c to b: " + cTob);
    }


}
