import {proxy} from "valtio/vanilla";
import {type LoginUserBo, UserControllerService} from "../../api/user";
import ResponseCode from "@/models/enum/ResponseCode.ts";
import AccessEnum from "@/access/accessEnum.ts";

const baseVisitorUser = {
    userName: "未登录",
    userRole: AccessEnum.VISITOR
};

const store = proxy({
    loginUser: baseVisitorUser as LoginUserBo|undefined,
});

export const getUser = async () => {
    const res = await UserControllerService.getCurrentUser();
    if (res.code == ResponseCode.SUCCESS) {
        updateUser(res.data);
        return res.data;
    }else {
        updateUser(baseVisitorUser);
    }
    return null;
}

const updateUser = (user?: LoginUserBo|undefined) => {
    store.loginUser = user;
}


export default store;