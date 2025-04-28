package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.stockInfo;

import java.util.List;


public interface IStockInfoService extends IService<stockInfo> {
    List<stockInfo> findAllstockInfo();
}
