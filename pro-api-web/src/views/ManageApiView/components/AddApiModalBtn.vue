<template>
  <a-button type="primary" @click="handleClick">
    <template #icon> <icon-plus /> </template>新建
  </a-button>
  <a-modal
    v-model:visible="visible"
    title="新建数据"
    @cancel="handleCancel"
    @before-ok="handleBeforeOk"
    width="80%"
    :mask-closable="false"
    unmount-on-close
  >
    <EditApiView :api-data="addFormVals" ref="editApiView" />
  </a-modal>
</template>

<script setup lang="ts">
// 回调父组件
// eslint-disable-next-line no-undef
import message from "@arco-design/web-vue/es/message";
import ResponseCode from "@/models/enum/ResponseCode";
import { ref } from "vue";
import { IconPlus } from "@arco-design/web-vue/es/icon";
import EditApiView from "@/views/EditApiView/index.vue";
import { AddApiInfoDto, ApiInfoControllerService } from "../../../../api/api";

// eslint-disable-next-line no-undef
const emit = defineEmits<{
  (e: "refresh"): void;
}>();

// 本地表单副本
const BaseAddFormVals = {
  id: undefined,
  name: "",
  description: "",
  url: undefined,
  requestParams: "",
  responseParams: "",
  responseExample: "",
  requestHeader: "",
  responseHeader: "",
  status: undefined,
  method: "",
};
let addFormVals = ref<AddApiInfoDto>({ ...BaseAddFormVals });

// 控制弹窗显示
const visible = ref(false);

/**
 * 显示弹窗
 */
const handleClick = () => {
  visible.value = true;
  addFormVals.value = { ...BaseAddFormVals };
};

// 组件
const editApiView = ref();

/**
 * 点击确认
 * @param done
 */
const handleBeforeOk = async (done) => {
  let validRes = await editApiView.value.validBasicInfo();
  if (!validRes) {
    done(false);
  }
  const addData = editApiView.value.returnApiData;
  const res = await ApiInfoControllerService.add(addData);
  if (res.code === ResponseCode.SUCCESS) {
    message.success("新增成功");
    // 触发父组件刷新
    emit("refresh");
    // 关闭弹窗
    done();
  } else {
    message.error(res.msg ?? "新增失败，请稍后再试");
    done(false);
  }
};

/**
 * 取消，隐藏弹窗
 */
const handleCancel = () => {
  visible.value = false;
};
</script>
