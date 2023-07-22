package com.dd.bff.demo.jvm;

import com.dd.common.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Bryce_dd 2023/7/21 20:21
 */
public class OOMTest {

    public static List<Object> list = new ArrayList<>();

    // JVM设置
    // -Xms5M -Xmx5M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/brycedd/app_temp
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            list.add(new User((long) i++, UUID.randomUUID().toString()));
            new User((long) j--, UUID.randomUUID().toString());
        }
    }
}
