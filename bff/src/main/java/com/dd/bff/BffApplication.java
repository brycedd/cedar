package com.dd.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 提供统一的前端聚合服务
 */
@SpringBootApplication(scanBasePackages = {"com.dd.bff", "com.dd.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.dd.service.feign"})
public class BffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

}
