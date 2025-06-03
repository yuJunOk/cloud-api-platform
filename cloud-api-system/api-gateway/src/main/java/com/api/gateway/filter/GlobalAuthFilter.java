package com.api.gateway.filter;

import com.api.client.ApiInfoFeignClient;
import com.api.client.UserFeignClient;
import com.api.common.common.ResponseCode;
import com.api.common.exception.BusinessException;
import com.api.common.utils.CommonUtils;
import com.api.gateway.utils.NetUtils;
import com.api.model.domain.ApiInfoDo;
import com.api.model.domain.UserDo;
import com.api.model.enums.ApiStatusEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.api.clientsdk.utils.SignUtils.genNonceByTimeStamp;
import static com.api.clientsdk.utils.SignUtils.genSign;

/**
 * @author pengYuJun
 */
@Slf4j
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    @Resource
    private RedissonClient redissonClient;

    @Lazy
    @Resource
    private UserFeignClient userFeignClient;

    @Lazy
    @Resource
    private ApiInfoFeignClient apiInfoFeignClient;

    /**
     * 路径匹配工具
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 五分钟过期时间
     */
    private static final long FIVE_MINUTES = 5L * 60;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 日志
        // ServerHttpRequest request = exchange.getRequest();
        // log.info("请求唯一id：{}", request.getId());
        // log.info("请求方法：{}", request.getMethod());
        // log.info("请求路径：{}", request.getPath());
        // log.info("网关本地地址：{}", request.getLocalAddress());
        // log.info("请求远程地址：{}", request.getRemoteAddress());
        // log.info("接口请求IP：{}", getIp(request));
        // log.info("url:{}", request.getURI());

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getURI().getPath();

        if (antPathMatcher.match("/**/inner/**", path)) {
            // 判断路径中是否包含 inner，只允许内部调用
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("内部接口，无权限访问".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }else if (antPathMatcher.match("/api/mock/**", path)){
            // 判断是否开放API调用，进行校验和记录
            return verifyApiInvoke(exchange, chain);
        }
        // todo 统一权限校验，通过 JWT 获取登录用户信息
        return chain.filter(exchange);
    }


    private Mono<Void> verifyApiInvoke(ServerWebExchange exchange, GatewayFilterChain chain){
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String body = headers.getFirst("body");
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
        String timestamp = headers.getFirst("timestamp");
        String nonce = headers.getFirst("nonce");
        // 请求头中参数必须完整
        if (CommonUtils.isAnyBlank(body, sign, accessKey, timestamp)) {
            throw new BusinessException(ResponseCode.FORBIDDEN);
        }
        // 防重发XHR
        long currentTime = System.currentTimeMillis() / 1000;
        assert timestamp != null;
        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            throw new BusinessException(ResponseCode.FORBIDDEN, "会话已过期,请重试！");
        }
        // 验证nonce，防篡改请求头
        int nonceTmp = genNonceByTimeStamp(Long.parseLong(timestamp));
        assert nonce != null;
        if (Integer.parseInt(nonce) != nonceTmp) {
            throw new BusinessException(ResponseCode.FORBIDDEN, "非法请求");
        }
        // 数据校验
        try {
            // 用户校验
            final UserDo[] userDo = new UserDo[1];
            CompletableFuture<Void> validUserTask = CompletableFuture.runAsync(() -> {
                userDo[0] = userFeignClient.getUserByAk(accessKey);
                if (userDo[0] == null) {
                    throw new BusinessException(ResponseCode.FORBIDDEN, "会话已过期,请重试！");
                }
                // 校验签名
                if (!genSign(body, userDo[0].getSecretKey()).equals(sign)) {
                    throw new BusinessException(ResponseCode.FORBIDDEN, "非法请求");
                }
            });
            // 接口校验
            final ApiInfoDo[] apiInfo = new ApiInfoDo[1];
            CompletableFuture<Void> validApiInfoTask = CompletableFuture.runAsync(() -> {
                String method = Objects.requireNonNull(request.getMethod()).toString();
                String uri = request.getURI().toString().trim();
                if (CommonUtils.isAnyBlank(method, uri)) {
                    throw new BusinessException(ResponseCode.PARAMS_ERROR);
                }
                apiInfo[0] = apiInfoFeignClient.getApiInfo(uri, method);
                if (apiInfo[0] == null) {
                    throw new BusinessException(ResponseCode.NOT_FOUND, "接口不存在");
                }
                if (!apiInfo[0].getStatus().equals(ApiStatusEnum.ONLINE.getValue())){
                    throw new BusinessException(ResponseCode.FORBIDDEN, "接口未上线");
                }
            });
            // 合并任务并等待完成
            CompletableFuture.allOf(validUserTask, validApiInfoTask).join();
            //
            return handleResponse(exchange, chain, userDo[0], apiInfo[0]);
        }catch (Exception e){
            throw new BusinessException(ResponseCode.FORBIDDEN, e.getMessage());
        }
    }

    /**
     * 处理响应
     *
     * @param exchange 交换
     * @param chain    链条
     * @return {@link Mono}<{@link Void}>
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, UserDo user, ApiInfoDo apiInfoDo) {
        // 记录请求时间
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse originalResponse = exchange.getResponse();
        // 获取客户端IP
        String clientIp = NetUtils.getIp(request);
        // 获取设备信息
        String userAgent = Optional.ofNullable(request.getHeaders().getFirst("User-Agent")).orElse("");
        // 缓存数据的工厂
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 拿到响应码
        HttpStatusCode statusCode = originalResponse.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            // 装饰，增强能力
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                // 等调用完转发的接口后才会执行
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 往返回值里写数据
                        return super.writeWith(
                                fluxBody.map(dataBuffer -> {
                                    // 停止计时
                                    stopWatch.stop();
                                    long duration = stopWatch.getTotalTimeMillis();
                                    //
                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    // 释放掉内存
                                    DataBufferUtils.release(dataBuffer);
                                    String data = new String(content, StandardCharsets.UTF_8);
                                    // 记录日志
                                    log.info("响应码：{}，耗时：{}ms，IP：{}，设备：{}，响应体：{}",
                                            getStatusCode(), duration, clientIp, userAgent,
                                            data);
                                    //
                                    return bufferFactory.wrap(content);
                                }));
                    } else {
                        // 调用失败，返回一个规范的错误码
                        log.error("响应code异常：{}", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            // 设置 response 对象为装饰过的
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        // 降级处理返回数据
        return chain.filter(exchange);
    }

    /**
     * 优先级提到最高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}