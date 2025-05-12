package com.api.api.service;

import com.api.model.dto.api.ApiDebugDto;
import com.api.model.vo.api.ApiResponseVo;

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

}
