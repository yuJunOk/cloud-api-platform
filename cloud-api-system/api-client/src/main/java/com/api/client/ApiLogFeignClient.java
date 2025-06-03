package com.api.client;

import com.api.model.domain.ApiLogDo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@FeignClient(name = "api-service-api", contextId = "apiLog", path = "/api/api/inner/apiLog")
public interface ApiLogFeignClient {

    /**
     * 插入一条日志
     * @param apiLogDo
     * @return
     */
    Long addApiLog(ApiLogDo apiLogDo);

}
