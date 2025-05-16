import React from 'react';
import {Layout} from 'antd';
import {Outlet} from "react-router-dom";
import './BasicLayout.css'
import {BasicHeader} from "@/components/BasicHeader.tsx";
import {GlobalFooter} from "@/components/GlobalFooter.tsx";

const { Header, Content } = Layout;

export const BasicLayout: React.FC = () => {

    return (
        <Layout className="layout">
            <Header className="header">
                <BasicHeader />
            </Header>
            <Content className="content">
                <Outlet/>
            </Content>
            <GlobalFooter />
        </Layout>
    );
};