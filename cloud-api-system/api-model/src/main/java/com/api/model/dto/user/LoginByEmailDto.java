package com.api.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class LoginByEmailDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2339040579134251257L;

    private String email;

    private String loginPwd;
}
