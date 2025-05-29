package com.example.wms_springboot.controller;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wms_springboot.config.Logs;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.stockInfo;
import com.example.wms_springboot.service.IRecordInService;
import com.example.wms_springboot.utils.ResponseResult;
import com.example.wms_springboot.utils.logType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/recordIn")
public class RecordInController {

    @Autowired
    private IRecordInService recordInService;

/**
 * 获取审核人为当前用户的未审核申请
 */
    @GetMapping("/allPendingData")
    public ResponseResult getPendingDate(){

        List <recordIn> all=recordInService.findUserRecordPending();
        return ResponseResult.success(all);
//        return ResponseResult.success(recordInService.findAllRecordIn());

    }

    /**
     * 获取审核人为当前用户的已完成申请
     */
    @GetMapping("/allCompletedData")
    public ResponseResult getCompletedDate(){

        List <recordIn> all=recordInService.findUserRecordCompleted();
        return ResponseResult.success(all);
    }


    /**
     * 获取申请人为当前用户的未审核申请
     */
    @GetMapping("/myPendingData")
    public ResponseResult getMyPendingDate(){

        List <recordIn> all=recordInService.findMyRecordPending();
        return ResponseResult.success(all);

    }
    /**
     * 获取申请人为当前用户的已完成申请
     */
    @GetMapping("/myCompletedData")
    public ResponseResult getMyCompletedDate(){

        List <recordIn> all=recordInService.findMyRecordCompleted();
        return ResponseResult.success(all);
    }

    /**
     * 修改
     */
    @Logs(operation = "货流管理",type = logType.UPDATE)
    @PutMapping("/update")
    public ResponseResult updateRecord(@RequestBody recordIn record)
    {
        return ResponseResult.success(recordInService.updateById(record));
    }
    /**
     * 新增
     */
    @Logs(operation = "货流管理",type = logType.ADD)
    @PostMapping("/add")
    public ResponseResult addRecord(@RequestBody recordIn record)
    {
        record.setApplyTime(DateUtil.today());
//        record.setReviewTime(DateUtil.today());
//        record.setNo(IdUtil.fastSimpleUUID());订单编号
        
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
                                       @RequestParam String deposityIn,
                                       @RequestParam String deposityOut,
                                       @RequestParam String type,
                                       @RequestParam String applyTime){
        QueryWrapper<recordIn> queryWrapper = new QueryWrapper<recordIn>().orderByDesc("record_id");

        queryWrapper.like(StrUtil.isNotBlank(pname), "pname", pname);
        queryWrapper.like(StrUtil.isNotBlank(deposityIn), "deposity_in", deposityIn);
        queryWrapper.like(StrUtil.isNotBlank(deposityOut), "deposity_out", deposityOut);
        queryWrapper.like(StrUtil.isNotBlank(type), "type", type);
        queryWrapper.like(StrUtil.isNotBlank(applyTime),"apply_time",applyTime);
        IPage<recordIn> page =new Page<>(pageNum,pageSize);
        return ResponseResult.success(recordInService.page(page,queryWrapper));
    }

    /**
     * 批量导出数据
     */
    @Logs(operation = "货流管理",type = logType.EXPORT)
    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String pname,
                           @RequestParam(required = false) String deposityIn,
                           @RequestParam(required = false) String deposityOut,
                           @RequestParam(required = false) String type,
                           @RequestParam(name = "record_ids", required = false) String recordIds,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        QueryWrapper<recordIn> queryWrapper = new QueryWrapper<recordIn>();

        List<recordIn> list;
        if(StrUtil.isNotBlank(recordIds)){
            List<Integer> recordIdsArr1 = Arrays.stream(recordIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());//Stream流，把字符串list转成Integer的list
            queryWrapper.in("record_id",recordIdsArr1);
        }else {

            queryWrapper.like(StrUtil.isNotBlank(pname), "pname", pname);
            queryWrapper.like(StrUtil.isNotBlank(deposityIn), "deposity_in", deposityIn);
            queryWrapper.like(StrUtil.isNotBlank(deposityOut), "deposity_out", deposityOut);
            queryWrapper.like(StrUtil.isNotBlank(type), "type", type);
        }
        //全部导出
        list = recordInService.list(queryWrapper);//查询出当前deposityRecord表的所有数据
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("货流信息表","UTF-8") + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);
        writer.close();
        outputStream.flush();
        outputStream.close();//关闭输入流
    }

    /**
     * 数据批量导入，与文件上传类似
     */
    @Logs(operation = "货流管理",type = logType.IMPORT)
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



    /**
     * 统计图数据
     */
    @GetMapping("/charts")
    public ResponseResult charts(){
        //包装折线图的数据
        List<recordIn> list = recordInService.list();//数据多要换一种方法
        Set<String> dates = list.stream().map(recordIn::getApplyTime).collect(Collectors.toSet());//set是无序的,用list排序
        List<String> dateList = CollUtil.newArrayList(dates);
        dateList.sort(Comparator.naturalOrder());
        List<Dict> linelist = new ArrayList<>();
        for (String date :dateList){
            //统计当日所有金额总数
            BigDecimal sum = list.stream().filter(recordIn -> recordIn.getApplyTime().equals(date))
                    .map(recordIn::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Dict dict =Dict.create();
            Dict line = dict.set("date", date).set("value", sum);
            linelist.add(line);
        }
        return ResponseResult.success(linelist);

    }

}
