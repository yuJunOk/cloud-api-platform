import React from 'react';
import {LockOutlined, UserAddOutlined} from '@ant-design/icons';
import {Button, Form, Input} from 'antd';

export const RegisterByAccountForm: React.FC = () => {
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