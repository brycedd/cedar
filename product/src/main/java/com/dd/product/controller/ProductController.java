package com.dd.product.controller;

import com.dd.common.api.CommonResult;
import com.dd.product.manager.ProductManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@RestController
@RequestMapping(value = "/product")
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductController {

    private final ProductManager productManager;

    @RequestMapping(value = "/productInfo/{id}", method = RequestMethod.GET)
    public CommonResult<Object> getProductInfo(@PathVariable Long id) {
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String productDemo() {
        return productManager.demo();
    }

}
