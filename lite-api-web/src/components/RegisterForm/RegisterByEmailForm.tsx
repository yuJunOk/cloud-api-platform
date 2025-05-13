import React from 'react';
import {LockOutlined, MailOutlined, UserAddOutlined} from '@ant-design/icons';
import {Button, Form, Input, Flex} from 'antd';

export const RegisterByEmailForm: React.FC = () => {
    const onFinish = (values: any) => {
        console.log('Received values of form: ', values);
    };

    return (
        <Form
            name="login"
            initialValues={{ remember: true }}
            style={{ width: 300 }}
            onFinish={onFinish}
        >
            <Form.Item
                name="loginName"
                rules={[{ required: true, message: '请输入账号' }]}
            >
                <Input prefix={<UserAddOutlined />} placeholder="账号" />
            </Form.Item>
            <Form.Item
                name="loginPwd"
                rules={[{ required: true, message: '请输入密码' }]}
            >
                <Input type={"password"} prefix={<LockOutlined />} placeholder="密码" />
            </Form.Item>
            <Form.Item
                name="checkPwd"
                rules={[{ required: true, message: '请再次输入密码' }]}
            >
                <Input type={"password"} prefix={<LockOutlined />} placeholder="密码" />
            </Form.Item>
            <Form.Item
                name="email"
                rules={[{ required: true, message: '请输入邮箱' }]}
            >
                <Input prefix={<MailOutlined />} placeholder="邮箱" />
            </Form.Item>
            <Form.Item
                name="captcha"
                rules={[{ required: true, message: '去输入验证码' }]}
            >
                <Flex gap={10}>
                    <Input prefix={<MailOutlined />} placeholder="验证码"/>
                    <Button>获取验证码</Button>
                </Flex>
            </Form.Item>

            <Form.Item>
                <Button block type="primary" htmlType="submit" style={{ marginBottom: "10px" }}>
                    注册
                </Button>
                <Button block color="default" variant="filled">
                    登录
                </Button>
            </Form.Item>
        </Form>
    );
};