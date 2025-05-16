package com.api.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class LoginByAccountDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1822657603978931789L;

    private String loginName;

    private String loginPwd;
}
