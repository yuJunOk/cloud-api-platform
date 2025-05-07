package com.api.mock.controller.inner;

import com.api.client.MockFeignClient;
import com.api.mock.service.MockService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/inner")
public class MockInnerController implements MockFeignClient {
    @Resource
    private MockService mockService;

    @Override
    @GetMapping("/hello")
    public String hello() {
        return mockService.hello();
    }
}
