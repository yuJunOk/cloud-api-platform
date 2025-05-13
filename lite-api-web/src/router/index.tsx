import {HomePage} from "@/pages/Home/HomePage.tsx";
import LoginPage from "@/pages/Login/LoginPage.tsx";
import BasicLayout from "@/components/BaicLayout";
import {StatisticsPage} from "@/pages/Statistics/StatisticsPage.tsx";
import {ApiListPage} from "@/pages/ApiList/ApiListPage.tsx";

export const routes = [
    {
        path: '/',
        element: <BasicLayout />,
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
                path: '/statistics',
                element: <StatisticsPage />,
            },
            {
                path: '/login',
                element: <LoginPage />,
            },
            {
                path: '/register',
                element: <LoginPage />,
            },
            {
                path: '/forgetPwd',
                element: <LoginPage />,
            },
        ]
    },
]