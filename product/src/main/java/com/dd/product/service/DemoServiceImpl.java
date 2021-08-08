package com.dd.product.service;

import com.dd.service.service.demo.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String demo() {
        return "Hello World";
    }
}
