import {Button, Card, Flex, Input, Space, Typography} from "antd";
import type {SearchProps} from "antd/lib/input";
import {BugOutlined} from '@ant-design/icons';

const { Search } = Input;

const onSearch: SearchProps['onSearch'] = (value, _e, info) => console.log(info?.source, value);

export const ApiListPage = () => {
    return (
        <div style={{ margin: "24px auto", width: "88%"}}>
            <Card style={{ textAlign: "center", marginBottom: "36px" }}>
                <Search
                    placeholder="搜索"
                    allowClear
                    enterButton="Search"
                    size="large"
                    onSearch={onSearch}
                    style={{ width: "50%", marginBottom: 36 }}
                />
            </Card>
            <Flex wrap={true} gap={22}>
                <Card title={<Space><BugOutlined />{"res"}</Space>}
                      variant="borderless"
                      extra={<Button color="primary" variant="outlined">查看</Button>}
                      style={{width:'24%'}}>
                    <Typography.Paragraph ellipsis={{rows: 3, tooltip:true}}>
                        Card contentCard contentCard contentCard contentCard contentCard contentCard
                    </Typography.Paragraph>
                </Card>
                <Card title={<Space><BugOutlined />{"res"}</Space>}
                      variant="borderless"
                      extra={<Button color="primary" variant="outlined">查看</Button>}
                      style={{width:'24%'}}>
                    <Typography.Paragraph ellipsis={{rows: 3, tooltip:true}}>
                        Card contentCard contentCard contentCard contentCard contentCard contentCard
                    </Typography.Paragraph>
                </Card>
            </Flex>
        </div>
    );
};