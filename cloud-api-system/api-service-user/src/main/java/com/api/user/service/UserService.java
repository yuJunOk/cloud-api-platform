package com.api.user.service;

import com.api.model.bo.LoginUserBo;
import com.api.model.domain.UserDo;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author pengYuJun
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2025-05-08 12:38:39
*/
public interface UserService extends IService<UserDo> {

    /**
     * 校验参数
     * @param userDo
     */
    void validAddParams(UserDo userDo);


    /**
     * 根据邮箱登录
     * @param email
     * @param loginPwd
     * @param request
     * @return
     */
    LoginUserBo loginByEmail(String email, String loginPwd, HttpServletRequest request);

    /**
     * 注册
     * @param loginName
     * @param loginPwd
     * @param checkPwd
     * @param email
     * @param captcha
     * @param request
     * @return
     */
    long register(String loginName, String loginPwd, String checkPwd, String email, String captcha, HttpServletRequest request);

    /**
     * 发送注册验证码
     * @param email
     * @param request
     */
    void sendRegisterCaptcha(String email, HttpServletRequest request);

    /**
     * 发送改密验证码
     * @param email
     * @param request
     */
    void sendResetPwdCaptcha(String email, HttpServletRequest request);

    /**
     * 根据邮箱重设密码
     * @param email
     * @param captcha
     * @param loginPwd
     * @param checkPwd
     */
    void resetPwdByEmail(String email, String captcha, String loginPwd, String checkPwd, HttpServletRequest request);
}
