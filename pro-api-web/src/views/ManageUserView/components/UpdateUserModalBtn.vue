<template>
  <a-button type="text" size="small" @click="handleClick">修改</a-button>
  <a-modal
    v-model:visible="visible"
    title="修改数据"
    @cancel="handleCancel"
    @before-ok="handleBeforeOk"
  >
    <a-form :model="updateFormVals">
      <a-form-item field="id" label="ID">
        <a-input v-model="updateFormVals.id" readonly />
      </a-form-item>
      <a-form-item
        field="userName"
        label="用户名"
        :rules="[{ required: true, message: '用户名不能为空' }]"
      >
        <a-input v-model="updateFormVals.userName" />
      </a-form-item>
      <a-form-item
        field="loginName"
        label="账号"
        :rules="[{ required: true, message: '账号不能为空' }]"
      >
        <a-input v-model="updateFormVals.loginName" />
      </a-form-item>
      <a-form-item field="gender" label="性别">
        <a-select
          v-model="updateFormVals.gender"
          placeholder="选择性别"
          :options="genderOptionData"
        />
      </a-form-item>
      <a-form-item field="phone" label="电话">
        <a-input v-model="updateFormVals.phone" />
      </a-form-item>
      <a-form-item
        field="email"
        label="邮箱"
        :rules="[{ required: true, message: '邮箱不能为空' }]"
      >
        <a-input v-model="updateFormVals.email" />
      </a-form-item>
      <a-form-item field="userRole" label="角色">
        <a-select
          v-model="updateFormVals.userRole"
          placeholder="选择角色"
          :options="userRoleOptionData"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { UserControllerService, UserVo } from "../../../../api/user";
import {
  genderOptionData,
  userRoleOptionData,
} from "@/models/options/UserOptionData";
import ResponseCode from "@/models/enum/ResponseCode";
import message from "@arco-design/web-vue/es/message";

// 父组件传参
// eslint-disable-next-line no-undef
const props = defineProps<{
  userData?: UserVo;
}>();

// 回调父组件
// eslint-disable-next-line no-undef
const emit = defineEmits<{
  (e: "refresh"): void;
}>();

// 本地表单副本
let updateFormVals = reactive({ ...props.userData });

// 控制弹窗显示
const visible = ref(false);

/**
 * 显示弹窗
 */
const handleClick = () => {
  visible.value = true;
  updateFormVals = reactive({ ...props.userData });
};

/**
 * 点击确认
 * @param done
 */
const handleBeforeOk = async (done) => {
  const res = await UserControllerService.update(updateFormVals);
  if (res.code === ResponseCode.SUCCESS) {
    message.success("更新成功");
    // 触发父组件刷新
    emit("refresh");
    // 关闭弹窗
    done();
  } else {
    message.error(res.msg ?? "更新失败，请稍后再试");
    done(false);
  }
};
const handleCancel = () => {
  visible.value = false;
};
</script>
