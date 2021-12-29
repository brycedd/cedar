package com.dd.product;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
// springboot启动类；包含：@SpringBootConfiguration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication(scanBasePackages = {"com.dd.product","com.dd.cache"})
@EnableDiscoveryClient // 开启服务注册与发现
@MapperScan(basePackages={"com.dd.product.mapper","com.dd.cache"}) //mybatis 开启mapper接口扫描
@EnableMethodCache(basePackages = "com.dd.product") // jetCatch
@EnableCreateCacheAnnotation // jetCatch
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @RestController
    public static class EchoController {
        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return "Hello Nacos Discovery " + string;
        }
    }

}
