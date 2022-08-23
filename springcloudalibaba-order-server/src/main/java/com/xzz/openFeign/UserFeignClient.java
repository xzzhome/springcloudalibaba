package com.xzz.openFeign;

import com.xzz.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-server",fallback = UserClientFallback.class)
//有一级路径还要加@RequestMapping("/user")
public interface UserFeignClient {

    @GetMapping(value = "/user/{id}" )
    User getById(@PathVariable("id")Long id);
}