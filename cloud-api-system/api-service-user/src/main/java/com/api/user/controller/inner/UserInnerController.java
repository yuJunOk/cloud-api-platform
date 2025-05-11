package com.api.user.controller.inner;

import com.api.client.UserFeignClient;
import com.api.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;
}
