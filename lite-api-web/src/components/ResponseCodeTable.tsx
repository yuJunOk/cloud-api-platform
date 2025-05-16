import {Table, type TableProps} from "antd";

const requestCols: TableProps['columns'] = [
    {
        title: '参数名称',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: '错误码',
        dataIndex: 'code',
        key: 'code',
    },
    {
        title: '描述',
        dataIndex: 'description',
        key: 'description',
    },
];

const requestParams = [
    {
        key: "SUCCESS",
        name: 'SUCCESS',
        code: 0,
        description: '操作成功',
    },
    {
        key: "PARAMS_ERROR",
        name: 'PARAMS_ERROR',
        code: 40000,
        description: '请求参数错误',
    },
    {
        key: "UNAUTHORIZED",
        name: 'UNAUTHORIZED',
        code: 40100,
        description: '未登录',
    },
    {
        key: "FORBIDDEN",
        name: 'FORBIDDEN',
        code: 40300,
        description: '操作无权限',
    },
    {
        key: "NOT_FOUND",
        name: 'NOT_FOUND',
        code: 40400,
        description: '请求数据不存在',
    },
    {
        key: "SYSTEM_ERROR",
        name: 'SYSTEM_ERROR',
        code: 50000,
        description: '系统内部异常',
    },
    {
        key: "OPERATION_ERROR",
        name: 'OPERATION_ERROR',
        code: 50001,
        description: '操作失败',
    },
];

export const ResponseCodeTable = () => {
    return (
        <Table columns={requestCols} dataSource={requestParams} size={"small"} pagination={false}/>
    );
};