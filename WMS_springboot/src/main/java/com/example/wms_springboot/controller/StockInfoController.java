package com.example.wms_springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
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

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockInfoController {
    @Autowired
    @Resource
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
        stock.setStocktime(DateUtil.today());
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
                                       @RequestParam String deposity,
                                       @RequestParam String stocktime){
        QueryWrapper<stockInfo> queryWrapper = new QueryWrapper<stockInfo>().orderByDesc("stockid");


        queryWrapper.like(StrUtil.isNotBlank(pname), "pname", pname);
        queryWrapper.like(StrUtil.isNotBlank(deposity), "deposity", deposity);
        queryWrapper.like(StrUtil.isNotBlank(stocktime), "stocktime", stocktime);
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

            queryWrapper.like(StrUtil.isNotBlank(pname), "pname", pname);
            queryWrapper.like(StrUtil.isNotBlank(deposity), "deposity", deposity);
        }
        //全部导出
        list = stockInfoService.list(queryWrapper);//查询出当前deposityRecord表的所有数据
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("库存信息表","UTF-8") + ".xlsx");

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
/**
 * 统计图数据
 */
    @GetMapping("/charts")
    public ResponseResult charts(){
        //包装折线图的数据
        List<stockInfo> list = stockInfoService.list();//数据多要换一种方法
        Set<String> dates = list.stream().map(stockInfo::getStocktime).collect(Collectors.toSet());//set是无序的,用list排序
        List<String> dateList = CollUtil.newArrayList(dates);
        dateList.sort(Comparator.naturalOrder());
        List<Dict> linelist = new ArrayList<>();
        for (String date :dateList){
            //统计当日所有金额总数
            BigDecimal sum = list.stream().filter(stockInfo -> stockInfo.getStocktime().equals(date))
                    .map(stockInfo::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Dict dict =Dict.create();
            Dict line = dict.set("date", date).set("value", sum);
            linelist.add(line);
        }
        return ResponseResult.success(linelist);

    }
}
