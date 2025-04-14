package com.example.wms_springboot.dao;

import com.example.wms_springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {



    List<User> findAllUser();

    @Select("select * from user where username=#{userName}")
    User findByName(@Param("userName") String userName);//把这行的name传给上一行

    @Insert("insert into users(username,password,time) values (#{username},#{password},CURRENT_TIMESTAMP)")
    User insertData(User user);
}
