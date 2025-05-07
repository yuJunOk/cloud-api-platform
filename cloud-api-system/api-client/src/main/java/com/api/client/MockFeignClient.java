package com.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 模拟服务
 * @author pengYuJun
 */
@FeignClient(name = "api-mock", path = "/api/mock/inner")
public interface MockFeignClient {
    /**
     * hello一下
     * @return
     */
    @GetMapping("/hello")
    String hello();
}
