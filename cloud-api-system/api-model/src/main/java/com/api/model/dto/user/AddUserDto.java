package com.api.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class AddUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2525772025497054554L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String loginName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色值
     */
    private String userRole;

    /**
     * 密码
     */
    private String loginPwd;

    /**
     * 密码
     */
    private String checkPwd;
}
