import {Input, Select, Space, Button, type TableProps, Divider, Table, Flex, Popconfirm, App} from "antd";
import {CodeViewer} from "@/components/CodeViewer.tsx";
import {useEffect, useState} from "react";
import type {JsonApiDataRule} from "@/models/type/ApiType";
import {jsonDataTypeOptionData} from "@/models/options/ApiOptionData.ts";
import {InvokeControllerService} from "../../../api/api";
import ResponseCode from "@/models/enum/ResponseCode.ts";
import {getEditorFormat} from "@/utils/CodeEditorUtils.ts";
import {useNavigate} from "react-router-dom";

interface Props {
    id: number | undefined;
    method?: string | undefined;
    url?: string | undefined;
    requestParams?: string | undefined;
}

export const InvokePanel = ({id, method, url, requestParams}: Props) => {
    const { message } = App.useApp();
    const navigate = useNavigate();
    const [requestParamsData, setRequestParamsData] = useState<JsonApiDataRule[]>([]);
    const [responseBody, setResponseBody] = useState<string>("");
    const [editorLanguage, setEditorLanguage] = useState<string>("plaintext");

    useEffect(() => {
        resetTable();
    }, [requestParams]);

    const requestCols: TableProps<JsonApiDataRule>['columns'] = [
        {
            title: '参数名称',
            key: 'name',
            render: (_, record) => (
                <Input value={record.name} style={{width: 'auto'}} onChange={(e) => {
                    updateSelf({...record, name: e.target.value});
                }}/>
            ),
        },
        {
            title: '类型',
            key: 'type',
            render: (_, record) => (
                <Select value={record.type} options={jsonDataTypeOptionData} style={{display: 'block'}} onChange={(value) => {
                    updateSelf({...record, type: value});
                }}/>
            ),
        },
        {
            title: '参数值',
            key: 'value',
            render: (_, record) => (
                <Input value={record.value} style={{display: 'block'}} onChange={(e) => {
                    updateSelf({...record, value: e.target.value});
                }}/>
            ),
        },
        {
            title: '操作',
            key: 'type',
            render: (_, record) => (
                <Space size="small">
                    {record.type == "object" && <Button type="text" color="primary" onClick={() => addRow(record)}>新增行</Button>}
                    <Popconfirm title="确认删除？" placement="topRight" onConfirm={() => deleteSelf(record)}>
                        <Button type="text" danger>删除</Button>
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    // 添加一行数据
    const addRow = (record?: JsonApiDataRule) => {
        console.log(record);
        if (record != null) {
            setRequestParamsData(prev => {
                const newData = prev.map(item => {
                    // 深度遍历查找目标记录
                    const deepUpdate = (node: JsonApiDataRule): JsonApiDataRule => {
                        if (node === record) {
                            return {...node, children: [...(node.children || []), {} as JsonApiDataRule]};
                        }
                        if (node.children) {
                            return {...node, children: node.children.map(deepUpdate)};
                        }
                        return node;
                    };
                    return deepUpdate(item);
                });
                return maintainTableData(newData);
            });
        } else {
            setRequestParamsData(prev => maintainTableData([...(prev || []), {} as JsonApiDataRule]));
        }
        console.log(requestParamsData);
    }

    // 删除一行数据
    const deleteSelf = (record: JsonApiDataRule) => {
        // 优化点1：通过状态管理函数更新数据
        setRequestParamsData(prev => {
            // 优化点2：添加空值保护
            if (!prev) return prev;
            // 优化点3：简化递归逻辑
            const deepFilter = (nodes: JsonApiDataRule[]): JsonApiDataRule[] => {
                return nodes
                    .filter(node => node.key !== record.key) // 过滤当前层目标项
                    .map(node => ({
                        ...node,
                        // 递归处理子节点
                        children: node.children ? deepFilter(node.children) : undefined
                    }));
            };
            return maintainTableData(deepFilter(prev));
        });
    };

    // 更新一行数据
    const updateSelf = (record: JsonApiDataRule) => {
        // 优化点1：通过状态管理函数更新数据
        setRequestParamsData(prev => {
            // 优化点2：添加空值保护
            if (!prev) return prev;
            // 优化点3：简化递归逻辑
            const deepUpdate = (nodes: JsonApiDataRule[]): JsonApiDataRule[] => {
                return nodes.map(node => {
                    // 命中目标节点时合并新属性
                    if (node.key === record.key) {
                        return { ...node, ...record }; // 保留未修改属性，覆盖更新字段
                    }
                    // 递归处理子节点
                    return node.children?.length ? { ...node, children: deepUpdate(node.children) } : node;
                });
            };
            return maintainTableData(deepUpdate(prev));
        });
    };

    // 维护表数据
    const maintainTableData = (
        treeData: JsonApiDataRule[],
        parent?: JsonApiDataRule
    ): JsonApiDataRule[] => {
        return treeData.map((node, index) => {
            // 生成稳定键值
            const newKey = parent ? `${parent.key}-${index + 1}` : `${index + 1}`;
            // 创建新节点避免污染原数据
            const newNode: JsonApiDataRule = {
                ...node,
                key: newKey,
                name: node.name || `prop${newKey}`,
                type: node.type || 'string'
            };
            // 类型处理逻辑
            if (newNode.type !== 'object') {
                newNode.children = undefined;
                newNode.value = newNode.value ?? '';
            } else {
                newNode.value = undefined;
                newNode.children = newNode.children ? maintainTableData(newNode.children, newNode) : [];
            }
            return newNode;
        });
    };

    // 重置表
    const resetTable = () => {
        try{
            setRequestParamsData(JSON.parse(requestParams ?? "[]"));
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
        }catch (e) {
            setRequestParamsData([]);
        }
    }

    //
    const transformRules = (records: JsonApiDataRule[]): Record<string, any> => {
        return records.reduce((acc, rule) => {
            if (!rule.name) return acc; // 跳过无 name 的规则
            // 核心逻辑：当 value.children 是数组时递归
            if (rule.type === "object" && Array.isArray(rule.children)) {
                acc[rule.name] = transformRules(rule.children);
            } else {
                // 直接使用 value（兼容非对象类型）
                acc[rule.name] = rule.value;
            }
            return acc;
        }, {} as Record<string, any>);
    }

    // 发起请求
    const invoke = async () => {
        const requestData = transformRules(requestParamsData);
        const res = await InvokeControllerService.debug({
            url: url,
            method: method,
            requestParams: requestData,
        });
        if (res.code == ResponseCode.SUCCESS) {
            console.log(id);
            setEditorLanguage(getEditorFormat(res.data?.contentType));
            setResponseBody(res.data?.responseBody ?? "");
        } else if (res.code == ResponseCode.UNAUTHORIZED){
            navigate("/login")
        } else {
            message.error(res.message ?? "请求失败，请稍后再试");
        }
    }

    return (
        <div style={{ textAlign: "center" }}>
            <Space.Compact style={{ width: '60%' }}>
                <Select
                    defaultValue="GET"
                    value={method}
                    disabled
                    options={[{ value: 'GET', label: 'GET' },{ value: 'POST', label: 'POST' }]}
                />
                <Input value={url} readOnly />
                <Button type="primary" onClick={() => invoke()}>发起请求</Button>
            </Space.Compact>
            <Divider orientation="left">请求参数说明</Divider>
            <Flex gap="small" justify={"end"} style={{marginBottom: 10}}>
                <Button type="primary" onClick={() => addRow()}>添加行</Button>
                <Popconfirm title="重置会覆盖当前内容" placement="topRight" onConfirm={() => resetTable()}>
                    <Button type="primary" danger>重置</Button>
                </Popconfirm>
            </Flex>
            <Table columns={requestCols} dataSource={requestParamsData} size={"small"} pagination={false} style={{height: 200, overflowY: "auto"}}/>
            <Divider orientation="left">返回结果</Divider>
            <CodeViewer height={200} value={responseBody} language={editorLanguage}/>
        </div>
    );
};