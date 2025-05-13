import React from 'react';
import { MailOutlined, PhoneOutlined } from '@ant-design/icons';
import {Button, Checkbox, Form, Input, Flex} from 'antd';

export const LoginByPhoneForm: React.FC = () => {
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
                name="phone"
                rules={[{ required: true, message: '请输入手机号' }]}
            >
                <Input prefix={<PhoneOutlined />} placeholder="手机号" />
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