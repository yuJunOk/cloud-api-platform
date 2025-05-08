import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/login/LoginView.vue";

export const proRoutes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "主页",
    component: HomeView,
    meta: {
      title: "主页 - 母鸡API",
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
];
