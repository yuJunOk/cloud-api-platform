package com.api.api.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.api.model.vo.api.ApiResponseVo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengYuJun
 */
public class DebugUtils {

    /**
     * 请求
     * @param url
     * @param method
     * @param requestHeader
     * @param requestParams
     * @return
     */
    public static ApiResponseVo request(String url, String method, Map<String, String> requestHeader,
                                        Map<String, Object> requestParams, Map<String, String> responseHeader) {
        HttpResponse httpResponse;
        if (method.equals(HttpMethod.POST.name())) {
            httpResponse = post(url, requestHeader, requestParams);
        }else {
            httpResponse = get(url, requestHeader, requestParams);
        }
        // 响应头
        Map<String, String> responseHeaderRes = new HashMap<>();
        Map<String, List<String>> originalHeaders = httpResponse.headers();
        originalHeaders.forEach((key, values) -> {
            if (StringUtils.hasText(key)){
                responseHeaderRes.put(key, String.join(";", values));
            }
        });
        if (requestHeader != null) {
            responseHeaderRes.putAll(responseHeader);
        }
        //响应数据类型
        String contentType = httpResponse.header(HttpHeaders.CONTENT_TYPE);
        //响应数据
        String responseBody = httpResponse.body();
        return new ApiResponseVo(responseHeaderRes, responseBody, contentType);
    }

    /**
     * post请求
     * @param url
     * @param requestHeader
     * @param requestParams
     * @return
     */
    public static HttpResponse post(String url, Map<String, String> requestHeader,
                                    Map<String, Object> requestParams) {
        return HttpRequest.post(url)
                .addHeaders(requestHeader)
                .body(JSONUtil.toJsonStr(requestParams))
                .execute();
    }

    /**
     * get请求
     * @param url
     * @param requestHeader
     * @param requestParams
     * @return
     */
    public static HttpResponse get(String url, Map<String, String> requestHeader,
                                     Map<String, Object> requestParams) {
        return HttpRequest.get(url)
                .addHeaders(requestHeader)
                .form(requestParams)
                .execute();
    }

}
