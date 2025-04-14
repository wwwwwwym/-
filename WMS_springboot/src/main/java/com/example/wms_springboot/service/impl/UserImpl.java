package com.example.wms_springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.wms_springboot.dao.UserDao;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public User userRegister(User user) {
        //用户名校验
        String userName = user.getUsername();

        User dbUser = userDao.findByName(userName);
        if(ObjectUtil.isNotEmpty(dbUser))//用户名已存在抛出异常
        {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        else{//把注册信息写到表里
            userDao.insertData(user);

        }


        return user;
    }
}
