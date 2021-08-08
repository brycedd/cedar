package com.dd.bff.controller.demo;

import com.dd.service.resource.demo.DemoResource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd
 * @date 2021/8/8
 */
@RestController
@RequestMapping(value = "/demo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor(onConstructor_ = @Lazy)
public class DemoController {

    private final DemoResource demoResource;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo() {
        System.out.println("here");
        return demoResource.demo();
    }
}
