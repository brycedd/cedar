package com.dd.cache.cache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.dd.cache.cache.dao.CacheProductDAO;
import com.dd.cache.util.JetCacheDataAsyncLoadUtil;
import com.dd.common.model.PmsProduct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通过实现runner，完成缓存的初始化加载
 *
 * @author Bryce_dd 2021/12/2 22:36
 */
@Component
@Slf4j
public class ProductCache implements ApplicationRunner {

    @CreateCache(name = "productCacheMap", expire = 500, cacheType = CacheType.BOTH, localLimit = 10)
    private Cache<Long, PmsProduct> productCacheMap;

    @Resource
    @Qualifier(value = "com.dd.common.cache.CacheProductDAO")
    private CacheProductDAO cacheProductDAO;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        final Map<Long, PmsProduct> longPmsProductMap = initCache();
        productCacheMap.putAll(longPmsProductMap);
    }

    public PmsProduct getPmsProductCache(Long id) {
        // 缓存中没有，异步load数据
        return JetCacheDataAsyncLoadUtil.get(id,() -> getProductById(id),productCacheMap);
    }




    // 添加异步加载数据类
    private Map<Long, PmsProduct> initCache() {
        final val pmsProducts = cacheProductDAO.selectList(null);
        if (CollectionUtils.isEmpty(pmsProducts)) {
            return new HashMap<>();
        }
        return pmsProducts.stream().collect(Collectors.toMap(PmsProduct::getId, pmsProduct -> pmsProduct));
    }

    private PmsProduct getProductById(Long id) {
        return cacheProductDAO.selectById(id);
    }

}
