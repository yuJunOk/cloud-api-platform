import {Button, Card, Flex, Input, Space, Typography, message} from "antd";
import {BugOutlined} from '@ant-design/icons';
import {ApiInfoControllerService, type ApiInfoVo} from "../../../api/api";
import ResponseCode from "@/models/enum/ResponseCode.ts";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

const { Search } = Input;

export const ApiListPage = () => {
    // 消息弹窗
    const [messageApi, contextHolder] = message.useMessage();
    // api列表
    const [apiListData, setApiListData] = useState<ApiInfoVo[]>([]);
    // 搜索框内容
    const [searchText, setSearchText] = useState<string>("");

    //进入页面运行一次
    useEffect(() => {
        loadApiList();
    },[])

    // 搜索api列表
    const loadApiList = async () => {
        const res = await ApiInfoControllerService.search(searchText);
        if (res.code === ResponseCode.SUCCESS) {
            setApiListData(res.data ?? []);
        }else {
            messageApi.error(res.message ?? "搜索失败，请稍后再试");
        }
    }

    // 跳转
    const navigate = useNavigate();

    return (
        <div style={{ margin: "24px auto", width: "88%"}}>
            <Card style={{ textAlign: "center", marginBottom: "36px" }}>
                <Search
                    placeholder="搜索"
                    allowClear
                    enterButton="Search"
                    size="large"
                    onSearch={loadApiList}
                    value={searchText}
                    onChange={(e) => setSearchText(e.target.value)}
                    style={{ width: "50%", marginBottom: 36 }}
                />
            </Card>
            <Flex wrap={true} gap={22}>
                {apiListData.map((item) => (
                    <Card title={<Space><BugOutlined />{item.name}</Space>}
                          variant="borderless"
                          extra={<Button color="primary" variant="outlined"
                                         onClick={() => navigate("/api/info?id=" + item.id)}>查看</Button>}
                          style={{width:'24%'}}>
                        <Typography.Paragraph ellipsis={{rows: 3, tooltip:true}}>
                            {item.description}
                        </Typography.Paragraph>
                    </Card>
                ))}
            </Flex>

            {/*提示弹窗*/}
            {contextHolder}
        </div>
    );
};