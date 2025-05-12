<template>
  <a-button type="primary" @click="handleClick">
    <template #icon> <icon-plus /> </template>新建
  </a-button>
  <a-modal
    v-model:visible="visible"
    title="新建数据"
    @cancel="handleCancel"
    @before-ok="handleBeforeOk"
  >
    <a-form :model="addFormVals">
      <a-form-item
        field="userName"
        label="用户名"
        :rules="[{ required: true, message: '用户名不能为空' }]"
      >
        <a-input v-model="addFormVals.userName" />
      </a-form-item>
      <a-form-item
        field="loginName"
        label="账号"
        :rules="[{ required: true, message: '账号不能为空' }]"
      >
        <a-input v-model="addFormVals.loginName" />
      </a-form-item>
      <a-form-item field="gender" label="性别">
        <a-select
          v-model="addFormVals.gender"
          placeholder="选择性别"
          :options="genderOptionData"
        />
      </a-form-item>
      <a-form-item
        field="phone"
        label="电话"
        :rules="[{ required: true, message: '电话不能为空' }]"
      >
        <a-input v-model="addFormVals.phone" />
      </a-form-item>
      <a-form-item
        field="email"
        label="邮箱"
        :rules="[{ required: true, message: '邮箱不能为空' }]"
      >
        <a-input v-model="addFormVals.email" />
      </a-form-item>
      <a-form-item field="userRole" label="角色">
        <a-select
          v-model="addFormVals.userRole"
          placeholder="选择角色"
          :options="userRoleOptionData"
        />
      </a-form-item>
      <a-form-item
        field="loginPwd"
        label="密码"
        :rules="[{ required: true, message: '密码不能为空' }]"
      >
        <a-input-password
          v-model="addFormVals.loginPwd"
          placeholder="请填入密码"
          allow-clear
        >
          <template #prefix>
            <icon-lock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        field="checkPwd"
        label="一致密码"
        :rules="[{ required: true, message: '密码不能为空' }]"
      >
        <a-input-password
          v-model="addFormVals.checkPwd"
          placeholder="请再次填入密码"
          allow-clear
        >
          <template #prefix>
            <icon-lock />
          </template>
        </a-input-password>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { AddUserDto, UserControllerService } from "../../../../api/user";
import {
  genderOptionData,
  userRoleOptionData,
} from "@/models/options/select/UserOptionData";
import ResponseCode from "@/models/enum/ResponseCode";
import message from "@arco-design/web-vue/es/message";
import { IconLock, IconPlus } from "@arco-design/web-vue/es/icon";

// 回调父组件
// eslint-disable-next-line no-undef
const emit = defineEmits<{
  (e: "refresh"): void;
}>();

// 本地表单副本
const BaseAddFormVals = {
  id: undefined,
  userName: "",
  loginName: "",
  gender: undefined,
  phone: "",
  email: "",
  userRole: "",
  loginPwd: "",
  checkPwd: "",
};
let addFormVals = reactive<AddUserDto>({ ...BaseAddFormVals });

// 控制弹窗显示
const visible = ref(false);

/**
 * 显示弹窗
 */
const handleClick = () => {
  visible.value = true;
  addFormVals = reactive({ ...BaseAddFormVals });
};

/**
 * 点击确认
 * @param done
 */
const handleBeforeOk = async (done) => {
  const res = await UserControllerService.add(addFormVals);
  if (res.code === ResponseCode.SUCCESS) {
    message.success("新增成功");
    // 触发父组件刷新
    emit("refresh");
    // 关闭弹窗
    done();
  } else {
    message.error(res.msg ?? "新增失败，请稍后再试");
    done(false);
  }
};
const handleCancel = () => {
  visible.value = false;
};
</script>
