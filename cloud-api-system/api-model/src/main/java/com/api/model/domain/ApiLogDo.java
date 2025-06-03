package com.api.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 接口调用日志表
 * @author pengYuJun
 * @TableName tb_api_log
 */
@TableName(value ="tb_api_log")
@Data
public class ApiLogDo {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 调用人
     */
    private Long userId;

    /**
     * 调用接口
     */
    private Long apiId;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应数据
     */
    private String responseParams;

    /**
     * 响应码
     */
    private Integer responseCode;

    /**
     * 客户端IP
     */
    private String ipAddress;

    /**
     * 设备信息
     */
    private String userAgent;

    /**
     * 调用时长
     */
    private Integer duration;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
}