import {Tabs} from "antd";
import {LoginByAccountForm} from "@/components/LoginForm/LoginByAccountForm.tsx";
import {LoginByPhoneForm} from "@/components/LoginForm/LoginByPhoneForm.tsx";

const LoginOptions = [
    {
        key: "loginByAccount",
        label: "账户密码登录",
        children: (
            <LoginByAccountForm />
        ),
    },
    {
        key: "loginByPhone",
        label: "手机号登录",
        children: (
            <LoginByPhoneForm />
        ),
    }
]

export const LoginForm = () => {
    return (
        <Tabs
            defaultActiveKey="loginByAccount"
            centered
            items={LoginOptions}
        />
    );
};