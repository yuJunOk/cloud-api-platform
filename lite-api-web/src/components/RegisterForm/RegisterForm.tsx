import {Tabs} from "antd";
import {RegisterByPhoneForm} from "@/components/RegisterForm/RegisterByPhoneForm.tsx";
import {RegisterByEmailForm} from "@/components/RegisterForm/RegisterByEmailForm.tsx";
import {RegisterByAccountForm} from "@/components/RegisterForm/RegisterByAccountForm.tsx";

const LoginOptions = [
    {
        key: "registerByAccount",
        label: "账号注册",
        children: (
            <RegisterByAccountForm />
        ),
    },{
        key: "registerByEmail",
        label: "邮箱注册",
        children: (
            <RegisterByEmailForm />
        ),
    },
    {
        key: "registerByPhone",
        label: "手机号注册",
        children: (
            <RegisterByPhoneForm />
        ),
    }
]

export const RegisterForm = () => {
    return (
        <Tabs
            defaultActiveKey="registerByAccount"
            centered
            items={LoginOptions}
        />
    );
};