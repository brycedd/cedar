package com.dd.common.config;

import com.dd.common.util.lock.DistributedLockUtil;
import com.dd.common.util.lock.RedisDistributedLock;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Bryce_dd 2022/3/20 15:37
 */
@Data
@ConfigurationProperties(prefix = "redis.lock")
public class RedisLockProperties {

    /**
     * 锁前缀
     */
    private String prefix = RedisDistributedLock.REDIS_LOCK_PREFIX;

    /**
     * 锁过期时间 单位：毫秒；
     */
    private long lockExpiredMillis = RedisDistributedLock.DEFAULT_EXPIRED_MILLIS;

    /**
     * 锁等待时间 单位：毫秒；
     */
    private long timeoutMillis = RedisDistributedLock.DEFAULT_TIMEOUT_MILLIS;

    /**
     * 锁单次等待时间 单位：毫秒；默认500毫秒
     */
    private long onceWaitMillis = RedisDistributedLock.DEFAULT_ONCE_WAIT_MILLIS;

    /**
     * 线程池初始化延迟执行时长 单位：秒
     */
    private int initialDelay = DistributedLockUtil.DEFAULT_INITIAL_DELAY;

    /**
     * 锁监控线程执行周期 单位：秒
     */
    private int delay = DistributedLockUtil.DEFAULT_DELAY;

}
