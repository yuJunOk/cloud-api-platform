package com.api.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class ResetPwdByEmailDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 809739062921679424L;

    private String email;

    private String captcha;

    private String loginPwd;

    private String checkPwd;
}
