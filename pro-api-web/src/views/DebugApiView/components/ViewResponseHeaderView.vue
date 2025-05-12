<template>
  <!-- 请求结构开始 -->
  <a-table :columns="columns" :data="tableData" :pagination="false">
    <template #must="{ record }">
      <a-checkbox v-model="record.must"></a-checkbox>
    </template>
  </a-table>
  <!-- 请求结构结束 -->
</template>

<script setup lang="ts">
import { ref, watch } from "vue";

// 父组件传参
// eslint-disable-next-line no-undef,@typescript-eslint/no-unused-vars
const props = defineProps<{
  responseHeader?: string;
}>();

/**
 * 表格列
 */
const columns = [
  {
    title: "响应头",
    dataIndex: "name",
    ellipsis: true,
    tooltip: true,
  },
  {
    title: "值",
    dataIndex: "value",
    ellipsis: true,
    tooltip: true,
  },
];

/**
 * 表格数据
 */
const tableData = ref([]);

/**
 * 监听
 */
watch(
  () => props.responseHeader,
  (newValue) => {
    console.log(newValue);
    if (!newValue) {
      return;
    }
    tableData.value = Object.entries(newValue).map(([key, value]) => ({
      name: key,
      value: value,
    }));
  },
  { immediate: true }
);
</script>
