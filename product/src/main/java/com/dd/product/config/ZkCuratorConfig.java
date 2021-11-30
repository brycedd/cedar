package com.dd.product.config;

import com.dd.product.component.ZKLock;
import com.dd.product.component.ZKLockImpl;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bryce_dd 2021/8/1
 */
@Configuration
public class ZkCuratorConfig {

    @Value("${zk.curator.retryCount}")
    private int retryCount;
    @Value("${zk.curator.elapsedTimeMs}")
    private int elapsedTimeMs;
    @Value("${zk.curator.connectUrl}")
    private String connectUrl;
    @Value("${zk.curator.sessionTimeOutMs}")
    private int sessionTimeOutMs;
    @Value("${zk.curator.connectionTimeOutMs}")
    private int connectionTimeOutMs;


    /**
     * 客户端初始化
     */
    @Bean(initMethod = "start",destroyMethod = "close")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient(
                connectUrl,
                sessionTimeOutMs,
                connectionTimeOutMs,
                new RetryNTimes(retryCount,elapsedTimeMs));
    }

    @Bean
    public ZKLock zkLock() {
        return new ZKLockImpl();
    }
}
