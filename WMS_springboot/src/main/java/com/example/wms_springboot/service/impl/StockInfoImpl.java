package com.example.wms_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.StockInfoDao;
import com.example.wms_springboot.entity.stockInfo;
import com.example.wms_springboot.service.IStockInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StockInfoImpl extends ServiceImpl<StockInfoDao, stockInfo> implements IStockInfoService {
    @Resource
    private StockInfoDao stockInfoDao;
    public List<stockInfo> findAllstockInfo(){

        return stockInfoDao.selectList(new QueryWrapper<stockInfo>()
                .orderByDesc("stockid"));
    }
}

