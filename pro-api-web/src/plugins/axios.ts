// Add a request interceptor
import axios from "axios";

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    config.withCredentials = true;
    config.baseURL = process.env["VUE_APP_BASE_URL"];
    console.log(process.env["VUE_APP_BASE_URL"]);
    // 去掉open api生成的固定域名
    const url = new URL(config.url ?? "/");
    config.url = url.pathname;
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
