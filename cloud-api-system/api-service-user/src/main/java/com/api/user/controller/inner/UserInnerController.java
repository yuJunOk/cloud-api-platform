package com.api.user.controller.inner;

import com.api.client.UserFeignClient;
import com.api.common.common.ResponseCode;
import com.api.common.exception.BusinessException;
import com.api.model.domain.UserDo;
import com.api.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    @GetMapping("getUserByAk")
    @Override
    public UserDo getUserByAk(String accessKey) {
        if (StringUtils.hasText(accessKey)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDo::getAccessKey, accessKey);
        try{
            return userService.getOne(queryWrapper);
        }catch (Exception e){
            return null;
        }
    }
}
