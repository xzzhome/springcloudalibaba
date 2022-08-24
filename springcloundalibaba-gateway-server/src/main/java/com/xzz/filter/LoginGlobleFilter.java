package com.xzz.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j//日志器
public class LoginGlobleFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        List<String> token = exchange.getRequest().getHeaders().get("token");

        log.info("检查 TOKEN = {}" ,token);
        if(token == null || token.isEmpty()){
            //响应对象
            ServerHttpResponse response = exchange.getResponse();
            //构建错误结果
            HashMap<String,Object> data = new HashMap<>();
            data.put("success",false);
            data.put("message","未登录");
            //构建响应内容
            DataBuffer buffer = null;
            try {
                byte[] bytes = JSON.toJSONString(data).getBytes("utf-8");
                buffer = response.bufferFactory().wrap(bytes);

                //设置完成相应，不会继续执行后面的filter
                //response.setComplete();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //把结果写给客户端
            return response.writeWith(Mono.just(buffer));
        }

        log.info("Token不为空 ，放行");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}