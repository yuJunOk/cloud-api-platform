<template>
  <ProBreadcrumd />
  <a-card class="manage-user-view">
    <a-row>
      <a-col :flex="1">
        <a-form
          :model="searchParams"
          :label-col-props="{ flex: 'none' }"
          :wrapper-col-props="{ flex: 1 }"
        >
          <a-row :gutter="16">
            <a-col :span="5">
              <a-form-item field="userName" label="昵称">
                <a-input
                  v-model="searchParams.userName"
                  placeholder="输入昵称"
                />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="loginName" label="账号">
                <a-input
                  v-model="searchParams.loginName"
                  placeholder="输入账号"
                />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="gender" label="性别">
                <a-select
                  v-model="searchParams.gender"
                  placeholder="选择性别"
                  :options="[{ label: '全部', value: '' }, ...genderOptionData]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="phone" label="电话">
                <a-input v-model="searchParams.phone" placeholder="输入电话" />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="email" label="邮箱">
                <a-input v-model="searchParams.email" placeholder="输入邮箱" />
              </a-form-item>
            </a-col>
            <a-col :span="5">
              <a-form-item field="userRole" label="角色">
                <a-select
                  v-model="searchParams.userRole"
                  placeholder="选择角色"
                  :options="[
                    { label: '全部', value: '' },
                    ...userRoleOptionData,
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
          <AddUserModalBtn @refresh="loadData" />
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
        <UpdateUserModalBtn :user-data="record" @refresh="loadData" />
        <a-popconfirm
          content="确认删除?"
          type="warning"
          @ok="deleteById(record.id)"
        >
          <a-button type="text" size="small" status="danger">删除</a-button>
        </a-popconfirm>
      </template>
      <template #gender="{ record }">
        <span>{{
          findOptionsLabelByValue(genderOptionData, record.gender)
        }}</span>
      </template>
      <template #userRole="{ record }">
        <span>{{
          findOptionsLabelByValue(userRoleOptionData, record.userRole)
        }}</span>
      </template>
      <template #userAvatar="{ record }">
        <a-avatar :size="48" shape="square">
          <a-image
            v-if="record.userAvatar"
            alt="avatar"
            :src="record.userAvatar"
          />
        </a-avatar>
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
import {
  QueryUserPageDto,
  UserControllerService,
  UserVo,
} from "../../../api/user";
import ResponseCode from "@/models/enum/ResponseCode";
import {
  genderOptionData,
  userRoleOptionData,
} from "@/models/options/UserOptionData";
import { findOptionsLabelByValue } from "@/utils/CommonUtils";
import UpdateUserModalBtn from "@/views/ManageUserView/components/UpdateUserModalBtn.vue";
import AddUserModalBtn from "@/views/ManageUserView/components/AddUserModalBtn.vue";

// 批量选择keys
const selectedKeys = ref([]);
// 数据总数
const total = ref(0);
// 查询参数
const baseSearchParam = {
  current: 1,
  pageSize: 5,
  userName: "",
  loginName: "",
  gender: undefined,
  phone: "",
  email: "",
  userRole: "",
};
let searchParams = ref<QueryUserPageDto>({ ...baseSearchParam });
// 列数据
const columns = [
  {
    title: "用户名",
    dataIndex: "userName",
  },
  {
    title: "账号",
    dataIndex: "loginName",
  },
  {
    title: "头像",
    slotName: "userAvatar",
  },
  {
    title: "性别",
    slotName: "gender",
  },
  {
    title: "电话",
    dataIndex: "phone",
  },
  {
    title: "邮箱",
    dataIndex: "email",
  },
  {
    title: "角色",
    slotName: "userRole",
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
const renderData = ref<UserVo[] | undefined>([]);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await UserControllerService.getByPage(searchParams.value);
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
  let res = await UserControllerService.delete({ id });
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
  const res = await UserControllerService.deleteBatch({
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
.manage-user-view {
  background-color: #fff;
  padding: 10px;
}
.action-icon {
  margin-left: 12px;
  cursor: pointer;
}
</style>
