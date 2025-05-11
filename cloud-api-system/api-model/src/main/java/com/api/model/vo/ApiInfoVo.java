package com.api.model.vo;

import com.api.model.domain.ApiInfoDo;
import com.api.model.domain.UserDo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author pengYuJun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfoVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5725036116359647891L;

    /**
     * id
     */
    private Long id;

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

    /**
     * 接口简介
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 根据do构建vo
     * @param apiInfoDo 表用户类
     */
    public ApiInfoVo(ApiInfoDo apiInfoDo) {
        this.id = apiInfoDo.getId();
        this.name = apiInfoDo.getName();
        this.url = apiInfoDo.getUrl();
        this.status = apiInfoDo.getStatus();
        this.method = apiInfoDo.getMethod();
        this.description = apiInfoDo.getDescription();
        this.createTime = apiInfoDo.getCreateTime();
    }
}
