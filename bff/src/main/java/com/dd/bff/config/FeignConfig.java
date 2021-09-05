package com.dd.bff.config;

import feign.Logger.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启feign调用日志，需要在配置文件中配置日志级别
 * @author Bryce_dd
 * @date 2021/9/5
 */
@Configuration
public class FeignConfig {

    @Bean
    public Level feignLoggerLevel() {
        return Level.FULL;
    }
}
