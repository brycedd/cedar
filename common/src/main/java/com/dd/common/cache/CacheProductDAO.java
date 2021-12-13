package com.dd.common.cache;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.common.model.PmsProduct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Bryce_dd 2021/12/2 23:51
 */
@Component
@Qualifier(value = "com.dd.common.cache.CacheProductDAO")
public interface CacheProductDAO extends BaseMapper<PmsProduct> {
}
