package com.dd.product.service;

import com.dd.common.product.domain.PmsProductParam;
import com.dd.common.cache.ProductCache;
import com.dd.product.mapper.PortalProductMapper;
import com.dd.service.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author Bryce_dd 2021/8/8
 */
@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductServiceImpl implements ProductService {

    private PortalProductMapper productMapper;

    private ProductCache productCache;

    @Override
    public String demo() {
        return "Product: Hello World";
    }

    @Override
    public PmsProductParam getProductInfo(Long id) {
//        PmsProduct productById = productMapper.getProductById(id);
        return productMapper.getProductInfo(id);
    }
}
