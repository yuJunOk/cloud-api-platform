package com.api.mock;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

/**
 * @author pengYuJun
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.api.client"})
@Component("com.api")
@MapperScan("com.api.mock.mapper")
@SpringBootApplication
public class ApiMockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMockApplication.class, args);
    }

}
