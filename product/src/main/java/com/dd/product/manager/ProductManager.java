package com.dd.product.manager;

import com.dd.common.cache.ProductCache;
import com.dd.common.model.PmsProduct;
import com.dd.common.product.domain.PmsProductParam;
import com.dd.common.util.SpringContextUtil;
import com.dd.service.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * @author Bryce_dd 2021/9/5
 */
@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductManager {

    private ProductService productService;

    private ProductCache productCache;

    public String demo() {
        return productService.demo();
    }

    public PmsProductParam getProductInfo(Long id) {
        return productService.getProductInfo(id);
    }

    public PmsProduct plusGetProductById(Long id) {
        return productService.plusGetProductById(id);
    }

    public PmsProduct getProductInfoWithCache(Long id) {
        return productCache.getPmsProductCache(id);
    }
}
