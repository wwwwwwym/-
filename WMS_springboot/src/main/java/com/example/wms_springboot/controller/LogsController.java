package com.example.wms_springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.logs;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.service.ILogsService;
import com.example.wms_springboot.service.IRecordInService;
import com.example.wms_springboot.service.IUserService;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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