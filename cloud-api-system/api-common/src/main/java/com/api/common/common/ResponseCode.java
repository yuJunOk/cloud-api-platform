package com.api.common.common;

import lombok.Getter;

/**对服务响应信息枚举类
 *
 * @email: pengyujun53@163.com
 * @author: peng_YuJun
 * @date: 2022/9/18
 * @time: 9:47
 */
@Getter
public enum ResponseCode {

    /**
     * 操作成功
     */
    SUCCESS("操作成功", 0),
    /**
     * 参数错误
     */
    PARAMS_ERROR("请求参数错误", 40000),
    /**
     * 未登录
     */
    UNAUTHORIZED("未登录", 40100),
    /**
     * 操作无权限
     */
    FORBIDDEN("操作无权限", 40300),
    /**
     * 参数错误
     */
    NOT_FOUND("请求数据不存在", 40400),
    /**
     * 系统内部异常
     */
    SYSTEM_ERROR("系统内部异常", 50000),
    /**
     * 操作失败
     */
    OPERATION_ERROR("操作失败", 50001);

    private final String message;
    private final long code;

    ResponseCode(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
