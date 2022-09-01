package com.xzz.openFeign;

import com.xzz.Movie;
import com.xzz.User;
import com.xzz.UserTicket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieClientFallback implements MovieFeignClient {
	@Override    
	public Movie getById(Long id) {
		return new Movie(-1L,"熔断", -1,null,null,"-1",-1L,-1L,-1);
	}

	@Override
	public void add(Movie movie) {

	}

	@Override
	public UserTicket getByIdU(Long id) {
		return null;
	}

	@Override
	public void addU(UserTicket userTicket) {

	}

	@Override
	public List<Long> findAll() {
		return null;
	}

	@Override
	public void update(UserTicket userTicket) {

	}
}