package com.api.model.vo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author pengYuJun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseVo {
    /**
     * 响应头
     */
    private Map<String, String> responseHeader;
    /**
     * 响应参数
     */
    private String responseBody;
    /**
     * 响应参数类型
     */
    private String contentType;
}
