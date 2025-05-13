import React from 'react';
import {Layout} from 'antd';
import {Outlet} from "react-router-dom";
import './BasicLayout.css'
import {BasicHeader} from "@/components/BasicHeader.tsx";

const { Header, Content, Footer } = Layout;

const App: React.FC = () => {

    return (
        <Layout className="layout">
            <Header className="header">
                <BasicHeader />
            </Header>
            <Content className="content">
                <Outlet/>
            </Content>
            <Footer className="footer">
                Ant Design ©{new Date().getFullYear()} Created by Ant UED
            </Footer>
        </Layout>
    );
};

export default App;