import {Button, Card, Flex, Space, Typography} from 'antd';
import React from "react";
import {WelcomeCard} from "@/components/WelcomeCard/WelcomeCard.tsx";
import {useNavigate} from "react-router-dom";

const { Title, Paragraph  } = Typography;

export const HomePage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <Card style={{minHeight: "100%", paddingTop: 50}}>
            <Flex wrap={true} justify={'center'} gap={80}>
                <Flex vertical={true} gap={20}>
                    <Title level={1}>母鸡API开放平台🎉</Title>
                    <Paragraph style={{fontSize:"large"}}>母鸡API开放平台是一个为用户和开发者提供全面API接口调用服务的平台 🛠</Paragraph>
                    <Paragraph style={{fontSize:"large"}} strong>—— 极速响应，让速度为您见证一切</Paragraph>
                    <Space>
                        <Button type="primary" shape="round" size="large"
                                onClick={() => navigate("/api/list")}>开始使用</Button>
                        <Button shape="round" size="large">查看文档</Button>
                    </Space>
                </Flex>
                <WelcomeCard />
            </Flex>
        </Card>
    );
};