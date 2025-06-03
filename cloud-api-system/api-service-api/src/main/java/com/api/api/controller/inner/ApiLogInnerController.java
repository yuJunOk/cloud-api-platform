package com.api.api.controller.inner;

import com.api.api.service.ApiLogService;
import com.api.client.ApiLogFeignClient;
import com.api.model.domain.ApiLogDo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/inner/apiLog")
public class ApiLogInnerController implements ApiLogFeignClient {

    @Resource
    private ApiLogService apiLogService;

    @Override
    public Long addApiLog(ApiLogDo apiLogDo) {
        try{
            apiLogService.save(apiLogDo);
        }catch (Exception e){
            return null;
        }
        return apiLogDo.getId();
    }
}
