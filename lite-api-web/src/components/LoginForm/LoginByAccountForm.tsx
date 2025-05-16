import React from 'react';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import {Button, Checkbox, Form, Input, Flex, message} from 'antd';
import {useNavigate} from "react-router-dom";
import {LOGIN_FORM_PROPS_KEY} from "@/constant/UserConstant.ts";
import {UserControllerService} from "../../../api/user";
import ResponseCode from "@/models/enum/ResponseCode.ts";
import {getUser} from "@/store/user.ts";

type LoginFormProp = {
    loginName: string;
    loginPwd: string;
    remember: boolean;
}

export const LoginByAccountForm: React.FC = () => {
    // 路由
    const navigate = useNavigate();
    // 消息弹窗
    const [messageApi, contextHolder] = message.useMessage();
    //
    const onFinish = async (values: LoginFormProp) => {
        const res = await UserControllerService.loginByAccount({
            loginName: values.loginName,
            loginPwd: values.loginPwd,
        });
        if (res.code == ResponseCode.SUCCESS){
            if (values.remember){
                localStorage.setItem(LOGIN_FORM_PROPS_KEY, JSON.stringify(values));
            }
            await getUser();
            navigate('/');
        }else {
            messageApi.error(res.message ?? "登录失败，请稍后再试");
        }
    };
    // 进入页面从storage把数据取出来
    const getInitialValues = () => {
        try {
            const savedData = localStorage.getItem(LOGIN_FORM_PROPS_KEY);
            return savedData ? JSON.parse(savedData) : { remember: true };
        } catch (error) {
            console.error("解析 localStorage 数据失败", error);
            return { remember: true };
        }
    };

    return (
        <Form
            name="login"
            initialValues={getInitialValues()}
            style={{ width: 300 }}
            onFinish={onFinish}
        >
            <Form.Item
                name="loginName"
                rules={[{ required: true, message: '请输入账户' }]}
            >
                <Input prefix={<UserOutlined />} placeholder="账户" />
            </Form.Item>
            <Form.Item
                name="loginPwd"
                rules={[{ required: true, message: '请输入密码' }]}
            >
                <Input prefix={<LockOutlined />} type="password" placeholder="密码" />
            </Form.Item>
            <Form.Item>
                <Flex justify="space-between" align="center">
                    <Form.Item name="remember" valuePropName="checked" noStyle>
                        <Checkbox>记住密码</Checkbox>
                    </Form.Item>
                    <a href="/forgetPwd">忘记密码？</a>
                </Flex>
            </Form.Item>

            <Form.Item>
                <Button block type="primary" htmlType="submit" style={{ marginBottom: "10px" }}>
                    登录
                </Button>
                <Button block color="default" variant="filled" onClick={() => navigate("/register")}>
                    注册
                </Button>
            </Form.Item>

            {/*提示弹窗*/}
            {contextHolder}
        </Form>
    );
};