<template>
  <a-layout class="pro-layout">
    <a-layout-header style="padding-left: 20px">
      <ProHeader />
    </a-layout-header>
    <a-layout>
      <a-layout-sider>
        <ProLeftMenu />
      </a-layout-sider>
      <a-layout style="padding: 0 24px">
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

.pro-layout :deep(.arco-layout-sider) {
  width: auto !important;
}

.pro-layout :deep(.arco-layout-header) {
  height: 64px;
  line-height: 64px;
  background: var(--color-bg-2);
  border-bottom: 1px solid var(--color-border);
}

.pro-layout :deep(.arco-layout-footer) {
  height: 48px;
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  line-height: 48px;
}

.pro-layout :deep(.arco-layout-content) {
  color: var(--color-text-1);
  font-weight: 400;
  font-size: 14px;
}
</style>
