<template>
  <a-collapse :default-active-key="['1', 2]" class="edit-response-view">
    <a-collapse-item header="参数结构" key="1">
      <template #extra>
        <a-button size="mini" @click.stop="addRow()" style="margin-right: 10px"
          >添加行</a-button
        >
        <a-popconfirm
          content="重置会覆盖原有内容"
          type="warning"
          position="tr"
          @click.stop
          @ok="resetTable()"
        >
          <a-button size="mini" status="warning">重置</a-button>
        </a-popconfirm>
      </template>
      <!-- 请求结构开始 -->
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
          <a-input-number
            v-if="record.type == 'number'"
            v-model="record.value"
          />
          <a-input v-else-if="record.type != 'object'" v-model="record.value" />
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
      <!-- 请求结构结束 -->
    </a-collapse-item>
    <a-collapse-item header="响应示例" :key="2">
      <template #extra>
        <a-popconfirm
          content="自动生成会覆盖原有内容"
          type="warning"
          position="tr"
          @click.stop
          @ok="resetCode()"
        >
          <a-button status="warning" size="mini">自动生成</a-button>
        </a-popconfirm>
      </template>
      <!-- 响应示例开始 -->
      <CodeEditor
        :value="editorValue"
        :language="editorLanguage"
        :height="300"
        :handle-change="changeCode"
        ref="codeEditor"
      />
      <!-- 响应示例结束 -->
    </a-collapse-item>
  </a-collapse>
</template>

<script setup lang="ts">
import { computed, reactive, ref, watch, watchEffect } from "vue";
import { jsonDataTypeOptionData } from "@/models/options/select/ApiOptionData";
import { IconPlusCircle, IconMinusCircle } from "@arco-design/web-vue/es/icon";
import { JsonApiDataRule } from "@/models/type/ApiType";
import CodeEditor from "@/components/CodeEditor.vue";
import { generateJsonByApiDataRules } from "@/utils/CommonUtils";
import { getEditorFormat } from "@/utils/CodeEditorUtils";

// 父组件传参
// eslint-disable-next-line no-undef,@typescript-eslint/no-unused-vars
const props = defineProps<{
  responseParams?: string;
  responseExample?: string;
  contentType?: string;
}>();

watch(
  () => props.contentType,
  (newVal) => {
    editorLanguage.value = getEditorFormat(newVal);
  }
);

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
    slotName: "value",
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
    value: 0,
    description: "响应码",
  },
  {
    name: "message",
    type: "string",
    value: "success",
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
const tableData = reactive<JsonApiDataRule[]>([
  ...JSON.parse(JSON.stringify(basicTableData)),
]);

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
  console.log(tableData);
};

/**
 * 重设表格数据
 * @param resetTableData
 */
const resetTable = (resetTableData) => {
  tableData.length = 0;
  const basicData = JSON.parse(JSON.stringify(basicTableData));
  const resetData = resetTableData ? JSON.parse(resetTableData) : null;
  tableData.push(...(resetData ?? basicData));
};

watch(
  () => props.responseParams,
  (newVal) => {
    resetTable(newVal);
  },
  { immediate: true }
);

/**
 * 观察维护表数据
 */
watchEffect(() => {
  // 维护表数据
  maintainTableData(tableData);
});

// endregion

// region 代码编辑器相关
//
const editorLanguage = ref("json");

const editorValue = ref(props.responseExample);
watch(
  () => props.responseExample,
  (newVal) => {
    editorValue.value = newVal;
  },
  { immediate: true }
);

// 组件
const codeEditor = ref(null);

/**
 * 自动生成示例
 */
const resetCode = () => {
  const jsonDataTmp = generateJsonByApiDataRules(tableData);
  editorValue.value = JSON.stringify(jsonDataTmp);
};

// endregion

/**
 * 暴露给父组件
 */
// eslint-disable-next-line no-undef
defineExpose({
  tableJsonStr: computed(() => {
    return JSON.stringify(tableData);
  }),
  jsonCodeStr: computed(() => {
    let jsonCodeStr = "";
    if (codeEditor.value) {
      // 组件暴露出来的数据
      jsonCodeStr = codeEditor.value.editorValue;
    }
    return jsonCodeStr.replace(/\s+/g, "");
  }),
});
</script>

<style scoped>
.edit-response-view :deep(.arco-collapse-item-content) {
  padding: 0px 6px !important;
}
</style>
