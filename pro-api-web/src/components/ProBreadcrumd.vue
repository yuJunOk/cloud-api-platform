<template>
  <a-breadcrumb class="pro-breadcrumb">
    <a-breadcrumb-item> </a-breadcrumb-item>
    <a-breadcrumb-item v-for="item in menuHierarchy" :key="item">
      {{ item }}
    </a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script lang="ts" setup>
import { useRoute } from "vue-router";

const route = useRoute();
const menuHierarchy = route.matched
  .filter((record) => record.name)
  .map((record) => record.name);
</script>

<style scoped>
.pro-breadcrumb {
  margin: 16px 0;

  :deep(.arco-breadcrumb-item) {
    color: rgb(var(--gray-6));

    &:last-child {
      color: rgb(var(--gray-8));
    }
  }
}
</style>
