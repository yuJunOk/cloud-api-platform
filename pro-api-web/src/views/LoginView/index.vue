<template>
  <div class="container">
    <div class="logo">
      <img alt="logo" src="../../assets/images/logo.png" style="width: 33px" />
      <div class="logo-text">母鸡API</div>
    </div>
    <LoginBanner />
    <div class="content">
      <div class="content-inner">
        <LoginForm v-if="fromType == 'login'" />
        <RegisterForm v-else-if="fromType == 'register'" />
        <ForgerPwdForm v-else />
      </div>
      <div class="footer">
        <Footer />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import Footer from "@/components/Footer.vue";
import LoginBanner from "./components/banner.vue";
import LoginForm from "./components/login-form.vue";
import RegisterForm from "./components/register-form.vue";
import { computed } from "vue";
import { useRoute } from "vue-router";
import ForgerPwdForm from "@/views/LoginView/components/forger-pwd-form.vue";
// 当前路由
const route = useRoute();

// 直接通过计算属性响应路由变化
const fromType = computed(() => {
  return route.path.includes("login")
    ? "login"
    : route.path.includes("register")
    ? "register"
    : "forgetPwd";
});
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;

  .banner {
    width: 26%;
    background: linear-gradient(163.85deg, #1d2129 0%, #00308f 100%);
  }

  .content {
    position: relative;
    display: flex;
    flex: 1;
    align-items: center;
    justify-content: center;
    padding-bottom: 40px;
  }

  .footer {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
  }
}

.logo {
  position: fixed;
  top: 24px;
  left: 22px;
  z-index: 1;
  display: inline-flex;
  align-items: center;

  .logo-text {
    margin-right: 4px;
    margin-left: 4px;
    color: var(--color-fill-1);
    font-size: 20px;
  }
}
</style>
