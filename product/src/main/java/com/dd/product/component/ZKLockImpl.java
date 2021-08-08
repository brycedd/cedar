package com.dd.product.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bryce_dd
 * @date 2021/8/1
 */
@Slf4j
//@AllArgsConstructor(onConstructor_ = @Lazy)
public class ZKLockImpl implements ZKLock, InitializingBean {

    private final static String LOCK_ROOT_PATH = "/ZkLock";

    private Map<String, CountDownLatch> concurrentMap = new ConcurrentHashMap<>();

    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private CuratorFramework curatorFramework;


    @Override
    public boolean lock(String lockPath) {
        boolean result = false;
        String keyPath = LOCK_ROOT_PATH + lockPath;

        try {
            curatorFramework
                    .create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(Ids.OPEN_ACL_UNSAFE)
                    .forPath(keyPath);
            result = true;
            log.info("success to acquire mutex lock for path:{}", keyPath);
        } catch (Exception e) {
            log.info("Thread:{};failed to acquire mutex lock for path:{}", Thread.currentThread().getName(), keyPath);
            if (!concurrentMap.containsKey(lockPath)) {
                try {
                    //这里考虑到高并发情景，
                    //必须保证对同一个节点加锁的线程失败后落在同一个countdown对象上，
                    //否则有的线程永远无法被唤醒
                    lock.lock();
                    //双重校验
                    if (!concurrentMap.containsKey(lockPath)) {
                        concurrentMap.put(lockPath, new CountDownLatch(1));
                    }
                } finally {
                    lock.unlock();
                }
            }
            try {
                CountDownLatch countDownLatch = concurrentMap.get(lockPath);
                if (countDownLatch != null) {
                    countDownLatch.await();
                }
            } catch (InterruptedException e1) {
                log.info("InterruptedException message:{}", e1.getMessage());
            }
        }
        return result;
    }


    @Override
    public boolean unLock(String lockPath) {
        String keyPath = LOCK_ROOT_PATH + lockPath;

        try {
            if (curatorFramework.checkExists().forPath(keyPath) != null) {
                curatorFramework.delete().forPath(keyPath);
            }
        } catch (Exception e) {
            log.error("failed to release mutex lock");
            return false;
        }
        return true;
    }

    /**
     * 监听节点事件
     *
     * @param lockPath 加锁路径
     * @throws Exception 异常
     */
    private void addWatcher(String lockPath) throws Exception {
        String keyPath;
        if (LOCK_ROOT_PATH.equals(lockPath)) {
            keyPath = lockPath;
        } else {
            keyPath = LOCK_ROOT_PATH + lockPath;
        }

        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
        cache.start(StartMode.POST_INITIALIZED_EVENT);

        //添加监听器
        cache.getListenable().addListener((client, event) -> {
            if (event.getType().equals(Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                log.info("oldPath delete:{},缓存已经更新！", oldPath);
                if (oldPath.contains(lockPath)) {
                    //释放计数器，释放锁
                    CountDownLatch countDownLatch = concurrentMap.remove(oldPath);
                    if (countDownLatch != null) {
                        //有可能没有竞争，countdown不存在
                        countDownLatch.countDown();
                    }
                }
            }
        });
    }


    @Override
    public void afterPropertiesSet() {
        curatorFramework = curatorFramework.usingNamespace("zkLock-namespace");
        //zk锁的根路径 不存在则创建
        try {
            if (curatorFramework.checkExists().forPath(LOCK_ROOT_PATH) == null) {
                curatorFramework
                        .create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(Ids.OPEN_ACL_UNSAFE)
                        .forPath(LOCK_ROOT_PATH);
            }
            //启动监听器
            addWatcher(LOCK_ROOT_PATH);
        } catch (Exception e) {
            log.error("connect zookeeper failed:{}", e.getMessage(), e);
        }

    }
}
