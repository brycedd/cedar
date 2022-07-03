package com.dd.common.config;

import com.dd.common.util.SpringContextUtil;

/**
 * @author Bryce_dd 2022/3/20 15:59
 */
public class RedisLockPropertiesHolder {

    private static RedisLockProperties redisLockProperties;

    static {
        redisLockProperties = SpringContextUtil.getBean(RedisLockProperties.class);
    }

    public static RedisLockProperties getRedisLockProperties() {
        return redisLockProperties;
    }
}
