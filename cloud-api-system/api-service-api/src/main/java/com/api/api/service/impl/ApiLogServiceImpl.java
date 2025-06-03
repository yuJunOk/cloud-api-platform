package com.api.api.service.impl;

import com.api.api.mapper.ApiLogMapper;
import com.api.api.service.ApiLogService;
import com.api.model.domain.ApiLogDo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author pengYuJun
* @description 针对表【tb_api_log(接口调用日志表)】的数据库操作Service实现
* @createDate 2025-05-22 16:15:46
*/
@Service
public class ApiLogServiceImpl extends ServiceImpl<ApiLogMapper, ApiLogDo>
    implements ApiLogService {

}




