<template>
  <div class="pro-header">
    <div class="left-side">
      <a-space>
        <img alt="logo" src="../assets/images/logo.png" style="width: 33px" />
        <a-typography-title
          :style="{ margin: 0, fontSize: '18px' }"
          :heading="5"
        >
          母鸡API
        </a-typography-title>
      </a-space>
    </div>
    <ul class="right-side">
      <li>
        <a-tooltip content="搜索">
          <a-button class="nav-btn" type="outline" :shape="'circle'">
            <template #icon>
              <icon-search />
            </template>
          </a-button>
        </a-tooltip>
      </li>
      <li>
        <a-tooltip
          :content="isFullscreen ? '点击退出全屏模式' : '点击切换全屏模式'"
        >
          <a-button
            class="nav-btn"
            type="outline"
            :shape="'circle'"
            @click="toggleFullScreen"
          >
            <template #icon>
              <icon-fullscreen-exit v-if="isFullscreen" />
              <icon-fullscreen v-else />
            </template>
          </a-button>
        </a-tooltip>
      </li>
      <li>
        <a-dropdown trigger="click">
          <a-avatar
            :size="32"
            :style="{ marginRight: '8px', cursor: 'pointer' }"
          >
            <img v-if="avatar" alt="avatar" :src="avatar" />
          </a-avatar>
          <template #content>
            <a-doption>
              <a-space @click="handleLogout">
                <icon-export />
                <span> 退出登录 </span>
              </a-space>
            </a-doption>
          </template>
        </a-dropdown>
      </li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
import { Message } from "@arco-design/web-vue";
import { useFullscreen } from "@vueuse/core";
import { computed } from "vue";
import {
  IconExport,
  IconFullscreen,
  IconFullscreenExit,
  IconSearch,
} from "@arco-design/web-vue/es/icon";
import store from "@/store";

const { isFullscreen, toggle: toggleFullScreen } = useFullscreen();

const avatar = computed(() => {
  let loginUser = store.state.user.loginUser;
  return loginUser.userAvatar;
});

const handleLogout = () => {
  Message.info("退出登录");
};
</script>

<style scoped>
.pro-header {
  display: flex;
  justify-content: space-between;
  height: 100%;
  background-color: var(--color-bg-2);
  border-bottom: 1px solid var(--color-border);
}

.left-side {
  display: flex;
  align-items: center;
  padding-left: 20px;
}

.right-side {
  display: flex;
  padding-right: 20px;
  list-style: none;

  li {
    display: flex;
    align-items: center;
    padding: 0 10px;
  }

  a {
    color: var(--color-text-1);
    text-decoration: none;
  }

  .nav-btn {
    border-color: rgb(var(--gray-2));
    color: rgb(var(--gray-8));
    font-size: 16px;
  }
}
</style>
