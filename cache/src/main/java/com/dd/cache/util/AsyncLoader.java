package com.dd.cache.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.dd.common.constant.Const.ThreadName.AUTO_LOAD_EXECUTOR_NAME;

/**
 * @author Bryce_dd 2021/12/13 22:32
 */
@Component
public class AsyncLoader {

    @Resource
    @Qualifier(value = AUTO_LOAD_EXECUTOR_NAME)
    private ExecutorService executor;

    public <V> Future<?> submit(Function0<V> supplier) {
        return executor.submit(supplier::apply);
    }
}
