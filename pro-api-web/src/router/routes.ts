import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/login/index.vue";
import NoAuthView from "@/views/NoAuthView.vue";
import ACCESS_ENUM from "@/access/accessEnum";
import ManageUserView from "@/views/ManageUserView/index.vue";

export const proRoutes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "主页",
    component: HomeView,
    meta: {
      title: "主页 - 母鸡API",
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/manage/user",
    name: "管理用户",
    component: ManageUserView,
    meta: {
      title: "管理用户 - 母鸡API",
      access: ACCESS_ENUM.ADMIN,
    },
  },
];

export const routes: Array<RouteRecordRaw> = [
  ...proRoutes,
  {
    path: "/login",
    name: "登录",
    component: LoginView,
    meta: {
      title: "登录 - 母鸡API",
      layout: "mono",
    },
  },
  {
    path: "/register",
    name: "注册",
    component: LoginView,
    meta: {
      title: "注册 - 母鸡API",
      layout: "mono",
    },
  },
  {
    path: "/forgetPwd",
    name: "忘记密码",
    component: LoginView,
    meta: {
      title: "忘记密码 - 母鸡API",
      layout: "mono",
    },
  },
  {
    path: "/noAuth",
    name: "无权限",
    component: NoAuthView,
    meta: {
      title: "提示 - 母鸡API",
      layout: "mono",
    },
  },
];
