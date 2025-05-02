package com.example.wms_springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.logs;
import com.example.wms_springboot.service.ILogsService;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/logs")
public class LogsController {
    @Autowired
    private ILogsService logsService;
    @Autowired
    private IUserService userService;

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteUser(@PathVariable Integer id) {
        return ResponseResult.success(logsService.removeById(id));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public ResponseResult deleteUserBatch(@RequestBody List<Integer> ids) {
        return ResponseResult.success(logsService.removeByIds(ids));
    }

    /**
     * 分页模糊查询
     */
    @GetMapping("/selectByPage")
    public ResponseResult selectByPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String operation,
                                       @RequestParam String type,
                                       @RequestParam String user) {
        QueryWrapper<logs> queryWrapper = new QueryWrapper<logs>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(operation), "operation", operation);
        queryWrapper.like(StrUtil.isNotBlank(type), "type", type);
        queryWrapper.like(StrUtil.isNotBlank(user), "user", user);
        IPage<logs> page = new Page<>(pageNum, pageSize);
        return ResponseResult.success(logsService.page(page, queryWrapper));
    }

}