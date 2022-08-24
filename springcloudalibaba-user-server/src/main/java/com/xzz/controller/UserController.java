package com.xzz.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xzz.block.ExceptionUtil;
import com.xzz.User;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope  //刷新配置
@RestController
public class UserController {

    @GetMapping("/user/{id}")
    //blockHandler限流，fallback熔断         ,blockHandlerClass= ExceptionUtil.class
    @SentinelResource(value="user", blockHandler="exceptionHandler", fallback="getByIdfallback")
    public User getById(@PathVariable("id") Long id){
        //int i = 1 / 0;	//方法异常，触发熔断
        return new User(id,"zs:"+id, "我是zs");
    }

    // 限流降级
    public User exceptionHandler(@PathVariable("id") Long id, BlockException ex) {
        ex.printStackTrace();
        return new User(-1L,"限流了","限流了");
    }

    // 熔断降级
    public User getByIdfallback(@PathVariable("id") Long id){
        return new User(-1L,"zs:"+id, "熔断托底了");
    }
}