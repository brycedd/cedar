package com.dd.product.service;

import com.dd.cache.product.ProductCache;
import com.dd.common.model.PmsProduct;
import com.dd.common.product.domain.PmsProductParam;
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
        return productMapper.getProductInfo(id);
    }

    @Override
    public PmsProduct plusGetProductById(Long id) {

        return productMapper.selectById(id);
    }
}
