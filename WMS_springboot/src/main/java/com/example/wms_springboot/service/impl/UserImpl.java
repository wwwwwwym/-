package com.example.wms_springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.UserDao;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.MD5;
import com.example.wms_springboot.utils.ResultCode;
import com.example.wms_springboot.utils.TokenUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
//import javax.xml.rpc.ServiceException;
//import java.io.Serializable;
//import java.util.Collection;
import java.util.List;

@Service
public class UserImpl extends ServiceImpl<UserDao,User> implements IUserService {

    @Autowired
    @Resource
    private UserDao userDao;

    @Override
    public List<User> findAllUser() {

        return userDao.selectList(new QueryWrapper<User>().eq("roleid",1).orderByDesc("userid"));
    }

    public User selectById(Integer userid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        return getOne(queryWrapper);

    }
/**
 * 注册
 */
    @Override
    public User userRegister(User user) {
        //用户名校验
        Integer userId = user.getUserid();
        User dbUser = selectById(userId);
        String newPass = new MD5().getMD5ofStr(user.getPassword());
        if(dbUser==null){
            throw new CustomException(ResultCode.ID_ERROR);
        }
        if (dbUser.getIsregister())//用户已注册抛出异常
        {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        } else {//把注册信息写到表里
            user.setPassword(newPass);
            user.setIsregister(true);
        }
        return user;
    }
/**
 * 登录
 */
    @Override
    public User userLogin(User user) {
        //用户校验,密码账号对应
        Integer userid = user.getUserid();
//        String password = user.getPassword();
        User dbUser = selectById(userid);
        if (!dbUser.getIsregister())//用户未注册抛出异常
        {
            throw new CustomException(ResultCode.USER_NOT_EXITS_ERROR);
        }
        String newPass = new MD5().getMD5ofStr(user.getPassword());
        String dbPassword = dbUser.getPassword();
        if (ObjectUtil.notEqual(dbPassword, newPass)) {//账号或密码错误
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROE);
        }

        //生成token
        String token = TokenUtils.createToken(dbUser.getUserid().toString(), dbUser.getPassword());
        dbUser.setToken(token);

        return dbUser;
    }
/**
 * 忘记密码
 */
    @Override
    public Integer forgetPass(User user) {
        User dbUser = selectById(user.getUserid());
        if(ObjectUtil.isEmpty(user.getUserid()) || ObjectUtil.isEmpty(user.getTelephone()))
        {
            throw new CustomException(ResultCode.PARAM_LOST);
        }//输入不合法，有空值
        if(dbUser==null)
        {
            throw new CustomException(ResultCode.USER_NOT_EXITS_ERROR);
        }//用户不存在
        if(!user.getTelephone().equals(dbUser.getTelephone()))
        {
            throw new CustomException(ResultCode.CHECK_ERROR);
        }//电话不匹配
        String newPass = new MD5().getMD5ofStr("123456");
        dbUser.setPassword(newPass);
        updateById(dbUser);
        return null;

    }
}
