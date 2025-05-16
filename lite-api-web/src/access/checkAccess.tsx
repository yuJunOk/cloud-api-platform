// src/utils/authHocs/withRoleCheck.tsx
import {Navigate, useLocation, useNavigate} from 'react-router-dom';
import {useSnapshot} from "valtio/react";
import store, {getUser} from "@/store/user.ts";
import AccessEnum from "@/access/accessEnum.ts";
import {useEffect} from "react";

// 高阶守卫组件 TODO: 需要优化闪烁问题，每次刷新页面丢失状态值的时候，会短暂地跳到login页面，再切回来
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
export const RequireRole = ({children, allowedRoles}) => {
    //登录用户
    const {loginUser} = useSnapshot(store);
    // 跳转方法
    const navigate = useNavigate();
    // 当前路径
    const location = useLocation();

    // 判断是否登录
    const isNotLogin = !loginUser?.userRole || loginUser?.userRole == AccessEnum.VISITOR;
    //
    useEffect(() => {
        if (isNotLogin){
            getUser().then((user) => {
                if (user && allowedRoles.includes(user?.userRole)){
                    navigate(location);
                }
            });
        }
    }, [allowedRoles, isNotLogin, location, navigate]);
    // 增加角色未定义错误处理
    if (isNotLogin) {
        return <Navigate to="/login" replace/>;
    }
    // 无权限
    if (!allowedRoles.includes(loginUser?.userRole)) {
        return <Navigate to="/unauth" replace/>;
    }
    return children;
}