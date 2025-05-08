package com.api.mock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author pengYuJun
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.api.client"})
@ComponentScan("com.api")
@MapperScan("com.api.mock.mapper")
@SpringBootApplication
public class ApiServiceMockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServiceMockApplication.class, args);
    }

}
