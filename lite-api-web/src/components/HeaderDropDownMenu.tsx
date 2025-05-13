import React from 'react';
import { LogoutOutlined, UserOutlined } from '@ant-design/icons';
import {Avatar, type MenuProps} from 'antd';
import { Dropdown, Space } from 'antd';
import logo from "/logo.svg";

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
        label: (
            <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com">
                退出登录
            </a>
        ),
        icon: <LogoutOutlined />,
        danger: true,
    },
];

const App: React.FC = () => (
    <Dropdown menu={{ items }}>
        <Space style={{cursor: "pointer"}} onClick={(e) => e.preventDefault()}>
            <Avatar src={logo} />
            <div>游客</div>
        </Space>
    </Dropdown>
);

export default App;