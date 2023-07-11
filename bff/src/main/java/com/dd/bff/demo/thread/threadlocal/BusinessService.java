package com.dd.bff.demo.thread.threadlocal;

import org.springframework.transaction.TransactionStatus;

/**
 * @author Bryce_dd 2023/7/11 17:51
 */
public class BusinessService {
    public void performBusinessLogic() {
        TransactionStatus transactionStatus = TransactionContext.getTransactionStatus();

        // 在此处使用事务对象进行数据库操作
        // ...
    }
}

