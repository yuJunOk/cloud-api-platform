import React from 'react';
import { LogoutOutlined, UserOutlined } from '@ant-design/icons';
import {Avatar, type MenuProps} from 'antd';
import { Dropdown, Space } from 'antd';
import {useSnapshot} from "valtio/react";
import store from "@/store/user.ts";
import {UserControllerService} from "../../api/user";
import {useNavigate} from "react-router-dom";

const App: React.FC = () => {
    //
    const {loginUser} = useSnapshot(store);
    //
    const navigate = useNavigate();

    const logout = async () => {
        await UserControllerService.logout();
        navigate("login", {replace: true});
    }

    const items: MenuProps['items'] = [
        {
            key: '1',
            label: (
                <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
                    个人中心
                </a>
            ),
            icon: <UserOutlined />,
        },
        {
            key: '2',
            label: ( <div onClick={logout}>退出登录</div> ),
            icon: <LogoutOutlined />,
            danger: true,
        },
    ];

    return (
        <Dropdown menu={{ items }}>
            <Space style={{cursor: "pointer"}} onClick={(e) => e.preventDefault()}>
                <Avatar src={loginUser?.userAvatar} />
                <div>{loginUser?.userName}</div>
            </Space>
        </Dropdown>
    );
};

export default App;