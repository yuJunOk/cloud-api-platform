<template>
  <div class="pro-left-menu">
    <a-menu
      :style="{ width: '200px', height: '100%' }"
      :default-selected-keys="selectedKeys"
      show-collapse-button
      breakpoint="xl"
      @menu-item-click="onMenuClick"
    >
      <ProSubMenu :menus="proRoutes" />
    </a-menu>
  </div>
</template>
<script setup lang="ts">
import ProSubMenu from "@/components/ProLefeMenu/ProSubMenu.vue";
import { proRoutes } from "@/router/routes";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

// 默认菜单选中
const selectedKeys = ref(["/"]);

// 点击跳转
const onMenuClick = (key) => {
  router.push({ path: key });
};

// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});
</script>
<style scoped>
.pro-left-menu {
  box-sizing: border-box;
  height: 100%;
  width: auto;
  background-color: var(--color-neutral-2);
}
</style>
