package com.xzz.openFeign;

import com.xzz.Movie;
import com.xzz.UserTicket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "movie-server",fallback = MovieClientFallback.class)
//有一级路径还要加@RequestMapping("/user")
public interface MovieFeignClient {

    @GetMapping(value = "/movie/{id}" )
    Movie getById(@PathVariable("id")Long id);

    @PostMapping("/movie/add")
    void add(@RequestBody Movie movie);

    @GetMapping("/movieU/{id}")
    UserTicket getByIdU(@PathVariable("id") Long id);

    @PostMapping( "/movieU/add" )
    void addU(@RequestBody UserTicket userTicket);

    @GetMapping( "/movie/findAll" )
    List<Long> findAll();

    @GetMapping( "/movie/update" )
    void update(UserTicket userTicket);
}