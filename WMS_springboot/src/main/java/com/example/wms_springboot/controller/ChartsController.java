package com.example.wms_springboot.controller;

import cn.hutool.core.lang.Dict;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.stockInfo;
import com.example.wms_springboot.service.IRecordInService;
import com.example.wms_springboot.service.IStockInfoService;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ChartsController {
    @Autowired
    private IRecordInService recordInService;
    @Autowired
    private IStockInfoService stockInfoService;

    @GetMapping("/linechartsIn")
    public ResponseResult linechartsIn() {
        // 获取所有记录
        List<recordIn> list = recordInService.list();
        // 获取所有日期（去重并排序）
        List<String> dates = list.stream()
                .filter(record -> "已完成".equals(record.getState()))
                .map(recordIn::getApplyTime)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        // 定义仓库列表（与前端对应）
        List<String> warehouses = Arrays.asList("1号仓库", "2号仓库", "3号仓库", "4号仓库", "5号仓库");
        // 构建数据结构：日期为键，每个仓库的金额为值
        Map<String, Map<String, BigDecimal>> dateWarehouseMap = new LinkedHashMap<>();
        // 初始化数据结构
        for (String date : dates) {
            Map<String, BigDecimal> warehouseValues = new HashMap<>();
            for (String warehouse : warehouses) {
                warehouseValues.put(warehouse, BigDecimal.ZERO);
            }
            dateWarehouseMap.put(date, warehouseValues);
        }
        // 填充出库数据
        for (recordIn record : list) {
            if ("已完成".equals(record.getState())&&("出货单".equals(record.getType())||"调货单".equals(record.getType())) ) {
                String date = record.getApplyTime();
                String warehouse = record.getDeposityOld(); // 假设记录中有仓库字段
                BigDecimal totalValue = record.getPrice().multiply((BigDecimal.valueOf(record.getQuantity())));

                if (dateWarehouseMap.containsKey(date) && warehouse != null) {
                    Map<String, BigDecimal> warehouseValues = dateWarehouseMap.get(date);
                    BigDecimal currentValue = warehouseValues.getOrDefault(warehouse, BigDecimal.ZERO);
                    warehouseValues.put(warehouse, currentValue.add(totalValue));
                }
            }
        }
        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        for (String date : dates) {
            Map<String, Object> dateData = new HashMap<>();
            dateData.put("date", date);
            Map<String, BigDecimal> warehouseValues = dateWarehouseMap.get(date);
            for (String warehouse : warehouses) {
                // 生成字段名：warehouse1, warehouse2...
                String field = "warehouse" + (warehouses.indexOf(warehouse) + 1);
                dateData.put(field, warehouseValues.get(warehouse));
            }
            result.add(dateData);
        }
        return ResponseResult.success(result);
    }


    @GetMapping("/linechartsOut")
    public ResponseResult linechartsOut() {
        // 获取所有记录
        List<recordIn> list = recordInService.list();
        // 获取所有日期（去重并排序）
        List<String> dates = list.stream()
                .filter(record -> "已完成".equals(record.getState()))
                .map(recordIn::getApplyTime)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        // 定义仓库列表（与前端对应）
        List<String> warehouses = Arrays.asList("1号仓库", "2号仓库", "3号仓库", "4号仓库", "5号仓库");
        // 构建数据结构：日期为键，每个仓库的金额为值
        Map<String, Map<String, BigDecimal>> dateWarehouseMap = new LinkedHashMap<>();
        // 初始化数据结构
        for (String date : dates) {
            Map<String, BigDecimal> warehouseValues = new HashMap<>();
            for (String warehouse : warehouses) {
                warehouseValues.put(warehouse, BigDecimal.ZERO);
            }
            dateWarehouseMap.put(date, warehouseValues);
        }
        // 填充入库数据
        for (recordIn record : list) {
            if ("已完成".equals(record.getState())&&("进货单".equals(record.getType())||"调货单".equals(record.getType())) ) {
                String date = record.getApplyTime();
                String warehouse = record.getDeposityNew(); // 假设记录中有仓库字段
                BigDecimal totalValue = record.getPrice().multiply((BigDecimal.valueOf(record.getQuantity())));

                if (dateWarehouseMap.containsKey(date) && warehouse != null) {
                    Map<String, BigDecimal> warehouseValues = dateWarehouseMap.get(date);
                    BigDecimal currentValue = warehouseValues.getOrDefault(warehouse, BigDecimal.ZERO);
                    warehouseValues.put(warehouse, currentValue.add(totalValue));
                }
            }
        }
        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        for (String date : dates) {
            Map<String, Object> dateData = new HashMap<>();
            dateData.put("date", date);
            Map<String, BigDecimal> warehouseValues = dateWarehouseMap.get(date);
            for (String warehouse : warehouses) {
                // 生成字段名：warehouse1, warehouse2...
                String field = "warehouse" + (warehouses.indexOf(warehouse) + 1);
                dateData.put(field, warehouseValues.get(warehouse));
            }
            result.add(dateData);
        }
        return ResponseResult.success(result);
    }

    @GetMapping("/piecharts")
    public ResponseResult piecharts(){
        List<recordIn> list = recordInService.list();
        Set<String> products = list.stream().map(recordIn :: getPname).collect(Collectors.toSet());
        List<Dict> pielist1 = new ArrayList<>();
        for (String product :products){
            Integer quantity= list.stream().filter(recordIn -> recordIn.getPname().equals(product) &&"已完成".equals(recordIn.getState()) &&"出货单".equals(recordIn.getType()))
                    .map(recordIn -> recordIn.getQuantity())
                    .reduce(0,Integer::sum);
//            BigDecimal price =list.stream().filter(recordIn -> recordIn.getPname().equals(product) && "已完成".equals(recordIn.getState())&& "出货单".equals(recordIn.getType()))
//                    .map(recordIn->recordIn.getPrice().multiply(BigDecimal.valueOf(recordIn.getQuantity())))
//                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

            Dict dict =Dict.create();
            System.out.println("pielist = " + pielist1);
            Dict pie1 =dict.set("name",product).set("value",quantity);
            pielist1.add(pie1);
        }

        //饼图2
        List<recordIn> list2 = recordInService.list();
        Set<String> products2 = list2.stream().map(recordIn :: getPname).collect(Collectors.toSet());
        List<Dict> pielist2 = new ArrayList<>();
        for (String product :products2){
//            BigDecimal price =list2.stream().filter(recordIn -> recordIn.getPname().equals(product) && "已完成".equals(recordIn.getState())&&"进货单".equals(recordIn.getType()))
//                    .map(recordIn->recordIn.getPrice().multiply(BigDecimal.valueOf(recordIn.getQuantity())))
//                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Integer quantity= list2.stream().filter(recordIn -> recordIn.getPname().equals(product) &&"已完成".equals(recordIn.getState()) &&"进货单".equals(recordIn.getType()))
                    .map(recordIn -> recordIn.getQuantity())
                    .reduce(0,Integer::sum);

            Dict dict =Dict.create();
            Dict pie2 =dict.set("name",product).set("value",quantity);
            pielist2.add(pie2);
        }
        Dict res=Dict.create().set("pie1",pielist1).set("pie2",pielist2);
        return ResponseResult.success(res);
    }



@GetMapping("/storecharts")
    public ResponseResult barcharts(){
        List<stockInfo> list = stockInfoService.list();
        Set<String> products = list.stream().map(stockInfo :: getPname).collect(Collectors.toSet());
        List<Dict> barlist = new ArrayList<>();
        for (String product :products){
            Integer quantity =list.stream().filter(stockInfo -> stockInfo.getPname().equals(product)).map(stockInfo::getQuantity)
                    .reduce(0, Integer::sum);
            BigDecimal price =list.stream().filter(stockInfo -> stockInfo.getPname().equals(product))
                    .map(stockInfo->stockInfo.getPrice().multiply(BigDecimal.valueOf(stockInfo.getQuantity())))
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Dict dict =Dict.create();
            System.out.println("barlist = " + barlist);
            Dict bar =dict.set("name",product).set("value",quantity).set("price",price);
            barlist.add(bar);
        }
        Dict res=Dict.create().set("bar",barlist);
        return ResponseResult.success(res);
}

}
