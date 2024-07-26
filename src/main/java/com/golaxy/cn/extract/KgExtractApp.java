package com.golaxy.cn.extract;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.golaxy.cn.extract")
@SpringBootApplication
public class KgExtractApp {
    public static void main(String[] args) {
        SpringApplication.run(KgExtractApp.class, args);
    }
}

