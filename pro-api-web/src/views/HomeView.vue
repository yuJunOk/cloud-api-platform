<template>
  <ProBreadcrumd />
  <div class="home" style="text-align: center">
    <img alt="logo" src="../assets/images/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HelloWorld from "@/components/HelloWorld.vue";
import ProBreadcrumd from "@/components/ProBreadcrumd.vue"; // @ is an alias to /src

export default defineComponent({
  name: "HomeView",
  components: {
    ProBreadcrumd,
    HelloWorld,
  },
});
</script>
