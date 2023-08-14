package com.dd.demo.demo.thread.springtxdemo;

import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Bryce_dd 2023/7/11 19:27
 */

public class TransactionManager {
    private static final ThreadLocal<Transaction> transactionHolder = new ThreadLocal<>();

    public static void beginTransaction() {
        Transaction transaction = new Transaction();
        transaction.start();
        transactionHolder.set(transaction);
        TransactionSynchronizationManager.initSynchronization();
    }

    public static void commitTransaction() {
        Transaction transaction = transactionHolder.get();
        transaction.commit();
        transactionHolder.remove();
        TransactionSynchronizationManager.clearSynchronization();
    }

    public static void rollbackTransaction() {
        Transaction transaction = transactionHolder.get();
        transaction.rollback();
        transactionHolder.remove();
        TransactionSynchronizationManager.clearSynchronization();
    }

    public static Transaction getCurrentTransaction() {
        return transactionHolder.get();
    }
}




