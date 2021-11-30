package com.dd.service.feign.demo;

import com.dd.common.constant.Const;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Bryce_dd 2021/8/8
 */
@FeignClient(value = Const.ServiceName.CEDAR_PRODUCT)
public interface DemoResource {

    @RequestMapping(value = "/demo/demo")
    String demo();
}
