package com.dd.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.common.model.PmsProduct;
import com.dd.common.product.domain.PmsProductParam;
import org.apache.ibatis.annotations.Param;

/**
 * @author Bryce_dd 2021/11/18 23:42
 */
public interface PortalProductMapper extends BaseMapper<PmsProduct> {
    PmsProductParam getProductInfo(@Param(value = "id") Long id);

    PmsProduct getProductById(@Param(value = "id") Long id);
}
