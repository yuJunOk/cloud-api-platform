package com.api.mock.controller;

import com.api.mock.service.MockService;
import com.api.mock.utils.IpUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("getPublicIp")
    public String getPublicIp(HttpServletRequest request) {
        return IpUtils.getClientPublicIp(request);
    }

}
