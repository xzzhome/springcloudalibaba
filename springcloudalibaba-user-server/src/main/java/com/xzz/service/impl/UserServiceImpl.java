package com.xzz.service.impl;

import com.xzz.User;
import com.xzz.mapper.UserMapper;
import com.xzz.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public void add(User User) {
        userMapper.add(User);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
