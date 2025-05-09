<template>
  <div class="register-form-wrapper">
    <div class="register-form-title">欢迎注册 母鸡API</div>
    <div class="register-form-sub-title">请输入注册数据</div>
    <a-form
      class="register-form"
      layout="vertical"
      :model="registerFormValue"
      @submit="handleSubmit"
    >
      <a-form-item
        field="loginName"
        :rules="[{ required: true, message: '账户名不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input
          v-model="registerFormValue.loginName"
          placeholder="请填入账户名"
        >
          <template #prefix>
            <icon-user />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item
        field="loginPwd"
        :rules="[{ required: true, message: '密码不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input-password
          v-model="registerFormValue.loginPwd"
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
        :rules="[{ required: true, message: '密码不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input-password
          v-model="registerFormValue.checkPwd"
          placeholder="请再次填入密码"
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
        <a-input v-model="registerFormValue.email" placeholder="请填入邮箱">
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
          v-model="registerFormValue.captcha"
          placeholder="请输入验证码"
        />
      </a-form-item>
      <a-space :size="16" direction="vertical">
        <div class="register-form-password-actions">
          <a-link @click="toLogin">已有账户？去登录</a-link>
        </div>
        <a-button
          type="primary"
          html-type="submit"
          long
          :loading="submitLoading"
        >
          注册
        </a-button>
      </a-space>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { IconUser, IconLock, IconEmail } from "@arco-design/web-vue/es/icon";
import { UserControllerService, UserRegisterDto } from "../../../../api/user";
import ResponseCode from "@/models/enum/ResponseCode";
import message from "@arco-design/web-vue/es/message";
import { useCountdown } from "@vueuse/core";

// 路由
const router = useRouter();

/**
 * 表单信息
 */
const registerFormValue = reactive({
  loginName: "",
  loginPwd: "",
  checkPwd: "",
  email: "",
  captcha: "",
} as UserRegisterDto);

/**
 * 注册提交
 */
const submitLoading = ref(false);
const handleSubmit = async () => {
  submitLoading.value = true;
  const res = await UserControllerService.register(registerFormValue);
  submitLoading.value = false;
  // 注册成功，跳转到登录页
  if (res.code === ResponseCode.SUCCESS) {
    message.success("注册成功！");
    // 跳转
    toLogin();
  } else {
    message.error(res.message ?? "注册失败，请稍后再试");
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
  if (!registerFormValue.email) {
    message.warning("请先输入邮箱！");
    return;
  }
  const res = await UserControllerService.sendRegisterCaptcha({
    email: registerFormValue.email,
  });
  if (res.code === ResponseCode.SUCCESS) {
    message.success("发送成功，请到邮箱收件查看验证码");
    start();
  } else {
    message.error(res.message ?? "发送失败，请稍后再试");
  }
};

/**
 * 跳转到登录页
 */
const toLogin = () => {
  router.replace("/login");
};
</script>

<style scoped>
.register-form-wrapper {
  width: 320px;
}

.register-form-title {
  color: var(--color-text-1);
  font-weight: 500;
  font-size: 24px;
  line-height: 32px;
}

.register-form-sub-title {
  color: var(--color-text-3);
  font-size: 16px;
  line-height: 24px;
  margin-bottom: 32px;
}

.register-form-password-actions {
  display: flex;
  justify-content: flex-end;
}

.register-form-login-btn {
  color: var(--color-text-3) !important;
}
</style>
