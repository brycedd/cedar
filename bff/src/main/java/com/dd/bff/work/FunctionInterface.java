package com.dd.bff.work;

import java.util.function.Consumer;

/**
 * @author Bryce_dd 2021/12/20 23:55
 */
public class FunctionInterface {

    public static void main(String[] args) {
        final FunctionInterface functionInterface = new FunctionInterface();
        functionInterface.demoTest1();
        functionInterface.demoTest2();
    }

    private void demoTest1() {
        String otherParam = "other";
        String res = functionDemo(this::function1, "demo1的字符串", otherParam);
        System.out.println("demoTest1结束，返回值为: " + res);
    }

    private void demoTest2() {
        String otherParam = "other2";
        String res = functionDemo(this::function2, 1, otherParam);
        System.out.println("demoTest2结束，返回值为: " + res);
    }

    /**
     * 此处提取公共代码，将特定逻辑封装为方法传入，并执行
     *
     * @param consumer   特殊逻辑(consumer:有入参无返回值、Supplier:无入参有返回值、Function:有入参有返回值)
     * @param t          特殊逻辑方法入参
     * @param otherParam 通用逻辑参数
     * @param <T>        特殊逻辑参数类型
     * @return 返回值
     */
    private <T> String functionDemo(Consumer<T> consumer, T t, String otherParam) {
        System.out.println("开始一段通用逻辑");
        consumer.accept(t);
        System.out.println("再来一段通用逻辑");
        // 处理通用逻辑
        otherParam = otherParam + "通用逻辑处理";
        return otherParam;
    }

    // 以下为特殊逻辑
    private void function1(String demo1Str) {
        demo1Str = demo1Str + "执行逻辑";
        System.out.println(demo1Str);
    }

    private void function2(Integer demo2Integer) {
        demo2Integer = demo2Integer + demo2Integer;
        System.out.println(demo2Integer);
    }
}
