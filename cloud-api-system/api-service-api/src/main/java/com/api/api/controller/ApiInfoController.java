package com.api.api.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.api.api.service.ApiInfoService;
import com.api.common.common.ResponseCode;
import com.api.common.common.ResponseEntity;
import com.api.common.constant.CommonConstant;
import com.api.common.exception.BusinessException;
import com.api.model.domain.ApiInfoDo;
import com.api.model.domain.UserDo;
import com.api.model.dto.IdBatchDto;
import com.api.model.dto.IdDto;
import com.api.model.dto.api.AddApiInfoDto;
import com.api.model.dto.api.QueryApiInfoPageDto;
import com.api.model.dto.api.UpdateApiInfoDto;
import com.api.model.vo.ApiInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.api.common.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author pengYuJun
 */
@RequestMapping("/")
@RestController
public class ApiInfoController {

    @Resource
    private ApiInfoService apiInfoService;

    // region 增删改查

    /**
     * 增
     *
     * @param addApiInfoDto
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Long> add(@RequestBody AddApiInfoDto addApiInfoDto, HttpServletRequest request) {
        if (addApiInfoDto == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        HttpSession session = request.getSession();
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        // 插入
        Long id = apiInfoService.addApiInfo(addApiInfoDto, request);
        if (id == null) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(id);
    }

    /**
     * 删除
     *
     * @param idDto
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody IdDto idDto) {
        if (idDto == null || idDto.getId() == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 删除
        boolean result = apiInfoService.removeById(idDto.getId());
        if (!result) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(true);
    }

    /**
     * 批量删除
     *
     * @param idBatchDto
     * @return
     */
    @PostMapping("/deleteBatch")
    public ResponseEntity<Boolean> deleteBatch(@RequestBody IdBatchDto idBatchDto) {
        if (idBatchDto == null || CollectionUtil.isEmpty(idBatchDto.getIdList())) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 删除
        boolean result = apiInfoService.removeBatchByIds(idBatchDto.getIdList());
        if (!result) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(true);
    }

    /**
     * 更新
     * @param updateApiInfoDto
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<Boolean> update(@RequestBody UpdateApiInfoDto updateApiInfoDto) {
        if (updateApiInfoDto == null || updateApiInfoDto.getId() == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 校验
        ApiInfoDo apiInfoDo = new ApiInfoDo();
        BeanUtils.copyProperties(updateApiInfoDto, apiInfoDo);
        apiInfoService.validNotNullParams(apiInfoDo);
        // 更新
        boolean result = apiInfoService.updateById(apiInfoDo);
        if (!result) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(true);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("getById")
    public ResponseEntity<ApiInfoDo> getById(Long id) {
        if (id == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 查
        ApiInfoDo apiInfoDo = apiInfoService.getById(id);
        if (apiInfoDo == null) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(apiInfoDo);
    }

    /**
     * 分页查询
     * @param queryApiInfoPageDto
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<Page<ApiInfoVo>> getByPage(@RequestBody QueryApiInfoPageDto queryApiInfoPageDto){
        if (queryApiInfoPageDto == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        UserDo userQuery = new UserDo();
        BeanUtils.copyProperties(queryApiInfoPageDto, userQuery);
        long current = queryApiInfoPageDto.getCurrent();
        long size = queryApiInfoPageDto.getPageSize();
        String sortField = queryApiInfoPageDto.getSortField();
        String sortOrder = queryApiInfoPageDto.getSortOrder();
        // 限制爬虫
        if (size > CommonConstant.LIMIT_PAGE_SIZE){
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<ApiInfoDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(queryApiInfoPageDto.getName()), ApiInfoDo::getName, queryApiInfoPageDto.getName())
                .like(StringUtils.hasText(queryApiInfoPageDto.getUrl()), ApiInfoDo::getUrl, queryApiInfoPageDto.getUrl())
                .eq(queryApiInfoPageDto.getStatus() != null, ApiInfoDo::getStatus, queryApiInfoPageDto.getStatus())
                .eq(StringUtils.hasText(queryApiInfoPageDto.getMethod()), ApiInfoDo::getMethod, queryApiInfoPageDto.getMethod());
        Page<ApiInfoDo> page = apiInfoService.page(new Page<>(current, size), queryWrapper);
        // 排序 (可以多排序)
        if (StringUtils.hasText(sortField)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(CommonConstant.SORT_ORDER_ASC.equals(queryApiInfoPageDto.getSortOrder()));
            orderItem.setColumn(queryApiInfoPageDto.getSortField());
            page.addOrder(orderItem);
        }
         Page<ApiInfoVo> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(page.getRecords().stream().map(ApiInfoVo::new).toList());
        return ResponseEntity.success(voPage);
    }

    // endregion
}
