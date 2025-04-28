package com.example.wms_springboot.controller;



import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.service.IRecordInService;
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

@CrossOrigin
@RestController
@RequestMapping("/recordIn")
public class RecordInController {

    @Autowired
    private IRecordInService recordInService;

    @GetMapping("/allRecordInData")
    public ResponseResult getDate(){

        List <recordIn> all=recordInService.findAllRecordIn();
        return ResponseResult.success(all);
//        return ResponseResult.success(recordInService.findAllRecordIn());

    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult updateRecord(@RequestBody recordIn record)
    {
        return ResponseResult.success(recordInService.updateById(record));
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public ResponseResult addRecord(@RequestBody recordIn record)
    {
        return ResponseResult.success(recordInService.save(record));
    }

//    /**
//     * 删除
//     */
//    @DeleteMapping("/delete/{recordId}")
//    public ResponseResult deleteUser(@PathVariable Integer recordId)
//
//    {
//        return ResponseResult.success(recordInService.removeById(recordId));
//    }
//
//    /**
//     * 批量删除
//     */
//    @DeleteMapping("/delete/batch")
//    public ResponseResult deleteUserBatch(@RequestBody List<Integer> recordIds)
//    {
//        return ResponseResult.success(recordInService.removeByIds(recordIds));
//    }

    /**
     * 分页模糊查询
     */
    @GetMapping("/selectByPage")
    public ResponseResult selectByPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String pname,
                                       @RequestParam String deposity){
        QueryWrapper<recordIn> queryWrapper = new QueryWrapper<recordIn>().orderByDesc("record_id");


        // 仅当 pname 非空且非空字符串时，添加模糊匹配条件
        if (StringUtils.hasText(pname)) {
            queryWrapper.like("pname", pname.trim());
        }
        if (StringUtils.hasText(deposity)) {
            queryWrapper.like("deposity", deposity.trim());
        }
        IPage<recordIn> page =new Page<>(pageNum,pageSize);
        return ResponseResult.success(recordInService.page(page,queryWrapper));
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String pname,
                           @RequestParam(required = false) String deposity,
                           @RequestParam(name = "record_ids", required = false) String recordIds,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        QueryWrapper<recordIn> queryWrapper = new QueryWrapper<recordIn>();

        List<recordIn> list;
        if(StrUtil.isNotBlank(recordIds)){
            List<Integer> recordIdsArr1 = Arrays.stream(recordIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());//Stream流，把字符串list转成Integer的list
            queryWrapper.in("record_id",recordIdsArr1);
        }else {

            // 仅当 pname 非空且非空字符串时，添加模糊匹配条件
            if (StringUtils.hasText(pname)) {
                queryWrapper.like("pname", pname.trim());
            }
            if (StringUtils.hasText(deposity)) {
                queryWrapper.like("deposity", deposity.trim());
            }
        }
        //全部导出
        list = recordInService.list(queryWrapper);//查询出当前deposityRecord表的所有数据
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("入库信息表","UTF-8") + ".xlsx");

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
        List<recordIn> recordList = reader.readAll(recordIn.class);

        boolean saveBatch;
        //写入数据到数据库
        try {
            saveBatch = recordInService.saveBatch(recordList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error("批量导入数据出错");
        }
        return ResponseResult.success(saveBatch);
    }

}
