package com.api.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author pengYuJun
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.api.client"})
@ComponentScan("com.api")
@MapperScan("com.api.user.mapper")
@SpringBootApplication
public class ApiServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServiceUserApplication.class, args);
    }

}
