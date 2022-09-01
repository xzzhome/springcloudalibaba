package com.xzz.service.impl;

import com.xzz.Movie;
import com.xzz.User;
import com.xzz.UserTicket;
import com.xzz.mapper.MovieMapper;
import com.xzz.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService {
    @Autowired
    private MovieMapper movieMapper;

    @Override
    public Movie findById(Long id) {
        return movieMapper.findById(id);
    }

    @Override
    public void add(Movie Movie) {
        movieMapper.add(Movie);
    }

    @Override
    public void addU(UserTicket userTicket) {
        movieMapper.addU(userTicket);
    }

    @Override
    public UserTicket findByIdU(Long id) {
        return movieMapper.findByIdU(id);
    }

    @Override
    public List<Long> findAll() {
        return movieMapper.findAll();
    }

    @Override
    public void update(UserTicket userTicket) {
        movieMapper.update(userTicket);
    }
}
