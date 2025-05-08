<template>
  <MonoLayout v-if="route.meta.layout == 'mono'" />
  <ProLayout v-else />
</template>

<script setup lang="ts">
import ProLayout from "@/layouts/ProLayout.vue";
import { useRoute } from "vue-router";
import MonoLayout from "@/layouts/MonoLayout.vue";

const route = useRoute();
</script>

<style></style>
