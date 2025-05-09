package com.api.user.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.api.common.common.ResponseCode;
import com.api.common.common.ResponseEntity;
import com.api.common.exception.BusinessException;
import com.api.common.utils.CommonUtils;
import com.api.common.utils.MailUtils;
import com.api.model.bo.LoginUserBo;
import com.api.model.domain.UserDo;
import com.api.model.dto.user.AddUserDto;
import com.api.model.dto.user.UpdateUserDto;
import com.api.user.mapper.UserMapper;
import com.api.user.service.UserService;
import com.api.user.utils.ChineseNameGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.api.common.constant.UserConstant.*;

/**
* @author pengYuJun
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2025-05-08 12:38:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo>
    implements UserService {

    @Resource
    private MailUtils mailUtils;

    private void validOnlyParams(String phone, String email, String loginName) {
        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
        // 验证电话
        if (StringUtils.hasText(phone)) {
            queryWrapper.eq(UserDo::getPhone, phone);
            if (this.count(queryWrapper) > 0){
                throw new BusinessException(ResponseCode.PARAMS_ERROR, "电话不能重复");
            }
        }
        // 验证邮箱
        if (StringUtils.hasText(email)) {
            queryWrapper.clear();
            queryWrapper.eq(UserDo::getEmail, email);
            if (this.count(queryWrapper) > 0){
                throw new BusinessException(ResponseCode.PARAMS_ERROR, "邮箱不能重复");
            }
        }
        // 验证账户
        if (StringUtils.hasText(loginName)) {
            queryWrapper.clear();
            queryWrapper.eq(UserDo::getLoginName, loginName);
            if (this.count(queryWrapper) > 0){
                throw new BusinessException(ResponseCode.PARAMS_ERROR, "账户不能重复");
            }
        }
    }

    @Override
    public Long addUser(AddUserDto addUserDto) {
        // 1. 校验
        if (CommonUtils.isAnyBlank(addUserDto.getEmail(), addUserDto.getLoginPwd(), addUserDto.getPhone(), addUserDto.getUserName())) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 账户不能包含特殊字符
        Matcher matcher = Pattern.compile(VALID_LOGIN_NAME_PATTERN).matcher(addUserDto.getLoginName());
        if (matcher.find()){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        // 密码与校验密码相同
        if (!addUserDto.getLoginPwd().equals(addUserDto.getCheckPwd())){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "密码与校验密码不相同");
        }
        // 不能重复验证
        validOnlyParams(addUserDto.getPhone(), addUserDto.getEmail(), addUserDto.getLoginName());
        // 2. 加密
        String md5Pwd = DigestUtils.md5DigestAsHex((PASSWORD_SALT + addUserDto.getLoginPwd()).getBytes());
        // 3. 分配ak、sk
        String accessKey = DigestUtils.md5DigestAsHex((PASSWORD_SALT + addUserDto.getLoginName() + RandomUtil.randomNumbers(5)).getBytes());
        String secretKey = DigestUtils.md5DigestAsHex((PASSWORD_SALT + addUserDto.getLoginName() + RandomUtil.randomNumbers(8)).getBytes());
        // 4. 插入数据
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(addUserDto, userDo);
        userDo.setLoginPwd(md5Pwd);
        userDo.setAccessKey(accessKey);
        userDo.setSecretKey(secretKey);
        boolean saveResult = this.save(userDo);
        if (!saveResult){
            throw new BusinessException(ResponseCode.OPERATION_ERROR, "注册失败");
        }
        return userDo.getId();
    }

    @Override
    public boolean updateUserById(UpdateUserDto updateUserDto) {
        if (updateUserDto == null || updateUserDto.getId() == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 看数据库里有没有该用户
        UserDo userDo = this.getById(updateUserDto.getId());
        if (userDo == null || userDo.getId() == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        // 验证不能重复
        String validPhone = null;
        String validEmail = null;
        String validLoginName = null;
        if (!updateUserDto.getLoginName().equals(userDo.getLoginName())) {
            validLoginName = userDo.getLoginName();
        }
        if (!updateUserDto.getPhone().equals(userDo.getPhone())) {
            validPhone = userDo.getPhone();
        }
        if (!updateUserDto.getEmail().equals(userDo.getEmail())) {
            validEmail = userDo.getEmail();
        }
        validOnlyParams(validPhone, validEmail, validLoginName);
        // 验证通过，开始更新用户
        UserDo updateUserDo = new UserDo();
        BeanUtils.copyProperties(updateUserDto, updateUserDo);
        return this.updateById(updateUserDo);
    }

    @Override
    public LoginUserBo loginByEmail(String email, String loginPwd, HttpServletRequest request) {
        // 1. 校验
        if (CommonUtils.isAnyBlank(email, loginPwd)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 邮箱格式
        if (!Validator.isEmail(email)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "邮箱格式错误");
        }
        // 2. 加密
        String md5Pwd = DigestUtils.md5DigestAsHex((PASSWORD_SALT + loginPwd).getBytes());
        // 查询用户信息
        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDo::getEmail, email);
        queryWrapper.eq(UserDo::getLoginPwd, md5Pwd);
        UserDo userDo = this.getOne(queryWrapper);
        if (userDo == null){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "登录失败，账户或密码错误");
        }
        // 用户脱敏
        LoginUserBo loginUserBo = LoginUserBo.doToBo(userDo);
        // LOGIN_NAME_MIN_LEN. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, loginUserBo);
        return loginUserBo;
    }

    @Override
    public long registerByEmail(String loginName, String loginPwd, String checkPwd, String email, String captcha, HttpServletRequest request) {
        // 1. 校验
        if (CommonUtils.isAnyBlank(loginName, loginPwd, checkPwd, email, captcha)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 账户不能包含特殊字符
        Matcher matcher = Pattern.compile(VALID_LOGIN_NAME_PATTERN).matcher(loginName);
        if (matcher.find()){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        // 密码与校验密码相同
        if (!loginPwd.equals(checkPwd)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "密码与校验密码相同");
        }
        // 校验验证码
        String captchaOg = (String) request.getSession().getAttribute(USER_REGISTER_CAPTCHA);
        if (!StringUtils.hasText(captcha)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "验证码已失效，请重新获取");
        }
        if (!captcha.equals(captchaOg)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "验证码不匹配，请重新输入");
        }
        request.getSession().removeAttribute(USER_REGISTER_CAPTCHA);
        // 不能重复验证
        validOnlyParams(null, email, loginName);
        // 2. 加密
        String md5Pwd = DigestUtils.md5DigestAsHex((PASSWORD_SALT + loginPwd).getBytes());
        // 3. 分配ak、sk
        String accessKey = DigestUtils.md5DigestAsHex((PASSWORD_SALT + loginName + RandomUtil.randomNumbers(5)).getBytes());
        String secretKey = DigestUtils.md5DigestAsHex((PASSWORD_SALT + loginName + RandomUtil.randomNumbers(8)).getBytes());
        // 4. 插入数据
        UserDo userDo = new UserDo();
        userDo.setLoginName(loginName);
        userDo.setLoginPwd(md5Pwd);
        userDo.setEmail(email);
        userDo.setAccessKey(accessKey);
        userDo.setSecretKey(secretKey);
        userDo.setUserName(ChineseNameGenerator.generate());
        boolean saveResult = this.save(userDo);
        if (!saveResult){
            throw new BusinessException(ResponseCode.OPERATION_ERROR, "注册失败");
        }
        return userDo.getId();
    }

    @Override
    public void sendRegisterCaptcha(String email, HttpServletRequest request) {
        String captcha = sendCaptcha(email);
        request.getSession().setAttribute(USER_REGISTER_CAPTCHA, captcha);
    }

    @Override
    public void sendResetPwdCaptcha(String email, HttpServletRequest request) {
        String captcha = sendCaptcha(email);
        request.getSession().setAttribute(USER_FORGET_PWD_CAPTCHA, captcha);
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    private String sendCaptcha(String email){
        if (!StringUtils.hasText(email)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 随机生成6为随机数
        String captcha = (Math.random() + "").substring(2, LOGIN_PWD_MIN_LEN);
        boolean result = mailUtils.sendCaptchaMail(captcha, email);
        if (!result) {
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
        return captcha;
    }

    @Override
    public void resetPwdByEmail(String email, String captcha, String loginPwd, String checkPwd, HttpServletRequest request) {
        if (CommonUtils.isAnyBlank(email, loginPwd, checkPwd, captcha)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 密码与校验密码相同
        if (!loginPwd.equals(checkPwd)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "两次密码填写不一致！");
        }
        // 校验验证码
        String captchaOg = (String) request.getSession().getAttribute(USER_FORGET_PWD_CAPTCHA);
        if (!StringUtils.hasText(captcha)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "验证码已失效，请重新获取");
        }
        if (!captcha.equals(captchaOg)){
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "验证码不匹配，请重新输入");
        }
        // 查询用户信息
        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDo::getEmail, email);
        List<UserDo> userDoList = this.list(queryWrapper);
        if (userDoList == null || userDoList.isEmpty()){
            throw new BusinessException(ResponseCode.OPERATION_ERROR, "该邮箱未注册账号");
        }
        if (userDoList.size() > 1){
            throw new BusinessException(ResponseCode.OPERATION_ERROR, "该邮箱被异常注册，请联系管理员");
        }
        UserDo userDo = userDoList.get(0);
        // 加密
        String md5Pwd = DigestUtils.md5DigestAsHex((PASSWORD_SALT + loginPwd).getBytes());
        userDo.setLoginPwd(md5Pwd);
        // 修改密码
        if (!this.updateById(userDo)){
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
    }
}




