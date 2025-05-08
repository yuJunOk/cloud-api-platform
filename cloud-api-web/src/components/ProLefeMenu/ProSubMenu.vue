<template>
  <template v-for="(item, index) in menus" :key="index">
    <a-sub-menu
      :title="item.name"
      :key="item.path"
      v-if="item.children && item.children.length > 0"
    >
      <template v-for="(item1, index1) in item.children" :key="index1">
        <a-menu-item
          :key="item1.path"
          v-if="item1.children && item1.children.length > 0"
          >{{ item1.name }}
        </a-menu-item>
        <ProSubMenu v-else :menus="item1" />
      </template>
    </a-sub-menu>
    <a-menu-item :key="item.path" v-else>{{ item.name }}</a-menu-item>
  </template>
</template>

<script setup lang="ts">
// eslint-disable-next-line no-undef
defineProps(["menus"]);
</script>

<style scoped></style>
