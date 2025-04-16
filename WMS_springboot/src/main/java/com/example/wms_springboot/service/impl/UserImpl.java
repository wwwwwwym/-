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
        Integer userId = user.getUserid();

        User dbUser = userDao.findById(userId);
        if(ObjectUtil.isNotEmpty(dbUser))//用户名已存在抛出异常
        {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        else{//把注册信息写到表里
            userDao.insertData(user);

        }
        return user;
    }

    @Override
    public User userLogin(User user) {
        //用户校验,密码账号对应
        Integer userid = user.getUserid();
        String password = user.getPassword();
        User dbUser = userDao.findById(userid);
        String dbPassword = userDao.findPasswordById(userid);
        if(ObjectUtil.isEmpty(dbUser))//用户不存在抛出异常
        {
            throw new CustomException(ResultCode.USER_NOT_EXITS_ERROR);
        }
        else if(ObjectUtil.notEqual(password,dbPassword))
        {//账号或密码错误
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROE);
        }
        return user;
    }


}
