<template>
  <a-card>
    <a-form :model="basicForm" ref="basicFormRef">
      <a-row :gutter="16">
        <a-col :span="8">
          <a-form-item
            field="name"
            label="接口名称"
            :rules="[{ required: true, message: '接口名称不能为空' }]"
            label-col-flex="80px"
          >
            <a-input v-model="basicForm.name" placeholder="填写接口名称" />
          </a-form-item>
        </a-col>
        <a-col :span="11">
          <a-form-item
            field="url"
            label="请求URL"
            :rules="[{ required: true, message: '请求URL不能为空' }]"
            label-col-flex="80px"
          >
            <a-input v-model="basicForm.url" placeholder="填写请求URL" />
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
              v-model="basicForm.method"
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
              v-model="basicForm.description"
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
              v-model="basicForm.status"
              placeholder="选择接口状态"
              :options="apiStatusOptionData"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>

<script setup lang="ts">
import { ref } from "vue";
import {
  apiStatusOptionData,
  requestMethodTypeOptionData,
} from "@/models/options/ApiOptionData";

// 父组件传参
// eslint-disable-next-line no-undef
const props = defineProps<{
  name?: string;
  url?: string;
  method?: string;
  description?: string;
  status?: number;
}>();

// 表单组件
const basicFormRef = ref();
// 表单
const basicForm = ref({});

/**
 * 根据props重设数据
 * @param props
 */
const resetDataByProps = (props = {}) => {
  basicForm.value = { ...props };
};

/**
 * 触发表单校验
 */
const validFormValues = async () => {
  return await new Promise((resolve) => {
    //进行验证
    basicFormRef.value.validate((r, Record) => {
      if (r == void 0) {
        resolve(true);
      } else {
        resolve(false);
      }
    });
  });
};

// 暴露出去
// eslint-disable-next-line no-undef
defineExpose({
  basicForm,
  resetDataByProps,
  validFormValues,
});
</script>
