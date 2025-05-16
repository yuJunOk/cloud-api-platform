import {HomePage} from "@/pages/Home/HomePage.tsx";
import LoginPage from "@/pages/Login/LoginPage.tsx";
import {StatisticsPage} from "@/pages/Statistics/StatisticsPage.tsx";
import {ApiListPage} from "@/pages/ApiList/ApiListPage.tsx";
import {BasicLayout} from "@/components/BaicLayout/BasicLayout.tsx";
import {MonoLayout} from "@/components/MonoLayout.tsx";
import {RequireRole} from "@/access/checkAccess.tsx";
import AccessEnum from "@/access/accessEnum.ts";
import {ApiInfoPage} from "@/pages/ApiInfo/ApiInfoPage.tsx";

export const routes = [
    {
        path: '/',
        element: <RequireRole allowedRoles={AccessEnum.ADMIN}><BasicLayout /></RequireRole>,
        children: [
            {
                path: '/',
                element: <HomePage />,
            },
            {
                path: '/api/list',
                element: <ApiListPage />,
            },
            {
                path: '/api/info',
                element: <ApiInfoPage />,
            },
            {
                path: '/statistics',
                element: <StatisticsPage />,
            },
        ]
    },
    {
        path: '/login',
        element: <MonoLayout />,
        children: [
            {
                path: '/login',
                element: <LoginPage />,
            },
        ]
    },
    {
        path: '/register',
        element: <MonoLayout />,
        children: [
            {
                path: '/register',
                element: <LoginPage />,
            },
        ]
    },
    {
        path: '/forgetPwd',
        element: <MonoLayout />,
        children: [
            {
                path: '/forgetPwd',
                element: <LoginPage />,
            },
        ]
    },
]