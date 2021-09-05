package com.dd.product.feignClient;

import com.dd.product.manager.ProductManager;
import com.dd.service.resource.product.ProductResource;
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

    private ProductManager productManager;

    public String demo() {
        return productManager.demo();
    }



}
