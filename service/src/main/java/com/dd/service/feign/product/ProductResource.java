package com.dd.service.feign.product;


import com.dd.common.constant.Const;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@FeignClient(value = Const.ServiceName.CEDAR_PRODUCT/*,url = "127.0.0.1:5551"*/)
public interface ProductResource {

    @RequestMapping(value = "/product/demo")
    String demo();
}
