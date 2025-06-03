package com.api.client;

import com.api.common.common.ResponseCode;
import com.api.common.exception.BusinessException;
import com.api.model.bo.LoginUserBo;
import com.api.model.domain.UserDo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static com.api.common.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 * @author pengYuJun
 */
@FeignClient(name = "api-service-user", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    default LoginUserBo getCurrentUser(HttpServletRequest request){
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        LoginUserBo currentUser = (LoginUserBo) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }
        // 可以考虑在这里做全局权限校验
        return currentUser;
    }

    /**
     * 根据accessKey获取User
     * @param accessKey
     * @return
     */
    @GetMapping("/getUserByAk")
    UserDo getUserByAk(String accessKey);
}
