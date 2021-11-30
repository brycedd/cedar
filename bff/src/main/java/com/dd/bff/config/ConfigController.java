package com.dd.bff.config;

import com.dd.common.exception.ResponseEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce_dd 2021/8/8
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    //实现配置自动更新
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

    public static void main(String[] args) {
        ConfigController configController = null;
        ResponseEnum.BAD_LICENCE_TYPE.assertNotNul(configController);
        System.out.println("dd");
    }
}
