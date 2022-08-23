package com.xzz.controller;

import com.xzz.User;
import com.xzz.openFeign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //@RefreshScope刷新配置
public class OrderController {

    @Autowired
    private UserFeignClient userFeignClient;

    //浏览器来掉
    @RequestMapping("/order/{id}")
    public User getById(@PathVariable("id")Long id){
        //使用Feign调用用户服务获取User
        return userFeignClient.getById(id);
    }
}