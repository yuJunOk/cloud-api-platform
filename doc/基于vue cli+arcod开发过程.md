# 一、初始化vue项目

```
安装脚手架
npm install -g @vue/cli

vue create online-judge-web
```

初始化过程选择：要选vuex，这里漏了

![初始化前端项目](/images/初始化vue项目.png)

# 二、引入arcod ui组件

官方文档：[Ant Design Vue](https://2x.antdv.com/docs/vue/getting-started-cn)

```
npm install --save-dev @arco-design/web-vue
```

修改main.ts

```
import { createApp } from "vue";
import App from "./App.vue";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import store from "./store";
import router from "./router";

createApp(App).use(ArcoVue).use(store).use(router).mount("#app");
```

# 三、引入vue工具包

官方：[VueUse](https://vueuse.org/)

```
npm install @vueuse/core
```

# 四、引入open api

代码仓库：[github.com](https://github.com/ferdikoomen/openapi-typescript-codegen)

```
npm install openapi-typescript-codegen --save-dev
```

下载成功后执行

```
openapi --input http://localhost:8080/api/v3/api-docs --output ./generated --client axios
```

记着看生成文件夹下core/OpenAPI.ts的WITH_CREDENTIALS是否为true，为true能保证请求带cookie。

# 五、引入axios

官网：[Axios中文文档 | Axios中文网](https://www.axios-http.cn/)

```
npm install axios
```

拦截器

```
// Add a request interceptor
import axios from "axios";

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    console.log(config);
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
axios.interceptors.response.use(
  function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  }
);

```

在main.ts引入

```
import "@/plugins/axios";
```

# 六、添加代码编辑器

monaco-editor 代码仓库：[github.com](https://github.com/microsoft/monaco-editor)

```
npm install monaco-editor

# vue-cli 项目(webpack项目)整合 monaco-editor
npm install monaco-editor-webpack-plugin
```

修改vue.config.js

```
const { defineConfig } = require("@vue/cli-service");
const MonacoEditorWebpackPlugin = require("monaco-editor-webpack-plugin");

module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack(config) {
    config.plugin("monaco").use(new MonacoEditorWebpackPlugin());
  },
});
```

上面是全量加载的方式，还有一种选择加载的方式，如下（此处不采用）

```
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin')
module.exports = {
  chainWebpack: config => {
    config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
      {
        // Languages are loaded on demand at runtime
        languages: ['json', 'go', 'css', 'html', 'java', 'javascript', 'less', 'markdown', 'mysql', 'php', 'python', 'scss', 'shell', 'redis', 'sql', 'typescript', 'xml'], // ['abap', 'apex', 'azcli', 'bat', 'cameligo', 'clojure', 'coffee', 'cpp', 'csharp', 'csp', 'css', 'dart', 'dockerfile', 'ecl', 'fsharp', 'go', 'graphql', 'handlebars', 'hcl', 'html', 'ini', 'java', 'javascript', 'json', 'julia', 'kotlin', 'less', 'lexon', 'lua', 'm3', 'markdown', 'mips', 'msdax', 'mysql', 'objective-c', 'pascal', 'pascaligo', 'perl', 'pgsql', 'php', 'postiats', 'powerquery', 'powershell', 'pug', 'python', 'r', 'razor', 'redis', 'redshift', 'restructuredtext', 'ruby', 'rust', 'sb', 'scala', 'scheme', 'scss', 'shell', 'solidity', 'sophia', 'sql', 'st', 'swift', 'systemverilog', 'tcl', 'twig', 'typescript', 'vb', 'xml', 'yaml'],

        features: ['format', 'find', 'contextmenu', 'gotoError', 'gotoLine', 'gotoSymbol', 'hover' , 'documentSymbols'] //['accessibilityHelp', 'anchorSelect', 'bracketMatching', 'caretOperations', 'clipboard', 'codeAction', 'codelens', 'colorPicker', 'comment', 'contextmenu', 'coreCommands', 'cursorUndo', 'dnd', 'documentSymbols', 'find', 'folding', 'fontZoom', 'format', 'gotoError', 'gotoLine', 'gotoSymbol', 'hover', 'iPadShowKeyboard', 'inPlaceReplace', 'indentation', 'inlineHints', 'inspectTokens', 'linesOperations', 'linkedEditing', 'links', 'multicursor', 'parameterHints', 'quickCommand', 'quickHelp', 'quickOutline', 'referenceSearch', 'rename', 'smartSelect', 'snippets', 'suggest', 'toggleHighContrast', 'toggleTabFocusMode', 'transpose', 'unusualLineTerminators', 'viewportSemanticTokens', 'wordHighlighter', 'wordOperations', 'wordPartOperations']
      }
    ])
  }
}
```

组件代码：

```
<template>
  <div
    id="code-editor"
    ref="codeEditorRef"
    style="min-height: 400px; height: 60vh"
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
  language: () => "java",
  handleChange: (v: string) => {
    console.log(v);
  },
});

const codeEditorRef = ref();
const codeEditor = ref();

// const fillValue = () => {
//   if (!codeEditor.value) {
//     return;
//   }
//   // 改变值
//   toRaw(codeEditor.value).setValue("新的值");
// };

watch(
  () => props.language,
  () => {
    if (codeEditor.value) {
      monaco.editor.setModelLanguage(
        toRaw(codeEditor.value).getModel(),
        props.language
      );
    }
  }
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
      enabled: true,
    },
    readOnly: false,
    theme: "vs-dark",
    // lineNumbers: "off",
    // roundedSelection: false,
    // scrollBeyondLastLine: false,
  });

  // 编辑 监听内容变化
  codeEditor.value.onDidChangeModelContent(() => {
    props.handleChange(toRaw(codeEditor.value).getValue());
  });
});
</script>

<style scoped></style>
```

调用实例

```
<template>
  <CodeEditor
    :value="form.code as string"
    :language="form.language"
    :handle-change="changeCode"
  />
</template>

<script setup lang="ts">
import { ref } from "vue";
import CodeEditor from "@/components/CodeEditor.vue"; // @ is an alias to /src

const form = ref({
  language: "java",
  code: "",
});
const changeCode = (value: string) => {
  form.value.code = value;
};
</script>
```

启动时报了个错：Static class blocks are not enabled. Please add `@babel/plugin-transform-class-static-block` to your configuration

添加依赖

```
npm install --save-dev @babel/plugin-transform-class-static-block
```

修改 babel.config.js 配置

```
module.exports = {
presets: ['@vue/cli-plugin-babel/preset'],
plugins: ['@babel/plugin-transform-class-static-block']
}
```
