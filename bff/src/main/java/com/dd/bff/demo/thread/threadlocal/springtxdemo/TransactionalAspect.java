package com.dd.bff.demo.thread.threadlocal.springtxdemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Bryce_dd 2023/7/11 17:56
 */
@Aspect
public class TransactionalAspect {
    private final PlatformTransactionManager transactionManager;

    public TransactionalAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        TransactionContext.setTransactionStatus(transactionStatus);

        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(transactionStatus);
            return result;
        } catch (Throwable throwable) {
            transactionManager.rollback(transactionStatus);
            throw throwable;
        } finally {
            TransactionContext.clear();
        }
    }
}

