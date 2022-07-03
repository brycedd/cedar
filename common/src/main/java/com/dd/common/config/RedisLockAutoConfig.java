package com.dd.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author Bryce_dd 2022/3/20 15:51
 */
@Configuration
@DependsOn("SpringContextUtil-core")
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ComponentScan("com.dd.common")
@EnableConfigurationProperties(RedisLockProperties.class)
public class RedisLockAutoConfig {

    @Autowired
    protected RedisLockProperties redisLockProperties;
}
