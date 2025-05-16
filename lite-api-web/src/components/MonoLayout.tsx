import React from 'react';
import {Layout} from 'antd';
import {Outlet} from "react-router-dom";
import {GlobalFooter} from "@/components/GlobalFooter.tsx";

const { Content } = Layout;

export const MonoLayout: React.FC = () => {

    return (
        <Layout className="layout">
            <Content className="content">
                <Outlet/>
            </Content>
            <GlobalFooter />
        </Layout>
    );
};