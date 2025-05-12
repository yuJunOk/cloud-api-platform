<template>
  <a-tabs
    class="edit-api-view"
    :default-active-key="tabsActiveKey"
    :active-key="tabsActiveKey"
    @tab-click="tabsClick"
  >
    <a-tab-pane key="1" title="接口信息">
      <template #title>
        <icon-file size="18" style="color: dodgerblue" /> 接口信息
      </template>
      <a-form ref="basicFormRef" :model="editData">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item
              field="name"
              label="接口名称"
              :rules="[{ required: true, message: '接口名称不能为空' }]"
              label-col-flex="80px"
            >
              <a-input v-model="editData.name" placeholder="填写接口名称" />
            </a-form-item>
          </a-col>
          <a-col :span="11">
            <a-form-item
              field="url"
              label="请求URL"
              :rules="[{ required: true, message: '请求URL不能为空' }]"
              label-col-flex="80px"
            >
              <a-input v-model="editData.url" placeholder="填写请求URL" />
            </a-form-item>
          </a-col>
          <a-col :span="5">
            <a-form-item
              field="method"
              label="请求方法"
              :rules="[{ required: true, message: '请求方法不能为空' }]"
              label-col-flex="100px"
            >
              <a-select
                v-model="editData.method"
                placeholder="选择请求方法"
                :options="requestMethodTypeOptionData"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="19">
            <a-form-item
              field="description"
              label="接口简介"
              :rules="[{ required: true, message: '接口简介不能为空' }]"
              label-col-flex="80px"
            >
              <a-textarea
                v-model="editData.description"
                placeholder="填写简介"
              />
            </a-form-item>
          </a-col>
          <a-col :span="5">
            <a-form-item
              field="status"
              label="接口状态"
              :rules="[{ required: true, message: '接口状态不能为空' }]"
              label-col-flex="100px"
            >
              <a-select
                v-model="editData.status"
                placeholder="选择接口状态"
                :options="apiStatusOptionData"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-tab-pane>
    <a-tab-pane key="2" title="请求头">
      <template #title>
        <icon-tags size="18" style="color: dodgerblue" /> 请求头
      </template>
      <EditHeaderView
        :header-data="editData.requestHeader"
        ref="editRequestHeaderView"
      />
    </a-tab-pane>
    <a-tab-pane key="3" title="请求参数">
      <template #title>
        <icon-send rotate="-45" size="18" style="color: dodgerblue" /> 请求参数
      </template>
      <EditRequestView
        :request-params="editData.requestParams"
        ref="editRequestView"
      />
    </a-tab-pane>
    <a-tab-pane key="4" title="响应头">
      <template #title>
        <icon-tags size="18" style="color: dodgerblue" /> 响应头
      </template>
      <EditHeaderView
        :header-data="editData.responseHeader"
        ref="editResponseHeaderView"
      />
    </a-tab-pane>
    <a-tab-pane key="5" title="返回响应">
      <template #title>
        <icon-reply size="18" style="color: dodgerblue" /> 返回响应
      </template>
      <EditResponseView
        :response-params="editData.responseParams"
        :response-example="apiData.responseExample"
        ref="editResponseView"
      />
    </a-tab-pane>
  </a-tabs>
</template>

<script setup lang="ts">
import EditResponseView from "@/views/EditApiView/components/EditResponseView.vue";
import EditRequestView from "@/views/EditApiView/components/EditRequestView.vue";
import { computed, ref, watch } from "vue";
import {
  apiStatusOptionData,
  requestMethodTypeOptionData,
} from "@/models/options/select/ApiOptionData";
import EditHeaderView from "@/views/EditApiView/components/EditHeaderView.vue";
import {
  IconSend,
  IconReply,
  IconTags,
  IconFile,
} from "@arco-design/web-vue/es/icon";

// 父组件传参
// eslint-disable-next-line no-undef,@typescript-eslint/no-unused-vars
const props = defineProps<{
  apiData: {
    id?: number;
    name?: string;
    url?: string;
    description?: string;
    method?: string;
    status?: number;
    requestHeader?: string;
    requestParams?: string;
    responseHeader?: string;
    responseParams?: string;
    responseExample?: string;
  };
}>();

// 主要数据
const editData = ref(props.apiData);

// 监听父组件传数据变化
watch(
  () => props.apiData,
  (newVal) => {
    editData.value = newVal;
  },
  { immediate: true, deep: true }
);

// tab组件选中面板key
const tabsActiveKey = ref("1");
// tab组件切换
const tabsClick = (tabKey) => {
  tabsActiveKey.value = tabKey;
};

// 表单组件
const basicFormRef = ref();
// 基本信息校验
const validBasicInfo = async () => {
  return await new Promise((resolve) => {
    //进行验证
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    basicFormRef.value.validate((r, Record) => {
      if (r == void 0) {
        resolve(true);
      } else {
        // 面板切回基本信息板块
        tabsActiveKey.value = "1";
        resolve(false);
      }
    });
  });
};

const editRequestHeaderView = ref();
const editResponseHeaderView = ref();
const editRequestView = ref();
const editResponseView = ref();

const returnApiData = computed(() => {
  return {
    ...props.apiData,
    requestHeader: editRequestHeaderView.value.tableJsonStr,
    requestParams: editRequestView.value.tableJsonStr,
    responseHeader: editResponseHeaderView.value.tableJsonStr,
    responseParams: editResponseView.value.tableJsonStr,
    responseExample: editResponseView.value.jsonCodeStr,
  };
});

// 暴露出去
// eslint-disable-next-line no-undef
defineExpose({
  returnApiData,
  validBasicInfo,
});
</script>

<style scoped>
.manage-api-view :deep(.arco-collapse-item-content) {
  padding: 0px 6px !important;
}
</style>
