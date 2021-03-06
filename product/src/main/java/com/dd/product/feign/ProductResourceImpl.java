package com.dd.product.feign;

import com.dd.product.manager.ProductManager;
import com.dd.service.feign.product.ProductResource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd 2021/8/1
 */
@RestController
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductResourceImpl implements ProductResource {

    private final ProductManager productManager;

    public String demo() {
        return productManager.demo();
    }



}
