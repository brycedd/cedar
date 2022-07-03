package com.dd.common.util.lock;

import com.dd.common.config.RedisLockProperties;
import com.dd.common.config.RedisLockPropertiesHolder;
import com.dd.common.util.log.LogTextHolder;
import com.dd.common.util.log.LogUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bryce_dd 2022/3/18 20:00
 */
@Slf4j
public class RedisDistributedLock {

    /**
     * 默认单次锁等待时间（单位：毫秒）
     */
    public static final long DEFAULT_ONCE_WAIT_MILLIS = 500L;

    /**
     * 默认锁过期时间（单位：毫秒）
     */
    public static final long DEFAULT_EXPIRED_MILLIS = 15000L;

    /**
     * 默认锁等待时间（单位：毫秒）
     */
    public static final long DEFAULT_TIMEOUT_MILLIS = 3000L;

    /**
     * redis锁key前缀
     */
    public static final String REDIS_LOCK_PREFIX = "com.dd:";

    /**
     * 锁状态
     */
    boolean locked = false;

    /**
     * 结果：成功
     */
    private static final String RESULT_SUCCESS = "1";

    /**
     * 结果：失败
     */
    private static final String RESULT_FAIL = "0";

    /**
     * Lock held by take, poll, etc
     */
    private final ReentrantLock waitLock = new ReentrantLock();

    /**
     * Wait queue for waiting takes
     */
    private final Condition waitNotify = waitLock.newCondition();

    /**
     * lua开锁脚本
     */
    private static final RedisScript<String> lockScript;

    /**
     * lua解锁脚本
     */
    private static final RedisScript<String> unlockScript;

    /**
     * lua刷新锁过期时间脚本
     */
    private static final RedisScript<String> updateScript;

    /**
     * lock key
     */
    private final String lockKey;

    /**
     * 锁过期时间
     */
    private long lockExpiredTime;

    /**
     * 锁等待时间，防止线程一直获取不到锁而无限等待
     */
    private long timeoutMillis;

    /**
     * 锁单次等待时间
     */
    private long onceWaitMillis;

    /**
     * 锁ID
     */
    private final String lockId = UUID.randomUUID().toString();

    /**
     * 锁下次更新时间
     */
    private long nextUpdateMillis;

    static {
        lockScript = new DefaultRedisScript<>(ScriptCreator.lockScript(), String.class);
        unlockScript = new DefaultRedisScript<>(ScriptCreator.unlockScript(), String.class);
        updateScript = new DefaultRedisScript<>(ScriptCreator.updateLockScript(), String.class);
    }

    public RedisDistributedLock(String lockKey, long lockExpiredTime, long timeoutMillis) {
        Preconditions.checkArgument(!StringUtils.isEmpty(lockKey));
        Preconditions.checkArgument(lockExpiredTime > 0);
        Preconditions.checkArgument(timeoutMillis >= 0);
        this.lockKey = lockKey;
        this.lockExpiredTime = lockExpiredTime;
        this.timeoutMillis = timeoutMillis;
        modifyField();
    }

    public RedisDistributedLock(String lockKey, long lockExpiredTime, long timeoutMillis, long onceWaitMillis) {
        this(lockKey, lockExpiredTime, timeoutMillis);
        this.onceWaitMillis = onceWaitMillis;
        modifyField();
    }

    public String getLockName() {
        return lockId;
    }

    public boolean lock() {
        long start = System.currentTimeMillis();
        // 超时时间
        long timeout = timeoutMillis;
        // 轮询次数
        int times = 0;
        // 锁id
        String lockName = getLockName();

        // 单次开始时间
        long onceStart = 0L;

        try {
            while (timeout >= 0L) {
                // 1、得到锁
                String result = null;
                try {
                    result = RedisScriptUtil.eval(lockScript,
                            RedisLockPropertiesHolder.getRedisLockProperties().getPrefix() + lockKey,
                            lockName,
                            String.valueOf(lockExpiredTime));
                    int currentTimes = times + 1;
                    if (RESULT_SUCCESS.equals(result)) {
                        resetNextUpdateMillis();
                        locked = true;
                        LogUtil.debugLog(log, () -> LogTextHolder.instance(
                                "####redisLock#### 锁标识【{}】第【{}】 次获取锁成功，耗时：{} 毫秒",
                                getLockName(), currentTimes, System.currentTimeMillis() - start));
                        break;
                    } else {
                        LogUtil.debugLog(log, () -> LogTextHolder.instance(
                                "####redisLock#### 锁标识【{}】第【{}】次获取锁失败",
                                getLockName(), currentTimes));
                    }
                } catch (Exception e) {
                    log.error("acquire redis lock exception!", e);
                    throw e;
                }
                // 不等待，直接返回结果
                if (timeout <= 0) {
                    break;
                }
                // 2、未获取锁，等待 waitMillis
                long waitMillis = onceWaitMillis;
                onceStart = System.currentTimeMillis();
                try {
                    int currentTimes = times + 1;
                    LogUtil.debugLog(log, () -> LogTextHolder.instance("####redisLock#### 锁标识【{}】进入第【{}】次等待", getLockName(), currentTimes));
                    if (waitLock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
                        long getLockUseMillis = System.currentTimeMillis() - onceStart;
                        LogUtil.debugLog(log, () -> LogTextHolder.instance(
                                "####redisLock#### 锁标识【{}】第【{}】次等待{}毫秒",
                                getLockName(), currentTimes, waitMillis - getLockUseMillis));
                        // 判断是否超时
                        if (getLockUseMillis < timeout) {
                            waitNotify.await(waitMillis - getLockUseMillis, TimeUnit.MILLISECONDS);
                        }
                        LogUtil.debugLog(log, () -> LogTextHolder.instance(
                                "####redisLock#### 锁标识【{}】第【{}】次等待结束，总耗时：{}毫秒，进入下次尝试",
                                getLockName(), currentTimes, System.currentTimeMillis() - start));
                    }
                } catch (InterruptedException e) {
                    log.error("####redisLock#### 锁标识【{}】等待异常", getLockName(), e);
                } finally {
                    if (locked) {
                        waitLock.unlock();
                    }
                }

                // 减去实际等待时间
                timeout -= (System.currentTimeMillis() - onceStart);
                times++;
            }
        } finally {
            if (!locked) {
                int currentTimes = times;
                LogUtil.infoLog(log, () -> LogTextHolder.instance(
                        "{} can not acquire redis lock! spend time {} and cycle times {}",
                        Thread.currentThread().getName(), System.currentTimeMillis() - start, currentTimes));
            }
        }
        return locked;
    }

    public boolean unlock() {
        if (locked) {
            try {
                String result = RedisScriptUtil.eval(unlockScript, REDIS_LOCK_PREFIX + lockKey, getLockName());
                if (RESULT_FAIL.equals(result)) {
                    log.error("####redisLock#### can not unlock other lock");
                    locked = false;
                }
                LogUtil.debugLog(log, () -> LogTextHolder.instance("####redisLock#### 锁标识【{}】释放锁", getLockName()));
            } catch (Exception e) {
                log.error("redis unlock exception!", e);
            }
        }
        return locked;
    }

    public boolean updateLock() {
        boolean updateLockSuccess = false;
        String result = RedisScriptUtil.eval(updateScript, REDIS_LOCK_PREFIX + lockKey, getLockName());
        if (RESULT_SUCCESS.equals(result)) {
            resetNextUpdateMillis();
            updateLockSuccess = true;
        }
        return updateLockSuccess;
    }

    private void resetNextUpdateMillis() {
        this.nextUpdateMillis = System.currentTimeMillis() + lockExpiredTime >> 2;
    }

    public long ttl() {
        return RedisScriptUtil.ttl(REDIS_LOCK_PREFIX + lockKey);
    }

    private void modifyField() {
        RedisLockProperties redisLockProperties = RedisLockPropertiesHolder.getRedisLockProperties();

        if (this.lockExpiredTime <= 0) {
            if (redisLockProperties.getLockExpiredMillis() <= 0) {
                this.lockExpiredTime = DEFAULT_EXPIRED_MILLIS;
            } else {
                this.lockExpiredTime = redisLockProperties.getLockExpiredMillis();
            }
        }

        if (timeoutMillis < 0 || timeoutMillis < onceWaitMillis) {
            if (redisLockProperties.getTimeoutMillis() < redisLockProperties.getOnceWaitMillis()
                    || redisLockProperties.getOnceWaitMillis() < 0
                    || redisLockProperties.getTimeoutMillis() < 0) {
                LogUtil.infoLog(log, c -> log.info(
                        "---参数timeOutMillis={}, onceWaitMillis={} 不合法，请使用默认的参数：timeoutMillis={},onceWaitMillis={}",
                        timeoutMillis, onceWaitMillis, DEFAULT_TIMEOUT_MILLIS, DEFAULT_ONCE_WAIT_MILLIS));
                this.timeoutMillis = DEFAULT_TIMEOUT_MILLIS;
                this.onceWaitMillis = DEFAULT_ONCE_WAIT_MILLIS;
            } else {
                LogUtil.infoLog(log, c -> log.info(
                        "--- 参数timeoutMillis={}, onceWaitMillis={}不合法，使用配置的参数 timeoutMillis={}, onceWaitMillis={}",
                        timeoutMillis, onceWaitMillis, redisLockProperties.getTimeoutMillis(), redisLockProperties.getOnceWaitMillis()));
                this.timeoutMillis = redisLockProperties.getTimeoutMillis();
                this.onceWaitMillis = redisLockProperties.getOnceWaitMillis();
            }
        }
    }

    public long getNextUpdateMillis() {
        return this.nextUpdateMillis;
    }
}
