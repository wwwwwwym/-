package com.example.wms_springboot.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.RecordOutDao;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.recordOut;
import com.example.wms_springboot.service.IRecordOutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordOutImpl extends ServiceImpl<RecordOutDao, recordOut> implements IRecordOutService {


    @Resource
    private RecordOutDao recordOutDao;
    public List<recordOut> findAllRecordOut(){

        return recordOutDao.selectList(new QueryWrapper<recordOut>()
                .orderByDesc("record_id"));
    }
}
