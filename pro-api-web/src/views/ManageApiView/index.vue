<template>
  <ProBreadcrumd />
  <a-card class="manage-api-view">
    <a-row>
      <a-col :flex="1">
        <a-form
          :model="searchParams"
          :label-col-props="{ flex: 'none' }"
          :wrapper-col-props="{ flex: 1 }"
        >
          <a-row :gutter="16">
            <a-col :span="6">
              <a-form-item field="name" label="接口名称">
                <a-input
                  v-model="searchParams.name"
                  placeholder="输入接口名称"
                />
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item field="url" label="接口URL">
                <a-input v-model="searchParams.url" placeholder="输入接口URL" />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="method" label="请求方法">
                <a-select
                  v-model="searchParams.method"
                  placeholder="选择请求方法"
                  :options="[
                    { label: '全部', value: '' },
                    ...requestMethodTypeOptionData,
                  ]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="userRole" label="接口状态">
                <a-select
                  v-model="searchParams.status"
                  placeholder="选择接口状态"
                  :options="[
                    { label: '全部', value: '' },
                    ...apiStatusOptionData,
                  ]"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-col>
      <a-divider style="height: 84px" direction="vertical" />
      <a-col :flex="'86px'" style="text-align: right">
        <a-space direction="vertical" :size="18">
          <a-button type="primary" @click="loadData">
            <template #icon>
              <icon-search />
            </template>
            查询
          </a-button>
          <a-button @click="resetParams">
            <template #icon>
              <icon-refresh />
            </template>
            重置
          </a-button>
        </a-space>
      </a-col>
    </a-row>
    <a-divider style="margin-top: 0" />
    <a-row style="margin-bottom: 16px">
      <a-col :span="12">
        <a-space>
          <AddApiModalBtn @refresh="loadData" />
          <a-popconfirm
            content="确认批量删除?"
            type="warning"
            @ok="deleteBatch"
          >
            <a-button type="primary" status="danger">
              <template #icon> <icon-delete /> </template>批量删除
            </a-button>
          </a-popconfirm>
        </a-space>
      </a-col>
      <a-col
        :span="12"
        style="display: flex; align-items: center; justify-content: end"
      >
        <a-tooltip content="刷新">
          <div class="action-icon" @click="loadData">
            <icon-refresh size="18" />
          </div>
        </a-tooltip>
      </a-col>
    </a-row>
    <a-table
      :columns="columns"
      :data="renderData"
      row-key="id"
      :row-selection="{
        type: 'checkbox',
        showCheckedAll: true,
        onlyCurrent: false,
      }"
      v-model:selectedKeys="selectedKeys"
      column-resizable
      :bordered="false"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <template #optional="{ record }">
        <UpdateApiModalBtn :api-info-id="record.id" @refresh="loadData" />
        <DebugApiModalBtn
          :method="record.method"
          :url="record.url"
          :request-params="record.requestParams"
          :request-header="record.requestHeader"
          :response-header="record.responseHeader"
        />
        <a-popconfirm
          content="确认删除?"
          type="warning"
          @ok="deleteById(record.id)"
        >
          <a-button type="text" size="small" status="danger">删除</a-button>
        </a-popconfirm>
      </template>
      <template #method="{ record }">
        <span>{{
          findOptionsLabelByValue(requestMethodTypeOptionData, record.method)
        }}</span>
      </template>
      <template #status="{ record }">
        <span>{{
          findOptionsLabelByValue(apiStatusOptionData, record.status)
        }}</span>
      </template>
    </a-table>
  </a-card>
</template>

<script setup lang="ts">
import {
  IconRefresh,
  IconDelete,
  IconSearch,
} from "@arco-design/web-vue/es/icon";
import ProBreadcrumd from "@/components/ProBreadcrumd.vue";
import message from "@arco-design/web-vue/es/message";
import { onMounted, ref, watchEffect } from "vue";
import ResponseCode from "@/models/enum/ResponseCode";
import { findOptionsLabelByValue } from "@/utils/CommonUtils";
import AddApiModalBtn from "@/views/ManageApiView/components/AddApiModalBtn.vue";
import {
  apiStatusOptionData,
  requestMethodTypeOptionData,
} from "@/models/options/select/ApiOptionData";
import {
  ApiInfoControllerService,
  ApiInfoVo,
  QueryApiInfoPageDto,
} from "../../../api/api";
import UpdateApiModalBtn from "@/views/ManageApiView/components/UpdateApiModalBtn.vue";
import DebugApiModalBtn from "@/views/ManageApiView/components/DebugApiModalBtn.vue";

// 批量选择keys
const selectedKeys = ref([]);
// 数据总数
const total = ref(0);
// 查询参数
const baseSearchParam = {
  current: 1,
  pageSize: 5,
  name: "",
  url: "",
  status: undefined,
  method: "",
};
let searchParams = ref<QueryApiInfoPageDto>({ ...baseSearchParam });
// 列数据
const columns = [
  {
    title: "接口名",
    dataIndex: "name",
  },
  {
    title: "接口URL",
    dataIndex: "url",
  },
  {
    title: "接口简介",
    dataIndex: "description",
  },
  {
    title: "请求方法",
    slotName: "method",
  },
  {
    title: "接口状态",
    slotName: "status",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];
// 表格数据
const renderData = ref<ApiInfoVo[] | undefined>([]);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await ApiInfoControllerService.getByPage(searchParams.value);
  if (res.code === ResponseCode.SUCCESS && res.data) {
    renderData.value = res.data.records;
    total.value = res.data.total ?? 0;
  } else {
    message.error(res.message ?? "加载失败，请稍后再试");
  }
};

/**
 * 换页时
 * @param current
 */
const onPageChange = (current: number) => {
  searchParams.value = {
    ...searchParams.value,
    current,
  };
};

/**
 * 重置查询参数
 */
const resetParams = () => {
  searchParams.value = {
    ...baseSearchParam,
  };
};

/**
 * 根据id删除
 * @param id
 */
const deleteById = async (id: number) => {
  let res = await ApiInfoControllerService.delete({ id });
  if (res.code == ResponseCode.SUCCESS) {
    message.success("删除成功！");
    loadData();
  } else {
    message.error(res.message ?? "删除失败，请稍后再试");
  }
};

/**
 * 批量删除
 */
const deleteBatch = async () => {
  console.log(selectedKeys.value);
  if (!selectedKeys?.value || selectedKeys.value.length == 0) {
    message.warning("请先选择至少一条数据");
    return;
  }
  const res = await ApiInfoControllerService.deleteBatch({
    idList: selectedKeys.value,
  });
  if (res.code == ResponseCode.SUCCESS) {
    message.success("批量删除成功！");
    loadData();
  } else {
    message.error(res.message ?? "批量删除失败，请稍后再试");
  }
};

/**
 * 页面挂载时，运行一次
 */
onMounted(() => {
  loadData();
});

/**
 * 自动监听方法里的实例变量，当变量值发送变化时，自动执行方法
 */
watchEffect(() => {
  loadData();
});
</script>

<style scoped>
.manage-api-view {
  background-color: #fff;
  padding: 10px;
}
.action-icon {
  margin-left: 12px;
  cursor: pointer;
}
</style>
