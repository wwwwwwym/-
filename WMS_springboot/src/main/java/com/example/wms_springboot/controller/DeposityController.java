package com.example.wms_springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.Deposity;
import com.example.wms_springboot.service.IDeposityService;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/deposity")
public class DeposityController {
    @Autowired
    private IDeposityService deposityService;

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult updateRecord(@RequestBody Deposity deposity)
    {
        return ResponseResult.success(deposityService.updateById(deposity));
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public ResponseResult addRecord(@RequestBody Deposity deposity)
    {
        return ResponseResult.success(deposityService.save(deposity));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteUser(@PathVariable Integer id)

    {
        return ResponseResult.success(deposityService.removeById(id));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public ResponseResult deleteUserBatch(@RequestBody List<Integer> ids)
    {
        return ResponseResult.success(deposityService.removeByIds(ids));
    }

    /**
     * 分页模糊查询
     */
    @GetMapping("/selectByPage")
    public ResponseResult selectByPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String dname,
                                       @RequestParam String address){
        QueryWrapper<Deposity> queryWrapper = new QueryWrapper<Deposity>();

        queryWrapper.like(StrUtil.isNotBlank(dname), "dname", dname);
        queryWrapper.like(StrUtil.isNotBlank(address), "address", address);
        IPage<Deposity> page =new Page<>(pageNum,pageSize);
        return ResponseResult.success(deposityService.page(page,queryWrapper));
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String dname,
                           @RequestParam(required = false) String address,
                           @RequestParam(required = false) String ids,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        QueryWrapper<Deposity> queryWrapper = new QueryWrapper<Deposity>();

        List<Deposity> list;
        if(StrUtil.isNotBlank(ids)){
            List<Integer> idsArr1 = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());//Stream流，把字符串list转成Integer的list
            queryWrapper.in("id",idsArr1);
        }else {

            queryWrapper.like(StrUtil.isNotBlank(dname), "dname", dname);
            queryWrapper.like(StrUtil.isNotBlank(address), "address", address);
        }
        //全部导出
        list = deposityService.list(queryWrapper);//查询出当前deposityRecord表的所有数据
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("仓库信息表","UTF-8") + ".xlsx");

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
        List<Deposity> deposityList = reader.readAll(Deposity.class);

        boolean saveBatch;
        //写入数据到数据库
        try {
            saveBatch = deposityService.saveBatch(deposityList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error("批量导入数据出错");
        }
        return ResponseResult.success(saveBatch);
    }
}
