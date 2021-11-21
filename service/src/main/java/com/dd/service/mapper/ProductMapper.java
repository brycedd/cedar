package com.dd.service.mapper;

import com.dd.common.product.domain.PmsProductParam;

/**
 * @author Bryce_dd 2021/11/18 23:42
 */
public interface ProductMapper {
    PmsProductParam getProductInfo(Long id);
}
