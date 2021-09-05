package com.dd.product.feignClient;

import com.dd.service.feign.demo.DemoResource;
import com.dd.service.service.demo.DemoService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@RestController
@AllArgsConstructor(onConstructor_ = @Lazy)
public class DemoResourceImpl implements DemoResource {

    private final DemoService demoService;


    @Override
    public String demo() {
        return demoService.demo();
    }
}
