import React, {useEffect} from 'react';
import { LogoutOutlined, LoginOutlined } from '@ant-design/icons';
import {Avatar, type MenuProps} from 'antd';
import { Dropdown, Space } from 'antd';
import {useSnapshot} from "valtio/react";
import store, {getUser} from "@/store/user.ts";
import {UserControllerService} from "../../api/user";
import {useNavigate} from "react-router-dom";
import UserRoleEnum from "@/models/enum/UserRoleEnum.ts";

const App: React.FC = () => {
    //
    const {loginUser} = useSnapshot(store);
    //
    const navigate = useNavigate();
    // 退出登录
    const logout = async () => {
        await UserControllerService.logout();
        navigate("login", {replace: true});
    }

    // 重登逻辑
    useEffect(() => {
        getUser();
    }, [loginUser]);

    const visitorMenuItems: MenuProps['items'] = [
        {
            key: '1',
            label: ( <div onClick={() => navigate("/login")}>去登录</div> ),
            icon: <LoginOutlined />,
        },
    ];

    const userMenuItems: MenuProps['items'] = [
        // {
        //     key: '1',
        //     label: (
        //         <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
        //             个人中心
        //         </a>
        //     ),
        //     icon: <UserOutlined />,
        // },
        {
            key: '2',
            label: ( <div onClick={logout}>退出登录</div> ),
            icon: <LogoutOutlined />,
            danger: true,
        },
    ];

    return (
        <Dropdown menu={loginUser?.userRole == UserRoleEnum.VISITOR ? {items: visitorMenuItems} : {items: userMenuItems}}>
            <Space style={{cursor: "pointer"}} onClick={(e) => e.preventDefault()}>
                <Avatar src={loginUser?.userAvatar} />
                <div>{loginUser?.userName}</div>
            </Space>
        </Dropdown>
    );
};

export default App;