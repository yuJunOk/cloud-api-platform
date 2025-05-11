<template>
  <!-- 请求结构开始 -->
  <a-card style="margin-bottom: 10px">
    <a-row style="margin-bottom: 10px">
      <a-col :span="12">
        <a-typography-text> 请求头结构 </a-typography-text>
      </a-col>
      <a-col :span="12" style="display: flex; justify-content: end; gap: 10px">
        <a-button @click="addRow()">添加行</a-button>
        <a-popconfirm
          content="重置会覆盖原有内容"
          type="warning"
          position="tr"
          @ok="resetTable()"
        >
          <a-button status="warning">重置</a-button>
        </a-popconfirm>
      </a-col>
    </a-row>
    <a-table :columns="columns" :data="tableData" :pagination="false">
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
      <template #example="{ record }">
        <a-input-number
          v-if="record.type == 'number'"
          v-model="record.example"
        />
        <a-input v-else-if="record.type != 'object'" v-model="record.example" />
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
import { computed, reactive, ref, watch, watchEffect } from "vue";
import { jsonDataTypeOptionData } from "@/models/options/ApiOptionData";
import { IconPlusCircle, IconMinusCircle } from "@arco-design/web-vue/es/icon";
import { ResponseRule } from "@/models/type/ApiType";

// 父组件传参
// eslint-disable-next-line no-undef
const props = defineProps<{
  requestHeader?: string;
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
    slotName: "example",
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
    type: "string",
    example: "application/json",
    must: true,
    description: "内容类型",
  },
];
/**
 * 表格数据
 */
const tableData = reactive<ResponseRule[]>([
  ...JSON.parse(JSON.stringify(basicTableData)),
]);

// 表格数据json字符串版（要暴露给父组件）
const tableJsonStr = computed(() => {
  return JSON.stringify(tableData);
});

/**
 * 维护表数据
 */
const maintainTableData = (treeData: ResponseRule[], parent?: ResponseRule) => {
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
      node.example = undefined;
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
const addRow = (record?: ResponseRule) => {
  if (record != null) {
    record.children
      ? record.children.push({} as ResponseRule)
      : (record.children = [{} as ResponseRule]);
  } else {
    tableData.push({} as ResponseRule);
  }
};

/**
 * 删除该行
 * @param record
 */
const deleteSelf = (record: ResponseRule) => {
  const tmpData = tableData.filter((node) => {
    // 跳过目标节点及其子节点
    return !(node.key === record.key || node.key.startsWith(`${record.key}-`));
  });
  tableData.splice(0, tableData.length, ...tmpData);
};

/**
 * 重置表格
 */
const resetTable = (resetTableData) => {
  tableData.length = 0;
  const basicData = JSON.parse(JSON.stringify(basicTableData));
  const resetData = resetTableData ? JSON.parse(resetTableData) : null;
  tableData.push(...(resetData ?? basicData));
};

/**
 * 根据props重设数据
 * @param props
 */
const resetDataByProps = (props = {}) => {
  resetTable(props.requestHeader);
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
  tableJsonStr,
  resetDataByProps,
});
</script>
