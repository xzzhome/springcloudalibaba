package com.xzz.mapper;

import com.xzz.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT * FROM t_user where id=#{id}")
    User findById(Long id);

    @Insert({"insert into t_user (username,balance) values (#{username}, #{balance})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(User user);

    @Update("update t_user set balance =#{balance} where id=#{id}")
    void update(User user);
}