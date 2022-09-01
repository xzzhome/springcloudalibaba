package com.xzz.openFeign;

import com.xzz.User;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserFeignClient {
	@Override    
	public User getById(Long id) {
		return new User(-1L,"熔断无此用户",-1);
	}

	@Override
	public void add(User user) {

	}

	@Override
	public void update(User user) {

	}
}