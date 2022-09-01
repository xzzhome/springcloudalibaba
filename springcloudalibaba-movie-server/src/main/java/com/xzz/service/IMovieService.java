package com.xzz.service;

import com.xzz.Movie;
import com.xzz.User;
import com.xzz.UserTicket;

import java.util.List;

public interface IMovieService {
    Movie findById(Long id);

    void add(Movie movie);

    void addU(UserTicket userTicket);

    UserTicket findByIdU(Long id);

    List<Long> findAll();

    void update(UserTicket userTicket);
}
