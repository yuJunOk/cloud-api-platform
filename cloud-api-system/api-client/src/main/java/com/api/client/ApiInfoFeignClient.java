package com.api.client;

import com.api.model.domain.ApiInfoDo;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 接口数据服务
 * @author pengYuJun
 */
@FeignClient(name = "api-service-api", contextId = "apiInfo", path = "/api/api/inner/apiInfo")
public interface ApiInfoFeignClient {
    /**
     * 获取Api接口数据
     * @param path
     * @param method
     * @return
     */
    ApiInfoDo getApiInfo(String path, String method);
}
