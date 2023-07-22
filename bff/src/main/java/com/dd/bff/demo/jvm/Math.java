package com.dd.bff.demo.jvm;

import com.dd.common.model.User;

/**
 * @author Bryce_dd 2023/7/18 22:31
 */
public class Math {

    public static final int initData = 666;
    public static User user = new User();

    public int compute() {  //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    // 使cpu标高，用于调试命令
    public static void main(String[] args) {
        Math math = new Math();
        while (true){
            math.compute();
        }
    }
}
