package com.example.wms_springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wms_springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserDao extends BaseMapper<User> {



//    List<User> findAllUser();
//
//    @Select("select * from user where username=#{username}")
//    User findByName(@Param("username") String username);//把这行的name传给上一行
//
//    @Select("select * from user where userid=#{userid}")
//    User findById(@Param("userid") Integer userid);//把这行的name传给上一行
//
//    @Select("select password from user where userid=#{userid}")
//    String findPasswordById(@Param("userid") Integer userid);//把这行的name传给上一行
//
    //新增记录
//    @Insert("insert into user(userid,username,password,time,deposity,telephone) values (#{userid},#{username},#{password},CURRENT_TIMESTAMP,#{deposity},#{telephone})")
//    Integer insertData(User user);
//
//    //更新记录
//    @Update("update user set telephone=#{telephone},deposity=#{deposity} where userid=#{userid}")
//    int modifyUserInfoByUserId(User user);
//
//    @Delete("delete from user where userid=#{userid}")
//    int removeUserByUserId(Integer userid); //参数为一个时，可以不加@param注解
//
////    批量删除
////    @Delete("delete from user where userid in   ")
////    int removeUserBatchByUserId(List<Integer> userids);
}
