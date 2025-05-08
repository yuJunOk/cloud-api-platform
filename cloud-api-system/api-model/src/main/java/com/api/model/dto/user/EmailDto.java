package com.api.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class EmailDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -4446153051730262560L;

    private String email;
}
