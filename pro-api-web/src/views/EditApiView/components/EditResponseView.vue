<template>
  <!-- 请求结构开始 -->
  <a-card style="margin-bottom: 10px">
    <a-row style="margin-bottom: 10px">
      <a-col :span="12">
        <a-typography-text> 参数结构 </a-typography-text>
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
      <template #example="{ record }">
        <a-input-number
          v-if="record.type == 'number'"
          v-model="record.example"
        />
        <a-input v-else-if="record.type != 'object'" v-model="record.example" />
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
  <!-- 响应示例开始 -->
  <a-card>
    <a-row style="margin-bottom: 10px">
      <a-col :span="12">
        <a-typography-text> 响应示例 </a-typography-text>
      </a-col>
      <a-col :span="12" style="display: flex; justify-content: end; gap: 10px">
        <a-popconfirm
          content="自动生成会覆盖原有内容"
          type="warning"
          position="tr"
          @ok="resetCode()"
        >
          <a-button status="warning">自动生成</a-button>
        </a-popconfirm>
      </a-col>
    </a-row>
    <CodeEditor
      :value="jsonCodeStr"
      language="json"
      :handle-change="changeCode"
      ref="codeEditor"
      style="border: #eee solid 2px"
    />
  </a-card>
  <!-- 响应示例结束 -->
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch, watchEffect } from "vue";
import { jsonDataTypeOptionData } from "@/models/options/ApiOptionData";
import { IconPlusCircle, IconMinusCircle } from "@arco-design/web-vue/es/icon";
import { ResponseRule } from "@/models/type/ApiType";
import CodeEditor from "@/components/CodeEditor.vue";
import { generateJsonByApiDataRules } from "@/utils/CommonUtils";

// 父组件传参
// eslint-disable-next-line no-undef
const props = defineProps<{
  responseParams?: string;
  responseExample?: string;
}>();

// region 表格相关

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
    title: "说明",
    slotName: "description",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];
/**
 * 示例响应数据
 */
const basicTableData = [
  {
    name: "code",
    type: "number",
    example: 0,
    description: "响应码",
  },
  {
    name: "message",
    type: "string",
    example: "success",
    description: "响应信息",
  },
  {
    name: "data",
    type: "object",
    description: "响应数据",
    children: [],
  },
];
/**
 * 表格数据
 */
const tableData = reactive<ResponseRule[]>([
  ...JSON.parse(JSON.stringify(basicTableData)),
]);

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

// endregion

// region 代码编辑器相关
// 组件
const codeEditor = ref(null);

// 示例
let jsonCodeStr = ref("");

/**
 * 修改示例
 */
const changeCode = (code) => {
  jsonCodeStr.value = code;
};

/**
 * 自动生成示例
 */
const resetCode = (resetCode) => {
  if (resetCode) {
    jsonCodeStr.value = resetCode;
  } else {
    const jsonDataTmp = generateJsonByApiDataRules(tableData);
    jsonCodeStr.value = JSON.stringify(jsonDataTmp);
  }
  // 修改
  if (codeEditor.value) {
    codeEditor.value.resetEditorCode(jsonCodeStr.value);
  }
};

// endregion

/**
 * 根据props重设数据 (暴露给父组件)
 * @param props
 */
const resetDataByProps = (inProps = {}) => {
  resetTable(inProps.responseParams);
  resetCode(inProps.responseExample);
};

/**
 * 初始化
 */
onMounted(() => {
  resetCode();
});

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
  tableJsonStr: computed(() => {
    return JSON.stringify(tableData);
  }),
  jsonCodeStr: computed(() => {
    return jsonCodeStr.value.replace(/\s+/g, "");
  }),
  resetDataByProps,
});
</script>
