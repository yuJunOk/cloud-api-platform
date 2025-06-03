package com.api.api.controller.inner;

import com.api.api.service.ApiInfoService;
import com.api.client.ApiInfoFeignClient;
import com.api.model.domain.ApiInfoDo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pengYuJun
 */
@RestController
@RequestMapping("/inner/apiInfo")
public class ApiInfoInnerController implements ApiInfoFeignClient {

    @Resource
    private ApiInfoService apiInfoService;

    @Override
    public ApiInfoDo getApiInfo(String path, String method) {
        // 如果带参数，去除第一个？和之后后的参数
        if (path.contains("?")) {
            path = path.substring(0, path.indexOf("?"));
        }
        if (path.startsWith("http://")) {
            path = path.substring(7);
        }
        if (path.startsWith("https://")) {
            path = path.substring(8);
        }
        LambdaQueryWrapper<ApiInfoDo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiInfoDo::getMethod, method);
        lambdaQueryWrapper.like(ApiInfoDo::getUrl, path);
        try {
            return apiInfoService.getOne(lambdaQueryWrapper);
        }catch (Exception e){
            return null;
        }
    }
}
