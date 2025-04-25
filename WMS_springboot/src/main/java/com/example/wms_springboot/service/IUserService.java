package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.UserDao;
import com.example.wms_springboot.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    List<User> findAllUser();

    User userRegister(User user);
    User userLogin(User user);
    User selectById(Integer userid);
//    User findById(Integer userid);
//
//    Integer updateUser(User user);
//
//    Integer deleteUser(Integer userid);
//
//    Integer deleteUserBatch(List<Integer> userids);

    Integer forgetPass(User user);
}
