package com.api.model.dto.api;

import lombok.Data;

import java.util.Map;

/**
 * @author pengYuJun
 */
@Data
public class ApiInvokeByIdDto {
    /**
     * 请求id
     */
    private Long id;
    /**
     * 请求头
     */
    private Map<String, String> requestHeader;
    /**
     * 请求参数
     */
    private Map<String, Object> requestParams;
    /**
     * 响应头
     */
    private Map<String, String> responseHeader;
}
