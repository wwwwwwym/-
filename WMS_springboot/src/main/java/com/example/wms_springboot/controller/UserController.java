package com.example.wms_springboot.controller;



import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResponseResult;
import com.example.wms_springboot.utils.ResultCode;
import com.example.wms_springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


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
     * 新增
     */
    @PostMapping("/add")
    public ResponseResult addUser(@RequestBody User user)
    {
        return ResponseResult.success(userService.userRegister(user));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{userid}")
    public ResponseResult deleteUser(@PathVariable Integer userid)

    {
        User currentUser = TokenUtils.getCurrentUser();
        if(userid.equals(currentUser.getUserid()))
        {
            throw new CustomException(ResultCode.DELETE_ERROR);
        }
        return ResponseResult.success(userService.removeById(userid));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public ResponseResult deleteUserBatch(@RequestBody List<Integer> userids)
    {
        User currentUser = TokenUtils.getCurrentUser();
        if(currentUser!=null && currentUser.getUserid()!= null && userids.contains(currentUser.getUserid()))
        {
            throw new CustomException(ResultCode.DELETE_ERROR);
        }
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
            String id=String.valueOf(userid);
            queryWrapper.like("userid", id.trim());
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

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String username,
                           @RequestParam(required = false) String deposity,
                           @RequestParam(required = false) Integer userid,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        List<User> list=new ArrayList<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        // 仅当 userid 不为空时，添加精确匹配条件
        if (userid!=null) {
            String id=String.valueOf(userid);
            queryWrapper.like("userid", id.trim());
        }

        // 仅当 username 非空且非空字符串时，添加模糊匹配条件
        if (StringUtils.hasText(username)) {
            queryWrapper.like("username", username.trim());
        }
        if (StringUtils.hasText(deposity)) {
            queryWrapper.like("deposity", deposity.trim());
        }

        //全部导出
        list = userService.list(queryWrapper);//查询出当前User表的所有数据

        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("用户信息表","UTF-8") + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);
        writer.close();
        outputStream.flush();
        outputStream.close();//关闭输入流

    }

}


