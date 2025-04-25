package com.example.wms_springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.User;

import com.example.wms_springboot.service.IUserService;

import com.example.wms_springboot.utils.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询所有
     */
    @GetMapping("/allUserData")
    public ResponseResult getUserData(){

        return ResponseResult.success(userService.findAllUser());

    }
/**
 * 通过id查询
 */
    @GetMapping("/selectById/{userid}")
    public ResponseResult selectById(Integer userid){

        return ResponseResult.success(userService.selectById(userid));

    }
/**
 * 注册
 */
    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody User user)
    {
        return ResponseResult.success(userService.userRegister(user));
    }

    /**
     * 重置密码
     */
    @PutMapping("/forgetPass")
    public ResponseResult forgetPass(@RequestBody User user)
    {
        return ResponseResult.success(userService.forgetPass(user));
    }
/**
 * 登录
 */
    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody User user)
    {
        return ResponseResult.success(userService.userLogin(user));
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult updateUser(@RequestBody User user)
    {
        return ResponseResult.success(userService.updateById(user));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{userid}")
    public ResponseResult deleteUser(@PathVariable Integer userid)
    {
        return ResponseResult.success(userService.removeById(userid));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public ResponseResult deleteUserBatch(@RequestBody List<Integer> userids)
    {
        return ResponseResult.success(userService.removeByIds(userids));
    }

/**
 * 分页模糊查询
 */
    @GetMapping("/selectByPage")
    public ResponseResult selectByPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam Integer userid,
                                       @RequestParam String username,
                                       @RequestParam String deposity){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("userid");

        // 仅当 userid 不为空时，添加精确匹配条件
        if (userid!=null) {
            queryWrapper.eq("userid", userid);
        }

        // 仅当 username 非空且非空字符串时，添加模糊匹配条件
        if (StringUtils.hasText(username)) {
            queryWrapper.like("username", username.trim());
        }
        if (StringUtils.hasText(deposity)) {
            queryWrapper.like("deposity", deposity.trim());
        }
        IPage<User> page =new Page<>(pageNum,pageSize);
        return ResponseResult.success(userService.page(page,queryWrapper));
    }

}


