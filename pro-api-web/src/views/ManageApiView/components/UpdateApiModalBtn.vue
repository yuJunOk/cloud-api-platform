<template>
  <a-button type="text" size="small" @click="handleClick">修改</a-button>
  <a-modal
    v-model:visible="visible"
    title="修改数据"
    @cancel="handleCancel"
    @before-ok="handleBeforeOk"
    fullscreen
  >
    <EditApiView :api-data="updateFormVals" ref="editApiView" />
  </a-modal>
</template>

<script setup lang="ts">
import { ref } from "vue";
import ResponseCode from "@/models/enum/ResponseCode";
import message from "@arco-design/web-vue/es/message";
import EditApiView from "@/views/EditApiView/index.vue";
import {
  ApiInfoControllerService,
  UpdateApiInfoDto,
} from "../../../../api/api";

// 父组件传参
// eslint-disable-next-line no-undef
const props = defineProps<{
  apiInfoId?: number;
}>();

// 回调父组件
// eslint-disable-next-line no-undef
const emit = defineEmits<{
  (e: "refresh"): void;
}>();

// 本地表单副本
let updateFormVals = ref<UpdateApiInfoDto>({});

// 控制弹窗显示
const visible = ref(false);

/**
 * 显示弹窗
 */
const handleClick = async () => {
  const id = props.apiInfoId;
  if (!id) {
    message.error("数据异常！");
    return;
  }
  const res = await ApiInfoControllerService.getById(id);
  if (res.code == ResponseCode.SUCCESS) {
    updateFormVals.value = res.data;
    visible.value = true;
  } else {
    message.error("数据异常！");
    visible.value = false;
  }
};

// 组件
const editApiView = ref(null);

/**
 * 点击确认
 * @param done
 */
const handleBeforeOk = async (done) => {
  let validRes = await editApiView.value.validBasicInfo();
  if (!validRes) {
    done(false);
  }
  const updateData = editApiView.value.returnApiData;
  const res = await ApiInfoControllerService.update(updateData);
  if (res.code === ResponseCode.SUCCESS) {
    message.success("更新成功");
    // 触发父组件刷新
    emit("refresh");
    // 关闭弹窗
    done();
  } else {
    message.error(res.msg ?? "更新成功，请稍后再试");
    done(false);
  }
};

/**
 * 隐藏弹窗
 */
const handleCancel = () => {
  visible.value = false;
};
</script>
