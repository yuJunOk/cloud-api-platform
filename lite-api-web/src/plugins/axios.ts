// Add a request interceptor
import axios from "axios";

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  function (config) {
      // Do something before request is sent
      config.withCredentials = true;
      config.baseURL = import.meta.env.VITE_APP_BASE_URL;
      // 去掉open api生成的固定域名
      // 处理 URL 路径和参数
      if (config.url) {
          // 兼容绝对路径和相对路径（以当前域名为基准）
          const url = new URL(config.url, window.location.origin);
          // 保留路径、查询参数、哈希
          config.url = url.pathname + url.search + url.hash;
      }
      //
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
