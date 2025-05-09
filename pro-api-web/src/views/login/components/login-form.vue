<template>
  <div class="login-form-wrapper">
    <div class="login-form-title">欢迎登录 母鸡API</div>
    <div class="login-form-sub-title">请输入登录数据</div>
    <a-form
      class="login-form"
      layout="vertical"
      :model="loginFormValue"
      @submit="handleSubmit"
    >
      <a-form-item
        field="email"
        :rules="[{ required: true, message: '登录邮箱不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input v-model="loginFormValue.email" placeholder="请填入登录邮箱">
          <template #prefix>
            <icon-email />
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
          v-model="loginFormValue.loginPwd"
          placeholder="请填入密码"
          allow-clear
        >
          <template #prefix>
            <icon-lock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-space :size="16" direction="vertical">
        <div class="login-form-password-actions">
          <a-checkbox
            checked="rememberPassword"
            :model-value="loginConfig.remember"
            @change="setRememberLogin"
          >
            记住密码
          </a-checkbox>
          <a-link @click="toForgetPwd">忘记密码</a-link>
        </div>
        <a-button
          type="primary"
          html-type="submit"
          long
          :loading="loginLoading"
        >
          登录
        </a-button>
        <a-button
          type="text"
          long
          class="login-form-register-btn"
          @click="toRegister"
        >
          注册
        </a-button>
      </a-space>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { IconEmail, IconLock } from "@arco-design/web-vue/es/icon";
import { useStore } from "vuex";
import { useStorage } from "@vueuse/core";
import { LoginByEmailDto, UserControllerService } from "../../../../api/user";
import ResponseCode from "@/models/enum/ResponseCode";

const router = useRouter();
const store = useStore();

/**
 * 登录缓存设置
 */
const loginConfig = useStorage("login-config", {
  remember: true,
  email: "",
  loginPwd: "",
});
/**
 * 表单信息
 */
const loginFormValue = reactive({
  email: loginConfig.value.email,
  loginPwd: loginConfig.value.loginPwd,
} as LoginByEmailDto);

/**
 * 登录
 */
const loginLoading = ref(false);
const handleSubmit = async () => {
  loginLoading.value = true;
  const res = await UserControllerService.login(loginFormValue);
  loginLoading.value = false;
  // 登录成功，跳转到主页
  if (res.code === ResponseCode.SUCCESS) {
    if (loginConfig.value.remember) {
      loginConfig.value.email = loginFormValue.email;
      loginConfig.value.loginPwd = loginFormValue.loginPwd;
    }
    // 跳转
    await store.dispatch("user/getLoginUser");
    await router.replace("/");
  } else {
    message.error(res.message ?? "登录失败，请稍后再试");
  }
};

/**
 * 设置记住登录
 * @param value
 */
const setRememberLogin = (value: boolean) => {
  loginConfig.value.remember = value;
};

/**
 * 跳转忘记密码页
 */
const toForgetPwd = () => {
  router.replace("/forgetPwd");
};

/**
 * 跳转注册页
 */
const toRegister = () => {
  router.replace("/register");
};
</script>

<style scoped>
.login-form-wrapper {
  width: 320px;
}

.login-form-title {
  color: var(--color-text-1);
  font-weight: 500;
  font-size: 24px;
  line-height: 32px;
}

.login-form-sub-title {
  color: var(--color-text-3);
  font-size: 16px;
  line-height: 24px;
  margin-bottom: 32px;
}

.login-form-password-actions {
  display: flex;
  justify-content: space-between;
}

.login-form-register-btn {
  color: var(--color-text-3) !important;
}
</style>
