package com.dd.common.util.lock;

import com.dd.common.config.RedisLockProperties;
import com.dd.common.util.SpringContextUtil;
import com.dd.common.util.log.LogTextHolder;
import com.dd.common.util.log.LogUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Bryce_dd 2022/3/20 21:18
 */
@Slf4j
public class DistributedLockUtil {
    private DistributedLockUtil() {}

    /**
     * 定时器初始延迟时间 单位：秒
     */
    public static final int DEFAULT_INITIAL_DELAY = 3;

    /**
     * 定时器轮询周期 单位：秒
     */
    public static final int DEFAULT_DELAY = (int)RedisDistributedLock.DEFAULT_EXPIRED_MILLIS / 1000 / 2;

    /**
     * 当前所有获取的锁；key为锁的name
     */
    private static Map<String, RedisDistributedLock> lockMap = Maps.newConcurrentMap();

    /**
     * 启动一个更新
     */
    private static ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

    private static RedisLockProperties properties;
    static {
        properties = SpringContextUtil.getBean(RedisLockProperties.class);

        long initialDelay = properties.getInitialDelay();
        long delay = properties.getDelay();
        if (initialDelay < 0) {
            initialDelay = DEFAULT_INITIAL_DELAY;
        }

        if (delay <= 0) {
            delay = DEFAULT_DELAY;
        }
        threadPool.scheduleWithFixedDelay(() -> {

            long currMillis = System.currentTimeMillis();
            for (RedisDistributedLock lock : lockMap.values()) {
                if (currMillis > lock.getNextUpdateMillis()) {
                    LogUtil.debugLog(log, () -> LogTextHolder.instance(" **** 锁标识：{} 更新前-剩余时间：{} 毫秒******",
                            lock.getLockName(), lock.ttl()));
                    lock.updateLock();
                    LogUtil.debugLog(log,
                            () -> LogTextHolder.instance(" **** 锁标识：{} 更新后- 剩余时间：{} 毫秒 nextUpdateMillis={}******",
                                    lock.getLockName(), lock.ttl(), lock.getNextUpdateMillis()));
                }
            }
        }, initialDelay, delay, TimeUnit.SECONDS);

    }

    /**
     *
     * @Description: 尝试获取分布式锁并执行指定方法
     * @param <T>
     * @param lockKey 锁标志
     * @param handler 获取到锁后执行的方法体
     * @param waitMilliSeconds 锁超时时间
     * @return
     * @throws LockFailException
     */
    public static <T> T tryProcess(String lockKey, Handler<T> handler, long waitMilliSeconds) throws LockFailException {
        Preconditions.checkArgument(null != lockKey, "lock should be not null");
        Preconditions.checkArgument(handler != null, "handler should be not null");
        Preconditions.checkArgument(waitMilliSeconds >= 0, "waitMilliSeconds should be greater than 0");

        RedisDistributedLock lock =
                new RedisDistributedLock(lockKey, RedisDistributedLock.DEFAULT_EXPIRED_MILLIS, waitMilliSeconds);
        final Long start = System.currentTimeMillis();
        boolean locked = false;

        LogUtil.debugLog(log,
                () -> LogTextHolder.instance(" **** 锁标识：{} 尝试获取锁 ：{} ****** ", lock.getLockName(), lockKey));
        try {
            locked = lock.lock();
            if (locked) {
                lockMap.put(lock.getLockName(), lock);
                LogUtil.debugLog(log,
                        () -> LogTextHolder.instance(" **** 锁标识：{} 成功获取锁 ：{} ****** ", lock.getLockName(), lockKey));
                return handler.callback();
            } else {
                throw new LockFailException(String.format("获取分布式[%s]锁失败;", lockKey));
            }
        } finally {
            if (locked) {
                lock.unlock();
                lockMap.remove(lock.getLockName());
                LogUtil.infoLog(log, () -> LogTextHolder.instance(" **** 锁标识：{} 持有锁 ：{} 执行业务方法耗时：{} 毫秒 ****** ",
                        lock.getLockName(), lockKey, System.currentTimeMillis() - start));
            } else {
                LogUtil.debugLog(log, () -> LogTextHolder.instance(" **** 锁标识：{} 获取锁失败，耗时：{} ******",
                        lock.getLockName(), System.currentTimeMillis() - start));
            }
        }
    }

    public static <T,R extends DistributedLock> T tryProcess(List<R> list, Function<List<R>,T> handler) {
        Preconditions.checkArgument(handler != null, "handler should be not null");
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String,RedisDistributedLock> locks = new HashMap<>();
        List<R> tmp = list.parallelStream().filter(r->{
            RedisDistributedLock lock =
                    new RedisDistributedLock(r.key(), RedisDistributedLock.DEFAULT_EXPIRED_MILLIS, 0);
            boolean locked = lock.lock();
            if (locked){
                locks.put(r.key(),lock);
                lockMap.put(lock.getLockName(), lock);
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        try {
            return handler.apply(tmp);
        }finally {
            if (locks!=null && !locks.isEmpty()){
                locks.values().forEach(lock->{
                    lock.unlock();
                    lockMap.remove(lock.getLockName());
                });
            }
        }
    }
}
