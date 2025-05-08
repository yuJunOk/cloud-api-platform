// initial state
import { StoreOptions } from "vuex";
import ACCESS_ENUM from "@/access/accessEnum";
import { UserControllerService } from "../../api/user";
import RESPONSE_CODE from "@/models/enum/ResponseCode";

export default {
  namespaced: true,
  // 存储的状态信息，比如用户信息
  state: () => ({
    loginUser: {
      userName: "未登录",
    },
  }),
  // 执行异步操作，并且通过commit触发 mutation 的更改 (actions 调用 mutation)
  actions: {
    async getLoginUser({ commit, state }, payload) {
      const res = await UserControllerService.getCurrentUser();
      if (res.code === RESPONSE_CODE.SUCCESS) {
        commit("updateUser", res.data);
      } else {
        commit("updateUser", {
          ...state.loginUser,
          userRole: ACCESS_ENUM.VISITOR,
        });
      }
    },
  },
  // 定义了对变量进行增删改(更新)的方法，尽量同步
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
