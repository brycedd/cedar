package com.dd.bff.demo.thread.threadlocal;

import org.springframework.transaction.TransactionStatus;

/**
 * @author Bryce_dd 2023/7/11 17:48
 */
public class TransactionContext {
    private static final ThreadLocal<TransactionStatus> transactionContext = new ThreadLocal<>();

    public static void setTransactionStatus(TransactionStatus transactionStatus) {
        transactionContext.set(transactionStatus);
    }

    public static TransactionStatus getTransactionStatus() {
        return transactionContext.get();
    }

    public static void clear() {
        transactionContext.remove();
    }
}

