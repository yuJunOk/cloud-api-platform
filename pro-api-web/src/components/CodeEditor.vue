<template>
  <div
    id="code-editor"
    ref="codeEditorRef"
    style="min-height: 400px; height: 30vh"
  />
  <!--  <a-button @click="fillValue">填充值</a-button>-->
</template>

<script setup lang="ts">
import * as monaco from "monaco-editor";
import { onMounted, ref, toRaw, withDefaults, defineProps, watch } from "vue";

/**
 * 定义组件属性类型
 */
interface Props {
  value: string;
  language?: string;
  handleChange: (v: string) => void;
}

/**
 * 给组件指定初始值
 */
const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  language: () => "json",
  handleChange: (v: string) => {
    console.log(v);
  },
});

const codeEditorRef = ref();
const codeEditor = ref();

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

const resetEditorCode = (codeStr) => {
  if (codeEditor.value) {
    // 方法一：直接设置内容（推荐）
    toRaw(codeEditor.value).setValue(codeStr);
    // 格式化
    toRaw(codeEditor.value).getAction("editor.action.formatDocument")?.run();
    // 方法二：通过模型设置（适用于多模型场景）
    // toRaw(codeEditor.value).getModel()?.setValue(newCode);
  }
};

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
    readOnly: false,
    theme: "vs",
    // lineNumbers: "off",
    // roundedSelection: false,
    // scrollBeyondLastLine: false,
  });

  // 编辑 监听内容变化
  codeEditor.value.onDidChangeModelContent(() => {
    props.handleChange(toRaw(codeEditor.value).getValue());
  });

  // 开始有值的话，格式化一下
  if (props.value) {
    toRaw(codeEditor.value).getAction("editor.action.formatDocument")?.run();
  }
});

// eslint-disable-next-line no-undef
defineExpose({
  resetEditorCode,
});
</script>

<style scoped></style>
