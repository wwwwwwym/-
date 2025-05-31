package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.stockInfo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface IStockInfoService extends IService<stockInfo> {
    List<stockInfo> findAllstockInfo();
//    stockInfo findByStockid(Integer stockid);
    stockInfo findByOld(String pname,String deposityOld);
    stockInfo findByNew(String pname,String deposityNew);

}
