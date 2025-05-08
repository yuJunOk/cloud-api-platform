package com.api.common.constant;

/**
 * @author pengYuJun
 */
public interface UserConstant {

    /**
     * 盐值，混淆密码
     */
    String PASSWORD_SALT = "MyGoodPasswordSalt";

    /**
     * 用户登录态值
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户注册验证码
     */
    String USER_REGISTER_CAPTCHA = "userRegisterCaptcha";

    /**
     * 用户改密验证码
     */
    String USER_FORGET_PWD_CAPTCHA = "userForgetPwdCaptcha";

    /**
     * 检验账号是否包含特殊字符正则表达式
     */
    String VALID_LOGIN_NAME_PATTERN =  "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 账号最小长度
     */
    int LOGIN_NAME_MIN_LEN = 4;

    /**
     * 密码最小长度
     */
    int LOGIN_PWD_MIN_LEN = 8;

}
