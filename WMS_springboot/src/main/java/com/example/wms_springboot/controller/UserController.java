package com.example.wms_springboot.controller;



import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.config.AuthAccess;
import com.example.wms_springboot.config.Logs;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResponseResult;
import com.example.wms_springboot.utils.ResultCode;
import com.example.wms_springboot.utils.TokenUtils;
import com.example.wms_springboot.utils.logType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询所有
     */
    @AuthAccess
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
    @Logs(operation = "人员管理",type = logType.REGISTER)
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
    @Logs(operation = "人员管理",type = logType.LOGIN)
    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody User user)
    {
        return ResponseResult.success(userService.userLogin(user));
    }

    /**
     * 修改
     */
    @Logs(operation = "人员管理",type = logType.UPDATE)
    @PutMapping("/update")
    public ResponseResult updateUser(@RequestBody User user)
    {
        return ResponseResult.success(userService.updateById(user));
    }
    /**
     * 新增
     */
    @Logs(operation = "人员管理",type = logType.ADD)
    @PostMapping("/add")
    public ResponseResult addUser(@RequestBody User user)
    {
        return ResponseResult.success(userService.userRegister(user));
    }

    /**
     * 删除
     */
    @Logs(operation = "人员管理",type = logType.DELETE)
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
                           @RequestParam(required = false) String userids,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();

        List<User> list;
        if(StrUtil.isNotBlank(userids)){
            List<Integer> useridsArr1 = Arrays.stream(userids.split(",")).map(Integer::valueOf).collect(Collectors.toList());//Stream流，把字符串list转成Integer的list
            queryWrapper.in("userid",useridsArr1);
        }else {

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

    /**
     * 数据批量导入，与文件上传类似
     */
    @PostMapping("/import")
    public ResponseResult importData(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<User> userList = reader.readAll(User.class);

        boolean saveBatch;
        //写入数据到数据库
        try {
             saveBatch = userService.saveBatch(userList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error("批量导入数据出错");
        }
        return ResponseResult.success(saveBatch);
    }

}


