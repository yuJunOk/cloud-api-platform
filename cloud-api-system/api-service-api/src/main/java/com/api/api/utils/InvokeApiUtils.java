package com.api.api.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.api.model.bo.LoginUserBo;
import com.api.model.domain.ApiInfoDo;
import com.api.model.dto.api.ApiInvokeByIdDto;
import com.api.model.vo.api.ApiResponseVo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.clientsdk.utils.SignUtils.genNonceByTimeStamp;
import static com.api.clientsdk.utils.SignUtils.genSign;

/**
 * @author pengYuJun
 */
public class InvokeApiUtils {

    /**
     * 调用接口
     * @return
     */
    public static ApiResponseVo invoke(ApiInvokeByIdDto apiInvokeByIdDto, ApiInfoDo apiInfoDo, LoginUserBo currentUser){
        // 请求参数
        String method = apiInfoDo.getMethod();
        String url = apiInfoDo.getUrl();
        Map<String, String> requestHeader = apiInvokeByIdDto.getRequestHeader();
        Map<String, Object> requestParams = apiInvokeByIdDto.getRequestParams();
        Map<String, String> responseHeader = apiInvokeByIdDto.getResponseHeader();
        String body = JSONUtil.toJsonStr(requestParams);
        // 带请求头
        requestHeader.put("body", body);
        requestHeader.put("accessKey", currentUser.getAccessKey());
        requestHeader.put("sign", genSign(body, currentUser.getSecretKey()));
        long timeStamp = System.currentTimeMillis() / 1000;
        requestHeader.put("timestamp", String.valueOf(timeStamp));
        int nonce = genNonceByTimeStamp(timeStamp);
        requestHeader.put("nonce", String.valueOf(nonce));
        // 请求
        return request(url, method, requestHeader, requestParams, responseHeader);
    }

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
