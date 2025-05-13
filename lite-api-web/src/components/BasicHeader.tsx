import {Col, Menu, Row, Space} from "antd";
import logo from "/logo.svg";
import HeaderDropDownMenu from "@/components/HeaderDropDownMenu.tsx";
import {AreaChartOutlined, AppstoreOutlined} from "@ant-design/icons";
import {NavLink, useLocation, useNavigate} from 'react-router-dom';

const items = [
    {
        key: '/api/list',
        label: (
            <NavLink to={"/api/list"}>接口广场</NavLink>
        ),
        icon: <AppstoreOutlined />,
    },
    {
        key: '/statistics',
        label: (
            <NavLink to={"/statistics"}>调用统计</NavLink>
        ),
        icon: <AreaChartOutlined />,
    },
];

export const BasicHeader = () => {
    // 跳转钩子
    const navigate = useNavigate();
    // 路径钩子
    const location = useLocation();

    // dom主体
    return (
        <Row>
            <Col span={4} onClick={() => navigate("/")}>
                <Space style={{cursor: "pointer"}}>
                    <img src={logo} width={32} alt={"logo"} />
                    <div style={{fontSize: "large", fontWeight: "bolder"}}>母鸡API</div>
                </Space>
            </Col>
            <Col span={16}>
                <Menu
                    theme="light"
                    mode="horizontal"
                    items={items}
                    selectedKeys={[location.pathname]}
                />
            </Col>
            <Col span={4}>
                <HeaderDropDownMenu />
            </Col>
        </Row>
    );
};