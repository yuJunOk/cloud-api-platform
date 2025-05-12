package com.api.api.controller;

import com.api.api.service.InvokeService;
import com.api.common.common.ResponseCode;
import com.api.common.common.ResponseEntity;
import com.api.common.exception.BusinessException;
import com.api.model.dto.api.ApiDebugDto;
import com.api.model.vo.api.ApiResponseVo;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/invoke")
public class InvokeController {

    @Resource
    private InvokeService invokeService;

    @PostMapping("debug")
    public ResponseEntity<ApiResponseVo> debug(@RequestBody ApiDebugDto apiDebugDto) {
        if (apiDebugDto == null || !StringUtils.hasText(apiDebugDto.getUrl())) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        ApiResponseVo apiResponseVo = invokeService.debug(apiDebugDto);
        return ResponseEntity.success(apiResponseVo);
    }
}
