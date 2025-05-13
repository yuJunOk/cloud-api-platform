import React from 'react';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Flex } from 'antd';

export const LoginByAccountForm: React.FC = () => {
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
                name="username"
                rules={[{ required: true, message: '请输入账户' }]}
            >
                <Input prefix={<UserOutlined />} placeholder="账户" />
            </Form.Item>
            <Form.Item
                name="password"
                rules={[{ required: true, message: '请输入密码' }]}
            >
                <Input prefix={<LockOutlined />} type="password" placeholder="密码" />
            </Form.Item>
            <Form.Item>
                <Flex justify="space-between" align="center">
                    <Form.Item name="remember" valuePropName="checked" noStyle>
                        <Checkbox>记住密码</Checkbox>
                    </Form.Item>
                    <a href="">忘记密码？</a>
                </Flex>
            </Form.Item>

            <Form.Item>
                <Button block type="primary" htmlType="submit" style={{ marginBottom: "10px" }}>
                    登录
                </Button>
                <Button block color="default" variant="filled">
                    注册
                </Button>
            </Form.Item>
        </Form>
    );
};