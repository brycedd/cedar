package com.dd.service.service.product;

import com.dd.common.product.domain.PmsProductParam;

/**
 * @author Bryce_dd 2021/8/8
 */
public interface ProductService {

    String demo();

    PmsProductParam getProductInfo(Long id);
}
