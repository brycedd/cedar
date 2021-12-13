package com.dd.common.util;

import com.alicp.jetcache.Cache;
import com.dd.common.config.AsyncLoader;
import com.dd.common.config.Function0;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Bryce_dd 2021/12/13 22:30
 */
@Slf4j
public class JetCacheDataAsyncLoadUtil {
    public static <K,V> V get(K k, Function0<V> supplier, Cache<K, V> cacheMap) {

        V v1;
        v1 = cacheMap.get(k);
        if (!Objects.isNull(v1)) {
            return v1;
        }
        AsyncLoader asyncLoader = SpringContextUtil.getBean(AsyncLoader.class);
        @SuppressWarnings("unchecked")
        Future<V> future = (Future<V>)asyncLoader.submit(supplier);
        try {
            v1 = future.get();
        } catch (InterruptedException e) {
            log.error(String.format("jetCacheKey: %s >>> 加载数据线程被中断",k), e);
            future.cancel(true);
        } catch (ExecutionException e) {
            log.error(String.format("jetCacheKey: %s >>> 加载数据线程执行异常",k), e);
            future.cancel(true);
        }
        if (!Objects.isNull(v1)) {
            cacheMap.put(k,v1);
        }
        return v1;
    }
}
