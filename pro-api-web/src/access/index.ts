import router from "@/router";
import store from "@/store";
import ACCESS_ENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";
import { SYSTEM_NAME } from "@/constant/SystemConstant";

router.beforeEach(async (to, from, next) => {
  // console.log("登陆用户信息", store.state.user.loginUser);
  let loginUser = store.state.user.loginUser;
  // // 如果之前没登陆过，自动登录
  if (!loginUser || !loginUser.userRole) {
    // 加 await 是为了等用户登录成功之后，再执行后续的代码
    await store.dispatch("user/getLoginUser");
    loginUser = store.state.user.loginUser;
  }
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.VISITOR;
  // 要跳转的页面必须要登陆
  if (needAccess !== ACCESS_ENUM.VISITOR) {
    // 如果没登陆，跳转到登录页面
    if (
      !loginUser ||
      !loginUser.userRole ||
      loginUser.userRole === ACCESS_ENUM.VISITOR
    ) {
      next(`/login?redirect=${to.fullPath}`);
      return;
    }
    // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  document.title = (to.meta.title ?? SYSTEM_NAME) as string;
  next();
});
