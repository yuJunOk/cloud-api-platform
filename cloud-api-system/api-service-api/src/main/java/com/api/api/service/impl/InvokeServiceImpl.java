package com.api.api.service.impl;

import com.api.api.service.InvokeService;
import com.api.api.utils.DebugUtils;
import com.api.common.common.ResponseCode;
import com.api.common.exception.BusinessException;
import com.api.common.utils.CommonUtils;
import com.api.model.dto.api.ApiDebugDto;
import com.api.model.vo.api.ApiResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author pengYuJun
 */
@Slf4j
@Service
public class InvokeServiceImpl implements InvokeService {

    @Override
    public ApiResponseVo debug(ApiDebugDto apiDebugDto) {
        String url = apiDebugDto.getUrl();
        String method = apiDebugDto.getMethod();
        Map<String, Object> requestParams = apiDebugDto.getRequestParams();
        Map<String, String> requestHeader = apiDebugDto.getRequestHeader();
        Map<String, String> responseHeader = apiDebugDto.getResponseHeader();
        // 校验
        if (CommonUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        try {
            return DebugUtils.request(url, method, requestHeader, requestParams, responseHeader);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
    }

}
