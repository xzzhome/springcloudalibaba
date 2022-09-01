package com.xzz.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.xzz.Movie;
import com.xzz.User;
import com.xzz.UserTicket;
import com.xzz.openFeign.UserFeignClient;
import com.xzz.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RefreshScope  //刷新配置
@RestController
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/movie/{id}")
    //blockHandler限流，fallback熔断         ,blockHandlerClass= ExceptionUtil.class
    //@SentinelResource(value="movie", blockHandler="exceptionHandler", fallback="getByIdfallback")
    @SentinelResource(value="movie", fallback="getByIdfallback")
    public Movie getById(@PathVariable("id") Long id){
        //int i = 1 / 0;	//方法异常，触发熔断
        return movieService.findById(id);
    }

    @PostMapping( "/movie/add" )
    public void add(@RequestBody Movie movie){
        //根据id去数据库查询User
        movieService.add(movie);
    }

    @GetMapping( "/movie/findAll" )
    public List<Long> findAll(){
        return movieService.findAll();
    }

    @GetMapping( "/movie/update" )
    public void update(UserTicket userTicket){
        movieService.update(userTicket);
    }

    @GetMapping("/movieU/{id}")
    public UserTicket getByIdU(@PathVariable("id") Long id){
        return movieService.findByIdU(id);
    }

    @PostMapping( "/movieU/add" )
    public void addU(@RequestBody UserTicket userTicket){
        movieService.addU(userTicket);
    }

    //浏览器来掉
    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable("id")Long id){
        //使用Feign调用用户服务获取User
        return userFeignClient.getById(id);
    }

    @PostMapping( "/user/add" )
    public String addUser(@RequestBody Movie movie){
        //根据id去数据库查询Store
        Date date = new Date();
        date.setTime(date.getTime() + 3*60*1000);
        movie.setEnd_time(date);
        movieService.add(movie);

        User user = userFeignClient.getById(movie.getUser_id());
        user.setBalance(user.getBalance()-movie.getPrice());
        userFeignClient.update(user);

        UserTicket ticket = new UserTicket();
        ticket.setUser_id(movie.getUser_id());
        ticket.setMovie_ticket_id(movie.getId());
        ticket.setStatus(0);
        movieService.addU(ticket);

        return "ok";
    }


    // 限流降级
    /*public Movie exceptionHandler(@PathVariable("id") Long id, BlockException ex) {
        ex.printStackTrace();
        return new Movie(-1L,"限流了","限流了");
    }*/

    // 熔断降级
    public Movie getByIdfallback(@PathVariable("id") Long id){
        return new Movie(-1L,"熔断", -1,null,null,"-1",-1L,-1L,-1);
    }
}