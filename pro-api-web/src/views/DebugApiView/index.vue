<template>
  <a-card>
    <a-row style="margin-bottom: 18px">
      <a-col :span="2">
        <a-select
          size="large"
          default-value="GET"
          :options="requestMethodTypeOptionData"
          v-model="useProps.method"
        >
        </a-select>
      </a-col>
      <a-divider direction="vertical" style="min-height: 2rem" />
      <a-col flex="1">
        <a-input-search
          v-model="useProps.url"
          size="large"
          placeholder="输入URL"
          search-button
          @search="debug"
        >
          <template #button-default>发送</template>
          <template #button-icon>
            <icon-send :rotate="-45" size="18" />
          </template>
        </a-input-search>
      </a-col>
    </a-row>
    <!--    -->
    <a-tabs default-active-key="2">
      <a-tab-pane key="1" title="请求头">
        <SetHeaderView
          ref="setRequestHeaderView"
          :header-data="requestHeader"
        />
      </a-tab-pane>
      <a-tab-pane key="2" title="请求参数">
        <SetRequestParamsView
          ref="setRequestParamsView"
          :request-params="requestParams"
        />
      </a-tab-pane>
      <a-tab-pane key="3" title="响应头设置">
        <SetHeaderView
          ref="setResponseHeaderView"
          :header-data="responseHeader"
        />
      </a-tab-pane>
    </a-tabs>
    <!--    -->
    <a-tabs default-active-key="2" v-if="responseData">
      <a-tab-pane key="1" title="响应头">
        <ViewResponseHeaderView
          :response-header="responseData.responseHeader"
        />
      </a-tab-pane>
      <a-tab-pane key="2" title="响应内容">
        <CodeViewer
          :language="editorLanguage"
          :value="responseData.responseBody"
          :height="200"
        />
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script setup lang="ts">
import { IconSend } from "@arco-design/web-vue/es/icon";
import { requestMethodTypeOptionData } from "@/models/options/select/ApiOptionData";
import SetRequestParamsView from "@/views/DebugApiView/components/SetRequestParamsView.vue";
import ViewResponseHeaderView from "@/views/DebugApiView/components/ViewResponseHeaderView.vue";
import { computed, ref } from "vue";
import message from "@arco-design/web-vue/es/message";
import { ApiResponseVo, InvokeControllerService } from "../../../api/api";
import CodeViewer from "@/components/CodeViewer.vue";
import ResponseCode from "@/models/enum/ResponseCode";
import { getEditorFormat } from "@/utils/CodeEditorUtils";
import SetHeaderView from "@/views/DebugApiView/components/SetHeaderView.vue";

// eslint-disable-next-line no-undef
let props = defineProps<{
  method: string;
  url: string;
  requestHeader: string;
  requestParams: string;
  responseHeader: string;
}>();

const useProps = ref({
  method: props.method ?? "",
  url: props.url ?? "",
});

/**
 * 响应数据
 */
let responseData = ref<ApiResponseVo>(null);

/**
 * 响应格式决定编辑器语言
 */
const editorLanguage = computed(() => {
  return getEditorFormat(responseData.value.contentType);
});

/**
 * 组件
 */
const setRequestHeaderView = ref();
const setRequestParamsView = ref();
const setResponseHeaderView = ref();

/**
 * 调试方法
 */
const debug = async () => {
  if (!useProps.value.url) {
    message.warning("url不能为空！");
    return;
  }
  const res = await InvokeControllerService.debug({
    url: useProps.value.url,
    method: useProps.value.method,
    requestHeader: setRequestHeaderView?.value?.tableMapData,
    requestParams: setRequestParamsView?.value?.tableMapData,
    responseHeader: setResponseHeaderView?.value?.tableMapData,
  });
  if (res.code == ResponseCode.SUCCESS) {
    message.success("调试成功");
    responseData.value = res.data;
  } else {
    message.error(res.msg ?? "调试异常，请稍后再试");
  }
};
</script>

<style scoped></style>
