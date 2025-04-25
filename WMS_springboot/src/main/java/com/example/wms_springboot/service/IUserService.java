package com.example.wms_springboot.service;

import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.utils.ResponseResult;

import java.util.List;

public interface IUserService {
    List<User> findAllUser();

    User userRegister(User user);
    User userLogin(User user);
    User findById(Integer userid);

    Integer updateUser(User user);

    Integer deleteUser(Integer userid);

    Integer deleteUserBatch(List<Integer> userids);

    Integer forgetPass(User user);
}
