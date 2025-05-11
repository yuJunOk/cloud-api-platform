package com.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengYuJun
 */
@Getter
@AllArgsConstructor
public enum ApiStatusEnum {
    /**
     *
     */
    ONLINE("发布", 1),
    OFFLINE("下线", 0);

    /**
     * 文字
     */
    private final String text;

    /**
     * 值
     */
    private final Integer value;
}
