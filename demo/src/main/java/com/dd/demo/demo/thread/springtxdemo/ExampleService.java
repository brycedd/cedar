package com.dd.demo.demo.thread.springtxdemo;

/**
 * @author Bryce_dd 2023/7/11 19:29
 */

public class ExampleService {
    public void performDatabaseOperation() {
        TransactionManager.beginTransaction();
        try {
            // 执行数据库操作
            // ...
            TransactionManager.commitTransaction();
        } catch (Exception e) {
            TransactionManager.rollbackTransaction();
            throw e;
        }
    }
}

