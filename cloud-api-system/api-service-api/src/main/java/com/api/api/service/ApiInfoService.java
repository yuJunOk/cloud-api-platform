package com.api.api.service;

import com.api.model.domain.ApiInfoDo;
import com.api.model.dto.api.AddApiInfoDto;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author pengYuJun
* @description 针对表【tb_api_info(接口信息)】的数据库操作Service
* @createDate 2025-05-10 23:38:55
*/
public interface ApiInfoService extends IService<ApiInfoDo> {

    /**
     * 校验非空参数
     * @param apiInfoDo
     */
    void validNotNullParams(ApiInfoDo apiInfoDo);

    /**
     * 新增
     * @param addApiInfoDto
     * @return
     */
    Long addApiInfo(AddApiInfoDto addApiInfoDto, HttpServletRequest request);
}
