package com.dd.bff.demo.jvm;

import com.dd.common.model.User;

/**
 * @author Bryce_dd 2023/7/20 15:16
 */
public class AllotOnStack {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    private static void alloc() {
        User user = new User();
        user.setId(1L);
        user.setName("dd");
        user.setData(null);
    }
}
