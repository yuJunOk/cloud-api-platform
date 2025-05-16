import {App, Card, Descriptions, type DescriptionsProps, Tabs, type TabsProps, Tag, Typography} from "antd";
import {BugOutlined, FileExclamationOutlined, FileOutlined} from "@ant-design/icons";
import {ApiDoc} from "@/components/ApiDoc/ApiDoc.tsx";
import {InvokePanel} from "@/components/InvockPanel/InvokePanel.tsx";
import {ResponseCodeTable} from "@/components/ResponseCodeTable.tsx";
import {useEffect, useState} from "react";
import {ApiInfoControllerService, type ApiInfoDo} from "../../../api/api";
import {useLocation} from "react-router-dom";
import ResponseCode from "@/models/enum/ResponseCode.ts";

export const ApiInfoPage = () => {

    const { message } = App.useApp();

    const [apiInfo, setApiInfo] = useState<ApiInfoDo>({});

    // 基本信息
    const basicInfoItems: DescriptionsProps['items'] = [
        {
            key: '1',
            span: 1,
            label: '请求URL',
            children: <Typography.Paragraph copyable>{apiInfo.url}</Typography.Paragraph>,
        },
        {
            key: '2',
            span: 1,
            label: '请求方法',
            children: <Tag color="blue">{apiInfo.method}</Tag>,
        },
        {
            key: '5',
            span: 1,
            label: '请求状态',
            children: <Tag color={apiInfo.status == 1 ? "blue" : "red"}>{apiInfo.status == 1 ? "发布" : "下线"}</Tag>,
        },
        {
            key: '4',
            span: 3,
            label: '请求简介',
            children: <Typography.Paragraph ellipsis={{rows: 2, expandable: 'collapsible'}}>{apiInfo.description}</Typography.Paragraph>,
        },
    ];

    // tabs页
    const tabsItems: TabsProps['items'] = [
        {
            key: '1',
            label: 'API文档',
            icon: <FileOutlined />,
            children: <ApiDoc
                requestParams={apiInfo.requestParams}
                responseParams={apiInfo.responseParams}
                responseExample={apiInfo.responseExample} />,
        },
        {
            key: '2',
            label: '在线调试工具',
            icon: <BugOutlined />,
            children: <InvokePanel
                id={apiInfo.id} url={apiInfo.url} method={apiInfo.method} requestParams={apiInfo.requestParams} />,
        },
        {
            key: '3',
            label: '响应码参照',
            icon: <FileExclamationOutlined />,
            children: <ResponseCodeTable />,
        },
    ];

    // 获取 location 对象
    const location = useLocation();

    // 获取数据
    useEffect(() => {
        // 解析查询参数
        const queryParams = new URLSearchParams(location.search);
        // 获取 id=1 中的 1
        const id = queryParams.get("id");
        if (!id){
            message.error("页面url异常，无法获取数据");
            return;
        }
        ApiInfoControllerService.getById(Number(id)).then((result) => {
            if (result.code == ResponseCode.SUCCESS) {
                setApiInfo(result.data ?? {})
            }else {
                message.error(result.message ?? "获取数据失败，请稍后再试");
            }
        });
    }, []);

    return (
        <div style={{margin: "24px auto", width: "88%"}}>
            <Card title={"Say Hello"} style={{marginBottom:'20px'}}>
                <Descriptions items={basicInfoItems} />
            </Card>
            <Card>
                <Tabs defaultActiveKey="1" items={tabsItems} />
            </Card>
        </div>
    );
};