import {Card, Flex, Space, Typography} from "antd";
import React from "react";
import background from "@/assets/images/background.png"
import logo from "/logo.svg";
import {LoginForm} from "@/components/LoginForm/LoginForm.tsx";
import {RegisterForm} from "@/components/RegisterForm/RegisterForm.tsx";
import {ForgetPwdForm} from "@/components/ForgetPwdForm/ForgetPwdForm.tsx";
import {useLocation} from "react-router-dom";

const { Title, Paragraph } = Typography;

const LoginPage: React.FC = () => {
    const location = useLocation();
    // 路由
    return (
        <Card style={{minHeight: "100%", paddingTop: 50, backgroundImage: `url(${background})`, backgroundSize:"100% 100%"}} >
            <Flex align="center" vertical={true}>
                <Title>
                    <Space>
                        <img src={logo} width={46} alt={"logo"}/>
                        母鸡API接口开放平台
                    </Space>
                </Title>
                <Paragraph italic={true} style={{fontSize: "medium"}}>母鸡API接口开放平台致力于提供稳定、安全、高效的接口调用服务</Paragraph>
                {/* 表单 */}
                {location.pathname == "/login" && <LoginForm />}
                {location.pathname == "/register" && <RegisterForm />}
                {location.pathname == "/forgetPwd" && <ForgetPwdForm />}
            </Flex>
        </Card>
    );
};

export default LoginPage;