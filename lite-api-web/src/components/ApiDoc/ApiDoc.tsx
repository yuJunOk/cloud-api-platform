import {Divider, Table, type TableProps} from "antd";
import {CodeViewer} from "@/components/CodeViewer.tsx";
import type {JsonApiDataRule} from "@/models/type/ApiType";
import {useEffect, useState} from "react";

interface Props {
    requestParams?: string | undefined;
    responseParams?: string | undefined;
    responseExample?: string | undefined;
}

const requestCols: TableProps['columns'] = [
    {
        title: '参数名称',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
    },
    {
        title: '示例',
        dataIndex: 'value',
        key: 'value',
    },
    {
        title: '必选',
        dataIndex: 'must',
        key: 'must',
    },
    {
        title: '描述',
        dataIndex: 'description',
        key: 'description',
    },
];

const responseCols: TableProps['columns'] = [
    {
        title: '参数名称',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
    },
    {
        title: '示例',
        dataIndex: 'value',
        key: 'value',
    },
    {
        title: '描述',
        dataIndex: 'description',
        key: 'description',
    },
];

export const ApiDoc = ({requestParams, responseParams, responseExample }: Props) => {
    const [requestParamsData, setRequestParamsData] = useState<JsonApiDataRule[]>([]);
    const [responseParamsData, setResponseParams] = useState<JsonApiDataRule[]>([]);

    useEffect(() => {
        try{
            setRequestParamsData(JSON.parse(requestParams ?? "[]"));
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
        }catch (e) {
            setRequestParamsData([]);
        }
    }, [requestParams]);

    useEffect(() => {
        try{
            setResponseParams(JSON.parse(responseParams ?? "[]"));
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
        }catch (e) {
            setResponseParams([]);
        }
    }, [responseParams]);

    return (
        <>
            <Divider orientation="left">请求参数说明</Divider>
            <Table columns={requestCols} dataSource={requestParamsData} size={"small"} pagination={false} style={{height: 200, overflowY: "auto"}}/>
            <Divider orientation="left">响应参数说明</Divider>
            <Table columns={responseCols} dataSource={responseParamsData} size={"small"} pagination={false} style={{height: 200, overflowY: "auto"}}/>
            <Divider orientation="left">返回示例</Divider>
            <CodeViewer height={200} value={responseExample} language={"json"}/>
        </>
    );
};