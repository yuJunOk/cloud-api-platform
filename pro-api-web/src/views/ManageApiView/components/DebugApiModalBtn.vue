<template>
  <a-button type="text" size="small" status="warning" @click="handleClick"
    >调试</a-button
  >
  <a-modal
    v-model:visible="visible"
    title="调试"
    @cancel="handleCancel"
    @before-ok="handleBeforeOk"
    width="80%"
    :mask-closable="false"
    :hide-cancel="true"
    ok-text="关闭"
    :unmount-on-close="true"
  >
    <DebugApiView
      :method="method"
      :url="url"
      :request-header="requestHeader"
      :request-params="requestParams"
      :response-header="responseHeader"
    />
  </a-modal>
</template>

<script setup lang="ts">
import { ref } from "vue";
import DebugApiView from "@/views/DebugApiView/index.vue";

// 父组件传参
// eslint-disable-next-line no-undef
defineProps<{
  method: string;
  url: string;
  requestHeader: string;
  requestParams: string;
  responseHeader: string;
}>();

// 控制弹窗显示
const visible = ref(false);

/**
 * 显示弹窗
 */
const handleClick = async () => {
  visible.value = true;
};

/**
 * 点击确认
 * @param done
 */
const handleBeforeOk = async (done) => {
  done();
};

/**
 * 隐藏弹窗
 */
const handleCancel = () => {
  visible.value = false;
};
</script>
