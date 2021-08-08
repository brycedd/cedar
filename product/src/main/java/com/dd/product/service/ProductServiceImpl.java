package com.dd.product.service;

import com.dd.service.service.product.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public String demo() {
        return "Product: Hello World";
    }
}
