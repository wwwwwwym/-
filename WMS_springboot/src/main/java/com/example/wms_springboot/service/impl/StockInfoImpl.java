package com.example.wms_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.StockInfoDao;
import com.example.wms_springboot.entity.recordIn;
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
    public stockInfo findByOld(String pname,String deposityOld){

        QueryWrapper<stockInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pname",pname).eq("deposity",deposityOld);
        return getOne(queryWrapper);
    }
    public stockInfo findByNew(String pname,String deposityNew){

        QueryWrapper<stockInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pname",pname).eq("deposity",deposityNew);
        return getOne(queryWrapper);
    }
}

