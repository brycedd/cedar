package com.dd.demo.demo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bryce_dd 2023/7/18 23:13
 */
public class HeapTest {

    byte[] a = new byte[1024 * 100]; // 100kb

    public static void main(String[] args) throws InterruptedException {
        List<HeapTest> heapTests = new ArrayList<>();
        while (true) {
            heapTests.add(new HeapTest());
            Thread.sleep(100);
        }
    }
}
