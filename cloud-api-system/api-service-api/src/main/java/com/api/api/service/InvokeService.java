package com.api.api.service;

import com.api.model.dto.api.ApiDebugDto;
import com.api.model.dto.api.ApiInvokeByIdDto;
import com.api.model.vo.api.ApiResponseVo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author pengYuJun
 */
public interface InvokeService {

    /**
     * debug方法
     * @param apiDebugDto
     * @return
     */
    ApiResponseVo debug(ApiDebugDto apiDebugDto);

    /**
     * invoke方法
     * @param apiInvokeByIdDto
     * @param request
     * @return
     */
    ApiResponseVo invokeById(ApiInvokeByIdDto apiInvokeByIdDto, HttpServletRequest request);
}
