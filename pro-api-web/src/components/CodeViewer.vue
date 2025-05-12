<template>
  <div
    id="code-editor"
    ref="codeEditorRef"
    :style="`min-height: ${reactiveHeight}px`"
  />
</template>

<script setup lang="ts">
import * as monaco from "monaco-editor";
import { onMounted, ref, toRaw, withDefaults, defineProps, watch } from "vue";

/**
 * 给组件指定初始值
 */
const props = withDefaults(
  defineProps<{
    value: string;
    language?: string;
    height?: number;
  }>(),
  {
    value: () => "",
    language: () => "plaintext",
    height: () => 400,
  }
);

const codeEditorRef = ref();
const codeEditor = ref();

// 监听 尺寸
const reactiveHeight = ref(props.height);
watch(
  () => [props.height],
  () => {
    reactiveHeight.value = props.height;
  }
);

// 监听 语言
watch(
  () => [props.language],
  () => {
    if (codeEditor.value) {
      monaco.editor.setModelLanguage(
        toRaw(codeEditor.value).getModel(),
        props.language
      );
    }
  },
  { immediate: true }
);

// 监听 内容
watch(
  () => [props.value],
  () => {
    if (codeEditor.value) {
      // 方法一：直接设置内容（推荐）
      toRaw(codeEditor.value).setValue(props.value);
      // 格式化
      toRaw(codeEditor.value).getAction("editor.action.formatDocument")?.run();
      // 方法二：通过模型设置（适用于多模型场景）
      // toRaw(codeEditor.value).getModel()?.setValue(newCode);
    }
  },
  { immediate: true }
);

onMounted(() => {
  if (!codeEditorRef.value) {
    return;
  }
  // Hover on each property to see its docs!
  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: props.value,
    language: props.language,
    automaticLayout: true,
    colorDecorators: true,
    minimap: {
      enabled: false,
    },
    readOnly: true,
    theme: "vs",
    // lineNumbers: "off",
    // roundedSelection: false,
    // scrollBeyondLastLine: false,
  });

  // 开始有值的话，格式化一下
  if (props.value) {
    toRaw(codeEditor.value).getAction("editor.action.formatDocument")?.run();
  }
});
</script>

<style scoped></style>
