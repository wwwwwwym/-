package com.example.wms_springboot.controller;


import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody User user)
    {
        return ResponseResult.success(userService.userLogin(user));
    }


}


