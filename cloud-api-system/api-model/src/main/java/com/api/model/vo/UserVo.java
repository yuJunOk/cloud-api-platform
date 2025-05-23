package com.api.model.vo;

import com.api.model.domain.UserDo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author pengYuJun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -8460210827254062525L;

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
     * 签名 accessKey
     */
    private String accessKey;

    /**
     * 签名 secretKey
     */
    private String secretKey;

    /**
     * 头像链接
     */
    private String userAvatar;

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
     * 用户状态
     */
    private Integer status;

    /**
     * 角色值
     */
    private String userRole;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 根据User构建UserVo
     * @param userDo 表用户类
     */
    public UserVo(UserDo userDo) {
        this.id = userDo.getId();
        this.userName = userDo.getUserName();
        this.loginName = userDo.getLoginName();
        this.userAvatar = userDo.getUserAvatar();
        this.gender = userDo.getGender();
        this.phone = userDo.getPhone();
        this.email = userDo.getEmail();
        this.userRole = userDo.getUserRole();
        this.createTime = userDo.getCreateTime();
    }
}
