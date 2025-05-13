import {Tabs} from "antd";
import {ResetByPhoneForm} from "@/components/ForgetPwdForm/ResetByPhoneForm.tsx";
import {ResetByEmailForm} from "@/components/ForgetPwdForm/ResetByEmailForm.tsx";

const LoginOptions = [
    {
        key: "resetByEmail",
        label: "通过邮箱重设密码",
        children: (
            <ResetByEmailForm />
        ),
    },
    {
        key: "resetByPhone",
        label: "通过手机号重设密码",
        children: (
            <ResetByPhoneForm />
        ),
    }
]

export const ForgetPwdForm = () => {
    return (
        <Tabs
            defaultActiveKey="registerByAccount"
            centered
            items={LoginOptions}
        />
    );
};