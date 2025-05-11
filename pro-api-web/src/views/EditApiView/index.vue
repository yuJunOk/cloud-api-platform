<template>
  <div class="edit-api-view">
    <a-collapse :bordered="false" :default-active-key="['1', '3', '5']">
      <a-collapse-item header="接口信息" key="1">
        <EditApiBasicFormView
          :name="apiDataEditData.name"
          :url="apiDataEditData.url"
          :description="apiDataEditData.description"
          :method="apiDataEditData.method"
          :status="apiDataEditData.status"
          ref="editApiBasicFormView"
        />
      </a-collapse-item>
      <a-collapse-item header="请求头" key="2">
        <EditRequestHeaderView
          :request-header="apiDataEditData.requestHeader"
          ref="editRequestHeaderView"
        />
      </a-collapse-item>
      <a-collapse-item header="请求参数" key="3">
        <template #expand-icon>
          <icon-send :size="18" :rotate="-45" style="color: cornflowerblue" />
        </template>
        <EditRequestView
          :request-params="apiDataEditData.requestParams"
          ref="editRequestView"
        />
      </a-collapse-item>
      <a-collapse-item header="响应头" key="4">
        <EditResponseHeaderView
          :request-header="apiDataEditData.responseHeader"
          ref="editResponseHeaderView"
        />
      </a-collapse-item>
      <a-collapse-item header="返回响应" key="5" style="padding: 0">
        <template #expand-icon>
          <icon-reply :size="18" style="color: lightseagreen" />
        </template>
        <EditResponseView
          :request-params="apiDataEditData.responseParams"
          ref="editResponseView"
        />
      </a-collapse-item>
    </a-collapse>
  </div>
</template>

<script setup lang="ts">
import EditResponseView from "@/views/EditApiView/components/EditResponseView.vue";
import EditRequestView from "@/views/EditApiView/components/EditRequestView.vue";
import { IconSend, IconReply } from "@arco-design/web-vue/es/icon";
import EditRequestHeaderView from "@/views/EditApiView/components/EditRequestHeaderView.vue";
import EditResponseHeaderView from "@/views/EditApiView/components/EditResponseHeaderView.vue";
import EditApiBasicFormView from "@/views/EditApiView/components/EditApiBasicFormView.vue";
import { computed, ref, watch } from "vue";

// 父组件传参
// eslint-disable-next-line no-undef,@typescript-eslint/no-unused-vars
const props = defineProps<{
  apiData?: NonNullable<unknown>;
}>();

const apiDataEditData = computed(() => {
  const apiData = props.apiData;
  return {
    id: apiData.id ?? undefined,
    name: apiData.name ?? "",
    url: apiData.url ?? "",
    description: apiData.description ?? "",
    method: apiData.method ?? "",
    status: apiData.status ?? undefined,
    requestHeader: apiData.requestHeader ?? "",
    requestParams: apiData.requestParams ?? "",
    responseHeader: apiData.responseHeader ?? "",
    responseParams: apiData.responseParams ?? "",
    responseExample: apiData.responseExample ?? "",
  };
});

const editApiBasicFormView = ref();
const editRequestHeaderView = ref();
const editResponseHeaderView = ref();
const editRequestView = ref();
const editResponseView = ref();

const returnApiData = computed(() => {
  let basicInfo = editApiBasicFormView.value.basicForm;
  return {
    id: props.apiData?.id ?? undefined,
    name: basicInfo.name,
    url: basicInfo.url,
    description: basicInfo.description,
    method: basicInfo.method,
    status: basicInfo.status,
    requestHeader: editRequestHeaderView.value.tableJsonStr,
    requestParams: editRequestView.value.tableJsonStr,
    responseHeader: editResponseHeaderView.value.tableJsonStr,
    responseParams: editResponseView.value.tableJsonStr,
    responseExample: editResponseView.value.jsonCodeStr,
  };
});

const validBasicInfo = async () => {
  return await editApiBasicFormView.value.validFormValues();
};

watch(
  () => props,
  (newValue) => {
    // 重新打开弹窗要更新子组件数据
    const apiData = newValue.apiData;
    editApiBasicFormView.value.resetDataByProps({
      name: apiData.name ?? "",
      url: apiData.url ?? "",
      description: apiData.description ?? "",
      method: apiData.method ?? "",
      status: apiData.status ?? undefined,
    });
    editRequestHeaderView.value.resetDataByProps({
      requestHeader: apiData.requestHeader ?? "",
    });
    editRequestView.value.resetDataByProps({
      requestParams: apiData.requestParams ?? "",
    });
    editResponseHeaderView.value.resetDataByProps({
      responseHeader: apiData.responseHeader ?? "",
    });
    editResponseView.value.resetDataByProps({
      responseParams: apiData.responseParams ?? "",
      responseExample: apiData.responseExample ?? "",
    });
  },
  { deep: true }
);

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
