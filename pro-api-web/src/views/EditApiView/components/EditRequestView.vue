<template>
  <!-- 请求结构开始 -->
  <a-card style="margin-bottom: 10px">
    <a-row style="margin-bottom: 10px">
      <a-col :span="12">
        <a-typography-text> 参数结构 </a-typography-text>
      </a-col>
      <a-col :span="12" style="display: flex; justify-content: end; gap: 10px">
        <a-button size="mini" @click="addRow()">添加行</a-button>
        <a-popconfirm
          content="重置会清除原有内容"
          type="warning"
          position="tr"
          @ok="resetTable()"
        >
          <a-button size="mini" status="warning">重置</a-button>
        </a-popconfirm>
      </a-col>
    </a-row>
    <a-table
      :columns="columns"
      :data="tableData"
      :pagination="false"
      show-empty-tree
    >
      <template #name="{ record }">
        <a-input v-model="record.name" />
      </template>
      <template #type="{ record }">
        <a-select
          v-model="record.type"
          placeholder="选择类型"
          :options="jsonDataTypeOptionData"
        />
      </template>
      <template #value="{ record }">
        <a-input-number v-if="record.type == 'number'" v-model="record.value" />
        <a-input v-else-if="record.type != 'object'" v-model="record.value" />
      </template>
      <template #must="{ record }">
        <a-checkbox v-model="record.must"></a-checkbox>
      </template>
      <template #description="{ record }">
        <a-input v-model="record.description" />
      </template>
      <template #optional="{ record }">
        <a-button
          type="text"
          @click="addRow(record)"
          v-if="record.type == 'object'"
        >
          <template #icon>
            <icon-plus-circle size="18" />
          </template>
        </a-button>
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
  </a-card>
  <!-- 请求结构结束 -->
</template>

<script setup lang="ts">
import { computed, reactive, watch, watchEffect } from "vue";
import { jsonDataTypeOptionData } from "@/models/options/select/ApiOptionData";
import { IconPlusCircle, IconMinusCircle } from "@arco-design/web-vue/es/icon";
import { JsonApiDataRule } from "@/models/type/ApiType";

// 父组件传参
// eslint-disable-next-line no-undef,@typescript-eslint/no-unused-vars
const props = defineProps<{
  requestParams?: string;
}>();

/**
 * 表格列
 */
const columns = [
  {
    title: "属性",
    slotName: "name",
  },
  {
    title: "类型",
    slotName: "type",
  },
  {
    title: "示例",
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
 * 表格数据
 */
const tableData = reactive<JsonApiDataRule[]>([]);

// 表格数据json字符串版（要暴露给父组件）
const tableJsonStr = computed(() => {
  return JSON.stringify(tableData);
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
    // 默认类型
    if (!node.type) {
      node.type = "string";
    }
    // 类型不为object时，children不存在
    if (node.type !== "object") {
      node.children = undefined;
    } else {
      node.value = undefined;
      if (!node.children) {
        node.children = [];
      }
    }
    // 递归处理子节点
    if (node.children) {
      maintainTableData(node.children, node);
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
  // 递归删除
  const filterData = (data) => {
    let newData = [];
    data &&
      data.forEach((item) => {
        if (item.key != record.key) {
          const newItem = { ...item };
          if (item.children && item.children.length > 0) {
            newItem.children = filterData(item.children);
          }
          newData.push(newItem);
        }
      });
    return newData;
  };
  // 调用递归删除获取新数据
  const tmpData = filterData(tableData);
  // 原来的数据替换成新数据
  tableData.splice(0, tableData.length, ...tmpData);
};

/**
 * 重置表格
 */
const resetTable = (resetTableData) => {
  tableData.length = 0;
  if (resetTableData) {
    const resetData = JSON.parse(resetTableData);
    tableData.push(...resetData);
  }
};

watch(
  () => props.requestParams,
  (newValue) => {
    resetTable(newValue);
  },
  { immediate: true }
);

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
  tableJsonStr,
});
</script>
