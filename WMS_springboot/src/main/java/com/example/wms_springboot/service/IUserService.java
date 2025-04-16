package com.example.wms_springboot.service;

import com.example.wms_springboot.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAllUser();

    User userRegister(User user);
    User userLogin(User user);
}
