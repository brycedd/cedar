package com.dd.bff.controller.demo;

import com.dd.bff.work.CompletableFutureDemo;
import com.dd.common.util.CommonThreadPoolUtil;
import com.dd.service.feign.demo.DemoResource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Bryce_dd 2021/8/8
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

    @RequestMapping(value = "/threadTest", method = RequestMethod.GET)
    public void threadTest() {
        ThreadPoolExecutor commonThreadPool = CommonThreadPoolUtil.getCommonThreadPool();
        System.out.println("start one>>>>>>>>>>>");
        long t1 = System.currentTimeMillis();
        CompletableFuture.allOf(
                        CompletableFuture.runAsync(CompletableFutureDemo::doSomething, commonThreadPool),
                        CompletableFuture.runAsync(CompletableFutureDemo::doSomething2, commonThreadPool),
                        CompletableFuture.runAsync(CompletableFutureDemo::doSomething3, commonThreadPool))
                .join();
        System.out.println("第一次耗时：" + (System.currentTimeMillis() - t1));
        System.out.println("start two>>>>>>>>>>>");
        long t2 = System.currentTimeMillis();
        CompletableFutureDemo.doSomething();
        CompletableFutureDemo. doSomething2();
        CompletableFutureDemo.doSomething3();
        System.out.println("第二次耗时：" + (System.currentTimeMillis() - t2));
    }
}
