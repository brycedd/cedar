package com.dd.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Bryce_dd 2023/9/13 12:13
 */
public class AppConfig {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    }
}
