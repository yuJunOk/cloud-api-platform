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
