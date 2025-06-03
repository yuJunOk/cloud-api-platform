package com.api.api.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.api.api.mapper.ApiInfoMapper;
import com.api.api.service.InvokeService;
import com.api.api.utils.InvokeApiUtils;
import com.api.client.UserFeignClient;
import com.api.common.common.ResponseCode;
import com.api.common.exception.BusinessException;
import com.api.common.utils.CommonUtils;
import com.api.model.bo.LoginUserBo;
import com.api.model.domain.ApiInfoDo;
import com.api.model.dto.api.ApiDebugDto;
import com.api.model.dto.api.ApiInvokeByIdDto;
import com.api.model.enums.ApiStatusEnum;
import com.api.model.vo.api.ApiResponseVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author pengYuJun
 */
@Slf4j
@Service
public class InvokeServiceImpl implements InvokeService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private ApiInfoMapper apiInfoMapper;

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
            return InvokeApiUtils.request(url, method, requestHeader, requestParams, responseHeader);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public ApiResponseVo invokeById(ApiInvokeByIdDto apiInvokeByIdDto, HttpServletRequest request) {
        if (ObjectUtil.hasNull(apiInvokeByIdDto, apiInvokeByIdDto.getId())) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        ApiInfoDo apiInfoDo = apiInfoMapper.selectById(apiInvokeByIdDto.getId());
        if (ObjectUtil.isEmpty(apiInfoDo)) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        if (!apiInfoDo.getStatus().equals(ApiStatusEnum.ONLINE.getValue())){
            throw new BusinessException(ResponseCode.OPERATION_ERROR, "接口未开启");
        }
        // 获取用户信息
        LoginUserBo currentUser = userFeignClient.getCurrentUser(request);
        // 请求
        try {
            return InvokeApiUtils.invoke(apiInvokeByIdDto, apiInfoDo, currentUser);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
    }

}
