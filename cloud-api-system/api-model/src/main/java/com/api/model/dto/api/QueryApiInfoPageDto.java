package com.api.model.dto.api;

import com.api.model.dto.PageDto;
import lombok.Data;

import java.util.Date;

/**
 * 接口信息
 * @author pengYuJun
 */
@Data
public class QueryApiInfoPageDto extends PageDto {

    /**
     * 名称
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;
}