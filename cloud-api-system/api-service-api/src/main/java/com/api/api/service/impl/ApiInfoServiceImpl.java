package com.api.api.service.impl;

import com.api.api.mapper.ApiInfoMapper;
import com.api.client.UserFeignClient;
import com.api.common.common.ResponseCode;
import com.api.common.exception.BusinessException;
import com.api.model.bo.LoginUserBo;
import com.api.model.domain.ApiInfoDo;
import com.api.model.dto.api.AddApiInfoDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;;
import com.api.api.service.ApiInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author pengYuJun
* @description 针对表【tb_api_info(接口信息)】的数据库操作Service实现
* @createDate 2025-05-10 23:38:55
*/
@Service
public class ApiInfoServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfoDo>
    implements ApiInfoService{

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public void validNotNullParams(ApiInfoDo apiInfoDo) {
        if (!StringUtils.hasText(apiInfoDo.getName())){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "名称不能为空");
        }
        if (!StringUtils.hasText(apiInfoDo.getUrl())){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "URL不能为空");
        }
        if (!StringUtils.hasText(apiInfoDo.getMethod())){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "请求方法不能为空");
        }
        if (apiInfoDo.getStatus() == null){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "接口状态不能为空");
        }
    }

    @Override
    public Long addApiInfo(AddApiInfoDto addApiInfoDto, HttpServletRequest request) {
        //
        ApiInfoDo apiInfoDo = new ApiInfoDo();
        BeanUtils.copyProperties(addApiInfoDto, apiInfoDo);
        // 校验
        validNotNullParams(apiInfoDo);
        // 设置用户id
        LoginUserBo currentUser = userFeignClient.getCurrentUser(request);
        apiInfoDo.setUserId(currentUser.getId());
        // 插入
        this.save(apiInfoDo);
        // 返回id
        return apiInfoDo.getId();
    }
}




