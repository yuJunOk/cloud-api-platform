<template>
  <div class="forget-pwd-form-wrapper">
    <div class="forget-pwd-form-title">忘记密码？ 母鸡API</div>
    <div class="forget-pwd-form-sub-title">密码重设表单</div>
    <a-form
      class="forget-pwd-form"
      layout="vertical"
      :model="resetPwdFormValue"
      @submit="handleSubmit"
    >
      <a-form-item
        field="loginPwd"
        :rules="[{ required: true, message: '密码不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input-password
          v-model="resetPwdFormValue.loginPwd"
          placeholder="请填入新密码"
          allow-clear
        >
          <template #prefix>
            <icon-lock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        field="checkPwd"
        :rules="[{ required: true, message: '密码不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input-password
          v-model="resetPwdFormValue.checkPwd"
          placeholder="请再次填入新密码"
          allow-clear
        >
          <template #prefix>
            <icon-lock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        field="email"
        :rules="[{ required: true, message: '邮箱不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input v-model="resetPwdFormValue.email" placeholder="请填入邮箱">
          <template #prefix>
            <icon-email />
          </template>
        </a-input>
        <a-button
          @click="handleCaptcha"
          :loading="captchaLoading"
          :style="{ marginLeft: '10px' }"
        >
          {{ captchaBtnText }}
        </a-button>
      </a-form-item>
      <a-form-item
        field="captcha"
        :rules="[{ required: true, message: '验证码不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input
          v-model="resetPwdFormValue.captcha"
          placeholder="请输入验证码"
        />
      </a-form-item>
      <a-space :size="16" direction="vertical">
        <div class="forget-pwd-form-password-actions">
          <a-link @click="toLogin">想起来？回去登录</a-link>
          <a-link @click="toRegister">未注册？去注册</a-link>
        </div>
        <a-button
          type="primary"
          html-type="submit"
          long
          :loading="resetLoading"
        >
          修改密码
        </a-button>
      </a-space>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { IconLock, IconEmail } from "@arco-design/web-vue/es/icon";
import {
  ResetPwdByEmailDto,
  UserControllerService,
} from "../../../../api/user";
import ResponseCode from "@/models/enum/ResponseCode";
import message from "@arco-design/web-vue/es/message";
import { useCountdown } from "@vueuse/core";

// 路由
const router = useRouter();

/**
 * 表单信息
 */
const resetPwdFormValue = reactive({
  email: "",
  loginPwd: "",
  checkPwd: "",
  captcha: "",
} as ResetPwdByEmailDto);

/**
 * 修改提交
 */
const resetLoading = ref(false);
const handleSubmit = async () => {
  resetLoading.value = true;
  const res = await UserControllerService.resetPwdByEmail(resetPwdFormValue);
  resetLoading.value = false;
  // 修改成功，跳转到登录页
  if (res.code === ResponseCode.SUCCESS) {
    message.success("修改成功！");
    // 跳转
    toLogin();
  } else {
    message.error(res.message ?? "修改失败，请稍后再试");
  }
};

/**
 * 按钮冷却设置
 */
const captchaLoading = ref(false);
const captchaBtnText = ref("发送验证码");
const { remaining, start, stop, pause, resume } = useCountdown(60, {
  onComplete() {
    captchaLoading.value = false;
    captchaBtnText.value = "发送验证码";
  },
  onTick() {
    if (!captchaLoading.value) {
      captchaLoading.value = true;
    }
    captchaBtnText.value = `${remaining.value}秒`;
  },
});

const handleCaptcha = async () => {
  if (!resetPwdFormValue.email) {
    message.warning("请先输入邮箱！");
    return;
  }
  const res = await UserControllerService.sendResetPwdCaptcha({
    email: resetPwdFormValue.email,
  });
  if (res.code === ResponseCode.SUCCESS) {
    message.success("发送成功，请到邮箱收件查看验证码");
    start();
  } else {
    message.error(res.message ?? "发送失败，请稍后再试");
  }
};

/**
 * 设置记住登录
 * @param value
 */
const toLogin = () => {
  router.replace("/login");
};

/**
 * 跳转注册页
 */
const toRegister = () => {
  router.replace("/register");
};
</script>

<style scoped>
.forget-pwd-form-wrapper {
  width: 320px;
}

.forget-pwd-form-title {
  color: var(--color-text-1);
  font-weight: 500;
  font-size: 24px;
  line-height: 32px;
}

.forget-pwd-form-sub-title {
  color: var(--color-text-3);
  font-size: 16px;
  line-height: 24px;
  margin-bottom: 32px;
}

.forget-pwd-form-password-actions {
  display: flex;
  justify-content: space-between;
}

.forget-pwd-form-register-btn {
  color: var(--color-text-3) !important;
}
</style>
