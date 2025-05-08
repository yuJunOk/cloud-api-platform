package com.api.mock.controller;

import com.api.mock.service.MockService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/")
public class MockController {

    @Resource
    private MockService mockService;

    @GetMapping("/hello")
    public String hello() {
        return mockService.hello();
    }

}
