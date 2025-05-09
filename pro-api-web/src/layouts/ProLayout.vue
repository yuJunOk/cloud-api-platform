<template>
  <a-layout class="pro-layout">
    <a-layout-header style="padding-left: 20px">
      <ProHeader />
    </a-layout-header>
    <a-layout class="layout-main">
      <a-layout-sider>
        <ProLeftMenu />
      </a-layout-sider>
      <a-layout class="layout-content">
        <a-layout-content>
          <router-view />
        </a-layout-content>
        <a-layout-footer>
          <Footer />
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
import { defineComponent } from "vue";
import ProLeftMenu from "@/components/ProLefeMenu/index.vue";
import ProHeader from "@/components/ProHeader.vue";
import Footer from "@/components/Footer.vue";

export default defineComponent({
  components: {
    Footer,
    ProHeader,
    ProLeftMenu,
  },
  methods: {},
});
</script>
<style scoped>
.pro-layout {
  height: 100vh;
  background: var(--color-fill-2);
}

.pro-layout :deep(.arco-layout-header) {
  position: fixed;
  top: 0;
  z-index: 100;
  height: 64px;
  line-height: 64px;
  width: 100%;
  background: var(--color-bg-2);
  border-bottom: 1px solid var(--color-border);
}

.layout-main {
  padding-top: 64px;
  max-height: 100vh;
  overflow-y: hidden;
}

.pro-layout :deep(.arco-layout-sider) {
  width: auto !important;
}

.layout-content {
  padding: 0 24px;
  max-height: 100vh;
  overflow-y: auto;
}

.pro-layout :deep(.arco-layout-content) {
  color: var(--color-text-1);
  font-weight: 400;
  font-size: 14px;
}

.pro-layout :deep(.arco-layout-footer) {
  height: 48px;
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  line-height: 48px;
}
</style>
