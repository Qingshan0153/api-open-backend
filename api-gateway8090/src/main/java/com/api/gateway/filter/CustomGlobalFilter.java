package com.groupapi.gateway.filter;

import com.api.common.model.entity.InterfaceInfo;
import com.api.common.model.vo.UserVO;
import com.api.common.service.InnerInterfaceInfoService;
import com.api.common.service.InnerUserInterfaceInfoService;
import com.api.common.service.InnerUserService;
import com.openapi.openapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 全局请求拦截处理
 *
 * @author mendax
 * @version 2023/7/29 12:32
 */

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Collections.singletonList("127.0.0.1");

    @DubboReference
    InnerUserService userService;

    @DubboReference
    InnerInterfaceInfoService interfaceInfoService;
    @DubboReference
    InnerUserInterfaceInfoService userInterfaceInfoService;

    private static final String INTERFACE_HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. 用户发送请求到网关
        log.info("Custom Global Filter");
        //2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求标识：" + request.getId());
        log.info("请求路径：" + request.getPath().value());
        log.info("请求方法：" + request.getMethodValue());
        log.info("请求参数：" + request.getQueryParams());
        String localAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求地址：" + localAddress);
        ServerHttpResponse response = exchange.getResponse();
        //3. （黑白名单）
        if (!IP_WHITE_LIST.contains(localAddress)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //4. 用户鉴权（判断ak,sk是否合法）
        HttpHeaders header = request.getHeaders();
        String accessKey = header.getFirst("accessKey");
        String nonce = header.getFirst("nonce");
        String body = header.getFirst("body");
        String timestamp = header.getFirst("timestamp");
        String sign = header.getFirst("sign");

        //  从数据库查询 accessKey 和 secretKey 进行校验（随机数 nonce 从数据库或redis中读取）
        UserVO invokeUser = null;
        try {
            invokeUser = userService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error " + e);
        }

        if (invokeUser == null) {
            return this.handleInvokeError(response);
        }

//        if (!invokeUser.getAccessKey().equals(accessKey)) {
//            return this.handleUnAuth(response);
//        }

        if (nonce == null) {
            return this.handleUnAuth(response);
        }
        if (timestamp == null) {
            return this.handleUnAuth(response);
        }
        if (Integer.parseInt(nonce) > 10000) {
            return this.handleUnAuth(response);
        }

        final long fIVE_MINUTES = 60 * 5L;
        long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - Long.parseLong(timestamp) >= fIVE_MINUTES) {
            return this.handleUnAuth(response);

        }
        Map<String, String> map = new HashMap<>(8);
        map.put("accessKey", accessKey);
        map.put("nonce", nonce);
        map.put("body", body);
        map.put("timestamp", timestamp);
        //  实际情况secretKey从数据库中查询
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(map, secretKey);
        if (sign == null) {
            return this.handleUnAuth(response);
        }
        if (!sign.equals(serverSign)) {
            return this.handleUnAuth(response);
        }
        //5. 调用模拟接口是否存在
        //  从数据查询接口信息，判断URL和 method是否相同
        String urlPath = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethodValue().toString();
        InterfaceInfo interfaceInfo = interfaceInfoService.getInterfaceInfo(urlPath, method);
        if (interfaceInfo == null) {
            return this.handleInvokeError(response);
        }
        //6. 请求转发，调用模拟接口(+ 响应日志)
        return this.handleResponse(exchange, chain, interfaceInfo.getId(), interfaceInfo.getUserId());
    }


    /**
     * 处理 响应对象
     * 利用response装饰者，增强原有response的处理能力
     *
     * @param exchange 交换机
     * @param chain    过滤链
     * @return Mono<Void>
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // response 装饰对象
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 接口调用完后才会执行
                    @NotNull
                    @Override
                    public Mono<Void> writeWith(@NotNull Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                //8. 调用成功，调用次数 +1
                                try {
                                    userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                } catch (Exception e) {
                                    log.error("invokeCount error " + e);
                                }

                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                //释放掉内存
                                DataBufferUtils.release(dataBuffer);
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);
                                //data
                                sb2.append(data);
                                // 打印日志
                                log.info("响应结果：" + data);
                                log.info(sb2.toString(), rspArgs.toArray());
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                            //9. 调用失败，返回规范错误信息
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象，为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            //降级处理返回数据
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("网关日志异常：" + e);
            return chain.filter(exchange);
        }

    }


    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 处理无权限
     *
     * @param response 响应
     * @return Mono
     */
    private Mono<Void> handleUnAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 处理调用错误
     *
     * @param response 响应
     * @return Mono
     */
    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
