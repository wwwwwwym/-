package com.example.wms_springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.stockInfo;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IRecordInService;
import com.example.wms_springboot.service.IStockInfoService;
import com.example.wms_springboot.utils.ResponseResult;
import com.example.wms_springboot.utils.ResultCode;
import com.example.wms_springboot.utils.TokenUtils;
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
@RequestMapping("/stock")
public class StockInfoController {
    @Autowired
    private IStockInfoService stockInfoService;

    @GetMapping("/allStockInfoData")
    public ResponseResult getDate(){

        List<stockInfo> all=stockInfoService.findAllstockInfo();
        return ResponseResult.success(all);
//        return ResponseResult.success(recordInService.findAllRecordIn());

    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult updateRecord(@RequestBody stockInfo stock)
    {
        return ResponseResult.success(stockInfoService.updateById(stock));
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public ResponseResult addRecord(@RequestBody stockInfo stock)
    {
        return ResponseResult.success(stockInfoService.save(stock));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{stockid}")
    public ResponseResult deleteUser(@PathVariable Integer stockid)
    {
        return ResponseResult.success(stockInfoService.removeById(stockid));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public ResponseResult deleteUserBatch(@RequestBody List<Integer> stockid)
    {
        return ResponseResult.success(stockInfoService.removeByIds(stockid));
    }

    /**
     * 分页模糊查询
     */
    @GetMapping("/selectByPage")
    public ResponseResult selectByPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String pname,
                                       @RequestParam String deposity){
        QueryWrapper<stockInfo> queryWrapper = new QueryWrapper<stockInfo>().orderByDesc("stockid");


        // 仅当 pname 非空且非空字符串时，添加模糊匹配条件
        if (StringUtils.hasText(pname)) {
            queryWrapper.like("pname", pname.trim());
        }
        if (StringUtils.hasText(deposity)) {
            queryWrapper.like("deposity", deposity.trim());
        }
        IPage<stockInfo> page =new Page<>(pageNum,pageSize);
        return ResponseResult.success(stockInfoService.page(page,queryWrapper));
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String pname,
                           @RequestParam(required = false) String deposity,
                           @RequestParam(required = false) String stockids,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        QueryWrapper<stockInfo> queryWrapper = new QueryWrapper<stockInfo>();

        List<stockInfo> list;
        if(StrUtil.isNotBlank(stockids)){
            List<Integer> stockidsArr1 = Arrays.stream(stockids.split(",")).map(Integer::valueOf).collect(Collectors.toList());//Stream流，把字符串list转成Integer的list
            queryWrapper.in("stockid",stockidsArr1);
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
        list = stockInfoService.list(queryWrapper);//查询出当前deposityRecord表的所有数据
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
        List<stockInfo> recordList = reader.readAll(stockInfo.class);

        boolean saveBatch;
        //写入数据到数据库
        try {
            saveBatch = stockInfoService.saveBatch(recordList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error("批量导入数据出错");
        }
        return ResponseResult.success(saveBatch);
    }
}
