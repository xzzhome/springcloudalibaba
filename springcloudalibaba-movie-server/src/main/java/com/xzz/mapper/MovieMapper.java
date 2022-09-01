package com.xzz.mapper;

import com.xzz.Movie;
import com.xzz.User;
import com.xzz.UserTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MovieMapper {
    @Select("SELECT * FROM t_movie_ticket where id=#{id}")
    Movie findById(Long id);

    @Insert({"insert into t_movie_ticket (name,price,start_time,end_time,stock) values (#{name}, #{price}, #{start_time}, #{end_time}, #{stock})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(Movie movie);

    @Select("select id from t_movie_ticket")
    List<Long> findAll();

    @Insert({"insert into t_user_ticket (user_id,movie_ticket_id,status) values (#{user_id}, #{movie_ticket_id}, #{status})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addU(UserTicket userTicket);

    @Select("SELECT * FROM t_user_ticket where movie_ticket_id =#{id}")
    UserTicket findByIdU(Long id);

    @Update("update t_user_ticket set user_id=#{user_id},movie_ticket_id=#{movie_ticket_id},status=#{status} where movie_ticket_id=#{id}")
    void update(UserTicket userTicket);
}