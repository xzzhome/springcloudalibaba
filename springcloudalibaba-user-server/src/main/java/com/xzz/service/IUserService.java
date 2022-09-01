package com.xzz.service;

import com.xzz.User;

public interface IUserService {
    User findById(Long id);

    void add(User user);

    void update(User user);
}
