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

    @Select("select * from user where username=#{username}")
    User findByName(@Param("username") String username);//把这行的name传给上一行

    @Select("select * from user where userid=#{userid}")
    User findById(@Param("userid") Integer userid);//把这行的name传给上一行

    @Select("select password from user where userid=#{userid}")
    String findPasswordById(@Param("userid") Integer userid);//把这行的name传给上一行

    @Insert("insert into user(userid,username,password,time) values (#{userid},#{username},#{password},CURRENT_TIMESTAMP)")
    Integer insertData(User user);
}
