package com.dd.cache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.dd.common.product.domain.PmsProductParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 通过实现runner，完成缓存的初始化加载
 *
 * @author Bryce_dd 2021/12/2 22:36
 */
@Component
@Slf4j
public class ProductCache implements ApplicationRunner {

    @CreateCache(name = "productCacheMap", expire = 500, cacheType = CacheType.BOTH, localLimit = 10)
    private Cache<String, PmsProductParam> productCacheMap;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(productCacheMap);
    }


    //todo 添加异步加载数据类
}
