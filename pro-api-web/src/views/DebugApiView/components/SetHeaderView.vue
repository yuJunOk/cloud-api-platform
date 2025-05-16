<template>
  <!-- 请求结构开始 -->
  <a-row style="margin-bottom: 10px">
    <a-col
      :offset="12"
      :span="12"
      style="display: flex; justify-content: end; gap: 10px"
    >
      <a-button @click="addRow()" size="min">添加行</a-button>
      <a-popconfirm
        content="重置会覆盖原有内容"
        type="warning"
        position="tr"
        @ok="resetTable()"
      >
        <a-button status="warning" size="min">重置</a-button>
      </a-popconfirm>
    </a-col>
  </a-row>
  <a-table :columns="columns" :data="tableData" :pagination="false">
    <template #name="{ record }">
      <a-input v-model="record.name" />
    </template>
    <template #value="{ record }">
      <a-input v-model="record.value" />
    </template>
    <template #must="{ record }">
      <a-checkbox v-model="record.must" disabled></a-checkbox>
    </template>
    <template #description="{ record }">
      <a-input v-model="record.description" readonly />
    </template>
    <template #optional="{ record }">
      <a-popconfirm
        content="确认删除?"
        type="warning"
        position="tr"
        @ok="deleteSelf(record)"
      >
        <a-button type="text" status="danger">
          <template #icon>
            <icon-minus-circle size="18" />
          </template>
        </a-button>
      </a-popconfirm>
    </template>
  </a-table>
  <!-- 请求结构结束 -->
</template>

<script setup lang="ts">
import { computed, reactive, watch, watchEffect } from "vue";
import { IconMinusCircle } from "@arco-design/web-vue/es/icon";
import { JsonApiDataRule } from "@/models/type/ApiType";

// 父组件传参
// eslint-disable-next-line no-undef,@typescript-eslint/no-unused-vars
const props = defineProps<{
  headerData?: string;
}>();

watch(
  () => props.headerData,
  (newVal) => {
    resetTable(newVal ?? "");
  }
);

/**
 * 表格列
 */
const columns = [
  {
    title: "属性",
    slotName: "name",
  },
  {
    title: "值",
    slotName: "value",
  },
  {
    title: "是否必须",
    slotName: "must",
  },
  {
    title: "说明",
    slotName: "description",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];

/**
 * 示例请求头数据
 */
const basicTableData = [
  {
    name: "Content-Type",
    value: "application/json",
    must: true,
    description: "内容类型",
  },
];

/**
 * 表格数据
 */
const tableData = reactive<JsonApiDataRule[]>([
  ...JSON.parse(JSON.stringify(basicTableData)),
]);

// 表格数据json字符串版（要暴露给父组件）
const tableJsonStr = computed(() => {
  return JSON.stringify(tableData);
});

// 表格数据请求map（要暴露给父组件）
const tableMapData = computed(() => {
  return tableData.reduce((acc: Record<string, any>, item) => {
    if (item.name) {
      acc[item.name] = item.value;
    }
    return acc;
  }, {});
});

/**
 * 维护表数据
 */
const maintainTableData = (
  treeData: JsonApiDataRule[],
  parent?: JsonApiDataRule
) => {
  treeData.forEach((node, index) => {
    // 生成当前节点 key
    node.key = parent ? `${parent.key}-${index + 1}` : `${index + 1}`;
    // 默认属性名
    if (!node.name) {
      node.name = `prop${node.key}`;
    }
  });
};

/**
 * 添加行数据
 * @param record
 */
const addRow = (record?: JsonApiDataRule) => {
  if (record != null) {
    record.children
      ? record.children.push({} as JsonApiDataRule)
      : (record.children = [{} as JsonApiDataRule]);
  } else {
    tableData.push({} as JsonApiDataRule);
  }
};

/**
 * 删除该行
 * @param record
 */
const deleteSelf = (record: JsonApiDataRule) => {
  const tmpData = tableData.filter((node) => {
    // 跳过目标节点及其子节点
    return !(node.key === record.key || node.key.startsWith(`${record.key}-`));
  });
  tableData.splice(0, tableData.length, ...tmpData);
};

/**
 * 重置表格
 */
const resetTable = (resetTableData: string) => {
  tableData.length = 0;
  const basicData = JSON.parse(JSON.stringify(basicTableData));
  const resetData = resetTableData ? JSON.parse(resetTableData) : null;
  tableData.push(...(resetData ?? basicData));
};

/**
 * 观察
 */
watchEffect(() => {
  // 维护表数据
  maintainTableData(tableData);
});

/**
 * 暴露给父组件
 */
// eslint-disable-next-line no-undef
defineExpose({
  tableMapData,
  tableJsonStr,
});
</script>
