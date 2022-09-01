package com.xzz.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.xzz.Movie;
import com.xzz.User;
import com.xzz.UserTicket;
import com.xzz.openFeign.MovieFeignClient;
import com.xzz.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RefreshScope  //刷新配置
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private MovieFeignClient movieFeignClient;

    @GetMapping("/user/{id}")
    //blockHandler限流，fallback熔断         ,blockHandlerClass= ExceptionUtil.class
    //@SentinelResource(value="user", blockHandler="exceptionHandler", fallback="getByIdfallback")
    @SentinelResource(value="user", fallback="getByIdfallback")
    public User getById(@PathVariable("id") Long id){
        //int i = 1 / 0;	//方法异常，触发熔断
        return userService.findById(id);
    }

    @PostMapping( "/user/add" )
    public void add(@RequestBody User user){
        userService.add(user);

        UserTicket userTicket = new UserTicket();
        userTicket.setUser_id(user.getId());
        userTicket.setStatus(0);

        List<Long> givenList = movieFeignClient.findAll();
        Random rand = new Random();
        Long aLong = givenList.get(rand.nextInt(givenList.size()));

        userTicket.setMovie_ticket_id(aLong);

        movieFeignClient.addU(userTicket);
    }

    @PostMapping( "/user/update" )
    public void update(@RequestBody User user){
        //根据id去数据库查询User
        userService.update(user);
    }

    //浏览器来掉
    @RequestMapping("/movie/{id}")
    public Movie getMovie(@PathVariable("id")Long id){
        //使用Feign调用用户服务获取User
        return movieFeignClient.getById(id);
    }

    @RequestMapping("/movieU/{id}")
    public String getMovieU(@PathVariable("id")Long id){

        UserTicket userTicket = movieFeignClient.getByIdU(id);
        Movie movie = movieFeignClient.getById(id);

        if (userTicket.getStatus().equals(1)){
            return "电影票已经被使用";
        }else if (new Date().getTime() > movie.getEnd_time().getTime()){
            return "电影票已经过期，不可观看";
        }
        movieFeignClient.update(userTicket);

        return "电影票未过期，可以正常使用";
    }



    // 限流降级
    /*public User exceptionHandler(@PathVariable("id") Long id, BlockException ex) {
        ex.printStackTrace();
        return new User(-1L,"限流了","限流了");
    }*/

    // 熔断降级
    public User getByIdfallback(@PathVariable("id") Long id){
        return new User(-1L,"熔断", -1);
    }
}