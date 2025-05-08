package com.api.model.bo;

import com.api.model.domain.UserDo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author pengYuJun
 */
@Data
public class LoginUserBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 7738608283609313472L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 角色
     */
    private String userRole;

    /**
     * 头像链接
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 账号
     */
    private String loginName;

    /**
     * 密码
     */
    private String loginPwd;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * do转 bo
     * @param userDo
     * @return
     */
    public static LoginUserBo doToBo(UserDo userDo) {
        LoginUserBo loginUserBo = new LoginUserBo();
        BeanUtils.copyProperties(userDo, loginUserBo);
        return loginUserBo;
    }
}
