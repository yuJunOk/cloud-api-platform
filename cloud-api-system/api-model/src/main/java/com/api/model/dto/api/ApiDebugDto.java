package com.api.model.dto.api;

import lombok.Data;

import java.util.Map;

/**
 * @author pengYuJun
 */
@Data
public class ApiDebugDto {
    /**
     * 请求路径
     */
    private String url;
    /**
     * 请求方法
     */
    private String method;
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
