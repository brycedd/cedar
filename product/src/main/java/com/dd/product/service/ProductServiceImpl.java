package com.dd.product.service;

import com.dd.common.product.domain.PmsProductParam;
import com.dd.service.mapper.ProductMapper;
import com.dd.service.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;

    @Override
    public String demo() {
        return "Product: Hello World";
    }

    @Override
    public PmsProductParam getProductInfo(Long id) {
        return productMapper.getProductInfo(id);
    }
}
