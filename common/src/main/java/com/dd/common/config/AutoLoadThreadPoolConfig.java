package com.dd.common.config;

import com.dd.common.constant.Const;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2021/12/13 23:27
 */
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AutoLoadThreadPoolConfig {

    @Bean(Const.ThreadName.AUTO_LOAD_EXECUTOR_NAME)
    public ExecutorService getUploadAndDownloadExecutor() {
        return new ThreadPoolExecutor(
                2,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }
}
