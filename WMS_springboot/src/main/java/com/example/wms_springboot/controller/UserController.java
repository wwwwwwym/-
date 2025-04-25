package com.example.wms_springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResponseResult;
import com.example.wms_springboot.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.registry.QueryManager;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/allUserData")
    public ResponseResult getUserData(){

        return ResponseResult.success(userService.findAllUser());

    }

    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody User user)
    {
        return ResponseResult.success(userService.userRegister(user));
    }

    //重置密码
    @PutMapping("/forgetPass")
    public ResponseResult forgetPass(@RequestBody User user)
    {
        return ResponseResult.success(userService.forgetPass(user));
    }

    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody User user)
    {
        return ResponseResult.success(userService.userLogin(user));
    }

    //修改
    @PutMapping("/update")
    public ResponseResult updateUser(@RequestBody User user)
    {
        return ResponseResult.success(userService.updateUser(user));
    }

    //删除
    @DeleteMapping("/delete/{userid}")
    public ResponseResult deleteUser(@PathVariable Integer userid)
    {
        return ResponseResult.success(userService.deleteUser(userid));
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public ResponseResult deleteUserBatch(@RequestBody List<Integer> userids)
    {
        return ResponseResult.success(userService.deleteUserBatch(userids));
    }

//    分页模糊查询
    @GetMapping("/selectByPage")
    public ResponseResult selectByPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String username,
                                       @RequestParam Integer userid){
        return null;
    }



}


