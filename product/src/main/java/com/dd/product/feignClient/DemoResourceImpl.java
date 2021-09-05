package com.dd.product.feignClient;

import com.dd.service.resource.demo.DemoResource;
import com.dd.service.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@RestController
public class DemoResourceImpl implements DemoResource {

    @Autowired
    private DemoService demoService;


    @Override
    public String demo() {
        return demoService.demo();
    }
}
