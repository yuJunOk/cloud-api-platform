package com.api.user.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.api.common.common.ResponseCode;
import com.api.common.common.ResponseEntity;
import com.api.common.exception.BusinessException;
import com.api.common.utils.CommonUtils;
import com.api.model.bo.LoginUserBo;
import com.api.model.domain.UserDo;
import com.api.model.dto.IdBatchDto;
import com.api.model.dto.IdDto;
import com.api.model.dto.user.EmailDto;
import com.api.model.dto.user.LoginByEmailDto;
import com.api.model.dto.user.ResetPwdByEmailDto;
import com.api.model.dto.user.UserRegisterDto;
import com.api.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.api.common.constant.UserConstant.USER_LOGIN_STATE;
import static com.api.common.constant.UserConstant.USER_REGISTER_CAPTCHA;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 增
     *
     * @param userDo
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Long> add(@RequestBody UserDo userDo) {
        if (userDo == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 校验
        userService.validAddParams(userDo);
        // 插入
        boolean result = userService.save(userDo);
        if (!result) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(userDo.getId());
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
        boolean result = userService.removeById(idDto.getId());
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
        boolean result = userService.removeBatchByIds(idBatchDto.getIdList());
        if (!result) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(true);
    }

    /**
     * 更新
     * @param userDo
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<Boolean> update(@RequestBody UserDo userDo) {
        if (userDo == null || userDo.getId() == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 更新
        boolean result = userService.updateById(userDo);
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
    public ResponseEntity<UserDo> getById(Long id) {
        if (id == null) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 查
        UserDo userDo = userService.getById(id);
        if (userDo == null) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(userDo);
    }

    // endregion

    /**
     * 根据email登录
     * @param loginByEmailDto
     * @param request
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<LoginUserBo> login(@RequestBody LoginByEmailDto loginByEmailDto, HttpServletRequest request) {
        if (loginByEmailDto == null || CommonUtils.isAnyBlank(loginByEmailDto.getEmail(), loginByEmailDto.getLoginPwd())) {
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        // 登录
        LoginUserBo loginUserBo = userService.loginByEmail(loginByEmailDto.getEmail(), loginByEmailDto.getLoginPwd(), request);
        // 返回登录结果
        if (loginUserBo == null) {
            return new ResponseEntity<>(ResponseCode.OPERATION_ERROR);
        }
        return ResponseEntity.success(loginUserBo);
    }

    /**
     * 注册
     *
     * @param userRegisterDto
     * @param request
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Long> register(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest request) {
        if (userRegisterDto == null){
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        String loginName = userRegisterDto.getLoginName();
        String loginPwd = userRegisterDto.getLoginPwd();
        String checkPwd = userRegisterDto.getCheckPwd();
        String email = userRegisterDto.getEmail();
        String captcha = userRegisterDto.getCaptcha();
        if (CommonUtils.isAnyBlank(loginName, loginPwd, checkPwd, email, captcha)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        long result = userService.register(loginName, loginPwd, checkPwd, email,  captcha, request);
        return ResponseEntity.success(result);
    }

    @PostMapping("resetPwdByEmail")
    public ResponseEntity<?> resetPwdByEmail(@RequestBody ResetPwdByEmailDto resetPwdByEmailDto, HttpServletRequest request){
        if (resetPwdByEmailDto == null){
            return new ResponseEntity<>(ResponseCode.PARAMS_ERROR);
        }
        String email = resetPwdByEmailDto.getEmail();
        String loginPwd = resetPwdByEmailDto.getLoginPwd();
        String checkPwd = resetPwdByEmailDto.getCheckPwd();
        String captcha = resetPwdByEmailDto.getCaptcha();
        if (CommonUtils.isAnyBlank(email, loginPwd, checkPwd, captcha)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        userService.resetPwdByEmail(email, captcha, loginPwd, checkPwd, request);
        return ResponseEntity.success();
    }

    /**
     * 发送注册验证码
     * @param emailDto
     * @param request
     * @return
     */
    @PostMapping("sendRegisterCaptcha")
    public ResponseEntity<?> sendRegisterCaptcha(@RequestBody EmailDto emailDto, HttpServletRequest request) {
        userService.sendRegisterCaptcha(emailDto.getEmail(), request);
        return ResponseEntity.success();
    }

    /**
     * 发送修改密码验证码
     * @param emailDto
     * @param request
     * @return
     */
    @PostMapping("sendResetPwdCaptcha")
    public ResponseEntity<?> sendResetPwdCaptcha(@RequestBody EmailDto emailDto, HttpServletRequest request) {
        userService.sendResetPwdCaptcha(emailDto.getEmail(), request);
        return ResponseEntity.success();
    }

    /**
     * 从session中查询当前用户
     * @param request
     * @return
     */
    @GetMapping("/current")
    public ResponseEntity<LoginUserBo> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        LoginUserBo currentUser = (LoginUserBo) userObj;
        // 再查询，用户数据是否有效
        // todo: 校验用户是否合法
        try{
            Long userId = currentUser.getId();
            UserDo userDo = userService.getById(userId);
            LoginUserBo loginUserBo = new LoginUserBo();
            BeanUtils.copyProperties(userDo, loginUserBo);
            return ResponseEntity.success(loginUserBo);
        }catch (Exception e){
            return ResponseEntity.failed(ResponseCode.UNAUTHORIZED);
        }
    }
}
