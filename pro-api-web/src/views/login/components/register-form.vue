<template>
  <div class="login-form-wrapper">
    <div class="login-form-title">欢迎注册 母鸡API</div>
    <div class="login-form-sub-title">注册表单</div>
    <div class="login-form-error-msg">{{ errorMessage }}</div>
    <a-form
      ref="loginForm"
      :model="userInfo"
      class="login-form"
      layout="vertical"
      @submit="handleSubmit"
    >
      <a-form-item
        field="username"
        :rules="[{ required: true, message: '用户名不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input v-model="userInfo.username" placeholder="请填入用户名">
          <template #prefix>
            <icon-user />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item
        field="password"
        :rules="[{ required: true, message: '密码不能为空' }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input-password
          v-model="userInfo.password"
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
            :model-value="loginConfig.rememberPassword"
            @change="setRememberPassword as any"
          >
            记住密码
          </a-checkbox>
          <a-link>忘记密码</a-link>
        </div>
        <a-button type="primary" html-type="submit" long :loading="loading">
          注册
        </a-button>
        <a-button
          type="text"
          long
          class="login-form-register-btn"
          @click="toLogin"
        >
          登录
        </a-button>
      </a-space>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from "vue";
import { ValidatedError } from "@arco-design/web-vue/es/form/interface";
import { useStorage } from "@vueuse/core";
import { useRouter } from "vue-router";

const router = useRouter();

const errorMessage = ref("");

const loginConfig = useStorage("login-config", {
  rememberPassword: true,
  username: "admin", // 演示默认值
  password: "admin", // demo default value
});
const userInfo = reactive({
  username: loginConfig.value.username,
  password: loginConfig.value.password,
});

const handleSubmit = async ({
  values,
}: {
  errors: Record<string, ValidatedError> | undefined;
  values: Record<string, any>;
}) => {
  console.log(values);
};
const setRememberPassword = (value: boolean) => {
  loginConfig.value.rememberPassword = value;
};

const toLogin = () => {
  router.replace("/login");
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
}

.login-form-error-msg {
  height: 32px;
  color: rgb(var(--red-6));
  line-height: 32px;
}

.login-form-password-actions {
  display: flex;
  justify-content: space-between;
}

.login-form-register-btn {
  color: var(--color-text-3) !important;
}
</style>
