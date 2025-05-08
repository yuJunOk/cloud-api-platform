package com.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengYuJun
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    /**
     *
     */
    SUPER("超管", "super"),
    ADMIN("管理员", "admin"),
    USER("普通用户", "user"),
    BAN("封禁账户", "ban");

    /**
     * 文字
     */
    private final String text;

    /**
     * 值
     */
    private final String value;
}
