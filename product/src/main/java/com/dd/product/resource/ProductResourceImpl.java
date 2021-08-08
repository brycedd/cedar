package com.dd.product.resource;

import com.dd.service.resource.product.ProductResource;
import com.dd.service.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd
 * @date 2021/8/1
 */
@RestController
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductResourceImpl implements ProductResource {

    private ProductService productService;

    public String demo() {
        return productService.demo();
    }



}
