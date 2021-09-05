package com.dd.product.manager;

import com.dd.service.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author Bryce_dd
 * @date 2021/9/5
 */
@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductManager {

    private ProductService productService;

    public String demo() {
        return productService.demo();
    }
}
