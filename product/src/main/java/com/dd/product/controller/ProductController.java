package com.dd.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.dd.common.api.CommonResult;
import com.dd.common.model.PmsProduct;
import com.dd.common.product.domain.PmsProductParam;
import com.dd.product.manager.ProductManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd 2021/8/8
 */
@RestController
@RequestMapping(value = "/product")
@AllArgsConstructor(onConstructor_ = @Lazy)
public class ProductController {

    private final ProductManager productManager;

    @RequestMapping(value = "/productInfo/{id}", method = RequestMethod.GET)
    public CommonResult<Object> getProductInfo(@PathVariable Long id) {
        PmsProductParam productInfo = productManager.getProductInfo(id);
        return CommonResult.success(productInfo);
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String productDemo() {
        return productManager.demo();
    }

    @RequestMapping(value = "/mybatis-plus/demo/{id}", method = RequestMethod.GET)
    public CommonResult<Object> mybatisPlusDemo(@PathVariable Long id) {
        final PmsProduct pmsProduct = productManager.plusGetProductById(id);
        return CommonResult.success(pmsProduct);
    }

    @RequestMapping(value = "/productInfoWithCache/{id}", method = RequestMethod.GET)
    public CommonResult<Object> getProductInfoWithCache(@PathVariable Long id) {
        return CommonResult.success(productManager.getProductInfoWithCache(id));
    }


}
